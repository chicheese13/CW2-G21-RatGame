
import javafx.scene.image.Image;

public class Explosion extends CollideItem {

	private int tickCounter;
	private int TICK_DURATION = 15;
	
	public Explosion(Position position, Level currentLevel) {
		this.renderSprite = new Image("Textures/explosion.png");
		this.tickCounter = 0;
		this.objectPosition = position;
		this.currentLevel = currentLevel;
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		this.tickCounter++;
		if (this.tickCounter == TICK_DURATION) {
			System.out.println("TEST");
			this.currentLevel.despawnTempTile(this);
		}
	}
	
	public void collision(Object collidedObject) {
		if (collidedObject instanceof NormalRat) {
			((NormalRat) collidedObject).ratDeath();
		//Checks for every item but Gas (because it broken) and despawns it	
		} else if ((collidedObject instanceof Item)) {
			if ((collidedObject instanceof Gas)) {
				((Gas) collidedObject).instantDissapate();
			}
			((Item) collidedObject).currentLevel.despawnItem((Item) collidedObject);
		}
	}
// && ((collidedObject instanceof Gas) == false))
}
