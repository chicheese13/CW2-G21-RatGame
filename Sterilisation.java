import java.math.BigDecimal;

/**
 * @version 2.0
 * @author Armand Dorosz, Callum Young, Dylan Lewis
 * 
 *         Sterilisation class, which generates ready instance of the male sex
 *         changer, which is placed on the level
 *
 */
public class Sterilisation extends RenderObject {

	private final int STERILE_DURATION = 200;
	private final int RADIUS = 3;
	private int tickCounter = 0;

	/**
	 * Instantiates a new Sterilisation
	 * 
	 * @param objectPosition
	 * @param currentLevel
	 */
	public Sterilisation(Position objectPosition, Level currentLevel) {
		this.renderSprite = "sterilisation";
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;

		// create a sterile radius
		// get the top left
		double startX = this.objectPosition.getPosition()[0].doubleValue() - 1;
		double startY = this.objectPosition.getPosition()[1].doubleValue() - 1;

		this.currentLevel.spawnSound("Syringe");

		// create sterile tile objects
		for (int i = ((int) startX); i < startX + RADIUS; i++) {
			for (int i2 = ((int) startY); i2 < startY + RADIUS; i2++) {
				if (i == startX && i2 == startY) {
					this.currentLevel.spawnTempTile(new SterileRadius(
							new Position(new BigDecimal(i), new BigDecimal(i2)),
							this.currentLevel, this, "sterile-radius"));
				} else {
					this.currentLevel.spawnTempTile(new SterileRadius(
							new Position(new BigDecimal(i), new BigDecimal(i2)),
							this.currentLevel, this, ""));
				}
			}
		}
	}

	public int getTick() {
		return this.tickCounter;
	}

	/**
	 * Instantly removes the item after its duration
	 */
	public void instantRemove() {
		this.tickCounter = STERILE_DURATION;
	}

	/**
	 * Method, which is responsible for sterilisation behavior every tick
	 */
	@Override
	public void tick() {
		this.tickCounter++;
		if (this.tickCounter >= STERILE_DURATION) {
			this.currentLevel.despawnItem(this);
		}
	}

	/**
	 * Method for destroying this item, if is in bomb explosion radius
	 */
	public void collision(Object collidedObject) {
		//
	}
}
