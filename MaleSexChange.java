import javafx.scene.image.Image;

public class MaleSexChange extends Item {
	
	
	private Image maleSexChange = new Image("/items/malesexchangeronlevel.png");

	private TestLevel currentLevel;

	public MaleSexChange(Position objectPosition, TestLevel currentLevel) {
		this.renderSprite = maleSexChange;
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
	}
	
	public void ratToM() {
		//runs rat gender change method in rat classes
	}
	
	public void tick() {
		
	}
}
