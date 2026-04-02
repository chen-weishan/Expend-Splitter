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
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void addBalance(BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) > 0) {
			this.balance=this.balance.add(amount);
		}
	}

	public void subtractBalance(BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) > 0) {
			this.balance=this.balance.subtract(amount);
		}
	}
}
