package expensesplitter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestExpenseSplitter {

	public static void main(String[] args) {
		// 創造人物並新增到 ExpenseManager 的群組
		Person vincent = new Person("Vincent");
		Person alice = new Person("Alice");
		Person bob = new Person("Bob");
		Person cindy = new Person("Cindy");
		ArrayList<Person> allPeople = new ArrayList<>(List.of(vincent, alice, bob, cindy));
		ExpenseManager group = new ExpenseManager();
		buildGroup(group, allPeople);

		// 平分代墊或代收的策略: EqualSplitStrategy
		EqualSplitStrategy equalSplit = new EqualSplitStrategy();

		ArrayList<Person> drinkConsumers = new ArrayList<>(List.of(alice, bob));

		Transaction beef = new Transaction(TransactionType.EXPENSE, "牛肉湯", 601, vincent, allPeople, equalSplit);
		Transaction rice = new Transaction(TransactionType.EXPENSE, "豬油拌飯", 745, alice, allPeople, equalSplit);
		Transaction deposit = new Transaction(TransactionType.INCOME, "房租押金", 200, bob, allPeople, equalSplit);
		Transaction drinks = new Transaction(TransactionType.EXPENSE, "飲料代墊", 211, alice, drinkConsumers, equalSplit);

		// 客製化幫每個人代墊或代收金額的策略: ExactSplitStrategy
		ArrayList<Person> teaConsumers = new ArrayList<>(List.of(alice, bob));
		Map<Person, Integer> teaMap = new HashMap<>(Map.of(alice, 90, bob, 50));
		ExactSplitStrategy exactTeaSplit = new ExactSplitStrategy(teaMap);
		Transaction tea = new Transaction(TransactionType.EXPENSE, "飲料代墊", 209, alice, teaConsumers, exactTeaSplit);

		ArrayList<Person> refundReceivers = new ArrayList<>(List.of(alice, cindy));
		Map<Person, Integer> refundMap = new HashMap<>(Map.of(alice, 600, cindy, 300));
		ExactSplitStrategy exactRefundSplit = new ExactSplitStrategy(refundMap);
		Transaction refund = new Transaction(TransactionType.INCOME, "廠商退款與紅利", 1001, vincent, refundReceivers,
				exactRefundSplit);

		// 將每筆帳目加入ExpenManager群組，並用printSettlement()方法印出結果
		ArrayList<Transaction> transactionList = new ArrayList<>(List.of(beef, rice, deposit, drinks, tea, refund));
		addAndSplit(group, transactionList);
		result(group);
	}

	public static void buildGroup(ExpenseManager group, ArrayList<Person> persons) {
		for (Person p : persons) {
			group.addPerson(p);
		}
	}

	public static void addAndSplit(ExpenseManager group, ArrayList<Transaction> transactionList) {
		for (Transaction transaction : transactionList) {
			group.addTransactionAndSplitEqually(transaction);
		}
	}

	public static void result(ExpenseManager group) {
		BigDecimal balanceSum = BigDecimal.ZERO;
		for (Person p : group.getAllPeople()) {
			System.out.println("目前的內部損溢" + p.getName() + ": " + p.getBalance());
			balanceSum = balanceSum.add(p.getBalance());
		}
		System.out.println("群組總值淨損溢: " + balanceSum + "元");
		group.printSettlement();
	}
}
