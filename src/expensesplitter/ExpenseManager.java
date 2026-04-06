package expensesplitter;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ExpenseManager {
	private ArrayList<Person> people;
	private ArrayList<Transaction> transaction;

	public ExpenseManager() {
		people = new ArrayList<>();
		transaction = new ArrayList<>();
	}

	public void addPerson(Person people) {
		this.people.add(people);
	}

	public void addTransactionAndSplitEqually(Transaction transaction) {
		this.transaction.add(transaction);
		transaction.split();
	}

	public ArrayList<Person> getAllPeople() {
		return people;
	}

	public void printSettlement() {
		ArrayList<Person> debtors = new ArrayList<>();
		ArrayList<Person> creditors = new ArrayList<>();
		for (Person p : people) {
			if (p.getBalance().compareTo(BigDecimal.ZERO) < 0) {
				debtors.add(p);
			}
			if (p.getBalance().compareTo(BigDecimal.ZERO) > 0) {
				creditors.add(p);
			}

		}
		while (!debtors.isEmpty() && !creditors.isEmpty()) {
			Person debtor = debtors.get(0);
			Person creditor = creditors.get(0);
			BigDecimal amountToPay = debtor.getBalance().abs();
			BigDecimal amountToReceive = creditor.getBalance();
			BigDecimal transferAmount = amountToPay.min(amountToReceive);
			System.out.println(debtor.getName() + "需轉帳給" + creditor.getName() + ":" + transferAmount + "元");
			debtor.addBalance(transferAmount);
			creditor.subtractBalance(transferAmount);
			if (debtor.getBalance().compareTo(BigDecimal.ZERO) == 0) {
				debtors.remove(0);
			}
			if (creditor.getBalance().compareTo(BigDecimal.ZERO) == 0) {
				creditors.remove(0);
			}
		}
	}
}
