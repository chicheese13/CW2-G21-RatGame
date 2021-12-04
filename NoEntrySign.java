import javafx.scene.image.Image;

public class NoEntrySign extends CollideItem {

	private Image noEntrySign = new Image("/items/noentrysignonlevel.png");

	private final int NUMBER_OF_SIGN_FRAMES = 5;

	public int signHealth = 5;
	public boolean ratStompsOnSign = false;
	private int pictureNumber = 1;

	public NoEntrySign(Position objectPosition, Level currentLevel) {
		this.renderSprite = noEntrySign;
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
	}

	private Image loadImage(int pictureNumber) {

		Image sign = new Image("/noentrysign_images/noentrysign" + String.valueOf(pictureNumber) + ".png");

		return sign;
	}

	// method that gets run each time a rat hits a sign
	private void breakSign() {

		if (signHealth > 1) {
			signHealth--;
			if (pictureNumber < NUMBER_OF_SIGN_FRAMES) {
				pictureNumber++;
				this.renderSprite = loadImage(pictureNumber);
			}
		} else {
			this.currentLevel.despawnItem(this);
		}
	}

	// gets the signs health
	private int getSignHealth() {
		return signHealth;
	}

	public void tick() {

	}

	// If rat stomps on a sign, it is being pushed back and it starts moving
	// different direction, the sign loses health points
	public void collision(Object paramater) {
		if (paramater instanceof Rat) {
			((Rat) paramater).turnAround();
			this.currentLevel.spawnSound("signHit" + pictureNumber);
			breakSign();
		}
	}

}
