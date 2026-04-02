package expensesplitter;

public class TestExpenseSplitter {

	public static void main(String[] args) {
		Person vincent =new Person("Vincent");
		Person alice=new Person("Alice");
		Person bob=new Person("Bob");
		ExpenseManager group =new ExpenseManager();
		group.addPerson(vincent);
		group.addPerson(alice);
		group.addPerson(bob);
		Expense beef=new Expense("牛肉湯",601,vincent);
		group.addExpenseAndSplitEqually(beef);
		render(vincent);
		render(alice);
		render(bob);
		}
	public static void render(Person p) {
		System.out.println(p.getBalance());
	}
}
