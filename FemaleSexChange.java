import javafx.scene.image.Image;

public class FemaleSexChange extends CollideItem {
	
	
	private Image femaleSexChange = new Image("/items/femalesexchangeronlevel.png");

	private TestLevel currentLevel;

	public FemaleSexChange(Position objectPosition, TestLevel currentLevel) {
		this.renderSprite = femaleSexChange;
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
	}

//	public void ratToF(rat)

	public void ratToF() {
		//runs rat gender change method in rat classes
	}
	
	public void tick() {
		
	}
	
	public void collision(Object collidedObject) {
		
	}
}
