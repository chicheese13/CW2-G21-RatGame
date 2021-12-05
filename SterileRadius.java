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
			this.renderSprite = image;
		} else {
			this.renderSprite = "tr";
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
		System.out.println("STERILE CHILD");
		System.out.println(this.sterileParent.getTick());
		// TODO Auto-generated method stub
		if (this.sterileParent.getTick() >= STERILE_DURATION-1) {
			System.out.println("REMOVE STERILE");
			System.out.println(currentLevel);
			this.currentLevel.despawnTempTile(this);
		}
	}
}