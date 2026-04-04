package expensesplitter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Income extends Transaction {
	public Income(String description, int totalAmount, Person payer, ArrayList<Person> particitions) {
		super(description, totalAmount, payer, particitions);
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
