import javafx.scene.image.Image;

public class Bomb extends Item {
	
	
	private Image bomb = new Image("/items/bomb.png");
	private Image bomb1 = new Image("/items/bomb1.png");
	private Image bomb2 = new Image("/items/bomb2.png");
	private Image bomb3 = new Image("/items/bomb3.png");
	private Image bomb4 = new Image("/items/bomb4.png");
	private Image bomb5 = new Image("/items/bomb5.png");
	private Image bomb7 = new Image("/items/bomb7.png");
	
	private Image currentSprite = bomb;
	
	private int tickCounter = 0;
	
	public Bomb (int x, int y) {
		this.setLocation(x, y);
		
	}
	
	public void startTimer() {
		//after few ticks bomb explodes
		explode();
	}
	
	public void tick() {
		this.tickCounter++;
		if(tickCounter == 1) {
			currentSprite = bomb1;
		}
		if(tickCounter == 2) {
			currentSprite = bomb2;
		}
		
		if(tickCounter == 3) {
			currentSprite = bomb1;
		}
		
		if(tickCounter == 4) {
			currentSprite = bomb2;
		}
		
		if(tickCounter == 5) {
			currentSprite = bomb1;
		}
		if(tickCounter == 6) {
			currentSprite = bomb2;
		}
		
		if(tickCounter == 7) {
			currentSprite = bomb3;
		}
		
		if(tickCounter == 10) {
			currentSprite = bomb3;
		}	
		
		if(tickCounter == 11) {
			currentSprite = bomb4;
		}
		
		if(tickCounter == 12) {
			currentSprite = bomb5;
		}
		
		if(tickCounter == 14) {
			currentSprite = bomb7;
		}
	}
	
	private void explode() {
		//3x3 Tiles.getDamage();
		Bomb disappears;
	}
	
	public Image getSprite() {
		return this.currentSprite;
	}

}
