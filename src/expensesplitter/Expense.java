package expensesplitter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Expense extends Transaction {
	public Expense(String description, int totalAmount, Person payer, ArrayList<Person> participants) {
		super(description, totalAmount, payer, participants);
	}

	@Override
	protected BigDecimal getAmountSign() {
		return BigDecimal.ONE;
	}

	@Override
	protected RoundingMode getRoundingMode() {
		return RoundingMode.CEILING;
	}
}
