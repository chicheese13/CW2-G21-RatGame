import java.util.Arrays;

public class TestClass {
	public static void main(String[] args) {
		BabyRat testRat = new BabyRat (new Position(100,100), true);
		
		System.out.println(testRat.getGrowCounter());
		System.out.println(testRat.getRatGender());
		System.out.println(testRat.getRatHealth());
		System.out.println(testRat.getSterile());
		System.out.println(Arrays.toString(testRat.getRatPosition()));
	}
}
