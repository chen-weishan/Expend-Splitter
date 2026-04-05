package expensesplitter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum TransactionType {
	EXPENSE(BigDecimal.ONE, RoundingMode.CEILING), 
	INCOME(BigDecimal.valueOf(-1), RoundingMode.FLOOR);

	private final BigDecimal sign;
	private final RoundingMode roundingMode;

	TransactionType(BigDecimal sign, RoundingMode roundingMode) {
		this.sign = sign;
		this.roundingMode = roundingMode;
	}

	public BigDecimal getSign() {
		return sign;
	}

	public RoundingMode getRoundingMode() {
		return roundingMode;
	}
}
