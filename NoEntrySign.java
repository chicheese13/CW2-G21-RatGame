
public class NoEntrySign extends Item {

	public int signHealth = 100;
	public boolean ratStompsOnSign = false;

	public NoEntrySign(int x, int y) {
		this.setLocation(x, y);
	}
	
	// method that gets run each time a rat hits a sign
	private void breakSign() {
		if (ratStompsOnSign) {
			signHealth = getSignHealth() - 20;
			if (signHealth == 0) {
				NoEntrySign disappears;
			}
		}
	}
	
	// gets the signs health
	private int getSignHealth() {
		
		return signHealth;
	}

}
