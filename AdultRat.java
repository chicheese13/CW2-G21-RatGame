
public class AdultRat extends NormalRat {
	protected final boolean DEFAULT_PREGNANCY_VALUE = false;
	protected final int DEFAULT_PREGNANCY_COUNT = 0;
	protected final int DEFAULT_COOLDOWN_VALUE = 0;
	protected final int DEFAULT_ADULT_RAT_SPEED = 5;
	
	private boolean isPregnant;
	private int cooldown;
	private int pregnancyCounter;
	
	public AdultRat(Position position, boolean gender, boolean sterile, int ratHealth) {
		this.isPregnant = DEFAULT_PREGNANCY_VALUE;
		this.pregnancyCounter = DEFAULT_PREGNANCY_COUNT;
		this.cooldown = DEFAULT_COOLDOWN_VALUE;
		this.ratSpeed = DEFAULT_ADULT_RAT_SPEED;
		this.ratSterile = sterile;
		this.ratPosition = position;
		this.ratGender = gender;
		this.ratHealth = ratHealth;
		
		//javafx code for making rat appear.
	}
	
	//setters and getters 
	public void setPregnant (boolean pregnant) {
		this.isPregnant = pregnant;
	}
	
	public boolean getPregnant() {
		return this.isPregnant;
	}
	
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
	
	public int getCooldown () {
		return this.cooldown;
	}
	
	public void setPregnancyCounter(int pregnancyCounter) {
		this.pregnancyCounter = pregnancyCounter;
	}
	
	public int getPregnancyCounter() {
		return this.pregnancyCounter;
	}
}
