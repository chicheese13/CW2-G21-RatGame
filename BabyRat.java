/** 
 * BabyRat.java
 * @version 1.0
 * @author Dylan Lewis, Kien Lin
 *
 */

/**
 * BabyRat is a class which initialises an instance for BabyRat.
 *
 */

public class BabyRat extends NormalRat {
	protected final int DEFAULT_GROW_COUNT = 0;
	protected final int BABY_RAT_SPEED = 10;
	
	private int growCounter;
	
	public BabyRat(Position position, boolean gender) {
		this.ratPosition = position;
		this.ratGender = gender;
		this.ratSterile = DEFUALT_STERILE;
		this.ratHealth = RAT_HEALTH;
		
		this.growCounter = DEFAULT_GROW_COUNT;
	}
	
	public void setGrowCounter (int counter) {
		this.growCounter = counter;
	}
	
	public int getGrowCounter() {
		return this.growCounter;
	}
}
