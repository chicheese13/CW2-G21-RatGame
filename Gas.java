import javafx.scene.image.Image;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Gas extends CollideItem {

	private Image gas = new Image("/items/gasonlevel.png");
	
	//around 8 seconds.
	private int STOP_SPREAD_INTERVAL = 500;
	private int tickCounter = 0;
	private ArrayList<GasSpread> childrenGas = new ArrayList<GasSpread>();
	private int dissipateCounter = 0;
	private int dissipateIndex = 0;

	public Gas(Position objectPosition, Level currentLevel) {
		this.renderSprite = gas;
		this.objectPosition = objectPosition;
		this.currentLevel = currentLevel;
		this.renderSprite = new Image("Textures/gas.png");
		
		//spawn a gas spread on this tile
		this.currentLevel.spawnTempTile(new GasSpread(new Position(this.getObjectPosition()[0], this.getObjectPosition()[1]), this.currentLevel, this));
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
		
		
		if (this.tickCounter == STOP_SPREAD_INTERVAL) {
			this.dissipateCounter++;
			if (this.dissipateCounter == 13) {
				if (dissipateIndex < childrenGas.size()){
					this.currentLevel.despawnTempTile(this.childrenGas.get(0));
					this.childrenGas.remove(0);
				} else {
					this.currentLevel.despawnItem(this);
				}
				//remove index
				this.dissipateCounter = 0;
			}
		} else {
			this.tickCounter++;
		}
	}

}
