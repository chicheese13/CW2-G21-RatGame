import javafx.scene.image.Image;

public class DeathRatSpawner extends Item {
 
	private Image deathRat = new Image("/items/deathratonlevel.png");

	private TestLevel currentLevel;

	public DeathRatSpawner(Position objectPosition, TestLevel currentLevel) {
		this.renderSprite = deathRat;
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
	}
	
	public void tick() {
		
	}
}
