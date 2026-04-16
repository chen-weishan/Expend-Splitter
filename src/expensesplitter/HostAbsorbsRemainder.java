package expensesplitter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostAbsorbsRemainder implements RemainderStrategy{
	public List<BigDecimal> allocate(int headcount, BigDecimal remainder){
		List<BigDecimal> absorbsAllocate =new ArrayList<>();
		for(int i=0;i<headcount;i++) {
			absorbsAllocate.add(BigDecimal.ZERO);
		}
		return absorbsAllocate;
	}
}
