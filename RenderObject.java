
/** 
 * RenderObject.java
 * @version 2.0
 * @author Dylan Lewis, Kien Lin, Callum, Armand
 *
 */

import javafx.scene.image.Image;
import java.math.BigDecimal;

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
	
	protected TestLevel currentLevel;
	
	/**
	 * getter for position
	 * splits into x and y array instead of just a position object.
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
	public void setSprite(Image newSprite) {
		this.renderSprite = newSprite;
	}
	
	/**
	 * Method for a RenderObject to remove themselves from the board.
	 */
	public void removeSelf() {
		for (int i = 0; i < this.currentLevel.getRenderObjects().size(); i++) {
			if (this.currentLevel.getRenderObjects().get(i) == this) {
				this.currentLevel.removeRenderObject(i);
				//prematurely exits for loop as we have found what we need.
				i = this.currentLevel.getRenderObjects().size();
			}
		}
	}
	
	/**
	 * Abstract method for behaviour of a render object per tick.
	 */
	public abstract void tick();
}






