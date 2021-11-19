import javafx.scene.image.Image;

/** 
 * DeathRat.java
 * @version 1.0
 * @author Dylan Lewis, Kien Lin
 *
 */

/**
 * DeathRat is a class which initialises an instance for DeathRat.
 *
 */
public class DeathRat extends Rat { 
	/**
	 * The death rat image sprite.
	 */
	protected final Image DEATH_RAT_SPRITE = new Image("Textures/death-rat.png");
	/**
	 * This is the default value for the death rat speed.
	 */
	protected final int DEFAULT_DEATH_RAT_SPEED = 5;
	/**
	 * This is the default value for wait count
	 */
	protected final int DEFAULT_WAIT_COUNT = 0;
	/**
	 * This is the value the wait count has to hit before we start doing stuff.
	 */
	protected final int WAIT_LIMIT = 1000;
	/**
	 * Counter for making the rat wait.
	 */
	private int waitCount;
	
	/**
	 * Creates a death rat
	 * @param position
	 */
	public DeathRat(Position position){
		this.ratSprite = DEATH_RAT_SPRITE;
		this.ratPosition = position;
		this.ratHealth = MAX_RAT_HEALTH;
		this.ratSpeed = DEFAULT_DEATH_RAT_SPEED;
		this.directionFacing = 'N';
		this.waitCount = DEFAULT_WAIT_COUNT;
	}
	
	/**
	 * Handles the waiting for a DeathRat and movement per tick.
	 */
	public void tick() {
		if (this.waitCount < WAIT_LIMIT) {
			this.waitCount = this.waitCount + 1;
		} else {
			//move
		}
	}
	
	/**
	 * Method for killing collided rats.
	 */
	public void kill(Rat rat) {
		//play a killing sound
		//remove the rat from the array.
	}
	
}
