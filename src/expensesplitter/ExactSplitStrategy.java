package expensesplitter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExactSplitStrategy implements SplitStrategy {
	private Map<Person, BigDecimal> customAmounts;
	private RemainderStrategy remainderStrategy;

	public ExactSplitStrategy(Map<Person, Integer> inputAmounts) {
		customAmounts = new HashMap<>();
		for (Map.Entry<Person, Integer> entry : inputAmounts.entrySet()) {
			customAmounts.put(entry.getKey(), BigDecimal.valueOf(entry.getValue()));
		}
		remainderStrategy = new SequentialRemainder();
	}

	public ExactSplitStrategy(Map<Person, Integer> inputAmounts, RemainderStrategy remainderStrategy) {
		customAmounts = new HashMap<>();
		for (Map.Entry<Person, Integer> entry : inputAmounts.entrySet()) {
			customAmounts.put(entry.getKey(), BigDecimal.valueOf(entry.getValue()));
		}
		this.remainderStrategy = remainderStrategy;
	}

	public void executeSplit(TransactionType type, BigDecimal totalAmount, Person actor,
			ArrayList<Person> participants) {
		if (participants == null || participants.isEmpty()) {
			return;
		}
		BigDecimal customSum = BigDecimal.ZERO;
		for (BigDecimal amount : customAmounts.values()) {
			customSum = customSum.add(amount);
		}
		int intHeadcount = participants.size();
		BigDecimal headcount = BigDecimal.valueOf(intHeadcount);
		BigDecimal sharedAmount = totalAmount.subtract(customSum);

		BigDecimal baseShared = sharedAmount.divide(headcount, 0, RoundingMode.DOWN);
		BigDecimal remainder = sharedAmount.subtract(baseShared.multiply(headcount));
		List<BigDecimal> allocate = remainderStrategy.allocate(intHeadcount,remainder);
		BigDecimal actualTotalDeducted = BigDecimal.ZERO;

		for (int i =0;i<intHeadcount;i++) {
			Person personNow = participants.get(i);
			BigDecimal custom = customAmounts.getOrDefault(personNow, BigDecimal.ZERO);
			BigDecimal currentShared = baseShared;

			BigDecimal rawExpense = custom.add(currentShared);
			BigDecimal signExpense = rawExpense.multiply(type.getSign());
			BigDecimal remainderAdded = signExpense.add(allocate.get(i).multiply(type.getSign()));
			personNow.subtractBalance(remainderAdded);
			actualTotalDeducted = actualTotalDeducted.add(remainderAdded);
		}
		actor.addBalance(actualTotalDeducted);
	}
}
