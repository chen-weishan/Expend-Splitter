package expensesplitter;

import java.math.BigDecimal;
import java.util.ArrayList;

public class EqualSplitStrategy implements SplitStrategy {
	public void executeSplit(BigDecimal totalAmount, Person actor, ArrayList<Person> participants,
			TransactionType type) {
		if (participants == null || participants.isEmpty()) {
			return;
		}
		BigDecimal headcount = BigDecimal.valueOf(participants.size());
		BigDecimal baseExpenseForEach = totalAmount.divide(headcount, 0, type.getRoundingMode());
		BigDecimal signedExpendForEach = baseExpenseForEach.multiply(type.getSign());
		for (Person p : participants) {
			p.subtractBalance(signedExpendForEach);
		}
		BigDecimal actualTotal = signedExpendForEach.multiply(headcount);
		actor.addBalance(actualTotal);

	}
}
