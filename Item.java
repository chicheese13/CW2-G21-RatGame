
public class Item {

	private int locationX;
	private int locationY;

	public void location(int x, int y) {
		this.locationX = x;
		this.locationY = y;
	}

	public int[] getLocation() {
		int returnArray[] = { locationX, locationY };
		return returnArray;
	}

	public void setLocation(int x, int y) {
		this.locationX = x;
		this.locationY = y;
	}
}
