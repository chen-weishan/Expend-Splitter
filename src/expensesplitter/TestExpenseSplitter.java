package expensesplitter;

import java.math.BigDecimal;
import java.util.ArrayList;

public class TestExpenseSplitter {

	public static void main(String[] args) {
		Person vincent = new Person("Vincent");
		Person alice = new Person("Alice");
		Person bob = new Person("Bob");
		Person candy= new Person("Candy");
		ExpenseManager group = new ExpenseManager();
		group.addPerson(vincent);
		group.addPerson(alice);
		group.addPerson(bob);
		group.addPerson(candy);
		
		Transaction beef = new Transaction(TransactionType.EXPENSE,"牛肉湯", 601, vincent, group.getAllPeople());
		Transaction rice = new Transaction(TransactionType.EXPENSE,"豬油拌飯", 745, bob, group.getAllPeople());
		Transaction deposit = new Transaction(TransactionType.INCOME,"房租押金", 200, alice, group.getAllPeople());
		group.addTransactionAndSplitEqually(beef);
		group.addTransactionAndSplitEqually(deposit);
		group.addTransactionAndSplitEqually(rice);
		
		ArrayList<Person> drinkConsumers=new ArrayList<>();
		drinkConsumers.add(alice);
		drinkConsumers.add(bob);	
		
		Transaction drinks = new Transaction(TransactionType.EXPENSE,"飲料代墊",210,alice,drinkConsumers);
		group.addTransactionAndSplitEqually(drinks);

		result(group);
	}

	public static void result(ExpenseManager group) {
		BigDecimal balanceSum=BigDecimal.ZERO;
		for(Person p:group.getAllPeople()) {
		System.out.println("目前的內部損溢" + p.getName() + ": " + p.getBalance());
		balanceSum=balanceSum.add(p.getBalance());
		}
		System.out.println("群組總值淨損溢: "+balanceSum+"元");
		group.printSettlement();
		}
}
