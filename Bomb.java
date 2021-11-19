
public class Bomb extends Item {
	
	public Bomb (int x, int y) {
		this.setLocation(x, y);
	}
	
	public void startTimer() {
		//after few ticks bomb explodes
		explode();
	}
	
	private void explode() {
		//3x3 Tiles.getDamage();
		Bomb disappears;
	}
	

}
