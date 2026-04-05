package expensesplitter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestExpenseSplitter {

	public static void main(String[] args) {
		ArrayList<String> names =new ArrayList<>(List.of("vincent","alice","bob","candy"));
		ExpenseManager group =new ExpenseManager();
		buildGroup(group,names);
		
		EqualSplitStrategy equalSplit = new EqualSplitStrategy();
		
		ArrayList<Person> drinkConsumers=new ArrayList<>();
		drinkConsumers.add(group.getAllPeople().get(1));
		drinkConsumers.add(group.getAllPeople().get(2));	
		
		Transaction beef = new Transaction(TransactionType.EXPENSE,"牛肉湯", 601, group.getAllPeople().get(0), group.getAllPeople(),equalSplit);
		Transaction rice = new Transaction(TransactionType.EXPENSE,"豬油拌飯", 745, group.getAllPeople().get(1), group.getAllPeople(),equalSplit);
		Transaction deposit = new Transaction(TransactionType.INCOME,"房租押金", 200, group.getAllPeople().get(2), group.getAllPeople(),equalSplit);
		Transaction drinks = new Transaction(TransactionType.EXPENSE,"飲料代墊",210,group.getAllPeople().get(1),drinkConsumers,equalSplit);
		ArrayList<Transaction> transactionList =new ArrayList<>(List.of(beef,rice,deposit,drinks));
		addAndSplit(group, transactionList);

		result(group);
	}

	public static void buildGroup(ExpenseManager group,ArrayList<String> names) {
		for(String name:names) {
			group.addPerson(new Person(name));
		}
	}
	
	public static void addAndSplit(ExpenseManager group,ArrayList<Transaction> transactionList) {
		for(Transaction transaction:transactionList) {
			group.addTransactionAndSplitEqually(transaction);
		}
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
