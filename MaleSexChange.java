import javafx.scene.image.Image;

public class MaleSexChange extends CollideItem {
	
	
	private Image maleSexChange = new Image ("/items/malesexchangeronlevel.png");


	public MaleSexChange(Position objectPosition, Level currentLevel) {
		this.renderSprite = "male-sex-change";
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
		this.currentLevel.spawnSound("placeItem");
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
			this.currentLevel.spawnSound("MaleSexChange");
			this.currentLevel.despawnItem(this);
		}
	}
	
}
