/** 
 * AdultRat.java
 * @version 1.0
 * @author Dylan Lewis, Kien Lin
 *
 */

import java.util.Random;

import javafx.scene.image.Image;

import java.math.BigDecimal;
import java.math.RoundingMode;
/**
 * AdultRat is a class which initialises an instance for AdultRat.
 *
 */

public class AdultRat extends NormalRat {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * This is the default value for the pregnancy attribute.
	 */
	protected final boolean DEFAULT_PREGNANCY_VALUE = false;
	/**
	 * This is the default value for the pregnancy count attribute.
	 */
	protected final int DEFAULT_PREGNANCY_COUNT = 0;
	/**
	 * Mating interval wait
	 */
	private final int MATING_WAIT_INTERVAL = 200;
	/**
	 * Default cooldown for female rat in ticks
	 */
	protected final int DEFAULT_COOLDOWN_VALUE_FEMALE = 400;
	/**
	 * Default cooldown for a male rat in ticks
	 */
	protected final int DEFAULT_COOLDOWN_VALUE_MALE = DEFAULT_COOLDOWN_VALUE_FEMALE + MATING_WAIT_INTERVAL;
	/**
	 * This is the default value for the non pregnant adult rat speed.
	 */
	protected final BigDecimal DEFAULT_ADULT_RAT_SPEED = new BigDecimal("0.02");
	/**
	 * This is the default value for the pregnant adult rat speed.
	 */
	protected final BigDecimal PREGNANT_FEMALE_RAT_SPEED = new BigDecimal("0.01");
	/**
	 * The maximum number of babies a pregnant rat can carry at one time.
	 */
	private final int MAXIMUM_PREGNANCY_COUNT = 5;
	/**
	 * The interval in ticks at which the pregnant female rat gives birth.
	 */
	private final int GIVE_BIRTH_INTERVAL = 333;
	
	/**
	 * The adult male rat image sprite.
	 */
	protected final Image COOLDOWN_SPRITE_MALE = new Image("Textures/test-sprite-one.png");
	protected final Image COOLDOWN_SPRITE_FEMALE = new Image("Textures/test-sprite-two.png");
	
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
	 * a boolean to determine whether or not a rat is currently waiting.
	 */
	private boolean isWaiting = false; 
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
	 * Mating cooldown interval wait
	 */
	
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
	 * @param currentLevel, the current level in which the rat is in.
	 * @param directionFacing, the direction in which the rat is facing at anytime.
	 */
	public AdultRat(Position position, boolean gender, boolean sterile, double ratHealth, char direction, Level currentLevel) {
		this.isPregnant = DEFAULT_PREGNANCY_VALUE;
		this.pregnancyCounter = DEFAULT_PREGNANCY_COUNT;
		this.ratSpeed = DEFAULT_ADULT_RAT_SPEED;
		this.ratSterile = sterile;
		this.objectPosition = position;
		this.ratGender = gender;
		this.ratHealth = ratHealth;
		this.directionFacing = direction;
		this.currentLevel = currentLevel;
		
		//this defines the sprite of the rat based on gender.
		if (gender) {
			this.cooldown = DEFAULT_COOLDOWN_VALUE_MALE;
			this.renderSprite = ADULT_MALE_RAT_SPRITE_EAST;
			this.ratSpriteNorth = ADULT_MALE_RAT_SPRITE_NORTH;
			this.ratSpriteEast = ADULT_MALE_RAT_SPRITE_EAST;
			this.ratSpriteSouth = ADULT_MALE_RAT_SPRITE_SOUTH;
			this.ratSpriteWest = ADULT_MALE_RAT_SPRITE_WEST;
		} else {
			this.cooldown = DEFAULT_COOLDOWN_VALUE_FEMALE;
			//this.renderSprite = ADULT_FEMALE_RAT_SPRITE;
			this.renderSprite = ADULT_FEMALE_RAT_SPRITE_EAST;
			this.ratSpriteNorth = ADULT_FEMALE_RAT_SPRITE_NORTH;
			this.ratSpriteEast = ADULT_FEMALE_RAT_SPRITE_EAST;
			this.ratSpriteSouth = ADULT_FEMALE_RAT_SPRITE_SOUTH;
			this.ratSpriteWest = ADULT_FEMALE_RAT_SPRITE_WEST;
		}
	}
	
	/**
	 * Become pregnant method, this makes the rat's speed slower, changes the sprite of the rat and randomises number of rats it's carrying.
	 */
	public void becomePregnant () {
		this.isPregnant = true;
		
		int min = 1;
		int max = MAXIMUM_PREGNANCY_COUNT;
		
		Random rand = new Random();
		this.pregnancyCounter = (rand.nextInt(max + min) + min);
		recallibratePosition(this.objectPosition, PREGNANT_FEMALE_RAT_SPEED);
		
		this.setRatWait(true);
		
	
		//might make a method purley for changing spirtes.
		
		//resets the give birth cooldown.
		this.giveBirthCooldown = 0;
	}
	
