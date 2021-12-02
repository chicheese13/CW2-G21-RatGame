import javafx.scene.image.Image;

public class Poison extends CollideItem {

	private Image poison = new Image("/items/poisononlevel.png");


	public Poison(Position objectPosition, Level currentLevel) {
		this.renderSprite = poison;
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
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
			this.currentLevel.despawnItem(this);
		}
	}
}
