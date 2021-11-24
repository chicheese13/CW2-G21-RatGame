
import javafx.scene.image.Image;

public class Bomb extends Item {

	private Image bomb = new Image("/items/bomb.png");

	private double tickCounter = 0;
	private double delayCount = 0;
	private int animationCounter = 0;
	private int pictureNumber = 0;

	private TestLevel currentLevel;

	public Bomb(Position objectPosition, TestLevel currentLevel) {
		this.renderSprite = bomb;
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
	}

	public void startTimer() {
		// after few ticks bomb explodes
		explode();
	}

	private Image loadImage(int pictureNumber) {

		Image bomb = new Image("/bomb_images/bomb" + String.valueOf(pictureNumber) + ".png");

		return bomb;
	}

	public void tick() {

		delayCount = delayCount + 12.5;
		if (delayCount > 4000) {
			
			if (pictureNumber < 17) {
				pictureNumber++;
			}

			if ((tickCounter == animationCounter) && (animationCounter <= 1700)) {
				this.renderSprite = loadImage(pictureNumber);
			}

			tickCounter = tickCounter + 20;

			if (tickCounter % 100 == 0) {
				animationCounter = animationCounter + 100;
			}

			if (tickCounter > 500) {
				for (int i = 0; i < this.currentLevel.getRenderObjects().size(); i++) {
					if (this.currentLevel.getRenderObjects().get(i) == this) {
						currentLevel.getRenderObjects().remove(i);
					}
				}
			}
		}
	}

	public void resetTick() {
		tickCounter = 0;
	}

	private void explode() {
		// 3x3 Tiles.getDamage();
		Bomb disappears;
	}

	public Image getSprite() {
		return this.renderSprite;
	}

}
