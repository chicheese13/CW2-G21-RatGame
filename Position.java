/** 
 * Position.java
 * @version 1.0
 * @author Dylan Lewis, Kien Lin
 *
 */

/**
 * Position is a class which defines a datatype Position which contains an x and y coord.
 *
 */
//change ints to floats when implementing smooth movement.
public class Position {
	/**
	 * the x coord for a position.
	 * the y coord for a position.
	 */
	private int positionX;
	private int positionY;
	
	/**
	 * This defines a position of x and y.
	 * @param x
	 * @param y
	 */
	public Position (int x, int y) {
		this.positionX = x;
		this.positionY = y;
	}
	
	/**
	 * Returns the x and y coord as an array of integers.
	 *	@return 1D array consisting of x and y.
	 */
	public int[] getPosition () {
		int retrunArray[] = {positionX, positionY};
		return retrunArray;
	}
	
	/**
	 * Overwrites the current x and y of a position,
	 */
	public void setPosition (int x, int y) {
		this.positionX = x;
		this.positionY = y;
	}
}
