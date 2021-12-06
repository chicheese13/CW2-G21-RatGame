/**
 * @version 1.0
 * @author Dylan Lewis
 * 
 *         Explosion class responsible for explosion behaviour after initial
 *         bomb explosion
 */
public class Explosion extends RenderObject {

	private int tickCounter;
	private int TICK_DURATION = 15;

	/**
	 * Instantiates a new Explosion
	 * 
	 * @param objectPosition
	 * @param currentLevel
	 */
	public Explosion(Position position, Level currentLevel) {
		this.renderSprite = "explosion";
		this.tickCounter = 0;
		this.objectPosition = position;
		this.currentLevel = currentLevel;
	}

	/**
	 * Method, which is responsible for explosion behavior every tick
	 */
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		this.tickCounter++;
		if (this.tickCounter == TICK_DURATION) {
			System.out.println("TEST");
			this.currentLevel.despawnTempTile(this);
		}
	}

	/**
	 * Method for destroying all the objects within explosion radius
	 */
	public void collision(Object collidedObject) {
		((RenderObject) collidedObject).collision(this);
	}
}
