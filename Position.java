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
//change doubles to doubles when implementing smooth movement.
public class Position {
	/**
	 * the x coord for a position.
	 * the y coord for a position.
	 */
	private double positionX;
	private double positionY;
	
	/**
	 * This defines a position of x and y.
	 * @param x
	 * @param y
	 */
	public Position (double x, double y) {
		this.positionX = x;
		this.positionY = y;
	}
	
	/**
	 * Returns the x and y coord as an array of doubleegers.
	 *	@return 1D array consisting of x and y.
	 */
	public double[] getPosition () {
		double retrunArray[] = {positionX, positionY};
		return retrunArray;
	}
	
	/**
	 * Overwrites the current x and y of a position,
	 */
	public void setPosition (double x, double y) {
		this.positionX = x;
		this.positionY = y;
	}
}
