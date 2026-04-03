package expensesplitter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public abstract class Transaction {
	protected String description;
	protected BigDecimal totalAmount;
	protected Person actor;

	public Transaction(String description, int totalAmount, Person actor) {
		this.description = description;
		this.totalAmount = BigDecimal.valueOf(totalAmount);
		this.actor = actor;
	}

	public String getDescription() {
		return this.description;
	}

	public BigDecimal getTotalAmount() {
		return this.totalAmount;
	}

	public Person getActor() {
		return this.actor;
	}

	protected abstract BigDecimal getAmountSign();

	protected abstract RoundingMode getRoundingMode();

	public void split(ArrayList<Person> people) {
		BigDecimal headcount = BigDecimal.valueOf(people.size());
		BigDecimal baseExpenseForEach = this.totalAmount.divide(headcount, 0, getRoundingMode());
		BigDecimal signedExpendForEach = baseExpenseForEach.multiply(getAmountSign());
		for (Person p : people) {
			if (p.getId().equals(this.actor.getId())) {
				BigDecimal amountForActor=signedExpendForEach.multiply(headcount.subtract(BigDecimal.ONE));
				p.addBalance(amountForActor);
			} else {
				p.subtractBalance(signedExpendForEach);
			}
		}
	}
}
