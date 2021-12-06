import java.util.*;

/**
 * @version 1.0
 * @author Callum Young
 * 
 *         Class for checking item uses and limits the amount that can be used
 *         per game
 */
public class ItemManager {

	private static final String BOMB_STR = "Bomb";
	private static final String POISON_STR = "Poison";
	private static final String GAS_STR = "Gas";
	private static final String STERILISATION_STR = "Sterilisation";
	private static final String MGENDERCHANGE_STR = "MGenderChange";
	private static final String FGENDERCHANGE_STR = "FGenderChange";
	private static final String DEATHRAT_STR = "DeathRat";
	private static final String NOENTRYSIGN_STR = "NoEntrySign";

	Hashtable<String, Integer> inventory = new Hashtable<>();

	/**
	 * Constructs an ItemManager with items and their amount
	 * 
	 * @param bomb          starting amount
	 * @param poison        starting amount
	 * @param gas           starting amount
	 * @param sterilisation starting amount
	 * @param mchange       starting amount
	 * @param fchange       starting amount
	 * @param deathrat      starting amount
	 * @param noentry       starting amount
	 */
	public ItemManager(int bomb, int poison, int gas, int sterilisation,
			int mchange, int fchange, int deathrat, int noentry) {
		inventory.put(BOMB_STR, bomb);
		inventory.put(POISON_STR, poison);
		inventory.put(GAS_STR, gas);
		inventory.put(STERILISATION_STR, sterilisation);
		inventory.put(MGENDERCHANGE_STR, mchange);
		inventory.put(FGENDERCHANGE_STR, fchange);
		inventory.put(DEATHRAT_STR, deathrat);
		inventory.put(NOENTRYSIGN_STR, noentry);
	}

	// Refills all items
	public void refillAll() {
		inventory.replace(BOMB_STR, 4);
		inventory.replace(POISON_STR, 4);
		inventory.replace(GAS_STR, 4);
		inventory.replace("Sterile", 4);
		inventory.replace(MGENDERCHANGE_STR, 4);
		inventory.replace(FGENDERCHANGE_STR, 4);
		inventory.replace(DEATHRAT_STR, 4);
		inventory.replace(NOENTRYSIGN_STR, 4);
	}

	/**
	 * Refills only the specific item you want
	 * 
	 * @param itemType
	 */
	public void refillOnly(String itemType) {
		inventory.replace(itemType, 4);
	}

	/**
	 * Gets the item count of desired item
	 * 
	 * @param itemType
	 * @return the number of items of chosen itemType
	 */
	public int getItemCount(String itemType) {
		return this.inventory.get(itemType);
	}

	/**
	 * Checks if the item is full
	 * 
	 * @param itemType
	 * @return the boolean value of whether the item chosen is full
	 */
	public boolean isItemFull(String itemType) {
		return ((this.inventory.get(itemType)) == 4);
	}

	/**
	 * Method for checking whether an item is depleted
	 * 
	 * @param itemType
	 * @return the boolean value of whether the item chosen is depleted
	 */
	public boolean isItemDepleted(String itemType) {
		return ((this.inventory.get(itemType)) == 0);
	}

	/**
	 * Checks if the item is full and if not then it allows another to be given
	 * to player
	 * 
	 * @param itemType
	 */
	public void tryIncreaseItem(String itemType) {
		if (isItemFull(itemType)) {
			// Item cannot have any more
		} else {
			// adds item to inventory
			inventory.replace(itemType, (this.inventory.get(itemType)) + 1);
		}
	}

	/**
	 * Checks if the item is depleted and if not then it allows dropping of it.
	 * 
	 * @param itemType
	 */
	public void tryReduceItem(String itemType) {
		if (isItemDepleted(itemType)) {
			// Item pickup error
		} else {
			// allows item to be placed
			inventory.replace(itemType, (this.inventory.get(itemType)) - 1);
		}
	}

	/**
	 * @return the string of item counts
	 */
	public String printItems() {
		return (inventory.get(BOMB_STR) + " " + inventory.get(POISON_STR) + " "
				+ inventory.get(GAS_STR) + " " + inventory.get(STERILISATION_STR)
				+ " " + inventory.get(MGENDERCHANGE_STR) + " "
				+ inventory.get(FGENDERCHANGE_STR) + " "
				+ inventory.get(DEATHRAT_STR) + " "
				+ inventory.get(NOENTRYSIGN_STR));

	}
}
