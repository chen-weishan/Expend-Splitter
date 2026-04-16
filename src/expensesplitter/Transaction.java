package expensesplitter;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Transaction {
	private TransactionType type;
	private String description;
	private BigDecimal totalAmount;
	private Person actor;
	private ArrayList<Person> participants;
	private SplitStrategy strategy;

	public Transaction(TransactionType type, String description, int totalAmount, Person actor,
			ArrayList<Person> participants, SplitStrategy strategy) {
		this.type = type;
		this.description = description;
		this.totalAmount = BigDecimal.valueOf(totalAmount);
		this.actor = actor;
		this.participants = participants;
		this.strategy = strategy;
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
		strategy.executeSplit(type, totalAmount, actor, participants);
	}
}
