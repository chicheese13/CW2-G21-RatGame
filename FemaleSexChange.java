import javafx.scene.image.Image;

public class FemaleSexChange extends CollideItem {
	
	
	private Image femaleSexChange = new Image("/items/femalesexchangeronlevel.png");


	public FemaleSexChange(Position objectPosition, Level currentLevel) {
		this.renderSprite = femaleSexChange;
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
	}

//	public void ratToF(rat)

	public void ratToF(NormalRat collidedObject) {
		//runs rat gender change method in rat classes
		collidedObject.changeToFemale();
	}
	
	public void tick() {
		
	}
	
	public void collision(Object collidedObject) {
		if (collidedObject instanceof NormalRat) {
			ratToF((NormalRat) collidedObject);
			this.currentLevel.despawnItem(this);
		}
	}
}
