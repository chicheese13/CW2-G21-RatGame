
import java.math.BigDecimal;
import java.util.ArrayList;

public class Gas extends CollideItem {

	// around 8 seconds.
	private int STOP_SPREAD_INTERVAL = 200;
	private int NEXT_ANIMATION = 3;
	private int tickCounter = 0;
	private int nextAnimationCounter = 0;
	private int pictureNumber = 5;
	private ArrayList<GasSpread> childrenGas = new ArrayList<GasSpread>();
	private int dissipateCounter = 0;
	private int dissipateIndex = 0;
	private boolean isWaiting = false;
	private boolean isBroken = false;
	private int LINGER_LIMIT = 200;
	private int lingerCounter = 0;
	private String sprinkler = "sprinkler";
	private String sprinklerBroken = "sprinkler-broken";

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

		if (collidedObject instanceof Explosion) {

			this.currentLevel.despawnItem(this);
		}
		// TODO Auto-generated method stub

	}

	public int getTick() {
		return this.tickCounter;
	}

	public void addChildToArray(GasSpread childGas) {
		this.childrenGas.add(childGas);
	}

	public void instantDissapate() {
		this.tickCounter = STOP_SPREAD_INTERVAL;
		this.lingerCounter = LINGER_LIMIT;
		System.out.println("STOP SPREAD");
		this.isBroken = true;
		//this.currentLevel.stopSound();

		// sprite change to show the fan is broken
		this.renderSprite = sprinklerBroken;
	}

	private String loadImage(int pictureNumber) {

		String gas = "sprinkler" + String.valueOf(pictureNumber);

		return gas;
	}

	@Override
	public void tick() {

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

					if (this.dissipateCounter == 20) {

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
				if (nextAnimationCounter >= NEXT_ANIMATION) {
					nextAnimationCounter = 0;

					if (pictureNumber == 5) {
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
