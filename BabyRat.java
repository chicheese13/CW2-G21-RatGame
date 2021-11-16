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

public class BabyRat extends Rat {
	protected final int RAT_SCORE = 10;
	protected final int BABY_RAT_SPEED = 10;
	protected final int DEFAULT_GROW_COUNT = 10;
	private boolean ratGender;
	private boolean ratSterile;
	private int growCount;
	
	public BabyRat(Position position, boolean gender) {
		this.ratGender = gender;
		this.ratPosition = position;
		this.ratSpeed = BABY_RAT_SPEED;
		this.ratSterile = false;
		this.ratHealth = RAT_HEALTH;
		this.growCount = DEFAULT_GROW_COUNT; 
	}
	
	public void ratDeath() {
		//Level.incrimentScore(RAT_SCORE);
	}
	
	public void replenishHealth() {
		this.ratHealth = RAT_HEALTH;
	}
	
	public boolean getRatGender() {
		return this.ratGender;
	}
	
	public void changeGender(boolean gender) {
		this.ratGender = gender;
	}
	
	public boolean getSterile() {
		return this.ratSterile;
	}
	
	public void becomeSterile() {
		this.ratSterile = true;
	}
	
	public int getGrowCount() {
		return this.growCount;
	}
}
