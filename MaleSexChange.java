import javafx.scene.image.Image;

public class MaleSexChange extends CollideItem {
	
	
	private Image maleSexChange = new Image("/items/malesexchangeronlevel.png");


	public MaleSexChange(Position objectPosition, Level currentLevel) {
		this.renderSprite = maleSexChange;
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
	}
	
	public void ratToM(NormalRat collidedRat) {
		//runs rat gender change method in rat classes
		collidedRat.changeToMale();
	}
	
	public void tick() {
		
	}
	
	public void collision(Object collidedObject) {
		if (collidedObject instanceof NormalRat) {
			ratToM((NormalRat) collidedObject);
			this.currentLevel.despawnItem(this);
		}
	}
	
}
