/** 
 * Rat.java
 * @version 1.0
 * @author Dylan Lewis, Kien Lin
 *
 */

/**
 * Rat is an abstract class responsible for rat health/damage, rat’s movement speed and the rat movement behaviour.
 *
 */

public abstract class Rat {
	/**
	 * 	The maximum amount of health a rat can have.
	 */
	protected final int MAX_RAT_HEALTH = 5;
	/**
	 * 	Health of the rat.
	 */
	protected int ratHealth;
	/**
	 * 	Speed of the rat.
	 */
	protected int ratSpeed;
	/**
	 * 	Position of the rat.
	 */
	protected Position ratPosition;
	
	
	/**
	 * movement() is a method which handles the movement behaviour of a rat.
	 */
	protected void movement() {
		//the move method will go here.
	}

	/**
	 *  Sets the ratHealth attribute of the rat object.
	 *  @param ratHealth - the value of health you are setting the ratHealth attribute to.
	 */
	public void setRatHealth (int ratHealth) {
		this.ratHealth = ratHealth;
	}

	/**
	 *  Sets the position attribute of the rat,
	 *  @param x - the x coordinate of the rat.
	 *  @param y - the y coordinate of the rat.
	 */
	public void setRatPosition(int x, int y) {
		this.ratPosition.setPosition(x, y);
	}
	
	
	/**
	 * Returns the attribute ratHealth.
	 * @return ratHealth
	 */
	public int getRatHealth() {
		return this.ratHealth;
	}
	
	/** Returns the rat's current position.
	 * @return ratPosition - an array of consisting of the rat's x and y coordinates. 
	 */
	public int[] getRatPosition() {
		return this.ratPosition.getPosition();
	}
	
	
	//maybe a method for takeDamage.
	/** Reduces rat's health by a desired value.
	 * @param damage - the value of damage that the rat is going to have taken off its health.
	 */
	public void takeDamage(int damage) {
		this.ratHealth = this.ratHealth - damage;
	}
	
	//maybe a method for javafx, actually placing image of rat on the board.
	//maybe a method for javafx which removes the rat sprite.
}
