package expensesplitter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class ExpenseManager {
	private ArrayList<Person> people;
	private ArrayList<Expense> expenses;

	public ExpenseManager() {
		this.people = new ArrayList<>();
		this.expenses = new ArrayList<>();
	}

	public void addPerson(Person p) {
		this.people.add(p);
	}

	public void addExpenseAndSplitEqually(Expense expense) {
		this.expenses.add(expense);
		BigDecimal headcount=BigDecimal.valueOf(people.size());
		BigDecimal expenseForEach =expense.getTotalAmount().divide(headcount,2,RoundingMode.CEILING);
		for(Person p:people) {
			if(p.getId().equals(expense.getPayer().getId())) {
				p.addBalance(expenseForEach.multiply(headcount.subtract(BigDecimal.ONE)));
			}else {
				p.subtractBalance(expenseForEach);
			}
		}
	}
}
