# Expense Splitter - 企業級帳務分攤核心模組

## 專案概述
本專案為一個高擴充性、高精準度的帳務分攤與結算核心系統。系統支援多重帳務情境（如平分、客製化金額、外送費/服務費差額攤提、代收退款等），並實作了嚴謹的財務稽核邏輯，確保系統在任何複雜拆帳情境下皆能維持絕對的「零和 (Zero-sum)」。

## 系統核心架構

系統遵循 SOLID 原則，採用模組化設計。以下為核心元件與依賴關係圖：

```text
[ ExpenseManager ] (核心指揮中心)
       |
       |--- 1..N --- [ Person ] (實體：參與者與帳戶餘額)
       |
       |--- 1..N --- [ Transaction ] (領域模型：交易紀錄)
                            |
                            |--- [ TransactionType ] (Enum：定義資金流向與捨入模式)
                            |
                            |--- << SplitStrategy >> (Interface：分帳演算法介面)
                                        |
                                        |--- [ EqualSplitStrategy ] (實作：均分策略)
                                        |--- [ ExactSplitStrategy ] (實作：複合客製化策略)
```

## 核心技術亮點與架構決策 (Architectural Decisions)

### 1. 策略模式 (Strategy Pattern) 實現開放封閉原則 (OCP)
為解決未來可能無限擴充的分帳邏輯（如按比例、按部門等），系統將「分帳演算法」自 `Transaction` 中抽離，定義為 `SplitStrategy` 介面。
*   **優勢**：新增分帳邏輯時，完全無需修改核心 `Transaction` 類別，達成完美的解耦與擴充性。
*   **應用場景**：透過注入不同的 Strategy，同一筆交易模型可無縫切換為「無條件平分」或「含外送費的客製化拆帳」。

### 2. 資料驅動行為 (Data-Driven Behavior) 與 Enum 應用
捨棄傳統使用 `Expense` 與 `Income` 繼承 `Transaction` 的冗贅類別架構 (Lazy Class Anti-pattern)，改為引入 `TransactionType` 列舉 (Enum)。
*   **優勢**：將資金正負號 (`Sign`) 與捨入模式 (`RoundingMode`) 封裝於 Enum 內部。減少了類別爆炸 (Class Explosion) 的風險，使系統更具可維護性。

### 3. 金融級精度與「一元丟失 (Penny Drop)」問題解決方案
在處理除不盡的帳務時（例如 100 元分給 3 人），系統實作了業界標準的 **「餘數分配演算法 (Remainder Distribution)」**。
*   **實作細節**：全面採用 `BigDecimal` 避免浮點數精度遺失。針對除不盡的餘數，演算法會將餘數逐元分配給佇列前方的參與者。
*   **稽核機制**：系統內建零和驗證 (Zero-sum Reconciliation)，在分配完成後，會將實際扣除的總額反饋給代墊者，確保系統資金總量誤差絕對為 0。

### 4. 貪婪演算法 (Greedy Algorithm) 最佳化結算路徑
在 `ExpenseManager.printSettlement()` 中，實作了基於雙指標 (Two-pointer) 概念的貪婪結算演算法。
*   **實作細節**：將系統內的參與者分為「債務人 (Debtors)」與「債權人 (Creditors)」兩組，每次取出兩方餘額絕對值較小者進行對沖與轉帳結算。
*   **優勢**：有效減少群組內的轉帳次數，提供使用者最簡潔的還款指示。

## API 介面與依賴反轉 (Dependency Inversion)
系統內部參數傳遞皆嚴格遵守「針對介面寫程式 (Programming to an interface)」原則。
例如在 `ExactSplitStrategy` 中，建構子參數宣告為 `Map<Person, Integer>` 而非 `HashMap`，確保底層演算法能接收任何具備 Map 合約的資料結構（如 `TreeMap`, `LinkedHashMap`），最大化系統的彈性。

## 未來展望 (Future Scope)
*   **持久化層 (Persistence Layer)**：整合關聯式資料庫 (如 MySQL/PostgreSQL)，將記憶體內的狀態持久化。
*   **RESTful API 封裝**：使用 Spring Boot 框架將核心邏輯封裝為 Web API，提供前端串接。