/**
 * @version 2.0
 * @author Armand Dorosz, Callum Young
 * 
 *         Poison class, which generates ready instance of the poison, which is
 *         placed on the level
 *
 */
public class Poison extends RenderObject {

	/**
	 * Instantiates a new poison
	 * 
	 * @param objectPosition
	 * @param currentLevel
	 */
	public Poison(Position objectPosition, Level currentLevel) {
		this.renderSprite = "poison";
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
		this.currentLevel.spawnSound("Poison");
	}

	/**
	 * Method for immadiate rat death
	 * 
	 * @param rat
	 */
	private void killRat(NormalRat rat) {
		if (rat instanceof NormalRat) {
			((NormalRat) rat).ratDeath();
		}
	}

	public void tick() {

	}

	/**
	 * Method for killing collided rats
	 */

	public void collision(Object parameter) {
		if (parameter instanceof NormalRat) {
			killRat((NormalRat) parameter);
			this.currentLevel.spawnSound("PoisonCollision");
			this.currentLevel.despawnItem(this);
		}
	}
}
