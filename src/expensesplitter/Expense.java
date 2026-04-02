package expensesplitter;

import java.math.BigDecimal;

public class Expense {
	private String description;
	private BigDecimal totalAmount;
	private Person payer;

	public Expense(String description, int totalAmount, Person payer) {
		this.description = description;
		this.totalAmount = BigDecimal.valueOf(totalAmount);
		this.payer = payer;
	}

	public String getDestription() {
		return this.description;
	}

	public BigDecimal getTotalAmount() {
		return this.totalAmount;
	}

	public Person getPayer() {
		return this.payer;
	}
}
