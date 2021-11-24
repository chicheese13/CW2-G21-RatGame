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
		
	private double tickCounter = 0;
	private double delayCount = 0;
	private TestLevel currentLevel;
	
	public Bomb (Position objectPosition, TestLevel currentLevel) {
		this.renderSprite = bomb;
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
	}
	
	public void startTimer() {
		//after few ticks bomb explodes
		explode();
	}
	
	public void tick() {
		delayCount = delayCount + 12.5;
		if (delayCount > 4000) {
			System.out.println(this.tickCounter);
			this.tickCounter = this.tickCounter + 12.5;
			if(tickCounter == 100) {
				this.renderSprite = bomb1;
				System.out.println("one");
			}
			if(tickCounter == 200) {
				this.renderSprite = bomb2;
			}
			
			if(tickCounter == 300) {
				this.renderSprite = bomb3;
			}
			
			if(tickCounter == 400) {
				this.renderSprite = bomb4;
			}
			
			if(tickCounter == 500) {
				this.renderSprite = bomb5;
			}
			if(tickCounter == 600) {
				this.renderSprite = explode1;
			}
			
			if(tickCounter == 700) {
				this.renderSprite = explode2;
			}
			
			if(tickCounter == 800) {
				this.renderSprite = explode3;
			}	
			
			if(tickCounter == 900) {
				this.renderSprite = explode4;
			}
			
			if(tickCounter == 1000) {
				this.renderSprite = explode5;
			}
			
			if(tickCounter == 1100) {
				this.renderSprite = explode6;
			}
			
			if(tickCounter == 1200) {
				this.renderSprite = explode7;
			}
			
			if(tickCounter == 1300) {
				this.renderSprite = explode8;
			}
			
			if(tickCounter == 1400) {
				this.renderSprite = explode9;
			}
			
			if(tickCounter == 1500) {
				this.renderSprite = explode10;
				for (int i = 0; i < this.currentLevel.getRenderObjects().size(); i++) {
					if (this.currentLevel.getRenderObjects().get(i) == this) {
						currentLevel.getRenderObjects().remove(i);
					}
				}
			}
		} else {
			
		}
	}
	
	public void resetTick() {
		tickCounter = 0;
	}
	
	private void explode() {
		//3x3 Tiles.getDamage();
		Bomb disappears;
	}
	
	public Image getSprite() {
		return this.renderSprite;
	}

}
