
/** 
 * DeathRat.java
 * @version 2.0
 * @author Dylan Lewis, Kien Lin
 *
 */

import javafx.scene.image.Image;
import java.math.BigDecimal;


/**
 * DeathRat is a class which initialises an instance for DeathRat.
 *
 */
public class DeathRat extends Rat { 
	/**
	 * The death rat image sprite.
	 */
	protected final Image DEATH_RAT_SPRITE_NORTH = new Image("Textures/death-rat-north.png");
	protected final Image DEATH_RAT_SPRITE_EAST = new Image("Textures/death-rat-east.png");
	protected final Image DEATH_RAT_SPRITE_SOUTH = new Image("Textures/death-rat-south.png");
	protected final Image DEATH_RAT_SPRITE_WEST = new Image("Textures/death-rat-west.png");
	/**
	 * This is the default value for the death rat speed.
	 */
	protected final BigDecimal DEFAULT_DEATH_RAT_SPEED = new BigDecimal("0.02");
	/**
	 * This is the default value for wait count
	 */
	protected final int DEFAULT_WAIT_COUNT = 0;
	/**
	 * This is the value the wait count has to hit before we start doing stuff, roughly two seconds.
	 */
	protected final int WAIT_LIMIT = 133;
	/**
	 * The amount of kills a DeathRat can have before removing itself.
	 */
	private final int KILL_LIMIT = 5;
	/**
	 * Counter for making the rat wait.
	 */
	private int waitCount;
	/**
	 * Counter for tracking kills.
	 */
	private int killCounter = 0;
	
	/**
	 * Creates a death rat
	 * @param position
	 * @param currentLevel, the level the rat is currently on.
	 */
	public DeathRat(Position position, Level currentLevel){
		this.renderSprite = "death-rat-north";
		this.objectPosition = position;
		this.ratSpeed = DEFAULT_DEATH_RAT_SPEED;
		this.directionFacing = 'N';
		this.waitCount = DEFAULT_WAIT_COUNT;
		this.ratSpriteNorth = "death-rat-north";
		this.ratSpriteEast = "death-rat-east";
		this.ratSpriteSouth = "death-rat-south";
		this.ratSpriteWest = "death-rat-west";
		this.currentLevel = currentLevel;
		

		this.currentLevel.spawnSound("DeathRat");
	}
	
	/**
	 * Handles the waiting for a DeathRat and movement per tick.
	 */
	public void tick() {
		if (this.waitCount < WAIT_LIMIT) {
			this.waitCount = this.waitCount + 1;
		} else {
			//move
			this.movement();
		}
		
		if (this.killCounter == KILL_LIMIT) {
			this.currentLevel.spawnSound("DeathRatDeactivated");
			this.currentLevel.despawnRat(this);
			//maybe play death sound
		}
	}
	
	/**
	 * Method for killing collided rats.
	 */
	public void collision(Object rat) {
		if (this.waitCount >= WAIT_LIMIT) {
			if (rat instanceof NormalRat) {
				this.currentLevel.spawnSound("DeathRatSlayVictim");
				((NormalRat) rat).ratDeath();
				this.killCounter++;
			}
		}
	}
	
	public String convertString() {
		return (this.ratSpeed 
		+ " " + this.directionFacing
		+ " " + this.objectPosition.getPosition()[0]
		+ " " + this.objectPosition.getPosition()[1]
		+ " " + this.waitCount
		+ " " + this.killCounter);
	}
	
	public void overwriteAttributes(String savedRat) {
		//overwrite variables for rat.
	}
	
}
