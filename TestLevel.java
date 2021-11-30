//test level for rat & items.

import java.util.ArrayList;
import java.math.BigDecimal;

public class TestLevel {
	public int score;
	
	private static String tilesArray [][] = {{"G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G"},
											{"G","P","P","P","P","P","P","P","G","G","G","G","G","G","G", "G","G","G","G","G","G","G","G","G","G","G","G","G","G","G"},
											{"G","P","G","G","T","G","G","P","G","G","G","G","P","P","P", "P","P","G","G","G","P","P","P","P","P","P","P","P","P","G"},
											{"G","P","G","P","P","P","G","P","G","G","G","P","P","G","G", "G","P","P","G","G","P","G","G","G","G","G","G","G","P","G"},
											{"G","P","G","P","G","P","G","P","G","G","G","P","G","G","G", "G","G","P","G","G","P","P","P","P","P","P","P","P","P","G"},
											{"G","P","G","P","G","P","T","P","G","G","P","P","G","G","G", "G","G","P","P","G","G","G","G","P","G","P","G","G","G","G"},
											{"G","P","G","P","G","P","G","P","G","G","P","G","G","G","G", "G","G","G","P","G","G","G","G","P","G","P","G","G","G","G"},
											{"G","P","G","P","P","P","G","P","G","G","P","G","G","G","G", "G","G","G","P","G","G","G","G","P","G","P","G","G","G","G"},
											{"G","P","G","T","G","G","G","P","G","G","P","P","P","P","P", "P","P","P","P","G","G","G","G","P","G","P","G","G","G","G"},
											{"G","P","P","P","P","P","P","P","G","G","P","G","G","G","G", "G","G","G","P","G","G","G","G","P","G","P","G","G","G","G"},
											{"G","P","G","G","G","G","G","G","G","G","P","P","P","P","P", "P","P","P","P","G","G","G","G","P","G","P","G","G","G","G"},
											{"G","P","G","P","P","G","G","G","G","G","P","G","G","G","G", "G","G","G","P","G","G","G","G","P","G","P","G","G","G","G"},
											{"G","P","G","G","P","G","G","G","G","G","P","G","G","G","G", "G","G","G","P","G","G","G","G","P","G","P","G","G","G","G"},
											{"G","P","G","G","P","P","G","G","G","G","P","G","G","G","G", "G","G","G","P","G","G","G","G","P","G","P","G","G","G","G"},
											{"G","P","G","G","G","P","P","G","G","G","P","G","G","G","G", "G","G","G","P","G","G","G","G","P","G","P","G","G","G","G"},
											{"G","P","G","G","G","G","P","P","G","G","P","G","G","G","G", "G","G","G","P","G","G","G","G","P","G","P","G","G","G","G"},
											{"G","P","G","G","G","G","G","P","P","P","P","T","T","T","T", "T","T","T","P","T","T","T","T","P","P","P","G","G","G","G"},
											{"G","P","G","G","G","G","G","T","G","G","G","G","G","G","G", "G","G","G","G","G","G","G","G","G","G","G","G","G","G","G"},
											{"G","P","P","P","P","P","P","P","G","G","G","G","G","G","G", "G","G","G","G","G","G","G","G","G","G","G","G","G","G","G"},
											{"G","G","G","G","G","G","G","G","G","G","G","G","G","G","G", "G","G","G","G","G","G","G","G","G","G","G","G","G","G","G"}};
	
	ArrayList<RenderObject> renderObjectsArray = new ArrayList<RenderObject>();
	
	public TestLevel() {
		this.score = 0;
	}
	
	public void incrimentScore(int scoreIncrease) {
		this.score = this.score + scoreIncrease;
	}
	
	public boolean tileAvailable (BigDecimal xIn, BigDecimal yIn, char direction){
		String tiles [][] = tilesArray;
		
		int x = (int) Math.round(xIn.doubleValue());
		int y = (int) Math.round(yIn.doubleValue());
		
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
	
	public void removeRenderObject(int index) {
		this.renderObjectsArray.remove(index);
	}
}
