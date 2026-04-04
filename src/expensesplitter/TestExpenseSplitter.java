package expensesplitter;

import java.util.ArrayList;

public class TestExpenseSplitter {

	public static void main(String[] args) {
		Person vincent = new Person("Vincent");
		Person alice = new Person("Alice");
		Person bob = new Person("Bob");
		ExpenseManager group = new ExpenseManager();
		group.addPerson(vincent);
		group.addPerson(alice);
		group.addPerson(bob);
		ArrayList<Person> allPeople =new ArrayList<>();
		allPeople.add(vincent);
		allPeople.add(alice);
		allPeople.add(bob);
		
		Expense beef = new Expense("牛肉湯", 601, vincent, group.getAllPeople());
		Income deposit = new Income("房租押金", 2000, alice, group.getAllPeople());
		group.addTransactionAndSplitEqually(beef);
		group.addTransactionAndSplitEqually(deposit);
		
		ArrayList<Person> drinkConsumers=new ArrayList<>();
		drinkConsumers.add(alice);
		drinkConsumers.add(bob);	
		
		Expense drinks = new Expense("飲料代墊",210,bob,drinkConsumers);
		group.addTransactionAndSplitEqually(drinks);

		result(group);
	}

	public static void result(ExpenseManager group) {
		for(Person p:group.getAllPeople()) {
		System.out.println("目前的內部營損" + p.getName() + ": " + p.getBalance());
		}
		group.printSettlement();
		}
}
