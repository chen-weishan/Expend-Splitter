package expensesplitter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExactSplitStrategy implements SplitStrategy {
	private Map<Person, BigDecimal> customAmounts;

	public ExactSplitStrategy(Map<Person, Integer> inputAmounts) {
		customAmounts = new HashMap<>();
		for (Map.Entry<Person, Integer> entry : inputAmounts.entrySet()) {
			customAmounts.put(entry.getKey(), BigDecimal.valueOf(entry.getValue()));
		}
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
		BigDecimal headcount = BigDecimal.valueOf(participants.size());
		BigDecimal sharedAmount = totalAmount.subtract(customSum);
		
		BigDecimal baseShared= sharedAmount.divide(headcount, 0, RoundingMode.DOWN);
		BigDecimal remainder = sharedAmount.subtract(baseShared.multiply(headcount));
		
		for (Person p : participants) {
			BigDecimal custom = customAmounts.getOrDefault(p,BigDecimal.ZERO);
			BigDecimal currentShared = baseShared;
			
			if(remainder.compareTo(BigDecimal.ZERO) > 0) {
				currentShared = currentShared.add(BigDecimal.ONE);
				remainder = remainder.subtract(BigDecimal.ONE);
			}else if(remainder.compareTo(BigDecimal.ZERO) < 0) {
				currentShared =currentShared.subtract(BigDecimal.ONE);
				remainder = remainder.add(BigDecimal.ONE);
			}
			
			BigDecimal rawExpense = custom.add(currentShared);
			BigDecimal signExpense = rawExpense.multiply(type.getSign());
			p.subtractBalance(signExpense);
		}
		actor.addBalance(totalAmount.multiply(type.getSign()));
	}
}
