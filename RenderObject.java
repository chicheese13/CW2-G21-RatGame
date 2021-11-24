
/** 
 * RenderObject.java
 * @version 1.0
 * @author Dylan Lewis, Kien Lin, Callum, Armand
 *
 */

import javafx.scene.image.Image;

/**
 * RenderObject defines objects which can be rendered, they all sprites and position in common.
 *
 */

public abstract class RenderObject {
	/**
	 * renderSprite is an image of the object being rendered.
	 */
	protected Image renderSprite;
	/**
	 * objectPosition is the current position of object being rendered.
	 */
	protected Position objectPosition;
	
	/**
	 * getter for renderSprite.
	 */
	public Image getSprite() {
		return this.renderSprite;
	}
	
	/**
	 * getter for position
	 * splits into x and y array instead of just a position object.
	 */
	public double[] getObjectPosition() {
		return this.objectPosition.getPosition();
	}
	
	
	//setter for object position 
	public void setObjectPosition(double x, double y) {
		this.objectPosition.setPosition(x, y);
	}
	
	public abstract void tick();
}






