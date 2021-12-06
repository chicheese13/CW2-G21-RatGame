import java.util.*;

/**
 * Class for checking item uses and limits the amount that can be used per game
 *
 * @author Callum Young
 */
public class ItemManager {

    Hashtable<String, Integer> inventory = new Hashtable<String, Integer>();

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

    // Refills only the specific item you want
    public void refillOnly(String itemType) {
        inventory.replace(itemType, 4);
    }

    public int getItemCount(String itemType) {
        return this.inventory.get(itemType);
    }

    // Checks if the item is full
    public boolean isItemFull(String itemType) {
        if ((this.inventory.get(itemType)) == 4) {
            return true;
        } else {
            return false;
        }
    }

    // Checks if an item is depleted
    public boolean isItemDepleted(String itemType) {
        if ((this.inventory.get(itemType)) == 0) {
            return true;
        } else {
            return false;
        }
    }

    // Checks if the item is full and if not then it allows another to be given
    // to player.
    public void tryIncreaseItem(String itemType) {
        if (isItemFull(itemType)) {
            // Item cannot have any more
        } else {
            // adds item to inventory
            inventory.replace(itemType, (this.inventory.get(itemType)) + 1);
        }
    }

    // Checks if the item is depleted and if not then it allows dropping of it.
    public void tryReduceItem(String itemType) {
        if (isItemDepleted(itemType)) {
            // Item pickup error
        } else {
            // allows item to be placed
            inventory.replace(itemType, (this.inventory.get(itemType)) - 1);
        }
    }

    public String printItems() {
        return (inventory.get("Bomb") + " " + inventory.get("Poison") + " "
                + inventory.get("Gas") + " " + inventory.get("Sterilisation")
                + " " + inventory.get("MGenderChange") + " "
                + inventory.get("FGenderChange") + " "
                + inventory.get("DeathRat") + " "
                + inventory.get("NoEntrySign"));

    }
}
