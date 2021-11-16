/** 
 * Rat.java
 * @version 1.0
 * @author Dylan Lewis, Kien Lin
 *
 */

/**
 * Rat is an abstract class responsible for rat damage, rat’s movement speed and the rat movement behaviour.
 *
 */

public abstract class Rat {
	protected final int RAT_HEALTH = 100;
	protected int ratHealth;
	protected int ratSpeed;
	protected Position ratPosition;
	
	
	/**
	 * movement() is a method which handles the movement behaviour of a rat.
	 */
	protected void movement() {
		//the move method will go here.
	}

	
	public void setRatHealth (int ratHealth) {
		this.ratHealth = ratHealth;
	}

	public void setRatPosition(int x, int y) {
		this.ratPosition.setPosition(x, y);
	}
	
	
	/**
	 * @return ratHealth
	 */
	public int getRatHealth() {
		return this.ratHealth;
	}
	
	/**
	 * @return ratPosition 
	 */
	public int[] getRatPosition() {
		return this.ratPosition.getPosition();
	}	
}
