package expensesplitter;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Transaction {
	private TransactionType type;
	private String description;
	private BigDecimal totalAmount;
	private Person actor;
	private ArrayList<Person> participants;
	
	public Transaction(TransactionType type,String description, int totalAmount, Person actor, ArrayList<Person> participants) {
		this.type=type;
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

	public void split() {
		if (participants == null || participants.isEmpty()) {
			return;
		}
		BigDecimal headcount = BigDecimal.valueOf(participants.size());
		BigDecimal baseExpenseForEach = totalAmount.divide(headcount, 0, type.getRoundingMode());
		BigDecimal signedExpendForEach = baseExpenseForEach.multiply(type.getSign());
		for(Person p:participants) {
			p.subtractBalance(signedExpendForEach);
		}
		BigDecimal actualTotal = signedExpendForEach.multiply(headcount);
		actor.addBalance(actualTotal);
	}
}
