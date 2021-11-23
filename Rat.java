
/** 
 * Rat.java
 * @version 1.0
 * @author Dylan Lewis, Kien Lin
 *
 */

import javafx.scene.image.Image;

/**
 * Rat is an abstract class responsible for rat health/damage, rat’s movement
 * speed and the rat movement behaviour.
 *
 */

public abstract class Rat {
	/**
	 * The maximum amount of health a rat can have.
	 */
	protected final int MAX_RAT_HEALTH = 5;
	/**
	 * Health of the rat.
	 */
	protected int ratHealth;
	/**
	 * Speed of the rat.
	 */
	protected int ratSpeed;
	/**
	 * Position of the rat.
	 */
	protected Position ratPosition;
	/**
	 * The image which represent the rat.
	 */
	protected Image ratSprite;
	/**
	 * The direction in which the rat is facing.
	 */
	protected char directionFacing;
<<<<<<< HEAD
	
	
	//might not keep
	/**
	 * The level the rat is currently in.
	 */
	protected TestLevel currentLevel;
=======
>>>>>>> parent of 835f125 (Rendering Rats)

	/**
	 * movement() is a method which handles the movement behaviour of a rat.
	 */
	protected void movement() {
		// the move method will go here.
		// the move method will go here.
		int[] currentPosition = this.getRatPosition();
		// incriment x
		System.out.println(currentPosition[0]);
		this.setRatPosition(currentPosition[0] + 1, currentPosition[1]);
	}

	/**
	 * Sets the ratHealth attribute of the rat object.
	 * 
	 * @param ratHealth - the value of health you are setting the ratHealth
	 *                  attribute to.
	 */
	public void setRatHealth(int ratHealth) {
		this.ratHealth = ratHealth;
	}

	/**
	 * Sets the position attribute of the rat,
	 * 
	 * @param x - the x coordinate of the rat.
	 * @param y - the y coordinate of the rat.
	 */
	public void setRatPosition(int x, int y) {
		this.ratPosition.setPosition(x, y);
	}

	/**
	 * Returns the attribute ratHealth.
	 * 
	 * @return ratHealth
	 */
	public int getRatHealth() {
		return this.ratHealth;
	}

	/**
	 * Returns the rat's current position.
	 * 
	 * @return ratPosition - an array of consisting of the rat's x and y
	 *         coordinates.
	 */
	public int[] getRatPosition() {
		return this.ratPosition.getPosition();
	}

	// maybe a method for takeDamage.
	/**
	 * Reduces rat's health by a desired value.
	 * 
	 * @param damage - the value of damage that the rat is going to have taken off
	 *               its health.
	 */
	public void takeDamage(int damage) {
		this.ratHealth = this.ratHealth - damage;
	}

	/**
	 * Abstract method which defines what happens every tick for a rat.
	 */
	abstract void tick();

	public Image getSprite() {
		return this.ratSprite;
	}
}
