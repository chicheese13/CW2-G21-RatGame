
import java.util.ArrayList;

/**
 * @version 3.0
 * @author Armand Dorosz, Callum Young, Dylan Lewis
 * 
 *         Gas class, which generates ready instance of the gas, which is placed
 *         on the level
 *
 */
public class Gas extends RenderObject {

	/**
	 * Attributes helping with animation and behaviour of the gas
	 */
	private final int NUMBER_OF_GAS_SPRITES = 5;
	private final int STOP_SPREAD_INTERVAL = 200;
	private final int DISSIPATE_LIMIT = 20;
	private final int LINGER_LIMIT = 200;
	private final int NEXT_ANIMATION = 3;
	
	private int tickCounter = 0;
	private int nextAnimationCounter = 0;
	private int pictureNumber = 5;
	private ArrayList<GasSpread> childrenGas = new ArrayList<GasSpread>();
	private int dissipateCounter = 0;
	private int dissipateIndex = 0;
	private boolean isWaiting = false;
	private boolean isBroken = false;
	private int lingerCounter = 0;

	/**
	 * String holding the names of the sprites for the gas
	 */
	private String sprinkler = "sprinkler";
	private String sprinklerBroken = "sprinkler-broken";

	/**
	 * Gas constructor
	 * 
	 * @param objectPosition
	 * @param currentLevel
	 */
	public Gas(Position objectPosition, Level currentLevel) {
		this.renderSprite = sprinkler;
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;

		this.currentLevel.spawnSound("Gas");

		// spawn a gas spread on this tile
		if (this.currentLevel.checkGas(this.objectPosition)) {
			this.currentLevel.spawnTempTile(new GasSpread(
					new Position(this.getObjectPosition()[0], this.getObjectPosition()[1]), this.currentLevel, this));
		} else {
			this.isWaiting = true;
		}

	}

	@Override
	public void collision(Object collidedObject) {

	}

	public int getTick() {
		return this.tickCounter;
	}

	public void addChildToArray(GasSpread childGas) {
		this.childrenGas.add(childGas);
	}

	/**
	 * Method for stopping the spread of gas, if sprinkler gets broken by bomb
	 */
	public void instantDissapate() {
		this.tickCounter = STOP_SPREAD_INTERVAL;
		this.lingerCounter = LINGER_LIMIT;
		System.out.println("STOP SPREAD");
		this.isBroken = true;
		// this.currentLevel.stopSound();

		// sprite change to show the fan is broken
		this.renderSprite = sprinklerBroken;
	}

	/**
	 * Method for loading the animation sprites
	 * 
	 * @param delayedPictureNumber
	 * @return name of the next animation
	 */
	private String loadImage(int pictureNumber) {

		String gas = "sprinkler" + String.valueOf(pictureNumber);

		return gas;
	}

	/**
	 * Method, which is responsible for gas behavior every tick
	 */
	@Override
	public void tick() {
		System.out.println("GAS CHILD");
		System.out.println(childrenGas.size());
		System.out.println("GAS TICK");
		System.out.println(this.tickCounter);
		if (isWaiting) {
			System.out.println("WAITING");

			if (this.currentLevel.checkGas(this.objectPosition)) {
				this.currentLevel.spawnTempTile(
						new GasSpread(new Position(this.getObjectPosition()[0], this.getObjectPosition()[1]),
								this.currentLevel, this));
				isWaiting = false;
			}

		} else {

			if (this.tickCounter == STOP_SPREAD_INTERVAL) {
				this.lingerCounter++;

				if (isBroken == false) {
					this.renderSprite = sprinkler;
				}

				if (lingerCounter >= LINGER_LIMIT) {
					this.dissipateCounter++;

					if (this.dissipateCounter == DISSIPATE_LIMIT) {

						if (dissipateIndex < childrenGas.size()) {
							this.currentLevel.despawnTempTile(this.childrenGas.get(0));
							this.childrenGas.remove(0);
						} else {
							this.currentLevel.despawnItem(this);
						}
						// remove index
						this.dissipateCounter = 0;
					}
				}
			} else {
				// loop the animation
				if (nextAnimationCounter >= NEXT_ANIMATION) {
					nextAnimationCounter = 0;

					if (pictureNumber == NUMBER_OF_GAS_SPRITES) {
						pictureNumber = 1;
					} else {
						pictureNumber++;
					}
					this.renderSprite = loadImage(pictureNumber);
				}
				this.nextAnimationCounter++;
				this.tickCounter++;
			}
		}
	}

}
