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
	 * How many ticks until the rat grows up, 999 is around 15 seconds.
	 */
	private final int GROW_COUNT_LIMIT = 999;

	/**
	 * All baby rat sprites for each direction.
	 */
	protected final Image BABY_RAT_SPRITE_EAST = new Image("TestTextures/baby-rat-east.png");
	protected final Image BABY_RAT_SPRITE_NORTH = new Image("TestTextures/baby-rat-north.png");
	protected final Image BABY_RAT_SPRITE_SOUTH = new Image("TestTextures/baby-rat-south.png");
	protected final Image BABY_RAT_SPRITE_WEST = new Image("TestTextures/baby-rat-west.png");
	/**
	 *  The grow counter keeps track of the baby growth.
	 */
	private int growCounter;
	
	/**
	 *  Creates a baby rat
	 *  @param position
	 *  @param gender
	 *  @param currentLevel
	 */
	public BabyRat(Position position, boolean gender, TestLevel currentLevel, char directionFacing) {
		this.objectPosition = position;
		this.ratGender = gender;
		this.renderSprite = BABY_RAT_SPRITE_EAST;
		this.ratSterile = DEFUALT_STERILE;
		this.ratHealth = MAX_RAT_HEALTH;
		this.growCounter = DEFAULT_GROW_COUNT;
		this.currentLevel = currentLevel;
		this.ratSpeed = BABY_RAT_SPEED;
		this.ratSpriteNorth = BABY_RAT_SPRITE_NORTH;
		this.ratSpriteEast = BABY_RAT_SPRITE_EAST;
		this.ratSpriteSouth = BABY_RAT_SPRITE_SOUTH;
		this.ratSpriteWest = BABY_RAT_SPRITE_WEST;
		this.directionFacing = directionFacing;
		
		System.out.println(this.getObjectPosition()[0]);
		System.out.println(this.getObjectPosition()[1]);
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
		//incriment score by RAT_SCORE wherever the score variable is.
		this.currentLevel.incrimentScore(RAT_SCORE);
		//create an instance of RenderScore with desired score incriment.
		this.currentLevel.addRenderObject(new RenderScore(this.objectPosition, RAT_SCORE, this.currentLevel));
		//remove itself from RenderObjects array.
		this.removeSelf();
		
		//play rat death sound clip.
		SoundClip ratDeathSound = new SoundClip("rat-death-sound");
		ratDeathSound.play();
	}
	
	/**
	 *  Method which is responsible for movement and growth every tick.
	 */
	public void tick() {		
		this.growCounter++;
		
		if (this.growCounter > GROW_COUNT_LIMIT) {
			this.growUp();
		} else {
			this.movement(true);
		}
	}	
	
	public void collision(Object collidedObject) {
		
	}
	
	public void growUp() {
		//create an adult rat 
		//might need the baby rat's current tickCounter.
		this.currentLevel.addRenderObject(new AdultRat(this.objectPosition, this.ratGender, this.ratSterile, this.ratHealth, this.tickCounter, this.directionFacing, this.currentLevel));
		//remove self from array
		this.removeSelf();
		
		//maybe play grow up sound
	}
	
}
