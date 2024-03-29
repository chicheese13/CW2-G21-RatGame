
/** 
 * AdultRat.java
 * @version 2.0
 * @author Dylan Lewis, Kien Lin
 *
 */

import java.util.Random;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * AdultRat is a class which initialises an instance for AdultRat.
 * 
 * @version 1.0
 * @author Dylan Lewis, Kien Lin
 */

@SuppressWarnings("serial")
public class AdultRat extends NormalRat {
    protected final boolean DEFAULT_PREGNANCY_VALUE = false; // Default value
                                                             // for pregnancy
                                                             // attribute
    protected final int DEFAULT_PREGNANCY_COUNT = 0; // Default value for
                                                     // pregnancy count
    private final int MATING_WAIT_INTERVAL = 200;
    protected final int DEFAULT_COOLDOWN_VALUE_FEMALE = 400; // Default cooldown
                                                             // for females in
                                                             // ticks
    protected final int DEFAULT_COOLDOWN_VALUE_MALE = DEFAULT_COOLDOWN_VALUE_FEMALE
            + MATING_WAIT_INTERVAL; // Default cooldown for males in ticks
    protected final BigDecimal DEFAULT_ADULT_RAT_SPEED = new BigDecimal("0.02"); // Default
                                                                                 // value
                                                                                 // for
                                                                                 // adult
                                                                                 // rat
                                                                                 // speed
    protected final BigDecimal PREGNANT_FEMALE_RAT_SPEED = new BigDecimal(
            "0.01"); // Default value for pregnant adult rat speed
    private final int MAXIMUM_PREGNANCY_COUNT = 5; // Max number of babies a
                                                   // pregnant rat can carry
    private final int GIVE_BIRTH_INTERVAL = 333; // interval in ticks at which
                                                 // the pregnant female rat
                                                 // gives birth

    // Rat image sprites
    protected final String COOLDOWN_SPRITE_MALE_NORTH = "cooldown-male-north";
    protected final String COOLDOWN_SPRITE_MALE_EAST = "cooldown-male-east";
    protected final String COOLDOWN_SPRITE_MALE_SOUTH = "cooldown-male-south";
    protected final String COOLDOWN_SPRITE_MALE_WEST = "cooldown-male-west";

    protected final String ADULT_MALE_RAT_SPRITE_NORTH = "male-rat-north";
    protected final String ADULT_MALE_RAT_SPRITE_EAST = "male-rat-east";
    protected final String ADULT_MALE_RAT_SPRITE_SOUTH = "male-rat-south";
    protected final String ADULT_MALE_RAT_SPRITE_WEST = "male-rat-west";

    protected final String COOLDOWN_SPRITE_FEMALE_NORTH = "cooldown-female-north";
    protected final String COOLDOWN_SPRITE_FEMALE_EAST = "cooldown-female-east";
    protected final String COOLDOWN_SPRITE_FEMALE_SOUTH = "cooldown-female-south";
    protected final String COOLDOWN_SPRITE_FEMALE_WEST = "cooldown-female-west";

    protected final String ADULT_FEMALE_RAT_SPRITE_NORTH = "female-rat-north";
    protected final String ADULT_FEMALE_RAT_SPRITE_EAST = "female-rat-east";
    protected final String ADULT_FEMALE_RAT_SPRITE_SOUTH = "female-rat-south";
    protected final String ADULT_FEMALE_RAT_SPRITE_WEST = "female-rat-west";

    protected final String ADULT_FEMALE_RAT_PREGNANT_SPRITE_NORTH = "female-pregnant-rat-north";
    protected final String ADULT_FEMALE_RAT_PREGNANT_SPRITE_EAST = "female-pregnant-rat-east";
    protected final String ADULT_FEMALE_RAT_PREGNANT_SPRITE_SOUTH = "female-pregnant-rat-south";
    protected final String ADULT_FEMALE_RAT_PREGNANT_SPRITE_WEST = "female-pregnant-rat-west";

    private boolean isWaiting = false;
    private boolean isPregnant;
    private int cooldown; // Cooldown for rat pregnancy
    private int pregnancyCounter; // Number of babies pregnant rat is carrying
    private int waitCounter = 0; // Counts ticks passed
    private int giveBirthCooldown;
    private boolean matingCooldown = false;

