package expensesplitter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class EqualSplitStrategy implements SplitStrategy {
	private RemainderStrategy remainderStrategy;

	public EqualSplitStrategy() {
		remainderStrategy = new SequentialRemainder();
	}

	public EqualSplitStrategy(RemainderStrategy remainderStrategy) {
		this.remainderStrategy = remainderStrategy;
	}

	public void executeSplit(TransactionType type, BigDecimal totalAmount, Person actor,
			ArrayList<Person> participants) {
		if (participants == null || participants.isEmpty()) {
			return;
		}
		int intHeadcount = participants.size();
		BigDecimal headcount = BigDecimal.valueOf(intHeadcount);
		BigDecimal baseExpenseForEach = totalAmount.divide(headcount, 0, RoundingMode.DOWN);
		BigDecimal remainder = totalAmount.subtract(baseExpenseForEach.multiply(headcount));
		List<BigDecimal> allocate = remainderStrategy.allocate(intHeadcount, remainder);
		BigDecimal signedExpendForEach = baseExpenseForEach.multiply(type.getSign());
		BigDecimal actualTotalDeducted = BigDecimal.ZERO;
		for (int i = 0; i < intHeadcount; i++) {
			BigDecimal remainderAdded = signedExpendForEach.add(allocate.get(i).multiply(type.getSign()));
			participants.get(i).subtractBalance(remainderAdded);
			actualTotalDeducted = actualTotalDeducted.add(remainderAdded);
		}
		actor.addBalance(actualTotalDeducted);

	}
}
