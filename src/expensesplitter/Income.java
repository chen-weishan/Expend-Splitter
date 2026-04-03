package expensesplitter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Income extends Transaction {
	public Income(String description, int totalAmount, Person payer) {
		super(description, totalAmount, payer);
	}

	@Override
	protected BigDecimal getAmountSign() {
		return new BigDecimal("-1");
	}

	@Override
	protected RoundingMode getRoundingMode() {
		return RoundingMode.FLOOR;
	}
}
