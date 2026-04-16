package expensesplitter;

import java.math.BigDecimal;
import java.util.List;

public interface RemainderStrategy {
	List<BigDecimal> allocate(int headcount, BigDecimal remainder);
}
