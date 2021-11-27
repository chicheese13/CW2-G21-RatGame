
/** 
 * Rat.java
 * @version 1.0
 * @author Dylan Lewis, Kien Lin
 *
 */

import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Random;

/**
 * Rat is an abstract class responsible for rat health/damage, rat’s movement
 * speed and the rat movement behaviour.
 *
 */

public abstract class Rat extends RenderObject {
	/**
	 * The maximum amount of health a rat can have.
	 */
	protected final int MAX_RAT_HEALTH = 5;
	/**
	 * Health of the rat.
	 */
	protected int ratHealth;
	/**
	 * Speed of the rat.
	 */
	protected double ratSpeed;
	/**
	 * The direction in which the rat is facing.
	 */
	protected char directionFacing;
	/**
	 * Tick counter for tile detection in smooth movement.
	 */
	protected float tickCounter = -2;
	/**
	 * size of tiles, used in smooth movement.
	 */
	protected final int TILE_SIZE = 50;
	
	protected final double BABY_RAT_SPEED = 0.04;
	
	/**
	 * rat sprites for each direction.
	 */
	protected Image ratSpriteNorth;
	protected Image ratSpriteSouth;
	protected Image ratSpriteEast;
	protected Image ratSpriteWest;
	
	/**
	 * Score sprites to display when a rat dies, might be multiple.
	 */
	protected final Image SCORE_PLUS_10 = new Image("Textures/plus-ten.png");

	/**
	 * movement() is a method which handles the movement behaviour of a rat.
	 */
	protected void movement(boolean moving) {
		double tickLimit = TILE_SIZE / (TILE_SIZE * this.ratSpeed);
		//for smooth movement we need 
		//double tickLimit = TILE_SIZE / (TILE_SIZE * this.ratSpeed);
		
		//movement here
		//for better general performance we can use a timer to detect when a rat has moved a full tile before doing
		//any tile processing, so when it moves 0.02px it doesn't do path finding until the position is an a full number
		//and not a decimal
		
		//e.g if x or y have a decimal then only move in the current direction
		//if x and y have .00 then do the movement calculations.
		//because we know the rat has moved a full tile since we last did the direction algorithm.
		
		//get a list of available directions
		
		//checks if the current position has a remainder, if not then we know it's moved a full tile so it's time to check
		//the availavle directions.
		
		
		tickCounter++;
		if (tickCounter == tickLimit || tickCounter == -1) {
			tickCounter = 0;
			boolean front = false;
			boolean left = false;
			boolean right = false;
			boolean back = false;
			
			//get the left
			if (this.currentLevel.tileAvailable(this.getObjectPosition()[0], this.getObjectPosition()[1], getDirection('L'))) {
				left = true;
				//System.out.println("Left True");
			}
			//get the right
			if (this.currentLevel.tileAvailable(this.getObjectPosition()[0], this.getObjectPosition()[1], getDirection('R'))) {
				right = true;
				//System.out.println("Right True");
			}
			//get the behind
			if (this.currentLevel.tileAvailable(this.getObjectPosition()[0], this.getObjectPosition()[1], getDirection('B'))) {
				back = true;
				//System.out.println("Back True");
			}
			//get the front
			if (this.currentLevel.tileAvailable(this.getObjectPosition()[0], this.getObjectPosition()[1], this.directionFacing)) {
				front = true;
				//System.out.println("Front True");
			}
			//if the only available direction is back then turn and move backwards
			if (left == false && right == false && front == false && back == true) {
				this.directionFacing = getDirection('B');
			} else {
				//pick a random direction
				ArrayList<Character> avalDirections = new ArrayList<Character>();
				
				//add all available directions to an arraylist and pick a random one.
				if (front) {
					avalDirections.add('F');
				}
				
				if (left) {
					avalDirections.add('L');
				}
				
				if (right) {
					avalDirections.add('R');
				}
			
				// if there is more than one available direction then randomise the direction.
				//if not then then get the direction at index 0.
				if (avalDirections.size() > 1) {
					int min = 1;
					int max = avalDirections.size()-1;
					
					Random rand = new Random();
					int randomIndex = (rand.nextInt(max + min) + min)-1;
					this.directionFacing = getDirection(avalDirections.get(randomIndex));
				} else {
					this.directionFacing = getDirection(avalDirections.get(0));
				}
			}
		}	
		//check the direction the rat is facing and incriment position based on that.
		//also change the rat sprite based on direction.
		if (moving == true) {
			if (this.directionFacing == 'N') {
				//minus y
				this.setObjectPosition(this.getObjectPosition()[0], this.getObjectPosition()[1] - this.ratSpeed);
				this.setSprite(this.ratSpriteNorth);
			} else if (this.directionFacing == 'E') {
				//plus x
				this.setObjectPosition(this.getObjectPosition()[0] + this.ratSpeed, this.getObjectPosition()[1]);
				this.setSprite(this.ratSpriteEast);
			} else if (this.directionFacing == 'S') {
				//plus y
				this.setObjectPosition(this.getObjectPosition()[0], this.getObjectPosition()[1] + this.ratSpeed);
				this.setSprite(this.ratSpriteSouth);
			} else if (this.directionFacing == 'W') {
				//minus x
				this.setObjectPosition(this.getObjectPosition()[0] - this.ratSpeed, this.getObjectPosition()[1]);
				this.setSprite(this.ratSpriteWest);
			}
		}
	}
	
	protected void resetTickCounter() {
		this.tickCounter = 50;
	}
	
	/**
	 * Takes in a direction e.g left and returns the direction relative to which the rat is facing.
	 * This is used for path finding
	 * @param direction
	 */
	public char getDirection(char direction) {
		//initialises default variables.
		char left = 'N';
		char right = 'N';
		char back = 'N';
		
		//switch case which takes in the direction the rat is currently facing
		//gets the direction which is left, right and behind.
		switch(this.directionFacing) {
		  case 'N':
		    left = 'W';
		    right = 'E';
		    back = 'S';
		    break;
		  case 'E':
			  left = 'N';
			  right = 'S';
			  back = 'W';
		    break;
		  case 'S':
			  left = 'E';
			  right = 'W';
			  back = 'N';
			  break;
		  case 'W':
			  left = 'S';
			  right = 'N';
			  back = 'E';
		  	  break;
		}
		
		//returns the desired the direction entered.
		if (direction == 'L') {
			return left;
		} else if (direction == 'R') {
			return right;
		} else if (direction == 'B') {
			return back;
		} else {
			return this.directionFacing;
		}
	}

	/**
	 * Sets the ratHealth attribute of the rat object.
	 * 
	 * @param ratHealth - the value of health you are setting the ratHealth
	 *                  attribute to.
	 */
	public void setRatHealth(int ratHealth) {
		this.ratHealth = ratHealth;
	}

	/**
	 * Returns the attribute ratHealth.
	 * 
	 * @return ratHealth
	 */
	public int getRatHealth() {
		return this.ratHealth;
	}

	// maybe a method for takeDamage.
	/**
	 * Reduces rat's health by a desired value.
	 * 
	 * @param damage - the value of damage that the rat is going to have taken off
	 *               its health.
	 */
	public void takeDamage(int damage) {
		this.ratHealth = this.ratHealth - damage;
	}

	/**
	 * Abstract method which defines what happens every tick for a rat.
	 */

}