    /**
     * Creates an adult rat
     * 
     * @param position        Position to spawn rat at
     * @param gender          Gender of rat
     * @param sterile         Whether or not the rat is sterile
     * @param ratHealth       Health of rat
     * @param currentLevel    the current level in which the rat is in.
     * @param directionFacing the direction in which the rat is facing at
     *                        anytime.
     */
    public AdultRat(Position position, boolean gender, boolean sterile,
            double ratHealth, char direction, Level currentLevel) {
        this.isPregnant = DEFAULT_PREGNANCY_VALUE;
        this.pregnancyCounter = DEFAULT_PREGNANCY_COUNT;
        this.ratSpeed = DEFAULT_ADULT_RAT_SPEED;
        this.ratSterile = sterile;
        this.objectPosition = position;
        this.ratGender = gender;
        this.ratHealth = ratHealth;
        this.directionFacing = direction;
        this.currentLevel = currentLevel;

        // this defines the sprite of the rat based on gender.
        if (gender) {
            this.cooldown = DEFAULT_COOLDOWN_VALUE_MALE;
            this.renderSprite = ADULT_MALE_RAT_SPRITE_EAST;
            this.ratSpriteNorth = ADULT_MALE_RAT_SPRITE_NORTH;
            this.ratSpriteEast = ADULT_MALE_RAT_SPRITE_EAST;
            this.ratSpriteSouth = ADULT_MALE_RAT_SPRITE_SOUTH;
            this.ratSpriteWest = ADULT_MALE_RAT_SPRITE_WEST;
        } else {
            this.cooldown = DEFAULT_COOLDOWN_VALUE_FEMALE;

            this.renderSprite = ADULT_FEMALE_RAT_SPRITE_EAST;
            this.ratSpriteNorth = ADULT_FEMALE_RAT_SPRITE_NORTH;
            this.ratSpriteEast = ADULT_FEMALE_RAT_SPRITE_EAST;
            this.ratSpriteSouth = ADULT_FEMALE_RAT_SPRITE_SOUTH;
            this.ratSpriteWest = ADULT_FEMALE_RAT_SPRITE_WEST;
        }
    }

    /**
     * Makes rat pregnant, causing it to become slower and change its sprite. It
     * also sets a random number of babies
     */
    public void becomePregnant() {
        this.isPregnant = true;

        int min = 1;
        int max = MAXIMUM_PREGNANCY_COUNT;

        Random rand = new Random();
        this.pregnancyCounter = (rand.nextInt(max + min) + min);
        recallibratePosition(this.objectPosition, PREGNANT_FEMALE_RAT_SPEED);

        this.setRatWait(true);
        this.giveBirthCooldown = 0;
    }

    /**
     * Despawns rat
     */
    public void ratDeath() {
        this.currentLevel.spawnSound("rat-death-sound");
        this.currentLevel.despawnRat(this);
        this.currentLevel
                .incrimentScore((this.pregnancyCounter + 1) * RAT_SCORE);
    }

    /**
     * Causes pregnant rat to give birth, spawning baby rats at its location
     */
    public void giveBirth() {
        // choose a random number between 0 and 1
        // 0 representing female
        // 1 representing male
        // then spawn a new baby rat on the AdultRat's current position.
        int min = 1;
        int max = 2;

        Random rand = new Random();
        int randomGender = (rand.nextInt(max - 1 + min) + min);

        boolean babyRatGender = false;

        if (randomGender == 1) {
            babyRatGender = true;
        }

        this.currentLevel.spawnRat(new BabyRat(
                new Position(
                        new BigDecimal(Math.round(
                                this.getObjectPosition()[0].doubleValue())),
                        new BigDecimal(Math.round(
                                this.getObjectPosition()[1].doubleValue()))),
                babyRatGender, this.currentLevel, this.directionFacing));
    }

    /**
     * Starts mating cooldown for rat, not letting it mate until the cooldown is
     * up
     */
    public void startMatingCooldown() {
        this.matingCooldown = true;
    }

    /**
     * Checks whether or not a rat is eligible to mate
     * 
     * @param rat to check
     */
    public boolean isMatabale(AdultRat check) {
        return (!check.getSterile() && !check.getPregnant()
                && !check.getMatingCooldown()
                && !this.matingCooldown && !this.isPregnant
                && !this.ratSterile
                && this.ratGender != check.getRatGender());
    }

    /**
     * Handles collisions with other AdultRats.
     * 
     * @param collidedRat rat collided with
     */
    public void ratCollision(AdultRat collidedRat) {
        // if the rat is not sterile and is not pregnant and is not on a mating
        // cooldown then
        if (isMatabale(collidedRat)) {
            this.setObjectPosition(
                    (this.getObjectPosition()[0]).setScale(0,
                            RoundingMode.HALF_UP),
                    (this.getObjectPosition()[1]).setScale(0,
                            RoundingMode.HALF_UP));
            collidedRat.setObjectPosition(
                    (this.getObjectPosition()[0]).setScale(0,
                            RoundingMode.HALF_UP),
                    (this.getObjectPosition()[1]).setScale(0,
                            RoundingMode.HALF_UP));
            if (!this.ratGender) {
                this.becomePregnant();

                collidedRat.setRatWait(true);
                collidedRat.startMatingCooldown();
            } else {
                collidedRat.becomePregnant();
                this.startMatingCooldown();
            }
            this.setRatWait(true);
        }
    }

