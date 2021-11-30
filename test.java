import java.math.BigDecimal;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BigDecimal test = new BigDecimal("0");
		for (int i = 0; i < 100; i++) {
			test = test.add(new BigDecimal("0.02"));
			System.out.println(test.doubleValue());
		}
	}

}
