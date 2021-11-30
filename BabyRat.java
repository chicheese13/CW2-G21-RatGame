/** 
 * BabyRat.java
 * @version 2.0
 * @author Dylan Lewis, Kien Lin
 *
 */

import java.math.BigDecimal;

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
	 * The speed of the baby rat, must divide into 1 with no remainders, or else it will break.
	 */
	protected final BigDecimal BABY_RAT_SPEED = new BigDecimal("0.04");
	
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
	 *  Constructor of a BabyRat
	 *  @param position
	 *  @param gender
	 *  @param currentLevel, the current level in which the rat is in.
	 *  @param directionFacing, the direction in which the rat is facing at anytime.
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
		//incriments the growCounter and if it's exceeds the grow count limit then make the rat grow up
		this.growCounter++;
		
		if (this.growCounter > GROW_COUNT_LIMIT) {
			this.growUp();
		} else {
			this.movement();
		}
	}	
	
	/**
	 *  Methods which handles collision with a collided oject.
	 */
	public void collision(Object collidedObject) {
		
	}
	
	/**
	 *  Makes the baby rat grow up into an adult
	 *  Removes instance of self and creates new instance of an AdultRat
	 */
	public void growUp() {
		//create an adult rat 
		//might need the baby rat's current tickCounter.
		this.currentLevel.addRenderObject(new AdultRat(this.objectPosition, this.ratGender, this.ratSterile, this.ratHealth, this.directionFacing, this.currentLevel));
		//remove self from array
		this.removeSelf();
		}
	
}
