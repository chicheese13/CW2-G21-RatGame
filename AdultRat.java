/** 
 * AdultRat.java
 * @version 1.0
 * @author Dylan Lewis, Kien Lin
 *
 */

import java.util.Random;

import javafx.scene.image.Image;

import java.math.BigDecimal;
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
	protected final BigDecimal DEFAULT_ADULT_RAT_SPEED = new BigDecimal("0.02");
	
	protected final BigDecimal PREGNANT_FEMALE_RAT_SPEED = new BigDecimal("0.01");
	
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
	private final int MATING_INTERVAL = 1000;
	/**
	 * Mating cooldown interval wait
	 */
	private final int MATING_COOLDOWN_INTERVAL = 1000;
	
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
	public AdultRat(Position position, boolean gender, boolean sterile, int ratHealth, double tickIn, char direction, TestLevel currentLevel) {
		this.isPregnant = DEFAULT_PREGNANCY_VALUE;
		this.pregnancyCounter = DEFAULT_PREGNANCY_COUNT;
		this.cooldown = DEFAULT_COOLDOWN_VALUE;
		this.ratSpeed = DEFAULT_ADULT_RAT_SPEED;
		this.ratSterile = sterile;
		this.objectPosition = position;
		this.ratGender = gender;
		this.ratHealth = ratHealth;
		this.directionFacing = direction;
		this.currentLevel = currentLevel;
		
		//works out speed difference for rats.
		//double oldLimit = TILE_SIZE / (TILE_SIZE * 0.04);
		//double newLimit = TILE_SIZE / (TILE_SIZE * DEFAULT_ADULT_RAT_SPEED);
		
		
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
		//double oldLimit = TILE_SIZE / (TILE_SIZE * this.ratSpeed);
		//double newLimit = TILE_SIZE / (TILE_SIZE * PREGNANT_FEMALE_RAT_SPEED);
		
		
		
		// new / old
		
		//float overwriteCounter = (float) (this.tickCounter * (newLimit / oldLimit));
		
		//this.tickCounter = overwriteCounter;
		
		
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
		int min = 1;
		int max = 2;
		
		Random rand = new Random();
		int randomGender = (rand.nextInt(max-1 + min) + min);
		
		System.out.println(randomGender);
		
		boolean babyRatGender = false;
		
		if (randomGender == 1) {
			babyRatGender = true;
		}
		
		this.currentLevel.addRenderObject(new BabyRat(new Position(new BigDecimal(Math.round(this.getObjectPosition()[0].doubleValue())), new BigDecimal(Math.round(this.getObjectPosition()[1].doubleValue()))), babyRatGender, this.currentLevel, this.directionFacing));
	}
	
	public void collision(Object parameter) {
		if (parameter instanceof AdultRat) {
			//if the rat is not sterile and is not pregnant and is not on a mating cooldown then
			if (((AdultRat) parameter).getSterile() == false 
				&& ((AdultRat) parameter).getPregnant() == false
				&& ((AdultRat) parameter).getMatingCooldown() == false
				&& this.getMatingCooldown() == false
				&& this.getPregnant() == false
				&& this.getSterile() == false
				&& this.getRatGender() != ((AdultRat) parameter).getRatGender()) {
					//make the female rat pregnant
					
					
					if (this.ratGender == false) {
						this.becomePregnant();
					} else {
						((AdultRat) parameter).becomePregnant();
					}
					
					this.setRatWait(true);
					((AdultRat) parameter).setRatWait(true);
					
					System.out.println("POSITION");
					System.out.println(this.getObjectPosition()[0]);
					System.out.println(this.getObjectPosition()[1]);
			}
		} else if (parameter instanceof Item) {
			
		} else if (parameter instanceof DeathRat) {
			
		}
	}
	
	public void setWaitCounter(int waitTime) {
		this.waitCounter = waitTime;
	}
	
	/**
	 *  Method which is responsible for movement, pregnancy and pregnancy cooldown.
	 */
	public void tick() {
		//double tickLimit = TILE_SIZE / (TILE_SIZE * this.ratSpeed);
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
				if (this.getObjectPosition()[0].stripTrailingZeros().scale() <= 0
					&& this.getObjectPosition()[1].stripTrailingZeros().scale() <= 0) {
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
				System.out.println("UNPREGNANT");
				//make it unpregnant here
				this.cooldown = MATING_COOLDOWN_INTERVAL;
				this.matingCooldown = true;
				//make not pregnant
				this.isPregnant = false;
				//reset sprites
				
				switch (this.directionFacing) {
				case 'N':
					System.out.println("test1");
					this.renderSprite = ADULT_FEMALE_RAT_SPRITE_NORTH;
					break;
				case 'E':
					System.out.println("test2");
					this.renderSprite = ADULT_FEMALE_RAT_SPRITE_EAST;
					break;
				case 'S':
					System.out.println("test3");
					this.renderSprite = ADULT_FEMALE_RAT_SPRITE_SOUTH;
					break;
				case 'W':
					System.out.println("test4");
					this.renderSprite = ADULT_FEMALE_RAT_SPRITE_WEST;
					break;
				}
				
				this.ratSpriteNorth = ADULT_FEMALE_RAT_SPRITE_NORTH;
				this.ratSpriteEast = ADULT_FEMALE_RAT_SPRITE_EAST;
				this.ratSpriteSouth = ADULT_FEMALE_RAT_SPRITE_SOUTH;
				this.ratSpriteWest = ADULT_FEMALE_RAT_SPRITE_WEST;
				
				this.ratSpeed = DEFAULT_ADULT_RAT_SPEED;
			}
		}
		
		
		//checks if the rat is currently waiting.
				//if it is waiting and pregnant then wait for 3 seconds and continue moving again.
				if (this.isWaiting == false) {
					this.movement(true);
				} else {
					this.waitCounter++;
					if (this.waitCounter == 200) {
						this.waitCounter = 0;
						this.setRatWait(false);
						
					}
				}
		
	}
}
