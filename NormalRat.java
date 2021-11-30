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
	
	
	/**
	 * Abstract method which defines what happens every tick for a rat.
	 */
	
	public Position recallibratePosition(Position position, BigDecimal newSpeed) {
		//get the current decimal
		//minus it from one to get the left over amount of tile
		//divide tile by new speed
		//if it's decimal math.ceil
		//multiply the new speed by math.ceil value
		//minus 1 this will get us the left over amount of tile
		//minus the left over from the current position.
		
		
		
		
		//get the remainder, minus it from one
		//divide the remainder by the speed
		//if it doesn't fit nicely then round up the remainder divided by speed and multiply
		//minus one from the remainder divided by speed
		//minus the remainder divided by speed to the current position
		
		//get the remainder
		BigDecimal flooredX = position.getPosition()[0].setScale(0, RoundingMode.DOWN); 
		BigDecimal flooredY = position.getPosition()[1].setScale(0, RoundingMode.DOWN); 
		
		BigDecimal remainderX = position.getPosition()[0].subtract(flooredX); 
		BigDecimal remainderY = position.getPosition()[1].subtract(flooredY); 
		
		System.out.println("------------");
		System.out.println(remainderX);
		System.out.println("------------");
		System.out.println(remainderY);
		System.out.println("------------");
		
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
			newX = flooredX.add(cleanDecimal(remainderX, newSpeed));
		}
		
		if (roundCheckY.stripTrailingZeros().scale() > 0) {
			System.out.println("DECIMAL");
			System.out.println(leftoverTileY);
			newY = flooredY.add(cleanDecimal(remainderY, newSpeed));
		}
		
		return (new Position(newX, newY));
	}
	
	public BigDecimal cleanDecimal(BigDecimal change, BigDecimal speed) {
		//take in the decimals
		
		//multiply by 100 to move two decimal places to the right
		BigDecimal multipliedChange = change.multiply(new BigDecimal("100"));
		BigDecimal multipliedSpeed = speed.multiply(new BigDecimal("100"));
		
		System.out.println(multipliedChange);
		System.out.println(multipliedSpeed);
		
		//divide the change by the speed, round the outcome and then multiply by speed
		BigDecimal roundedDivision = (multipliedChange.divide(multipliedSpeed)).setScale(0, RoundingMode.HALF_UP);
		//this rounds the change value to the nearest multiple of speed
		BigDecimal output = (roundedDivision.multiply(multipliedSpeed)).divide(new BigDecimal("100"));
		return (output);
	}
}
