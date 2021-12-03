import java.math.*;
import javafx.scene.image.Image;

public class GasSpread extends CollideItem {
	
	
	private int STOP_SPREAD_INTERVAL = 200;
	private int tickCounter = 0;
	private int TICK_LIMIT = 200;
	private Gas parentGas;
	private int dissipateCounter = 0;
	private final double DAMAGE_TO_RAT = 0.5;
	
	public GasSpread(Position position, Level currentLevel, Gas parent) {
		this.objectPosition = position;
		this.currentLevel = currentLevel;
		this.parentGas = parent;
		this.renderSprite = new Image("Textures/gas.png");
		this.parentGas.addChildToArray(this);
	}
	
	public boolean checkParent(Position position) {
		if (this.parentGas.getObjectPosition()[0].doubleValue() == position.getPosition()[0].doubleValue()
			&& this.parentGas.getObjectPosition()[1].doubleValue() == position.getPosition()[1].doubleValue()) {
			return false;
		}
		return true;
	}

	@Override
	
	public void tick() {
		this.tickCounter++;
		
		if (this.parentGas.getTick() >= STOP_SPREAD_INTERVAL) {
			
		} else if (this.tickCounter == 66) {
			//check available directions and add gas there.
			
			//check north
			
			if (this.currentLevel.tileAvailable(this.getObjectPosition()[0], this.getObjectPosition()[1], 'N')) {
				//create new position y - 1
			
				Position newPosition = new Position(this.getObjectPosition()[0], this.getObjectPosition()[1].subtract(new BigDecimal("1")));
				if (this.currentLevel.checkGas(newPosition) && checkParent(newPosition)) {
					this.currentLevel.spawnTempTile(new GasSpread(newPosition, this.currentLevel, this.parentGas));
				}
			}
			//check east
			if (this.currentLevel.tileAvailable(this.getObjectPosition()[0], this.getObjectPosition()[1], 'E')) {
				Position newPosition = new Position(this.getObjectPosition()[0].add(new BigDecimal("1")), this.getObjectPosition()[1]);
				
				if (this.currentLevel.checkGas(newPosition) && checkParent(newPosition)) {
					this.currentLevel.spawnTempTile(new GasSpread(newPosition, this.currentLevel, this.parentGas));
				}
			}
			
			//check south
			if (this.currentLevel.tileAvailable(this.getObjectPosition()[0], this.getObjectPosition()[1], 'S')) {
				Position newPosition = new Position(this.getObjectPosition()[0], this.getObjectPosition()[1].add(new BigDecimal("1")));
				if (this.currentLevel.checkGas(newPosition) && checkParent(newPosition)) {
					this.currentLevel.spawnTempTile(new GasSpread(newPosition, this.currentLevel, this.parentGas));
				}
			}
			
			//check west
			if (this.currentLevel.tileAvailable(this.getObjectPosition()[0], this.getObjectPosition()[1], 'W')) {
				Position newPosition = new Position(this.getObjectPosition()[0].subtract(new BigDecimal("1")), this.getObjectPosition()[1]);
				if (this.currentLevel.checkGas(newPosition) && checkParent(newPosition)) {
					this.currentLevel.spawnTempTile(new GasSpread(newPosition, this.currentLevel, this.parentGas));
				}
			}
			
		}
	}

	@Override
	public void collision(Object collidedObject) {
		if (collidedObject instanceof NormalRat) {
			//deal damage to the rat
			((NormalRat) collidedObject).dealDamage(DAMAGE_TO_RAT);
		}
	}
}
