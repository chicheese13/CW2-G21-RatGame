/** 
 * NormalRat.java
 * @version 1.0
 * @author Dylan Lewis, Kien Lin
 *
 */

/**
 * NormalRat is an abstract class responsible for behaviours and attributes that AdultRat and BabyRat have in common.
 *
 */

public abstract class NormalRat extends Rat {
	
	/**
	 * 	The score you get from killing a rat.
	 */
	protected final int RAT_SCORE = 10;
	/**
	 * 	Declares every new born rat is fertile.
	 */
	//maybe change, extra feature mother rat becomes sterile whilst pregnant may result in sterile baby rats.
	protected final boolean DEFUALT_STERILE = false;
	/**
	 * 	The gender of the rat, true meaning a male rat, false meaning a female rat.
	 */
	protected boolean ratGender;
	/**
	 * 	Whether or not the rat is sterile.
	 */
	protected boolean ratSterile;
	
	
	/**
	 * 	Replenishes the health of a rat back to full health.
	 */
	public void replenishHealth() {
		this.ratHealth = MAX_RAT_HEALTH;
	}
	
	/**
	 * 	Returns the gender of the rat.
	 * 	@return ratGender - the gender of the rat.
	 */
	public boolean getRatGender() {
		return this.ratGender;
	}
	
	/**
	 * 	Returns whether or not a rat is sterile.
	 * 	@return ratSterile - true or false value.
	 */
	public boolean getSterile() {
		return this.ratSterile;
	}
	
	/**
	 * 	Sets the ratGender value of rat
	 * 	@param gender - true or false representing rat gender.
	 */
	public void setRatGender(boolean gender) {
		this.ratGender = gender;
	}
	
	/**
	 *  Makes the rat sterile.
	 */
	public void becomeSterile() {
		this.ratSterile = true;
	}
	
	/**
	 *  Abstract method that needs to be implemented in the subclasses.
	 */
	public abstract void ratDeath();
}
