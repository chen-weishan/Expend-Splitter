package expensesplitter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TestBigDecimal {

	public static void main(String[] args) {
		BigDecimal a=BigDecimal.valueOf(100);
		BigDecimal b=BigDecimal.valueOf(3);
		BigDecimal avg =a.divide(b,0,RoundingMode.HALF_UP);
		System.out.println(avg);
	}

}
