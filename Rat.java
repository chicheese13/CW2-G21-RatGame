
/**
 * Rat.java
 * @version 2.0
 * @author Dylan Lewis, Kien Lin
 *
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;

/**
 * Rat is an abstract class responsible for rat health/damage, rat’s movement
 * speed and the rat movement behaviour.
 *
 */

public abstract class Rat extends RenderObject {
	protected String BABY_REGULAR = "baby-reg";
	protected String FEMALE_COOLDOWN = "female-cooldown";
	protected String FEMALE_REGULAR = "female-regular";
	protected String FEMALE_PREGNANT = "female-pregnant";
	protected String MALE_REGULAR = "male-regular";
	protected String MALE_COOLDOWN = "male-cooldown";
	protected String DEATH_RAT_REGULAR = "death-rat-regular";

	/**
	 * size of tiles, used in smooth movement.
	 */
	protected final int TILE_SIZE = 50;
	/**
	 * Speed of the rat.
	 */
	protected BigDecimal ratSpeed;
	/**
	 * The direction in which the rat is facing.
	 */
	protected char directionFacing;
	/**
	 * The current sprites being used.
	 */
	protected String currentSpriteMode;

	/**
	 * All the sprites for each direction facing for the rat.
	 */
	protected String ratSpriteNorth;
	protected String ratSpriteSouth;
	protected String ratSpriteEast;
	protected String ratSpriteWest;

	/**
	 * A method which checks the Rat's current position, checks all available
	 * directions it can move and chooses a random direction and set's the
	 * current direction facing to that method.
	 */
	protected void pathFinding() {
		// checks if the current position has moved a full tile
		// we know it has moved a full tile if the decimals used for the
		// positions no remainder.
		if (this.getObjectPosition()[0].stripTrailingZeros().scale() <= 0
				&& this.getObjectPosition()[1].stripTrailingZeros()
						.scale() <= 0) {
			boolean front = false;
			boolean left = false;
			boolean right = false;
			boolean back = false;

			// get the left
			if (this.currentLevel.tileAvailable(this.getObjectPosition()[0],
					this.getObjectPosition()[1], getDirection('L'))) {
				left = true;
			}
			// get the right
			if (this.currentLevel.tileAvailable(this.getObjectPosition()[0],
					this.getObjectPosition()[1], getDirection('R'))) {
				right = true;
			}
			// get the behind
			if (this.currentLevel.tileAvailable(this.getObjectPosition()[0],
					this.getObjectPosition()[1], getDirection('B'))) {
				back = true;
			}
			// get the front
			if (this.currentLevel.tileAvailable(this.getObjectPosition()[0],
					this.getObjectPosition()[1], this.directionFacing)) {
				front = true;
			}
			// if the only available direction is back then turn and move
			// backwards
			if (left == false && right == false && front == false
					&& back == true) {
				this.directionFacing = getDirection('B');
			} else {
				// pick a random direction
				ArrayList<Character> avalDirections = new ArrayList<Character>();

				// add all available directions to an arraylist and pick a
				// random one.
				if (front) {
					avalDirections.add('F');
				}

				if (left) {
					avalDirections.add('L');
				}

				if (right) {
					avalDirections.add('R');
				}

				// if there is more than one available direction then randomise
				// the direction.
				// if not then then get the direction at index 0.
				if (avalDirections.size() > 1) {
					int min = 1;
					int max = avalDirections.size() - 1;

					Random rand = new Random();
					int randomIndex = (rand.nextInt(max + min) + min) - 1;
					this.directionFacing = getDirection(
							avalDirections.get(randomIndex));
				} else if (avalDirections.size() == 1) {
					this.directionFacing = getDirection(avalDirections.get(0));
				}
			}
		}
	}

	/**
	 * A method which handles the movement of a rat, it calls the path finding
	 * method and incriment the rat's current position by it's speed in the
	 * direction it's currently facing.
	 */
	protected void movement() {
		// calls the path finding method to set the current rat direction
		// facing.
		pathFinding();
		// check the direction the rat is facing and incriment position based on
		// that.
		// also change the rat sprite based on direction.
		if (this.directionFacing == 'N') {
			// minus y
			this.setObjectPosition(this.getObjectPosition()[0],
					this.getObjectPosition()[1].subtract(this.ratSpeed));
			this.setSprite(this.ratSpriteNorth);
		} else if (this.directionFacing == 'E') {
			// plus x
			this.setObjectPosition(
					this.getObjectPosition()[0].add(this.ratSpeed),
					this.getObjectPosition()[1]);
			this.setSprite(this.ratSpriteEast);
		} else if (this.directionFacing == 'S') {
			// plus y
			this.setObjectPosition(this.getObjectPosition()[0],
					this.getObjectPosition()[1].add(this.ratSpeed));
			this.setSprite(this.ratSpriteSouth);
		} else if (this.directionFacing == 'W') {
			// minus x
			this.setObjectPosition(
					this.getObjectPosition()[0].subtract(this.ratSpeed),
					this.getObjectPosition()[1]);
			this.setSprite(this.ratSpriteWest);
		}

	}

	/**
	 * Takes in a direction e.g left or right and returns the cardinal direction
	 * of that direction relative to direction facing. This is used for path
	 * finding
	 * 
	 * @param direction
	 * @return character representing a cardinal direction.
	 */
	public char getDirection(char direction) {
		// initialises default variables.
		char left = 'N';
		char right = 'N';
		char back = 'N';

		// switch case which takes in the direction the rat is currently facing
		// gets the direction which is left, right and behind.
		switch (this.directionFacing) {
			case 'N':
				left = 'W';
				right = 'E';
				back = 'S';
				break;
			case 'E':
				left = 'N';
				right = 'S';
				back = 'W';
				break;
			case 'S':
				left = 'E';
				right = 'W';
				back = 'N';
				break;
			case 'W':
				left = 'S';
				right = 'N';
				back = 'E';
				break;
		}

		// returns the desired the direction entered.
		if (direction == 'L') {
			return left;
		} else if (direction == 'R') {
			return right;
		} else if (direction == 'B') {
			return back;
		} else {
			return this.directionFacing;
		}
	}

	public void turnAround() {
		switch (this.directionFacing) {
			case 'N':
				this.directionFacing = 'S';
				break;
			case 'E':
				this.directionFacing = 'W';
				break;
			case 'S':
				this.directionFacing = 'N';
				break;
			case 'W':
				this.directionFacing = 'E';
				break;
			default:
				break;
		}
	}

	public void changeSprites(String changeMode) {

	}
}
