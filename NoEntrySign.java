/**
 * @version 1.0
 * @author Armand Dorosz, Callum Young, Dylan Lewis
 * 
 * NoEntrySign class, which generates ready instance of the no entry sign, which
 * is placed on the level
 *
 */
public class NoEntrySign extends RenderObject {

    public int signHealth = 5;

    /**
     * Attributes helping with animation of the gas
     */
    private final int NUMBER_OF_SIGN_FRAMES = 5;
    private int pictureNumber = 1;

    /**
     * Instantiates a new NoEntrySign
     * 
     * @param objectPosition
     * @param currentLevel
     */
    public NoEntrySign(Position objectPosition, Level currentLevel) {
        this.renderSprite = "no-entry1";
        this.objectPosition = objectPosition;
        this.currentLevel = currentLevel;
        this.currentLevel.spawnSound("placeSign");
    }

    /**
     * Method for loading the animation sprites
     * 
     * @param delayedPictureNumber
     * @return name of the next animation
     */
    private String loadImage(int pictureNumber) {

        String sign = "no-entry" + String.valueOf(pictureNumber);

        return sign;
    }

    /**
     * Method that gets run each time a rat hits a sign
     */
    private void breakSign() {

        if (signHealth > 1) {
            signHealth--;
            if (pictureNumber < NUMBER_OF_SIGN_FRAMES) {
                pictureNumber++;
                this.renderSprite = loadImage(pictureNumber);
            }
        } else {
            this.currentLevel.despawnItem(this);
        }
    }

    public void tick() {

    }

    /**
     * If rat stomps on a sign, it is being pushed back and it starts moving
     * different direction, the sign loses health points
     * 
     */
    public void collision(Object collidedObject) {
        if (collidedObject instanceof Rat) {
            ((Rat) collidedObject).turnAround();
            this.currentLevel.spawnSound("signHit" + pictureNumber);
            breakSign();
        }
    }
}
