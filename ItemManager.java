import java.util.*;

/**
 * Class for checking item uses and limits the amount that can be used per game
 *
 * @author Callum Young
 */
public class ItemManager {
	
	Hashtable<String,Integer> inventory = new Hashtable<String,Integer>();
	
	public ItemManager() {
		inventory.put("Bomb",0);
		inventory.put("Poison",0);
		inventory.put("Gas",0);
		inventory.put("Sterilisation",0);
		inventory.put("MGenderChange",0);
		inventory.put("FGenderChange",0);
		inventory.put("DeathRat",0);
		inventory.put("NoEntrySign",0);
	}
	
	//Refills all items
	public void refillAll() {
		inventory.replace("Bomb",4);
		inventory.replace("Poison",4);
		inventory.replace("Gas",4);
		inventory.replace("Sterile",4);
		inventory.replace("MGenderChange",4);
		inventory.replace("FGenderChange",4);
		inventory.replace("DeathRat",4);
		inventory.replace("NoEntrySign",4);
	}
	
	//Refills only the specific item you want
	public void refillOnly(String itemType) {
		inventory.replace(itemType,4);
	}
	
	public int getItemCount(String itemType) {
		return this.inventory.get(itemType);
	}
	
	//Checks if the item is full
	public boolean isItemFull(String itemType) {
		if ((this.inventory.get(itemType)) == 4) {
			return true;
		} else {
			return false;
		}
	}
	
	//Checks if an item is depleted
	public boolean isItemDepleted(String itemType) {
		if ((this.inventory.get(itemType)) == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	//Checks if the item is full and if not then it allows another to be given to player.  
	public void tryIncreaseItem(String itemType) {
		if (isItemFull(itemType)) {
			//Item cannot have any more 
		} else {
			//adds item to inventory
			inventory.replace(itemType, (this.inventory.get(itemType)) + 1);
		}
	}
	
	//Checks if the item is depleted and if not then it allows dropping of it.  
	public void tryReduceItem(String itemType) {
		if (isItemDepleted(itemType)) {
			//Item pickup error
		} else {
			//allows item to be placed
			inventory.replace(itemType, (this.inventory.get(itemType)) - 1);
		}
	}
}