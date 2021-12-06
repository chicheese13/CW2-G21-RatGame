import java.util.*;

/**
 * @version 1.0
 * @author Callum Young
 * 
 * Class for checking item uses and limits the amount that can be used per game
 */
public class ItemManager {

    Hashtable<String, Integer> inventory = new Hashtable<String, Integer>();
    /**
     * Constructs an ItemManager with items and their amount
     * @param bomb starting amount
     * @param poison starting amount
     * @param gas starting amount
     * @param sterilisation starting amount
     * @param mchange starting amount
     * @param fchange starting amount
     * @param deathrat starting amount
     * @param noentry starting amount
     */
    public ItemManager(int bomb, int poison, int gas, int sterilisation,
            int mchange, int fchange, int deathrat, int noentry) {
        inventory.put("Bomb", bomb);
        inventory.put("Poison", poison);
        inventory.put("Gas", gas);
        inventory.put("Sterilisation", sterilisation);
        inventory.put("MGenderChange", mchange);
        inventory.put("FGenderChange", fchange);
        inventory.put("DeathRat", deathrat);
        inventory.put("NoEntrySign", noentry);
    }

    // Refills all items
    public void refillAll() {
        inventory.replace("Bomb", 4);
        inventory.replace("Poison", 4);
        inventory.replace("Gas", 4);
        inventory.replace("Sterile", 4);
        inventory.replace("MGenderChange", 4);
        inventory.replace("FGenderChange", 4);
        inventory.replace("DeathRat", 4);
        inventory.replace("NoEntrySign", 4);
    }


    /**
     *  Refills only the specific item you want
     * @param itemType
     */
    public void refillOnly(String itemType) {
        inventory.replace(itemType, 4);
    }

    /**
     * Gets the item count of desired item
     * @param itemType
     * @return the number of items of chosen itemType
     */
    public int getItemCount(String itemType) {
        return this.inventory.get(itemType);
    }


    /**
     * Checks if the item is full
     * @param itemType
     * @return the boolean value of whether the item chosen is full
     */
    public boolean isItemFull(String itemType) {
        if ((this.inventory.get(itemType)) == 4) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method for checking whether an item is depleted
     * @param itemType
     * @return the boolean value of whether the item chosen is depleted
     */
    public boolean isItemDepleted(String itemType) {
        if ((this.inventory.get(itemType)) == 0) {
            return true;
        } else {
            return false;
        }
    }

  
    /**
     * Checks if the item is full and if not then it allows another to be given
     * to player
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
        return (inventory.get("Bomb") + " " + inventory.get("Poison") + " "
                + inventory.get("Gas") + " " + inventory.get("Sterilisation")
                + " " + inventory.get("MGenderChange") + " "
                + inventory.get("FGenderChange") + " "
                + inventory.get("DeathRat") + " "
                + inventory.get("NoEntrySign"));

    }
}
