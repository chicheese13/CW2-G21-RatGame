import javafx.scene.image.Image;

public class Poison extends Item {
	
	private Image poison = new Image("/items/poisononlevel.png");

	private TestLevel currentLevel;

	public Poison(Position objectPosition, TestLevel currentLevel) {
		this.renderSprite = poison;
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
	}
	
	private void killRat() {
		//Rat.getRatHealth() = Rat.getRatHealth() - 100;
	}
	
	public void tick() {
		
	}
}
