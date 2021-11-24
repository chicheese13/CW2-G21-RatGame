/** 
 * BabyRat.java
 * @version 1.0
 * @author Dylan Lewis, Kien Lin
 *
 */

import javafx.scene.image.Image;

/**
 * BabyRat is a class which initialises an instance for BabyRat.
 *
 */

public class BabyRat extends NormalRat {
	/**
	 * This is the default value of the grow count attribute.
	 */
	protected final int DEFAULT_GROW_COUNT = 0;
	/**
	 * This is the speed for a baby rat.
	 */
	protected final double BABY_RAT_SPEED = 0.02;
	/**
	 * The baby rat image sprite.
	 */
	protected final Image BABY_RAT_SPRITE = new Image("TestTextures/baby-rat.png");
	/**
	 *  The grow counter keeps track of the baby growth.
	 */
	private int growCounter;
	
	/**
	 *  Creates a baby rat
	 *  @param position
	 *  @param gender
	 */
	public BabyRat(Position position, boolean gender, TestLevel currentLevel, double testSpeed) {
		this.objectPosition = position;
		this.ratGender = gender;
		this.renderSprite = BABY_RAT_SPRITE;
		this.ratSterile = DEFUALT_STERILE;
		this.ratHealth = MAX_RAT_HEALTH;
		this.growCounter = DEFAULT_GROW_COUNT;
		this.directionFacing = 'N';
		this.currentLevel = currentLevel;
		this.ratSpeed = BABY_RAT_SPEED;
		this.ratSpeed = testSpeed;
	}
	
	/**
	 *  Setter for growth counter.
	 *  @param counter
	 */
	public void setGrowCounter (int counter) {
		this.growCounter = counter;
	}
	
	/**
	 *  Getter for grow counter
	 */
	public int getGrowCounter() {
		return this.growCounter;
	}
	
	/**
	 *  Method which handles the death of a rat.
	 */
	public void ratDeath() {
		//incriment score by RAT_SCORE
		//remove rat from the ArrayList
	}
	
	/**
	 *  Method which is responsible for movement and growth every tick.
	 */
	public void tick() {
		this.movement();
	}	
}
