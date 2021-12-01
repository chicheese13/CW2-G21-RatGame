import javafx.scene.image.Image;

public class Gas extends CollideItem {

	private Image gas = new Image("/items/gasonlevel.png");

	private TestLevel currentLevel;

	public Gas (Position objectPosition, TestLevel currentLevel) {
		this.renderSprite = gas;
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
	}
	public void startGas () {
		//starts gas
	} 
	
	public void tick() {
		
	}
	
	public void collision(Object collidedObject) {
		
	}
}
