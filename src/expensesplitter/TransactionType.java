package expensesplitter;

import java.math.BigDecimal;

public enum TransactionType {
	EXPENSE(BigDecimal.ONE), INCOME(BigDecimal.valueOf(-1));

	private final BigDecimal sign;

	TransactionType(BigDecimal sign) {
		this.sign = sign;
	}

	public BigDecimal getSign() {
		return sign;
	}

}
