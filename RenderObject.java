import java.io.*;
/** 
 * RenderObject.java
 * @version 2.0
 * @author Dylan Lewis, Kien Lin, Callum Young, Armand Dorosz
 *
 */

import java.math.BigDecimal;

public abstract class RenderObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * renderSprite is an image of the object being rendered.
	 */
	protected String renderSprite;
	/**
	 * objectPosition is the current position of object being rendered.
	 */
	protected Position objectPosition;

	/**
	 * getter for renderSprite.
	 */
	public String getSprite() {
		return this.renderSprite;
	}

	protected Level currentLevel;

	/**
	 * getter for position splits into x and y array instead of just a position
	 * object.
	 */
	public BigDecimal[] getObjectPosition() {
		return this.objectPosition.getPosition();
	}

	/**
	 * Setter of the objectPosition.
	 */
	public void setObjectPosition(BigDecimal x, BigDecimal y) {
		this.objectPosition.setPosition(x, y);
	}

	/**
	 * Setter for renderSprite.
	 */
	public void setSprite(String newSprite) {
		this.renderSprite = newSprite;
	}

	/**
	 * Method for a RenderObject to remove themselves from the board.
	 */
	public void removeSelf() {

	}

	/**
	 * Abstract method for behaviour of a render object per tick.
	 */
	public abstract void tick();

	public abstract void collision(Object collidedObject);

	public void setLevel(Level inputLevel) {
		this.currentLevel = inputLevel;
	}
}
