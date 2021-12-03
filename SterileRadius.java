import java.math.*;
import javafx.scene.image.Image;

public class SterileRadius extends CollideItem {
	
	private Sterilisation sterileParent;
	private int STERILE_DURATION = 200;
	
	public SterileRadius(Position position, Level currentLevel, Sterilisation parent, String image) {
		this.objectPosition = position;
		this.currentLevel = currentLevel;
		this.sterileParent = parent;
		
		if (image != "") {
			this.renderSprite = new Image(image);
		} else {
			this.renderSprite = new Image("Textures/tr.png");
		}
		
	}

	@Override
	public void collision(Object collidedObject) {
		// TODO Auto-generated method stub
		if (collidedObject instanceof NormalRat) {
			System.out.println("STERILE COLLIDE");
			((NormalRat) collidedObject).becomeSterile();
		}
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if (this.sterileParent.getTick() >= STERILE_DURATION) {
			this.currentLevel.despawnTempTile(this);
		}
	}
}