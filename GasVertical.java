import javafx.scene.image.Image;

public class GasVertical extends CollideItem {

	public GasVertical (Position position, Level currentLevel) {
		this.renderSprite = new Image("Textures/gas-vertical.png");
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
