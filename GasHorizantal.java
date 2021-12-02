import javafx.scene.image.Image;

public class GasHorizantal extends CollideItem {

	public GasHorizantal (Position position, Level currentLevel) {
		this.renderSprite = new Image("Textures/gas-horizantal.png");
		this.objectPosition = position;
		this.currentLevel = currentLevel;
	}
	
	@Override
	public void collision(Object collidedObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

}