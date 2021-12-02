import javafx.scene.image.Image;

public class Sterilisation extends CollideItem {
	
	private Image sterilisation = new Image("/items/sterilisationonlevel.png");

	public Sterilisation(Position objectPosition, Level currentLevel) {
		this.renderSprite = sterilisation;
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
	}
	
	public void tick() {
		
	}
	
	public void collision(Object parameter) {
		if (parameter instanceof NormalRat) {
			((NormalRat) parameter).becomeSterile();
			SoundClip sterile = new SoundClip("Syringe");
			sterile.play();
			this.currentLevel.despawnItem(this);
		}
	}
}
