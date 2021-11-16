
public abstract class NormalRat extends Rat {
	protected final int RAT_SCORE = 10;
	protected final boolean DEFUALT_STERILE = false;
	
	protected boolean ratGender;
	protected boolean ratSterile;
	
	public void replenishHealth() {
		this.ratHealth = RAT_HEALTH;
	}
	
	public boolean getRatGender() {
		return this.ratGender;
	}
	
	public void changeGender(boolean gender) {
		this.ratGender = gender;
	}
	
	public boolean getSterile() {
		return this.ratSterile;
	}
	
	public void becomeSterile() {
		this.ratSterile = true;
	}
}
