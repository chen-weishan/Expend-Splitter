package expensesplitter;

import java.math.BigDecimal;
import java.util.UUID;

public class Person {
	private final String id;
	private String name;
	private BigDecimal balance;

	public Person(String name) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.balance = BigDecimal.ZERO;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void addBalance(BigDecimal amount) {
		balance = balance.add(amount);
	}

	public void subtractBalance(BigDecimal amount) {
		balance = balance.subtract(amount);
	}
}
