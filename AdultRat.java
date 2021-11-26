/** 
 * AdultRat.java
 * @version 1.0
 * @author Dylan Lewis, Kien Lin
 *
 */

import java.util.Random;

import javafx.scene.image.Image;

import java.math.*;

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
	
	protected final double PREGNANT_FEMALE_RAT_SPEED = 0.01;
	
	/**
	 * a boolean to determine whether or not a rat is currently waiting.
	 */
	private boolean isWaiting = false; 
	
	private final int MAXIMUM_PREGNANCY_COUNT = 5;
	
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
	 * keeps count of ticks passed.
	 */
	private int waitCounter = 0;
	
	/**
	 * Mating interval wait
	 */
	private final int MATING_INTERVAL = 200;
	/**
	 * Mating cooldown interval wait
	 */
	private final int MATING_COOLDOWN_INTERVAL = 200;
	
	private final int GIVE_BIRTH_INTERVAL = 4;
	
	private int giveBirthCooldown;
		
	/**
	 * Whether or not the rat is on a mating cooldown
	 */
	private boolean matingCooldown = false;
	
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
	public void becomePregnant () {
		this.isPregnant = true;
		
		//randomise number of babies for pregnancy counter
		int min = 1;
		int max = MAXIMUM_PREGNANCY_COUNT;
		
		Random rand = new Random();
		this.pregnancyCounter = (rand.nextInt(max + min) + min);
		
		this.pregnancyCounter = 5;
		
		System.out.println(this.pregnancyCounter);
		
		this.setRatWait(true);
		
		switch (this.directionFacing) {
		case 'N':
			this.renderSprite = ADULT_FEMALE_RAT_PREGNANT_SPRITE_NORTH;
			break;
		case 'E':
			this.renderSprite = ADULT_FEMALE_RAT_PREGNANT_SPRITE_EAST;
			break;
		case 'S':
			this.renderSprite = ADULT_FEMALE_RAT_PREGNANT_SPRITE_SOUTH;
			break;
		case 'W':
			this.renderSprite = ADULT_FEMALE_RAT_PREGNANT_SPRITE_WEST;
			break;
		}
		
		this.ratSpriteNorth = ADULT_FEMALE_RAT_PREGNANT_SPRITE_NORTH;
		this.ratSpriteEast = ADULT_FEMALE_RAT_PREGNANT_SPRITE_EAST;
		this.ratSpriteSouth = ADULT_FEMALE_RAT_PREGNANT_SPRITE_SOUTH;
		this.ratSpriteWest = ADULT_FEMALE_RAT_PREGNANT_SPRITE_WEST;
		
		
		//need to re adjust for the speed.
		double oldLimit = TILE_SIZE / (TILE_SIZE * this.ratSpeed);
		double newLimit = TILE_SIZE / (TILE_SIZE * PREGNANT_FEMALE_RAT_SPEED);
		
		
		
		// new / old
		
		float overwriteCounter = (float) (this.tickCounter * (newLimit / oldLimit));
		
		this.tickCounter = overwriteCounter;
		
		
		this.ratSpeed = PREGNANT_FEMALE_RAT_SPEED;
		
		this.giveBirthCooldown = GIVE_BIRTH_INTERVAL + 1;
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
	
	public void setRatWait(boolean waiting) {
		this.isWaiting = waiting;
	}
	
	/**
	 *  Getter for mating cooldown.
	 */
	public boolean getMatingCooldown() {
		return this.matingCooldown;
	}
	
	public void giveBirth() {
		//choose a random number between 0 and 1
		//0 representing female
		//1 representing male
		//then spawn a new baby rat on the AdultRat's current position.
		int min = 0;
		int max = 1;
		
		Random rand = new Random();
		int randomGender = (rand.nextInt(max + min) + min)-1;
		boolean babyRatGender = false;
		
		if (randomGender == 1) {
			babyRatGender = true;
		}
		
		this.currentLevel.addRenderObject(new BabyRat(new Position(Math.round(this.getObjectPosition()[0]), Math.round(this.getObjectPosition()[1])), babyRatGender, this.currentLevel, this.directionFacing));
	}
	
	/**
	 *  Method which is responsible for movement, pregnancy and pregnancy cooldown.
	 */
	public void tick() {
		double tickLimit = TILE_SIZE / (TILE_SIZE * this.ratSpeed);
		//checks if the rat is on a mating cooldown
		//if the countdown is finished revert the cooldown.
		if (this.matingCooldown == true && this.cooldown > 0) {
			this.cooldown--;
		} else {
			this.matingCooldown = false;
			this.cooldown = 0;
		}
		
		//if the rat is pregnancy with with more than 0 babies then give birth.
		//give birth with 2 second intervals
		if (isPregnant == true && isWaiting == false) {
			if (this.pregnancyCounter > 0) {
				//System.out.println(this.tickCounter);
				if (this.tickCounter == tickLimit-1) {
					this.giveBirthCooldown--;
				} else if (this.giveBirthCooldown == 0) {
					
					//reset the cooldown
					this.giveBirthCooldown = GIVE_BIRTH_INTERVAL;
					//give birth
					this.giveBirth();
					//deicnriment pregnancy counter
					this.pregnancyCounter--;
				}
			} else {
				//make it unpregnant here
				this.cooldown = MATING_COOLDOWN_INTERVAL;
				this.matingCooldown = true;
				//make not pregnant
				this.isPregnant = false;
				//reset sprites
				
				switch (this.directionFacing) {
				case 'N':
					this.renderSprite = ADULT_FEMALE_RAT_SPRITE_NORTH;
					break;
				case 'E':
					this.renderSprite = ADULT_FEMALE_RAT_SPRITE_EAST;
					break;
				case 'S':
					this.renderSprite = ADULT_FEMALE_RAT_SPRITE_SOUTH;
					break;
				case 'W':
					this.renderSprite = ADULT_FEMALE_RAT_SPRITE_WEST;
					break;
				}
				
				this.ratSpriteNorth = ADULT_FEMALE_RAT_SPRITE_NORTH;
				this.ratSpriteEast = ADULT_FEMALE_RAT_SPRITE_EAST;
				this.ratSpriteSouth = ADULT_FEMALE_RAT_SPRITE_SOUTH;
				this.ratSpriteWest = ADULT_FEMALE_RAT_SPRITE_WEST;
				
				double oldLimit = TILE_SIZE / (TILE_SIZE * this.ratSpeed);
				double newLimit = TILE_SIZE / (TILE_SIZE * PREGNANT_FEMALE_RAT_SPEED);
				
				float overwriteCounter = (float) (this.tickCounter * (newLimit / oldLimit));
				
				this.tickCounter = overwriteCounter;
				
				
				this.ratSpeed = DEFAULT_ADULT_RAT_SPEED;
			}
		}
		
		
		//checks if the rat is currently waiting.
				//if it is waiting and pregnant then wait for 3 seconds and continue moving again.
				if (this.isWaiting == false) {
					this.movement();
				} else if (this.isPregnant == true) {
					this.waitCounter++;
					if (this.waitCounter == 200) {
						this.waitCounter = 0;
						//stop the wait
						this.setRatWait(false);
						
					}
				}
		
	}
}
