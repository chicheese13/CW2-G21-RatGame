import java.math.*;

/**
 * @version 1.0
 * @author Dylan Lewis
 * 
 * GasSpread class, which generates toxic cloud on level
 *
 */
public class GasSpread extends RenderObject {

	private final int ONE_SECOND_IN_TICKS = 66;
	private final double DAMAGE_TO_RAT = 0.5;

	private int STOP_SPREAD_INTERVAL = 200;
	private int tickCounter = 0;

	// tells which sprinkler releases the gas
	private Gas parentGas;

	/**
	 * Instantiates a new GasSpread
	 * 
	 * @param position
	 * @param currentLevel
	 * @param parent       tells which sprinkler releases the gas
	 */
	public GasSpread(Position position, Level currentLevel, Gas parent) {
		this.objectPosition = position;
		this.currentLevel = currentLevel;
		this.parentGas = parent;
		this.renderSprite = "gas";
		this.parentGas.addChildToArray(this);
	}

	public boolean checkParent(Position position) {
		if (this.parentGas.getObjectPosition()[0].doubleValue() == position.getPosition()[0].doubleValue()
				&& this.parentGas.getObjectPosition()[1].doubleValue() == position.getPosition()[1].doubleValue()) {
			return false;
		}
		return true;
	}

	/**
	 * Method to calculate gas path every tick
	 */
	@Override
	public void tick() {
		this.tickCounter++;
		System.out.println(parentGas.getTick());

		if (this.parentGas.getTick() >= STOP_SPREAD_INTERVAL) {

		} else if (this.tickCounter == ONE_SECOND_IN_TICKS) {
			// check available directions and add gas there.

			// check north
			if (this.currentLevel.tileAvailable(this.getObjectPosition()[0], this.getObjectPosition()[1], 'N')) {
				// create new position y - 1

				Position newPosition = new Position(this.getObjectPosition()[0],
						this.getObjectPosition()[1].subtract(new BigDecimal("1")));
				if (this.currentLevel.checkGas(newPosition) && checkParent(newPosition)) {
					this.currentLevel.spawnTempTile(new GasSpread(newPosition, this.currentLevel, this.parentGas));
				}
			}
			// check east
			if (this.currentLevel.tileAvailable(this.getObjectPosition()[0], this.getObjectPosition()[1], 'E')) {
				Position newPosition = new Position(this.getObjectPosition()[0].add(new BigDecimal("1")),
						this.getObjectPosition()[1]);

				if (this.currentLevel.checkGas(newPosition) && checkParent(newPosition)) {
					this.currentLevel.spawnTempTile(new GasSpread(newPosition, this.currentLevel, this.parentGas));
				}
			}

			// check south
			if (this.currentLevel.tileAvailable(this.getObjectPosition()[0], this.getObjectPosition()[1], 'S')) {
				Position newPosition = new Position(this.getObjectPosition()[0],
						this.getObjectPosition()[1].add(new BigDecimal("1")));
				if (this.currentLevel.checkGas(newPosition) && checkParent(newPosition)) {
					this.currentLevel.spawnTempTile(new GasSpread(newPosition, this.currentLevel, this.parentGas));
				}
			}

			// check west
			if (this.currentLevel.tileAvailable(this.getObjectPosition()[0], this.getObjectPosition()[1], 'W')) {
				Position newPosition = new Position(this.getObjectPosition()[0].subtract(new BigDecimal("1")),
						this.getObjectPosition()[1]);
				if (this.currentLevel.checkGas(newPosition) && checkParent(newPosition)) {
					this.currentLevel.spawnTempTile(new GasSpread(newPosition, this.currentLevel, this.parentGas));
				}
			}

		}
	}

	/**
	 * Method to deal damage to the rats, if they get into gas cloud
	 */
	@Override
	public void collision(Object collidedObject) {
		if (collidedObject instanceof NormalRat) {
			// deal damage to the rat
			((NormalRat) collidedObject).dealDamage(DAMAGE_TO_RAT);
		}
	}
}
