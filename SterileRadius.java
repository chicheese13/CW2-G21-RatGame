
/**
 * @version 1.0
 * @author Dylan Lewis
 * 
 *         SterileRadius class, which makes rat in certain radius sterile
 *
 */
public class SterileRadius extends RenderObject {

	private Sterilisation sterileParent;
	private final int STERILE_DURATION = 200;

	/**
	 * Instantiates a new SterileRadius
	 * 
	 * @param position
	 * @param currentLevel
	 * @param parent
	 * @param image
	 */
	public SterileRadius(Position position, Level currentLevel, Sterilisation parent, String image) {
		this.objectPosition = position;
		this.currentLevel = currentLevel;
		this.sterileParent = parent;

		if (image != "") {
			this.renderSprite = image;
		} else {
			this.renderSprite = "tr";
		}

	}

	public void collision(Object collidedObject) {

	}

	/**
	 * Method for sterilisation of rats, which step into radius
	 */
	@Override
	public void tick() {
		System.out.println(this.sterileParent.getTick());
		if (this.sterileParent.getTick() >= STERILE_DURATION - 1) {
			this.currentLevel.despawnTempTile(this);
		}
	}
}
