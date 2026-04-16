package expensesplitter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostReleaseRemainder implements RemainderStrategy {
	public List<BigDecimal> allocate(int headcount, BigDecimal remainder) {
		List<BigDecimal> releaseAllocate =new ArrayList<>();
		BigDecimal signRemainder = BigDecimal.ONE;
		if(remainder.compareTo(BigDecimal.ZERO)<0) {
			signRemainder = BigDecimal.valueOf(-1);
		}
		for (int i = 0; i < headcount; i++) {
			releaseAllocate.add(signRemainder);
		}
		return releaseAllocate;
	}
}
