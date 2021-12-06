import java.math.BigDecimal;

/**
 * Bomb.java
 * 
 * @version 3.0
 * @author Armand Dorosz, Callum Young, Dylan Lewis
 * 
 *         Bomb class, which generates ready instance of the bomb, which can be
 *         placed on the level
 */
public class Bomb extends RenderObject {

	/**
	 * String holding the name of the sprite for the bomb
	 */
	private String bomb = "bomb";

	/**
	 * Attributes helping with animation of the bomb
	 */
	private final int NUMBER_OF_BOMB_FRAMES = 17;
	private final int EXPLOSION_FRAME = 6;
	private double tickCounter = 0;
	private double delayCount = 0;
	private int pictureNumber = 1;
	private int delayedPictureNumber = 4;
	private boolean timerStarted = false;

	/**
	 * Bomb constructor
	 * 
	 * @param objectPosition
	 * @param currentLevel
	 */
	public Bomb(Position objectPosition, Level currentLevel) {
		this.renderSprite = bomb;
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
	}

	/**
	 * Method for playing the ignition of the bomb
	 */
	public void startTimer() {
		this.currentLevel.spawnSound("Fuse");
	}

	/**
	 * Method for loading the animation sprites
	 * 
	 * @param pictureNumber
	 * @return name of the next animation
	 */

	private String loadImage(int pictureNumber) {
		String bomb = "bomb" + String.valueOf(pictureNumber);
		return bomb;
	}

	/**
	 * Method for loading the animation sprites
	 * 
	 * @param delayedPictureNumber
	 * @returnname of the next animation
	 */
	private String loadDelayImage(int delayedPictureNumber) {
		String delayBomb = "bomb-delayed" + String.valueOf(delayedPictureNumber);
		return delayBomb;
	}

	/**
	 * Method, which is responsible for bomb behavior every tick
	 */
	public void tick() {

		if (timerStarted == false) {
			if (delayCount == 0) {
				this.currentLevel.spawnSound("BombBackground");
			}
			if (delayCount % 1000 == 0) {
				this.currentLevel.spawnSound("Tick");
				this.renderSprite = loadDelayImage(delayedPictureNumber);
				if (delayedPictureNumber > 1) {
					delayedPictureNumber--;
				}
			}

			delayCount = delayCount + 12.5;

			if (delayCount >= 4000) {
				startTimer();
				timerStarted = true;
			}
		} else {
			if ((tickCounter == 3)) {
				tickCounter = 0;
				if (pictureNumber == EXPLOSION_FRAME) {
					explode();
					this.currentLevel.spawnSound("Explosion");
				}
				this.renderSprite = loadImage(pictureNumber);
				if (pictureNumber < NUMBER_OF_BOMB_FRAMES) {
					pictureNumber++;
				}
			}
			tickCounter++;
		}
	}

	/**
	 * Method for destroying items, which are in bomb explosion radius
	 */
	public void collision(Object collidedObject) {
		if (collidedObject instanceof Explosion) {
			this.currentLevel.despawnItem(this);
		}
	}

	/**
	 * Method to deal damage horizontally and vertically
	 */
	private void explode() {
		System.out.println("BOOM");

		// spawns new items for every every available tile on the x and y coords.
		this.currentLevel.spawnTempTile(new Explosion(
				new Position(this.getObjectPosition()[0], this.getObjectPosition()[1]), this.currentLevel));

		// add explosions north south east and west
		BigDecimal startX = this.getObjectPosition()[0];
		BigDecimal startY = this.getObjectPosition()[1];

		BigDecimal northY = startY.subtract(new BigDecimal("1"));
		BigDecimal southY = startY.add(new BigDecimal("1"));

		BigDecimal eastX = startX.subtract(new BigDecimal("1"));
		BigDecimal westX = startX.add(new BigDecimal("1"));

		// west
		while (this.currentLevel.tileAvailable(eastX.add(new BigDecimal("1")), startY, 'W')) {
			this.currentLevel.spawnTempTile(new Explosion(new Position(eastX, startY), this.currentLevel));
			eastX = eastX.subtract(new BigDecimal("1"));
		}

		// east
		while (this.currentLevel.tileAvailable(westX.subtract(new BigDecimal("1")), startY, 'E')) {
			this.currentLevel.spawnTempTile(new Explosion(new Position(westX, startY), this.currentLevel));
			westX = westX.add(new BigDecimal("1"));
		}

		// north
		while (this.currentLevel.tileAvailable(startX, northY.add(new BigDecimal("1")), 'N')) {
			this.currentLevel.spawnTempTile(new Explosion(new Position(startX, northY), this.currentLevel));
			northY = northY.subtract(new BigDecimal("1"));
		}

		// south
		while (this.currentLevel.tileAvailable(startX, southY.subtract(new BigDecimal("1")), 'S')) {
			this.currentLevel.spawnTempTile(new Explosion(new Position(startX, southY), this.currentLevel));
			southY = southY.add(new BigDecimal("1"));
		}

	}

	/**
	 * Method for passing the name of the image
	 */
	public String getSprite() {
		return this.renderSprite;
	}

}