    /**
     * Handles a collision of Rat or Item.
     */
    public void collision(Object parameter) {
        if (parameter instanceof AdultRat) {
            ratCollision(((AdultRat) parameter));
        } else if (parameter instanceof Explosion) {
            this.ratDeath();
        }

    }

    /**
     * Checks if the rat is on a mating cooldown, if so deincriment the cooldown
     * Once the cooldown is finished reset the counter and set matingCooldown to
     * false. Update the sprites.
     */
    public void mateCooldown() {
        if (this.matingCooldown && this.cooldown > 0) {
            this.cooldown--;
        } else if (this.matingCooldown) {
            if (!this.ratGender) {
                this.ratSpriteNorth = ADULT_FEMALE_RAT_SPRITE_NORTH;
                this.ratSpriteEast = ADULT_FEMALE_RAT_SPRITE_EAST;
                this.ratSpriteSouth = ADULT_FEMALE_RAT_SPRITE_SOUTH;
                this.ratSpriteWest = ADULT_FEMALE_RAT_SPRITE_WEST;
            } else {
                this.ratSpriteNorth = ADULT_MALE_RAT_SPRITE_NORTH;
                this.ratSpriteEast = ADULT_MALE_RAT_SPRITE_EAST;
                this.ratSpriteSouth = ADULT_MALE_RAT_SPRITE_SOUTH;
                this.ratSpriteWest = ADULT_MALE_RAT_SPRITE_WEST;
            }

            this.matingCooldown = false;
            this.cooldown = 333;
        }
    }

    /**
     * Handles a pregnant rats pregnancy behaviour per tick, if the rat is
     * carrying give birth at certain intervals Once the pregnancy is over
     * revert back to normal rat and have a cooldown
     */
    public void pregnancyTick() {
        if (isPregnant && !isWaiting) {
            this.giveBirthCooldown++;
            if (this.pregnancyCounter > 0) {
                if (this.giveBirthCooldown > GIVE_BIRTH_INTERVAL) {
                    this.giveBirthCooldown = 0;
                    // reset the cooldown
                    // give birth
                    this.giveBirth();
                    // deicnriment pregnancy counter
                    this.pregnancyCounter--;
                }
            } else {
                // make it unpregnant here
                this.cooldown = DEFAULT_COOLDOWN_VALUE_FEMALE;
                this.matingCooldown = true;
                // make not pregnant
                this.isPregnant = false;
                // reset sprites

                switch (this.directionFacing) {
                    case 'N':
                        this.renderSprite = COOLDOWN_SPRITE_FEMALE_NORTH;
                        break;
                    case 'E':
                        this.renderSprite = COOLDOWN_SPRITE_FEMALE_EAST;
                        break;
                    case 'S':
                        this.renderSprite = COOLDOWN_SPRITE_FEMALE_SOUTH;
                        break;
                    default:
                        this.renderSprite = COOLDOWN_SPRITE_FEMALE_WEST;
                        break;
                }

                this.ratSpriteNorth = COOLDOWN_SPRITE_FEMALE_NORTH;
                this.ratSpriteEast = COOLDOWN_SPRITE_FEMALE_EAST;
                this.ratSpriteSouth = COOLDOWN_SPRITE_FEMALE_SOUTH;
                this.ratSpriteWest = COOLDOWN_SPRITE_FEMALE_WEST;

                this.objectPosition = recallibratePosition(this.objectPosition,
                        DEFAULT_ADULT_RAT_SPEED);
                this.ratSpeed = DEFAULT_ADULT_RAT_SPEED;
            }
        }
    }

