package expensesplitter;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface SplitStrategy {
	void executeSplit(TransactionType type, BigDecimal totalAmount, Person actor, ArrayList<Person> participants);
}
