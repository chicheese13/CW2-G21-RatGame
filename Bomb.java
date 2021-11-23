import javafx.scene.image.Image;

public class Bomb extends Item {
	
	
	private Image bomb = new Image("/items/bomb.png");
	private Image bomb1 = new Image("/items/bomb1.png");
	private Image bomb2 = new Image("/items/bomb2.png");
	private Image bomb3 = new Image("/items/bomb3.png");
	private Image bomb4 = new Image("/items/bomb4.png");
	private Image bomb5 = new Image("/items/bomb5.png");
	
	private Image explode1 = new Image("/items/explode1.png");
	private Image explode2 = new Image("/items/explode2.png");
	private Image explode3 = new Image("/items/explode3.png");
	private Image explode4 = new Image("/items/explode4.png");
	private Image explode5 = new Image("/items/explode5.png");
	private Image explode6 = new Image("/items/explode6.png");
	private Image explode7 = new Image("/items/explode7.png");
	private Image explode8 = new Image("/items/explode8.png");
	private Image explode9 = new Image("/items/explode9.png");
	private Image explode10 = new Image("/items/explode10.png");
	
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
			currentSprite = bomb3;
		}
		
		if(tickCounter == 4) {
			currentSprite = bomb4;
		}
		
		if(tickCounter == 5) {
			currentSprite = bomb5;
		}
		if(tickCounter == 6) {
			currentSprite = explode1;
		}
		
		if(tickCounter == 7) {
			currentSprite = explode2;
		}
		
		if(tickCounter == 8) {
			currentSprite = explode3;
		}	
		
		if(tickCounter == 9) {
			currentSprite = explode4;
		}
		
		if(tickCounter == 10) {
			currentSprite = explode5;
		}
		
		if(tickCounter == 11) {
			currentSprite = explode6;
		}
		
		if(tickCounter == 12) {
			currentSprite = explode7;
		}
		
		if(tickCounter == 13) {
			currentSprite = explode8;
		}
		
		if(tickCounter == 14) {
			currentSprite = explode9;
		}
		
		if(tickCounter == 15) {
			currentSprite = explode10;
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
