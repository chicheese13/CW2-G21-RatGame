import javafx.scene.image.Image;
import java.math.BigDecimal;

public class Sterilisation extends Item {
	
	private Image sterilisation = new Image("/items/sterilisationonlevel.png");
	private int STERILE_DURATION = 200;
	private int tickCounter = 0;

	public Sterilisation(Position objectPosition, Level currentLevel) {
		this.renderSprite = sterilisation;
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
		
		//create a sterile radius
		//get the top left
		double startX = this.objectPosition.getPosition()[0].doubleValue()-1;
		double startY = this.objectPosition.getPosition()[1].doubleValue()-1;
		
		
		
		for (int i = ((int) startX); i < startX+3; i++) {
			for (int i2 = ((int) startY); i2 < startY+3; i2++) {
				if (i == startX && i2 == startY) {
					this.currentLevel.spawnTempTile(new SterileRadius(new Position(new BigDecimal(i), new BigDecimal(i2)), this.currentLevel, this, "Textures/sterile-radius.png"));
				} else {
					this.currentLevel.spawnTempTile(new SterileRadius(new Position(new BigDecimal(i), new BigDecimal(i2)), this.currentLevel, this, ""));
				}
			}
		}
	
		
		//create sterile tile objects
		
	}
	
	public int getTick() {
		return this.tickCounter;
	}

	public void instantRemove() {
		System.out.print("STERILE TEST");
		this.tickCounter = STERILE_DURATION;
	}
	
	@Override
	public void tick() {
		this.tickCounter++;
		if (this.tickCounter >= STERILE_DURATION) {
			this.currentLevel.despawnItem(this);
		}
		// TODO Auto-generated method stub
		
	}
	
}
