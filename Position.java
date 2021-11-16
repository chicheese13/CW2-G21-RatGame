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

public class Position {
	private int positionX;
	private int positionY;
	
	public Position (int x, int y) {
		this.positionX = x;
		this.positionY = y;
	}
	
	public int[] getPosition () {
		int retrunArray[] = {positionX, positionY};
		return retrunArray;
	}
	
	public void setPosition (int x, int y) {
		this.positionX = x;
		this.positionY = y;
	}
}
