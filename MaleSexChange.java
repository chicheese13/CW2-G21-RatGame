/**
 * @version 2.0
 * @author Armand Dorosz, Callum Young, Dylan Lewis
 * 
 * MaleSexChange class, which generates ready instance of the male sex changer,
 * which is placed on the level
 *
 */
public class MaleSexChange extends RenderObject {

    /**
     * Instantiates a new MaleSexChanger
     * 
     * @param objectPosition
     * @param currentLevel
     */
    public MaleSexChange(Position objectPosition, Level currentLevel) {
        this.renderSprite = "male-sex-change";
        this.objectPosition = objectPosition;
        this.currentLevel = currentLevel;
        this.currentLevel.spawnSound("placeItem");
    }

    /**
     * Method for changing the sex of a rat
     * 
     * @param collidedObject is the rat, which steps on the item
     */
    public void ratToM(NormalRat collidedRat) {
        // runs rat gender change method in rat classes
        collidedRat.changeToMale();
    }

    public void tick() {

    }

    /**
     * Method for changing the gender to male of collided rats
     * 
     */
    public void collision(Object collidedObject) {
        if (collidedObject instanceof NormalRat) {
            ratToM((NormalRat) collidedObject);
            this.currentLevel.spawnSound("MaleSexChange");
            this.currentLevel.despawnItem(this);
        }
    }
}
