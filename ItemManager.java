import java.util.*;

/**
 * Class for checking item uses and limits the amount that can be used per game
 *
 * @author Callum Young
 */
public class ItemManager {
<<<<<<< Updated upstream

=======
	
	Hashtable<String,Integer> inventory = new Hashtable<String,Integer>();
	
	public ItemManager() {
		inventory.put("Bomb",4);
		inventory.put("Poison",4);
		inventory.put("Gas",4);
		inventory.put("Sterilisation",4);
		inventory.put("MgenderChange",4);
		inventory.put("FGenderChange",4);
		inventory.put("DeathRat",4);
		inventory.put("NoEntrySign",4);
	}
	
	public void refillAll() {
		inventory.replace("Bomb",4);
		inventory.replace("Poison",4);
		inventory.replace("Gas",4);
		inventory.replace("Sterile",4);
		inventory.replace("MGChange",4);
		inventory.replace("FGChange",4);
		inventory.replace("DeathRat",4);
		inventory.replace("NoEntrySign",4);
	}
	
	public void refillOnly(String itemType) {
		inventory.replace(itemType,4);
	}
	
	public boolean isItemDepleted(String itemType) {
		if ((this.inventory.get(itemType)) == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void reduceItem(String itemType) {
		if (isItemDepleted(itemType)) {
			//Item pickup error
		} else {
			//allows item to be placed
			inventory.replace(itemType, (this.inventory.get(itemType))-1);
		}
	}
	
	
>>>>>>> Stashed changes
}