
public class Item {
	
	private int locationX;
	private int locationY;
	// Constructor for setting item location
	public void location(int x, int y) {
		this.locationX = x;
		this.locationY = y;
	}
	
	// Method for getting the location of an item
	public int[] getLocation() {
		int returnArray[] = { locationX, locationY };
		return returnArray;
	}
	
	//Method for setting the location of an item
	public void setLocation(int x, int y) {
		this.locationX = x;
		this.locationY = y;
	}
}
