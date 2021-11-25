import javafx.scene.image.Image;

public class NoEntrySign extends Item {

	private Image noEntrySign = new Image("/items/noentrysign.png");

	private final int NUMBER_OF_SIGN_FRAMES = 5;

	public int signHealth = 100;
	public boolean ratStompsOnSign = false;
	private int pictureNumber = 1;

	private TestLevel currentLevel;

	public NoEntrySign(Position objectPosition, TestLevel currentLevel) {
		this.renderSprite = noEntrySign;
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
	}

	private Image loadImage(int pictureNumber) {

		Image bomb = new Image("/noentrysign_images/noentrysign" + String.valueOf(pictureNumber) + ".png");

		return bomb;
	}

	// method that gets run each time a rat hits a sign
	private void breakSign() {
		if (ratStompsOnSign()) {

			signHealth = getSignHealth() - 20;

			if (pictureNumber < NUMBER_OF_SIGN_FRAMES) {
				pictureNumber++;
				this.renderSprite = loadImage(pictureNumber);
				
			}

			if (signHealth == 0) {
				for (int i = 0; i < this.currentLevel.getRenderObjects().size(); i++) {
					if (this.currentLevel.getRenderObjects().get(i) == this) {
						currentLevel.getRenderObjects().remove(i);
					}
				}
			}
		}
	}
	// If rat stomps on a sign, it is being pushed back and it starts moving different direction (we need to code it)
	public boolean ratStompsOnSign() {
		// if (RenderObject.getObjectPosition() == Rat.getRatPosition()) {
		// return true;
		// } else {
		return false;
		// }
	}

	// gets the signs health
	private int getSignHealth() {
		return signHealth;
	}

	public void tick() {

	}

}
