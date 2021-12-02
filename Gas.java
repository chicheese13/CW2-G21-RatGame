import javafx.scene.image.Image;

public class Gas extends CollideItem {

	private Image gas = new Image("/items/gasonlevel.png");



	public Gas(Position objectPosition, Level currentLevel) {
		this.renderSprite = gas;
		this.objectPosition = objectPosition;
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
