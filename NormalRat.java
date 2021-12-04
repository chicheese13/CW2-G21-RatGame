/** 
 * NormalRat.java
 * @version 2.0
 * @author Dylan Lewis, Kien Lin
 *
 */

/**
 * NormalRat is an abstract class responsible for behaviours and attributes that AdultRat and BabyRat have in common.
 */

import java.math.BigDecimal;
import java.math.RoundingMode;

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
	 * The maximum amount of health a rat can have.
	 */
	protected final double MAX_RAT_HEALTH = 100;
	/**
	 * 	The gender of the rat, true meaning a male rat, false meaning a female rat.
	 */
	protected boolean ratGender;
	/**
	 * Health of the rat.
	 */
	protected double ratHealth;
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
	
	public boolean damageCooldown = false;
	
	/**
	 *  Calculates a new position based on the current position and a new speed value, this is to prevent the new speed value from incrimenting the
	 *  position too high which will cause path finding failure. 
	 *  @param position, the current position that needs to be changed.
	 *  @param newSpeed, the speed that the rat will be changing to.
	 */
	public Position recallibratePosition(Position position, BigDecimal newSpeed) {
		//floors the x and y values
		//and minuses the floored values from the x and y to get the remainder
		//passes the remainder to the round to multiple function
		//adds the returned value from the round to multiple function to the floored values of x and y if need be
		//returns a new position that will work the new speed.
		
		BigDecimal flooredX = position.getPosition()[0].setScale(0, RoundingMode.DOWN); 
		BigDecimal flooredY = position.getPosition()[1].setScale(0, RoundingMode.DOWN); 
		
		BigDecimal remainderX = position.getPosition()[0].subtract(flooredX); 
		BigDecimal remainderY = position.getPosition()[1].subtract(flooredY); 
		
		//minus the remainder from one
		BigDecimal leftoverTileX = new BigDecimal("1").subtract(remainderX);
		BigDecimal leftoverTileY = new BigDecimal("1").subtract(remainderY);
		
		//remainder divided by speed
		BigDecimal roundCheckX = leftoverTileX.divide(newSpeed);
		BigDecimal roundCheckY = leftoverTileY.divide(newSpeed);
		
		BigDecimal newX = position.getPosition()[0];
		BigDecimal newY = position.getPosition()[1];
		
		//if it doesn't fit nice
		if (roundCheckX.stripTrailingZeros().scale() > 0) {
			newX = flooredX.add(roundToMutliple(remainderX, newSpeed));
		}
		
		if (roundCheckY.stripTrailingZeros().scale() > 0) {
			newY = flooredY.add(roundToMutliple(remainderY, newSpeed));
		}
		
		return (new Position(newX, newY));
	}
	
	/**
	 *  This takes in a value that needs to be changed to a multiple of the new rat speed value.
	 *  @param change, decimal value that needs to be rounded to the nearest multiple.
	 *  @param speed, the decimal value that the change needs to be rounded to a multiple of.
	 */
	public BigDecimal roundToMutliple(BigDecimal change, BigDecimal speed) {
		//take in the decimals
		
		//multiply by 100 to move two decimal places to the right
		BigDecimal multipliedChange = change.multiply(new BigDecimal("100"));
		BigDecimal multipliedSpeed = speed.multiply(new BigDecimal("100"));
		
		//divide the change by the speed, round the outcome and then multiply by speed
		BigDecimal roundedDivision = (multipliedChange.divide(multipliedSpeed)).setScale(0, RoundingMode.HALF_UP);
		//this rounds the change value to the nearest multiple of speed
		BigDecimal output = (roundedDivision.multiply(multipliedSpeed)).divide(new BigDecimal("100"));
		return (output);
	}
	
	public void dealDamage(double damage) {
		this.ratHealth = this.ratHealth - damage;
		System.out.println(this.ratHealth);
		this.damageCooldown = true;
		if (this.ratHealth <= 0) {
			this.ratDeath();
		}
	}
	
	/**
	 *  Abstract method which handles the death of a rat.
	 */
	public abstract void ratDeath();
	
	public abstract void changeToFemale();
	
	public abstract void changeToMale();
}