	/**
	 * Getter for isPregnant
	 */
	public boolean getPregnant() {
		return this.isPregnant;
	}
	
	/**
	 * handles the death of an adult rat.
	 */
	public void ratDeath() {
		this.currentLevel.spawnSound("rat-death-sound");
		this.currentLevel.despawnRat(this);
		this.currentLevel.incrimentScore((this.pregnancyCounter+1) * RAT_SCORE);
	}
	
	/**
	 * Setter for ratWait
	 */
	public void setRatWait(boolean waiting) {
		this.isWaiting = waiting;
	}
	
	/**
	 *  Getter for mating cooldown.
	 */
	public boolean getMatingCooldown() {
		return this.matingCooldown;
	}
	
	/**
	 * Method which creates an instance of BabyRat on the AdultRat's current position.
	 */
	public void giveBirth() {
		//choose a random number between 0 and 1
		//0 representing female
		//1 representing male
		//then spawn a new baby rat on the AdultRat's current position.
		int min = 1;
		int max = 2;
		
		Random rand = new Random();
		int randomGender = (rand.nextInt(max-1 + min) + min);
		
		
		boolean babyRatGender = false;
		
		if (randomGender == 1) {
			babyRatGender = true;
		}
		
		this.currentLevel.spawnRat(new BabyRat(new Position(new BigDecimal(Math.round(this.getObjectPosition()[0].doubleValue())), new BigDecimal(Math.round(this.getObjectPosition()[1].doubleValue()))), babyRatGender, this.currentLevel, this.directionFacing));
	}
	
	/**
	 * Starts the mating cooldown for an AdultRat, so it can't mate with other rats whilst true.
	 */
	public void startMatingCooldown() {
		this.matingCooldown = true;
	}
	
