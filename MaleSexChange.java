import javafx.scene.image.Image;

public class MaleSexChange extends CollideItem {
	
	
	private Image maleSexChange = new Image("/items/malesexchangeronlevel.png");


	public MaleSexChange(Position objectPosition, Level currentLevel) {
		this.renderSprite = maleSexChange;
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
		SoundClip placeItem = new SoundClip("placeItem");
		placeItem.play();
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
			SoundClip MaleChange = new SoundClip("MaleSexChange");
			MaleChange.play();
			this.currentLevel.despawnItem(this);
		}
	}
	
}
