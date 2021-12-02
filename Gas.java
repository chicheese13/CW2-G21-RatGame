import javafx.scene.image.Image;

public class Gas extends CollideItem {

	private Image gas = new Image("/items/gasonlevel.png");

	private int pictureNumber = 5;
	private int tickCounter = 0;
	private double endCounter = 0;

	public Gas(Position objectPosition, Level currentLevel) {
		this.renderSprite = gas;
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
	}

	private Image loadImage(int pictureNumber) {

		Image gas = new Image("/gas_images/Sprinkler" + String.valueOf(pictureNumber) + ".png");

		return gas;
	}

	public void startGas() {
		// starts gas
	}

	public void tick() {

		endCounter = endCounter + 12.5;

		if (tickCounter >= 60) {

			tickCounter = 0;

			if (pictureNumber == 5) {
				pictureNumber = 1;
			} else {
				pictureNumber++;
			}
			this.renderSprite = loadImage(pictureNumber);
		} else if (endCounter >= 4000) {
			this.currentLevel.despawnItem(this);

		} else {
			tickCounter = tickCounter + 15;
		}

	}

	public void collision(Object collidedObject) {

	}
}
