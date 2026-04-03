package expensesplitter;

public class TestExpenseSplitter {

	public static void main(String[] args) {
		Person vincent = new Person("Vincent");
		Person alice = new Person("Alice");
		Person bob = new Person("Bob");
		ExpenseManager group = new ExpenseManager();
		group.addPerson(vincent);
		group.addPerson(alice);
		group.addPerson(bob);
		Expense beef = new Expense("牛肉湯", 601, vincent);
		Income deposit = new Income("房租押金", 2000, alice);
		group.addTransactionAndSplitEqually(beef);
		group.addTransactionAndSplitEqually(deposit);
		render(vincent);
		render(alice);
		render(bob);
		group.printSettlement();
	}

	public static void render(Person p) {
		System.out.println("目前的內部營損" + p.getName() + ": " + p.getBalance());
	}
}