	/**
	 * Takes in a rat and checks if the they are eligible to mate, returns true if both are.
	 * @param check, an AdultRat to check.
	 */
	public boolean isMatabale(AdultRat check) {
		if (check.getSterile() == false 
				&& check.getPregnant() == false
				&& check.getMatingCooldown() == false
				&& this.matingCooldown == false
				&& this.isPregnant == false
				&& this.ratSterile == false
				&& this.ratGender != check.getRatGender()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Handles collisions with other AdultRats.
	 * @param collidedRat, an AdultRat which has collided with this Rat.
	 */
	public void ratCollision(AdultRat collidedRat) {
		//if the rat is not sterile and is not pregnant and is not on a mating cooldown then
		if (isMatabale(collidedRat)) {
			this.setObjectPosition((this.getObjectPosition()[0]).setScale(0, RoundingMode.HALF_UP),(this.getObjectPosition()[1]).setScale(0, RoundingMode.HALF_UP));
			collidedRat.setObjectPosition((this.getObjectPosition()[0]).setScale(0, RoundingMode.HALF_UP),(this.getObjectPosition()[1]).setScale(0, RoundingMode.HALF_UP));
			if (this.ratGender == false) {
				this.becomePregnant();
					
				collidedRat.setRatWait(true);
				collidedRat.startMatingCooldown();
			} else {
				collidedRat.becomePregnant();
				this.startMatingCooldown();
			}
			this.setRatWait(true);
		}
	}
	
	/**
	 * Handles a collision of Rat or Item. 
	 */
	public void collision(Object parameter) {
		if (parameter instanceof AdultRat) {
			ratCollision(((AdultRat) parameter));
		} else if (parameter instanceof Item) {
			this.ratDeath();
		}
	}
	
	/**
	 *  Checks if the rat is on a mating cooldown, if so deincriment the cooldown
	 *  Once the cooldown is finished reset the counter and set matingCooldown to false.
	 *  Update the sprites.
	 */
	public void mateCooldown() {
		if (this.matingCooldown == true && this.cooldown > 0) {
			this.cooldown--;
		} else if (this.matingCooldown == true) {
			if (this.ratGender == false) {
				this.ratSpriteNorth = ADULT_FEMALE_RAT_SPRITE_NORTH;
				this.ratSpriteEast = ADULT_FEMALE_RAT_SPRITE_EAST;
				this.ratSpriteSouth = ADULT_FEMALE_RAT_SPRITE_SOUTH;
				this.ratSpriteWest = ADULT_FEMALE_RAT_SPRITE_WEST;
			} else {
				this.ratSpriteNorth = ADULT_MALE_RAT_SPRITE_NORTH;
				this.ratSpriteEast = ADULT_MALE_RAT_SPRITE_EAST;
				this.ratSpriteSouth = ADULT_MALE_RAT_SPRITE_SOUTH;
				this.ratSpriteWest = ADULT_MALE_RAT_SPRITE_WEST;
			}
			
			this.matingCooldown = false;
			this.cooldown = 333;
		}
	}
	
	/**
	 *  Handles a pregnant rats pregnancy behaviour per tick, if the rat is carrying give birth at certain intervals
	 *  Once the pregnancy is over revert back to normal rat and have a cooldown
	 */
	public void pregnancyTick() {
		if (isPregnant == true && isWaiting == false) {
			this.giveBirthCooldown++;
			if (this.pregnancyCounter > 0) {
				if (this.giveBirthCooldown > GIVE_BIRTH_INTERVAL) {
					this.giveBirthCooldown = 0;
					//reset the cooldown
					//this.giveBirthCooldown = GIVE_BIRTH_INTERVAL;
					//give birth
					this.giveBirth();
					//deicnriment pregnancy counter
					this.pregnancyCounter--;
				}
			} else {
				//make it unpregnant here
				this.cooldown = DEFAULT_COOLDOWN_VALUE_FEMALE;
				this.matingCooldown = true;
				//make not pregnant
				this.isPregnant = false;
				//reset sprites
				
				switch (this.directionFacing) {
				case 'N':
					this.renderSprite = COOLDOWN_SPRITE_FEMALE;
					break;
				case 'E':
					this.renderSprite = COOLDOWN_SPRITE_FEMALE;
					break;
				case 'S':
					this.renderSprite = COOLDOWN_SPRITE_FEMALE;
					break;
				case 'W':
					this.renderSprite = COOLDOWN_SPRITE_FEMALE;
					break;
				}
				
				this.ratSpriteNorth = COOLDOWN_SPRITE_FEMALE;
				this.ratSpriteEast = COOLDOWN_SPRITE_FEMALE;
				this.ratSpriteSouth = COOLDOWN_SPRITE_FEMALE;
				this.ratSpriteWest = COOLDOWN_SPRITE_FEMALE;
				
				this.objectPosition = recallibratePosition(this.objectPosition, DEFAULT_ADULT_RAT_SPEED);
				this.ratSpeed = DEFAULT_ADULT_RAT_SPEED;
			}
		}
	}
	
	/**
	 *  This handles the rat's waiting behaviour per tick
	 *  If the rat is not waiting then it should move, else it should wait
	 *  If the rat is a male then make the sprite a cooldown sprite, and for female make it a pregnancy sprite.
	 */
	public void waitTick() {
		if (this.isWaiting == false) {
			this.movement();
		} else {
			this.waitCounter++;
			if (this.waitCounter == MATING_WAIT_INTERVAL) {
				this.waitCounter = 0;
				this.setRatWait(false);
				
				if (this.ratGender) {
					this.renderSprite = COOLDOWN_SPRITE_MALE;
					this.ratSpriteNorth = COOLDOWN_SPRITE_MALE;
					this.ratSpriteEast = COOLDOWN_SPRITE_MALE;
					this.ratSpriteSouth = COOLDOWN_SPRITE_MALE;
					this.ratSpriteWest = COOLDOWN_SPRITE_MALE;
				} else {
					//pregnancy
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
					this.ratSpeed = PREGNANT_FEMALE_RAT_SPEED;
				}
						
			}
		}
	}
	
	public void changeToFemale() {
		if (this.ratGender == true) {
			this.ratGender = false;
			//reset all sprites
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
			
			this.pregnancyCounter = 0;
			this.isPregnant = false;
			this.cooldown = DEFAULT_COOLDOWN_VALUE_FEMALE;
			this.matingCooldown = false;
			this.isWaiting = false;
			
			this.objectPosition = recallibratePosition(this.objectPosition, DEFAULT_ADULT_RAT_SPEED);
			this.ratSpeed = DEFAULT_ADULT_RAT_SPEED;
		}
	}
	
	public void changeToMale() {
		if (this.ratGender == false) {
			switch (this.directionFacing) {
			case 'N':
				this.renderSprite = ADULT_MALE_RAT_SPRITE_NORTH;
				break;
			case 'E':
				this.renderSprite = ADULT_MALE_RAT_SPRITE_EAST;
				break;
			case 'S':
				this.renderSprite = ADULT_MALE_RAT_SPRITE_SOUTH;
				break;
			case 'W':
				this.renderSprite = ADULT_MALE_RAT_SPRITE_WEST;
				break;
			}
			
			this.ratGender = true;
			
			this.ratSpriteNorth = ADULT_MALE_RAT_SPRITE_NORTH;
			this.ratSpriteEast = ADULT_MALE_RAT_SPRITE_EAST;
			this.ratSpriteSouth = ADULT_MALE_RAT_SPRITE_SOUTH;
			this.ratSpriteWest = ADULT_MALE_RAT_SPRITE_WEST;
			
			this.pregnancyCounter = 0;
			this.isPregnant = false;
			this.cooldown = DEFAULT_COOLDOWN_VALUE_FEMALE;
			this.matingCooldown = false;
			this.isWaiting = false;
			
			this.objectPosition = recallibratePosition(this.objectPosition, DEFAULT_ADULT_RAT_SPEED);
			this.ratSpeed = DEFAULT_ADULT_RAT_SPEED;
		}
	}
	
	/**
	 *  Method which is responsible for movement, pregnancy and pregnancy cooldown.
	 */
	public void tick() {
		this.damageCooldown = false;
		mateCooldown();
		pregnancyTick();
		waitTick();		
	}

	
	
}
