/** 
 * RenderScore.java
 * @version 1.0
 * @author Dylan Lewis, Kien Lin
 *
 */

import javafx.scene.image.Image;

/**
 * RenderScore is a RenderObject which displays a score on a tile.
 */
public class RenderScore extends RenderObject {

	/**
	 * Sprites for different potential scores.
	 */
	private Image PLUS_TEN_SPRITE = new Image("Textures/plus-ten.png");
	
	/**
	 * Tick limit is the number of ticks the score will stay for, 67 is just over 1 second worth of ticks.
	 */
	private int TICK_LIMIT = 67;
	
	/**
	 * Keeps count of ticks passed.
	 */
	private int tickCounter = 0;
	
	/**
	 * Constrcutor
	 */
	public RenderScore(Position scorePosition, int score, TestLevel currentLevel) {
		this.objectPosition = scorePosition;
		this.currentLevel = currentLevel;
		
		if (score == 10) {
			this.renderSprite = PLUS_TEN_SPRITE;
		}
	}
	
	/**
	 * Method which handles tick behaviour of RenderScore.
	 */
	public void tick() {
		//after 1 second remove self from render array.
		this.tickCounter++;
		
		if (tickCounter > TICK_LIMIT) {
			//remove self from render array in the current level.
			this.removeSelf();
		}
	}
}
