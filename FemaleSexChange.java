/**
 * @version 2.0
 * @author Armand Dorosz, Callum Young, Dylan Lewis
 * 
 *         FemaleSexChange class, which generates ready instance of the female
 *         sex
 *         changer, which is placed on the level
 *
 */
public class FemaleSexChange extends RenderObject {

	/**
	 * Instantiates a new FemaleSexChanger
	 * 
	 * @param objectPosition
	 * @param currentLevel
	 */
	public FemaleSexChange(Position objectPosition, Level currentLevel) {
		this.renderSprite = "female-sex-change";
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
		this.currentLevel.spawnSound("placeItem");
	}

	/**
	 * Method for changing the sex of a rat
	 * 
	 * @param collidedObject is the rat, which steps on the item
	 */
	public void ratToF(NormalRat collidedObject) {
		// runs rat gender change method in rat classes
		collidedObject.changeToFemale();
	}

	public void tick() {

	}

	/**
	 * Method for changing the gender to female of collided rats
	 * 
	 */
	public void collision(Object collidedObject) {
		if (collidedObject instanceof NormalRat) {
			ratToF((NormalRat) collidedObject);
			this.currentLevel.spawnSound("FemaleSexChange");
			this.currentLevel.despawnItem(this);
		}
	}
}
