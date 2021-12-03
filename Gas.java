import javafx.scene.image.Image;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Gas extends CollideItem {
	
	//around 8 seconds.
	private int STOP_SPREAD_INTERVAL =  200;
	private int tickCounter = 0;
	private ArrayList<GasSpread> childrenGas = new ArrayList<GasSpread>();
	private int dissipateCounter = 0;
	private int dissipateIndex = 0;
	private boolean isWaiting = false;
	private int LINGER_LIMIT = 200;
	private int lingerCounter = 0;

	public Gas(Position objectPosition, Level currentLevel) {
		this.renderSprite = new Image("gas_images/Sprinkler.png");
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
		
		//spawn a gas spread on this tile
		if (this.currentLevel.checkGas(this.objectPosition)) {
			this.currentLevel.spawnTempTile(new GasSpread(new Position(this.getObjectPosition()[0], this.getObjectPosition()[1]), this.currentLevel, this));
		} else {
			this.isWaiting = true;
		}
		
	}



	@Override
	public void collision(Object collidedObject) {
		// TODO Auto-generated method stub
		
	}
	
	public int getTick() {
		return this.tickCounter;
	}
	
	public void addChildToArray(GasSpread childGas) {
		this.childrenGas.add(childGas);
	}
	

	@Override
	public void tick() {
		if (isWaiting) {
			System.out.println("WAITING");
			if (this.currentLevel.checkGas(this.objectPosition)) {
				this.currentLevel.spawnTempTile(new GasSpread(new Position(this.getObjectPosition()[0], this.getObjectPosition()[1]), this.currentLevel, this));
				isWaiting = false;
			}

		} else {
			if (this.tickCounter == STOP_SPREAD_INTERVAL) {
				this.lingerCounter++;
				if (lingerCounter >= LINGER_LIMIT) {
					this.dissipateCounter++;
					if (this.dissipateCounter == 20) {
						if (dissipateIndex < childrenGas.size()){
							this.currentLevel.despawnTempTile(this.childrenGas.get(0));
							this.childrenGas.remove(0);
						} else {
							this.currentLevel.despawnItem(this);
						}
						//remove index
						this.dissipateCounter = 0;
					}
				}
			} else {
				this.tickCounter++;
			}
		}
	}

}