    /**
     * This handles the rat's waiting behaviour per tick If the rat is not
     * waiting then it should move, else it should wait If the rat is a male
     * then make the sprite a cooldown sprite, and for female make it a
     * pregnancy sprite.
     */
    public void waitTick() {
        if (!this.isWaiting) {
            this.movement();
        } else {
            this.waitCounter++;
            if (this.waitCounter == MATING_WAIT_INTERVAL) {
                this.waitCounter = 0;
                this.setRatWait(false);

                if (this.ratGender) {
                    this.renderSprite = COOLDOWN_SPRITE_MALE_NORTH;
                    this.ratSpriteNorth = COOLDOWN_SPRITE_MALE_NORTH;
                    this.ratSpriteEast = COOLDOWN_SPRITE_MALE_EAST;
                    this.ratSpriteSouth = COOLDOWN_SPRITE_MALE_SOUTH;
                    this.ratSpriteWest = COOLDOWN_SPRITE_MALE_WEST;
                } else {
                    // pregnancy
                    switch (this.directionFacing) {
                        case 'N':
                            this.renderSprite = ADULT_FEMALE_RAT_PREGNANT_SPRITE_NORTH;
                            break;
                        case 'E':
                            this.renderSprite = ADULT_FEMALE_RAT_PREGNANT_SPRITE_EAST;
                            break;
                        case 'S':
                            this.renderSprite = ADULT_FEMALE_RAT_PREGNANT_SPRITE_SOUTH;
                            break;
                        default:
                            this.renderSprite = ADULT_FEMALE_RAT_PREGNANT_SPRITE_WEST;
                            break;
                    }

                    this.ratSpriteNorth = ADULT_FEMALE_RAT_PREGNANT_SPRITE_NORTH;
                    this.ratSpriteEast = ADULT_FEMALE_RAT_PREGNANT_SPRITE_EAST;
                    this.ratSpriteSouth = ADULT_FEMALE_RAT_PREGNANT_SPRITE_SOUTH;
                    this.ratSpriteWest = ADULT_FEMALE_RAT_PREGNANT_SPRITE_WEST;
                    this.ratSpeed = PREGNANT_FEMALE_RAT_SPEED;
                }

            }
        }
    }

    /**
     * Changes gender to female
     */
    public void changeToFemale() {
        if (this.ratGender) {
            this.ratGender = false;
            // reset all sprites
            switch (this.directionFacing) {
                case 'N':
                    this.renderSprite = ADULT_FEMALE_RAT_SPRITE_NORTH;
                    break;
                case 'E':
                    this.renderSprite = ADULT_FEMALE_RAT_SPRITE_EAST;
                    break;
                case 'S':
                    this.renderSprite = ADULT_FEMALE_RAT_SPRITE_SOUTH;
                    break;
                default:
                    this.renderSprite = ADULT_FEMALE_RAT_SPRITE_WEST;
                    break;
            }

            this.ratSpriteNorth = ADULT_FEMALE_RAT_SPRITE_NORTH;
            this.ratSpriteEast = ADULT_FEMALE_RAT_SPRITE_EAST;
            this.ratSpriteSouth = ADULT_FEMALE_RAT_SPRITE_SOUTH;
            this.ratSpriteWest = ADULT_FEMALE_RAT_SPRITE_WEST;

            this.pregnancyCounter = 0;
            this.isPregnant = false;
            this.cooldown = DEFAULT_COOLDOWN_VALUE_FEMALE;
            this.matingCooldown = false;
            this.isWaiting = false;

            this.objectPosition = recallibratePosition(this.objectPosition,
                    DEFAULT_ADULT_RAT_SPEED);
            this.ratSpeed = DEFAULT_ADULT_RAT_SPEED;
        }
    }

    /**
     * Changes gender to male
     */
    public void changeToMale() {
        if (!this.ratGender) {
            switch (this.directionFacing) {
                case 'N':
                    this.renderSprite = ADULT_MALE_RAT_SPRITE_NORTH;
                    break;
                case 'E':
                    this.renderSprite = ADULT_MALE_RAT_SPRITE_EAST;
                    break;
                case 'S':
                    this.renderSprite = ADULT_MALE_RAT_SPRITE_SOUTH;
                    break;
                default:
                    this.renderSprite = ADULT_MALE_RAT_SPRITE_WEST;
                    break;
            }

            this.ratGender = true;

            this.ratSpriteNorth = ADULT_MALE_RAT_SPRITE_NORTH;
            this.ratSpriteEast = ADULT_MALE_RAT_SPRITE_EAST;
            this.ratSpriteSouth = ADULT_MALE_RAT_SPRITE_SOUTH;
            this.ratSpriteWest = ADULT_MALE_RAT_SPRITE_WEST;

            this.pregnancyCounter = 0;
            this.isPregnant = false;
            this.cooldown = DEFAULT_COOLDOWN_VALUE_FEMALE;
            this.matingCooldown = false;
            this.isWaiting = false;

            this.objectPosition = recallibratePosition(this.objectPosition,
                    DEFAULT_ADULT_RAT_SPEED);
            this.ratSpeed = DEFAULT_ADULT_RAT_SPEED;
        }
    }

    /**
     * Method which is responsible for movement, pregnancy and pregnancy
     * cooldown.
     */
    public void tick() {
        this.damageCooldown = false;
        mateCooldown();
        pregnancyTick();
        waitTick();
    }

    // Getters
    /**
     * @return whether or not rat is pregnant
     */
    public boolean getPregnant() {
        return this.isPregnant;
    }

    /**
     * @return cooldown for mating
     */
    public boolean getMatingCooldown() {
        return this.matingCooldown;
    }

    // Setters
    /**
     * @param waiting New isWaiting value
     */
    public void setRatWait(boolean waiting) {
        this.isWaiting = waiting;
    }

}
