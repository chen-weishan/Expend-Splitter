# 生活記帳與分帳系統 (Shared Expense Splitter)

這是一個以 Java 物件導向 (OOP) 概念開發的微型分帳系統，模擬真實世界中多重消費紀錄與帳務平分的商業邏輯。

## 系統亮點 (Features)

- **高精度金流運算**：捨棄傳統浮點數 (`double`)，全面採用 `BigDecimal` 處理帳務，並透過 `RoundingMode.CEILING` 解決除不盡的零頭問題，實作「代墊者優待原則」。
- **嚴謹的物件導向架構**：
  - 封裝 `Person` 與 `Expense` 實體。
  - 使用 Java 內建 `UUID` 作為使用者的唯一識別碼 (Primary Key)，確保帳目綁定正確。
- **動態狀態管理**：使用 `ArrayList` 儲存多筆消費紀錄，並由 `ExpenseManager` 集中處理平分演算法。

## 技術標籤 (Tech Stack)

- Java SE (JDK 17+)
- OOP (Encapsulation, MVC Pattern Concept)
- `java.math.BigDecimal`, `java.util.UUID`, `java.util.ArrayList`
