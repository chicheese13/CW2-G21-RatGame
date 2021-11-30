import javafx.scene.image.Image;

public class Sterilisation extends Item {
	
	private Image sterilisation = new Image("/items/sterilisationonlevel.png");

	private TestLevel currentLevel;

	public Sterilisation(Position objectPosition, TestLevel currentLevel) {
		this.renderSprite = sterilisation;
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
	}
	
	public void tick() {
		
	}
}
