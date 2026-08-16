# Expense Splitter

多人分帳與結算的核心邏輯練習。純 Java、無框架、記憶體內運算。

**規模**：12 個類別、約 400 行、無持久化、無自動化測試（`TestExpenseSplitter` 是 `main()` 手動驗證）。

寫這個專案的目的不是做出可上線的產品，而是把「分帳」這個看似簡單的問題裡真正麻煩的部分做對：
**除不盡的錢跑去哪裡**，以及**怎麼讓還款次數變少**。

---

## 結構

```text
ExpenseManager            管理參與者與交易，計算結算路徑
  ├── Person              參與者與餘額（BigDecimal）
  └── Transaction         單筆交易
        ├── TransactionType   Enum：資金正負號與捨入模式
        └── SplitStrategy     介面：怎麼分
              ├── EqualSplitStrategy    均分
              └── ExactSplitStrategy    部分人有指定金額，其餘均分
                    └── RemainderStrategy  介面：除不盡的餘數給誰
                          ├── SequentialRemainder   依序分給前幾人
                          ├── HostAbsorbsRemainder  代墊者自行吸收
                          └── HostReleaseRemainder  代墊者放棄不收
```

---

## 三個實際處理的問題

### 1. 除不盡的錢（Penny Drop）

100 元三人均分，每人 33 元，剩 1 元。用 `double` 會產生 33.333… 然後在加總時對不起來。

做法：全程 `BigDecimal`，除法明確指定 `RoundingMode.DOWN` 取整，**餘數單獨算出來再分配**，不靠捨入去吸收。

```java
BigDecimal baseShared = sharedAmount.divide(headcount, 0, RoundingMode.DOWN);
BigDecimal remainder  = sharedAmount.subtract(baseShared.multiply(headcount));
List<BigDecimal> allocate = remainderStrategy.allocate(intHeadcount, remainder);
```

餘數該給誰是**政策問題不是技術問題**，所以抽成 `RemainderStrategy` 介面讓呼叫端決定，
而不是在演算法裡寫死。目前三種實作對應三種現實情境：依序分攤、主揪吸收、主揪放棄。

### 2. 總額不能漏

分配時累加每個人**實際被扣的金額**，最後把這個累加值加回代墊者，而不是把「交易總額」加回去。

```java
actualTotalDeducted = actualTotalDeducted.add(remainderAdded);
...
actor.addBalance(actualTotalDeducted);
```

差別在於：若用交易總額回填，餘數分配造成的 ±1 元就會憑空出現或消失。用實扣總額回填，系統內金錢總和恆為 0。

### 3. 減少轉帳次數

`printSettlement()` 把參與者分成負餘額（債務人）與正餘額（債權人）兩組，
每次取兩邊各一人，以**兩者絕對值較小者**對沖。

每一次轉帳至少讓一個人歸零並退出清單，所以 n 個人最多 n−1 次轉帳就結清，
而不是每個人各自對每個人還一次。

---

## 設計取捨

**用 Enum 承載行為，而不是繼承。**
原本可以寫 `Expense extends Transaction` 與 `Income extends Transaction`，
但兩者的差別只有正負號與捨入方向，做成兩個類別只是把資料差異包裝成類別差異。
改用 `TransactionType` Enum 把 sign 與 RoundingMode 帶在列舉常數上，新增交易種類只要加一個常數。

**把「怎麼分」與「餘數給誰」拆成兩個介面。**
兩者變動的理由不同：分帳方式隨業務規則變，餘數政策隨群組習慣變。
綁在一起的話，新增一種分帳方式就要重寫一次餘數處理。

---

## 已知限制

這些是知道但沒做的，不是沒想到的：

| 限制 | 說明 |
|---|---|
| 無自動化測試 | 目前靠 `TestExpenseSplitter` 的 `main()` 手動看輸出。應改為 JUnit，尤其是餘數分配與零和驗證這兩塊 |
| `Person` 未覆寫 `equals`/`hashCode` | 它被當成 `HashMap` 的 key 用。目前因為全程重用同一批物件所以可行，**一旦加入序列化或持久化就會失效**。已有 `id` 欄位，補上即可 |
| 自訂金額只吃整數 | `ExactSplitStrategy` 的建構子收 `Map<Person, Integer>`，無法表示有小數的指定金額 |
| 結算未排序 | 貪婪對沖前未依餘額大小排序，轉帳次數在上限內但非最少 |
| 無持久化、無並行控制 | 全部在記憶體，單執行緒 |

---

## 執行

```bash
cd src
javac expensesplitter/*.java
java expensesplitter.TestExpenseSplitter
```
