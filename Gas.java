import javafx.scene.image.Image;

public class Gas extends CollideItem {

	private Image gas = new Image("/items/gasonlevel.png");

	private TestLevel currentLevel;
	private boolean isNorth = false;
	private boolean isSouth = false;
	private boolean isEast = false;
	private boolean isWest = false;

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
	
	public void movement() {
		
	}
	
	public void pathFinding() {
		//path finding algorithm for gas
		
		//pick every available tile
		//move in that direction
		
	}
	
	public void collision(Object collidedObject) {
		
	}
}
