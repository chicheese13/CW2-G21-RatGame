//test level for rat & items.

import java.util.ArrayList;

public class TestLevel {
	private static String tilesArray [][] = {{"G","G","G","G","G","G","G","G","G","G","G","G","G","G","G"},
		{"G","P","P","P","T","T","P","P","P","T","T","P","P","P","G"},
		{"G","G","G","P","G","G","G","G","P","G","G","P","G","P","G"},
		{"G","P","P","P","G","G","P","P","P","G","G","P","G","P","G"},
		{"G","P","G","G","G","G","G","G","P","G","G","P","G","P","G"},
		{"G","P","P","P","T","T","P","P","P","T","T","P","P","P","G"},
		{"G","G","G","G","G","G","G","G","G","G","G","G","G","G","G"}};
	
	ArrayList<RenderObject> renderObjectsArray = new ArrayList<RenderObject>();
	
	public TestLevel() {
		
	}
	
	public boolean tileAvailable (double xIn, double yIn, char direction){
		String tiles [][] = tilesArray;
		
		int x = (int) Math.floor(xIn);
		int y = (int) Math.floor(yIn);
		
		if (direction == 'N') {
			if (y > 0) {
				if (tiles[y-1][x] == "P" || tiles[y-1][x] == "T") {
					return true;
				}
			}
		} else if (direction == 'E'){
			if (x > 0) {
				if (tiles[y][x+1] == "P" || tiles[y][x+1] == "T") {
					return true;
				}
			}
		} else if (direction == 'S') {
			if (y < (tiles.length-1)) {
				if (tiles[y+1][x] == "P" || tiles[y+1][x] == "T") {
					return true;
				}
			}
		} else {
			if (x > 0) {
				if (tiles[y][x-1] == "P" || tiles[y][x-1] == "T") {
					return true;
				}
			}
		}
		return false;
	} 
	
	public String[][] getTiles() {
		return tilesArray;
	}
	
	public ArrayList<RenderObject> getRenderObjects () {
		return this.renderObjectsArray;
	}
	
	public void addRenderObject(RenderObject addElement) {
		this.renderObjectsArray.add(addElement);
	}
	
	public void removeRenderObject() {
		
	}
}
