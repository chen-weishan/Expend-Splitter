package expensesplitter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SequentialRemainder implements RemainderStrategy {
	public List<BigDecimal> allocate(int headcount, BigDecimal remainder) {
		List<BigDecimal> sequentialAllocate = new ArrayList<>();
		BigDecimal signRemainder = BigDecimal.ONE;
		if (remainder.compareTo(BigDecimal.ZERO) < 0) {
			signRemainder = BigDecimal.valueOf(-1);
		}
		for (int i = 0; i < headcount; i++) {
			if (BigDecimal.valueOf(i).compareTo(remainder.abs()) < 0) {
				sequentialAllocate.add(signRemainder);
			} else {
				sequentialAllocate.add(BigDecimal.ZERO);
			}
		}
		return sequentialAllocate;
	}
}
