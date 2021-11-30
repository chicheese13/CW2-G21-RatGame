/** 
 * AdultRat.java
 * @version 1.0
 * @author Dylan Lewis, Kien Lin
 *
 */

import javafx.scene.image.Image;

/**
 * AdultRat is a class which initialises an instance for AdultRat.
 *
 */

public class AdultRat extends NormalRat {
	/**
	 * This is the default value for the pregnancy attribute.
	 */
	protected final boolean DEFAULT_PREGNANCY_VALUE = false;
	/**
	 * This is the default value for the pregnancy count attribute.
	 */
	protected final int DEFAULT_PREGNANCY_COUNT = 0;
	/**
	 * This is the default value for the cooldown attribute.
	 */
	protected final int DEFAULT_COOLDOWN_VALUE = 0;
	/**
	 * This is the default value for the rat speed.
	 */
	protected final double DEFAULT_ADULT_RAT_SPEED = 0.02;
	/**
	 * The adult male rat image sprite.
	 */
	protected final Image ADULT_MALE_RAT_SPRITE_NORTH = new Image("Textures/male-rat-north.png");
	protected final Image ADULT_MALE_RAT_SPRITE_EAST = new Image("Textures/male-rat-east.png");
	protected final Image ADULT_MALE_RAT_SPRITE_SOUTH = new Image("Textures/male-rat-south.png");
	protected final Image ADULT_MALE_RAT_SPRITE_WEST = new Image("Textures/male-rat-west.png");
	
	protected final Image ADULT_FEMALE_RAT_SPRITE_NORTH = new Image("Textures/female-rat-north.png");
	protected final Image ADULT_FEMALE_RAT_SPRITE_EAST = new Image("Textures/female-rat-east.png");
	protected final Image ADULT_FEMALE_RAT_SPRITE_SOUTH = new Image("Textures/female-rat-south.png");
	protected final Image ADULT_FEMALE_RAT_SPRITE_WEST = new Image("Textures/female-rat-west.png");
	
	protected final Image ADULT_FEMALE_RAT_PREGNANT_SPRITE_NORTH = new Image("Textures/female-rat-pregnant-north.png");
	protected final Image ADULT_FEMALE_RAT_PREGNANT_SPRITE_EAST = new Image("Textures/female-rat-pregnant-east.png");
	protected final Image ADULT_FEMALE_RAT_PREGNANT_SPRITE_SOUTH = new Image("Textures/female-rat-pregnant-south.png");
	protected final Image ADULT_FEMALE_RAT_PREGNANT_SPRITE_WEST = new Image("Textures/female-rat-pregnant-west.png");
	
	/**
	 * The adult female rat image sprite.
	 */
	//protected final Image ADULT_FEMALE_RAT_SPRITE = new Image("Textures/female-rat.png");
	/**
	 * The adult pregnant female rat image sprite.
	 */
	//protected final Image PREGNANT_ADULT_FEMALE_RAT_SPRITE = new Image("Textures/pregnant-rat.png");
	
	/**
	 * Whether the rat is pregnant or not.
	 */
	private boolean isPregnant;
	/**
	 * Cool down for rat pregnancy.
	 */
	private int cooldown;
	/**
	 * Number of babies a pregnant rat is carrying.
	 */
	private int pregnancyCounter;
	
	
	/**
	 * Creates an adult rat
	 * @param position
	 * @param gender
	 * @param sterile
	 * @param ratHealth
	 */
	public AdultRat(Position position, boolean gender, boolean sterile, int ratHealth, TestLevel currentLevel) {
		this.isPregnant = DEFAULT_PREGNANCY_VALUE;
		this.pregnancyCounter = DEFAULT_PREGNANCY_COUNT;
		this.cooldown = DEFAULT_COOLDOWN_VALUE;
		this.ratSpeed = DEFAULT_ADULT_RAT_SPEED;
		this.ratSterile = sterile;
		this.objectPosition = position;
		this.ratGender = gender;
		this.ratHealth = ratHealth;
		this.directionFacing = 'N';
		this.currentLevel = currentLevel;
		
		//this defines the sprite of the rat based on gender.
		if (gender) {
			this.renderSprite = ADULT_MALE_RAT_SPRITE_EAST;
			this.ratSpriteNorth = ADULT_MALE_RAT_SPRITE_NORTH;
			this.ratSpriteEast = ADULT_MALE_RAT_SPRITE_EAST;
			this.ratSpriteSouth = ADULT_MALE_RAT_SPRITE_SOUTH;
			this.ratSpriteWest = ADULT_MALE_RAT_SPRITE_WEST;
		} else {
			//this.renderSprite = ADULT_FEMALE_RAT_SPRITE;
			this.renderSprite = ADULT_FEMALE_RAT_SPRITE_EAST;
			this.ratSpriteNorth = ADULT_FEMALE_RAT_SPRITE_NORTH;
			this.ratSpriteEast = ADULT_FEMALE_RAT_SPRITE_EAST;
			this.ratSpriteSouth = ADULT_FEMALE_RAT_SPRITE_SOUTH;
			this.ratSpriteWest = ADULT_FEMALE_RAT_SPRITE_WEST;
		}
	}
	
	/**
	 * Setter for isPregnant
	 * @param pregnant
	 */
	public void setPregnant (boolean pregnant) {
		this.isPregnant = pregnant;
	}
	
	/**
	 * Getter for isPregnant
	 */
	public boolean getPregnant() {
		return this.isPregnant;
	}
	
	/**
	 * setter for cooldown
	 * @param cooldown
	 */
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
	
	/**
	 * getter for the cooldown
	 */
	public int getCooldown () {
		return this.cooldown;
	}
	
	/**
	 * setter for pregnancyCounter
	 * @param pregnancyCounter
	 */
	public void setPregnancyCounter(int pregnancyCounter) {
		this.pregnancyCounter = pregnancyCounter;
	}
	
	
	/**
	 * getter for pregnancyCounter
	 */
	public int getPregnancyCounter() {
		return this.pregnancyCounter;
	}
	
	/**
	 * handles the death of an adult rat.
	 */
	public void ratDeath() {
		//incriment score (pregnancyCounter+1 * RAT_SCORE)
		this.currentLevel.incrimentScore((this.pregnancyCounter+1) * RAT_SCORE);
		//create an instance of RenderScore with desired score incriment.
		this.currentLevel.addRenderObject(new RenderScore(this.objectPosition, RAT_SCORE, this.currentLevel));
		//remove itself from RenderObjects array.
		SoundClip ratDeathSound = new SoundClip("rat-death-sound");
		ratDeathSound.play();
		this.removeSelf();
	}
	
	/**
	 *  Method which is responsible for movement, pregnancy and pregnancy cooldown.
	 */
	public void tick() {
		this.movement();
	}
}
