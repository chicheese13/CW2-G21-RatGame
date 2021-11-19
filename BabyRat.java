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
	protected final int DEFAULT_GROW_COUNT = 0;
	protected final int BABY_RAT_SPEED = 10;
	
	private int growCounter;
	private Image ratSprite;
	
	public BabyRat(Position position, boolean gender) {
		this.ratPosition = position;
		this.ratGender = gender;
		this.ratSprite = new Image("Textures/baby-rat.png");
		this.ratSterile = DEFUALT_STERILE;
		this.ratHealth = MAX_RAT_HEALTH;
		
		this.growCounter = DEFAULT_GROW_COUNT;
	}
	
	public void setGrowCounter (int counter) {
		this.growCounter = counter;
	}
	
	public int getGrowCounter() {
		return this.growCounter;
	}
	
	public void ratDeath() {
		//incriment score by RAT_SCORE
	}
	
	
	
	//temp tick
	public void tick() {
		this.movement();
	}
	
	//get sprite
	public Image getSprite() {
		return this.ratSprite;
	}
}
