package expensesplitter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public abstract class Transaction {
	protected String description;
	protected BigDecimal totalAmount;
	protected Person actor;
	protected ArrayList<Person> participants;

	public Transaction(String description, int totalAmount, Person actor, ArrayList<Person> participants) {
		this.description = description;
		this.totalAmount = BigDecimal.valueOf(totalAmount);
		this.actor = actor;
		this.participants = participants;

	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public Person getActor() {
		return actor;
	}

	protected abstract BigDecimal getAmountSign();

	protected abstract RoundingMode getRoundingMode();

	public void split() {
		if (participants == null || participants.isEmpty()) {
			return;
		}
		BigDecimal headcount = BigDecimal.valueOf(participants.size());
		BigDecimal baseExpenseForEach = totalAmount.divide(headcount, 0, getRoundingMode());
		BigDecimal signedExpendForEach = baseExpenseForEach.multiply(getAmountSign());
		for(Person p:participants) {
			p.subtractBalance(signedExpendForEach);
		}
		BigDecimal actualTotal = signedExpendForEach.multiply(headcount);
		actor.addBalance(actualTotal);
	}
}
