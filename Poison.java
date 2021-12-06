
public class Poison extends RenderObject {


	public Poison(Position objectPosition, Level currentLevel) {
		this.renderSprite = "poison";
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
		this.currentLevel.spawnSound("Poison");
	}

	private void killRat(NormalRat rat) {
		if (rat instanceof NormalRat) {
			((NormalRat) rat).ratDeath();
		}
	}

	public void tick() {

	}

	/**
	 * Method for killing collided rats.
	 */

	public void collision(Object parameter) {
		if (parameter instanceof NormalRat) {
			killRat((NormalRat) parameter);
			this.currentLevel.spawnSound("PoisonCollision");
			this.currentLevel.despawnItem(this);
		}
	}
}
