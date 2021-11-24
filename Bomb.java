
import javafx.scene.image.Image;

public class Bomb extends Item {

	private Image bomb = new Image("/items/bomb.png");
	
	private final int NUMBER_OF_BOMB_FRAMES = 17;
	private final int EXPLOSION_FRAME = 6;

	private double tickCounter = 0;
	private double delayCount = 0;
	private int frameTime = 0;
	private int pictureNumber = 1;
	private int delayedPictureNumber = 4;

	private TestLevel currentLevel;

	public Bomb(Position objectPosition, TestLevel currentLevel) {
		this.renderSprite = bomb;
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
	}

	public void startTimer() {

		if ((tickCounter == frameTime)) {
			if (pictureNumber == EXPLOSION_FRAME) {
				explode();
			}
			this.renderSprite = loadImage(pictureNumber);
			if (pictureNumber < NUMBER_OF_BOMB_FRAMES) {
				pictureNumber++;
			}
		}

		tickCounter = tickCounter + 100;

		if (tickCounter % 100 == 0) {
			frameTime = frameTime + 100;
		}

		if (frameTime > NUMBER_OF_BOMB_FRAMES * 100) {
			for (int i = 0; i < this.currentLevel.getRenderObjects().size(); i++) {
				if (this.currentLevel.getRenderObjects().get(i) == this) {
					currentLevel.getRenderObjects().remove(i);
				}
			}
		}
	}

	private Image loadImage(int pictureNumber) {

		Image bomb = new Image("/bomb_images/bomb" + String.valueOf(pictureNumber) + ".png");

		return bomb;
	}
	
	private Image loadDelayImage(int delayedPictureNumber) {

		Image delayBomb = new Image("/delayed_bomb_images/bomb" + String.valueOf(delayedPictureNumber) + ".png");

		return delayBomb;
	}

	public void tick() {

		if (delayCount % 1000 == 0) {
			this.renderSprite = loadDelayImage(delayedPictureNumber);
			if (delayedPictureNumber > 1) {
				delayedPictureNumber--;
			}
		}

		delayCount = delayCount + 12.5;

		if (delayCount > 4000) {
			startTimer();
		}
	}

	public void resetTick() {
		tickCounter = 0;
	}

	// Method to deal damage horizontally and vertically
	private void explode() {
		System.out.println("BOOM");
	}

	public Image getSprite() {
		return this.renderSprite;
	}

}
