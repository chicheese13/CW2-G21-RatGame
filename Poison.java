import javafx.scene.image.Image;

public class Poison extends Item {

	private Image poison = new Image("/items/poisononlevel.png");

	private TestLevel currentLevel;

	public Poison(Position objectPosition, TestLevel currentLevel) {
		this.renderSprite = poison;
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
	}

	private void killRat(Object rat) {
		if (rat instanceof Rat) {
			((NormalRat) rat).ratDeath();
			for (int i = 0; i < this.currentLevel.getRenderObjects().size(); i++) {
				if (this.currentLevel.getRenderObjects().get(i) == this) {
					currentLevel.getRenderObjects().remove(i);

				}
			}
			
		}
	}

	public void tick() {

	}

	/**
	 * Method for killing collided rats.
	 */

	public void collision(Object parameter) {
		killRat(parameter);
	}
}
