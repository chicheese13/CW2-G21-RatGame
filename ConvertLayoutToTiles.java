import java.util.ArrayList;
import java.math.*;

public class ConvertLayoutToTiles {
	private RenderTile[][] tiles;
	
	//ArrayList<String> cars = new ArrayList<String>();
	
	private ArrayList<RenderTile> afterTiles = new ArrayList<RenderTile>();
	private ArrayList<Position> afterTilesPositon = new ArrayList<Position>();
	
	public ConvertLayoutToTiles(String[][] tiles) {
		this.tiles = convertTiles(tiles);
	}
	
	public RenderTile[][] getTiles() {
		return this.tiles;
	}
	 
	public RenderTile[][] convertTiles(String[][] tileArray){
		RenderTile[][] returnArray = new RenderTile[tileArray.length][tileArray[0].length];
		//probably organise the images into groups, top, left, bottom, right.
		//presume that G = Grass, P = Path and T = Tunnel.
		
		
		for (int y=0; y < tileArray.length; y++) {
			for (int x = 0; x < tileArray[y].length; x++) {
				String fetchString;
				System.out.println("x " + x);
				System.out.println("y " + y);
				boolean isBottom = false;
				boolean isTop = false;
				boolean isLeft = false;
				boolean isRight = false;
				boolean isPath = false;
				boolean isGrass = false;
				boolean isTunnel = false;
				//check top
				//check left
				//check right
				//check bottom
				
				if (tileArray[y][x] == "T") {
					isTunnel = true;
				}
				
				if (tileArray[y][x] == "G") {
					isGrass = true;
				}
				
				if (tileArray[y][x] == "P") {
					isPath = true;
				}
				
				//checks if the current tile is tunnel or Path, if so we can check the surroudning tiles.
				if (tileArray[y][x] == "P") {
					//check top
					if (y > 1) {
						if (tileArray[y-1][x] == "T" || tileArray[y-1][x] == "P") {
							isTop = true;
						}
					}
					//check left
					if (x > 0) {
						if (tileArray[y][x-1] == "T" || tileArray[y][x-1] == "P") {
							isLeft = true;
						}
					}
					//check right
					if (x < tileArray[y].length-1) {
						if (tileArray[y][x+1] == "T" || tileArray[y][x+1] == "P") {
							isRight = true;
						}
					}
					
					if (y < tileArray.length-1) {
						if (tileArray[y+1][x] == "T" || tileArray[y+1][x] == "P") {
							isBottom = true;
						}
					}
				}
				
				//tunnels have special rendering.
				if (tileArray[y][x] == "T") {
					//check the top, left and right to see if its tunnel.
					if (y > 1) {
						if (tileArray[y-1][x] == "T") {
							isTop = true;
						}
					}
					//check left
					if (x > 0) {
						if (tileArray[y][x-1] == "T") {
							isLeft = true;
						}
					}
					//check right
					if (x < tileArray[y].length-1) {
						if (tileArray[y][x+1] == "T") {
							isRight = true;
						}
					}
					
					if (y < tileArray.length-1) {
						if (tileArray[y+1][x] == "T") {
							isBottom = true;
						}
					}
				}
				
				//get the tile.
				if (isGrass) {
					returnArray[y][x] = new RenderTile("Textures/grass.png");
				} else {
					fetchString = "Textures/";
					String left = "left";
					String right = "right";
					String top = "top";
					String bottom = "bottom";
					//String dash = "-";
					
					//top, bottom, left, right, .png
					if (isTop) {
						fetchString = fetchString + top;
					}
					
					if (isBottom) {
						fetchString = fetchString + bottom;
					}
					
					if (isLeft) {
						fetchString = fetchString + left;
					}
					
					if (isRight) {
						fetchString = fetchString + right;
					}
					
					if (isTunnel) {
						fetchString = fetchString + "tunnel";
						afterTiles.add(new RenderTile(fetchString+"tr.png"));
						afterTilesPositon.add(new Position(new BigDecimal(x), new BigDecimal(y)));
					}
					
					fetchString = fetchString + ".png";
					System.out.println(fetchString);
					returnArray[y][x] = new RenderTile(fetchString);
				}
			}
		}	
		return returnArray;
		//return returnArray;
	}
	
	public ArrayList<RenderTile> getAfterList(){
		return this.afterTiles;
	}
	
	public ArrayList<Position> getAfterPositionList(){
		return this.afterTilesPositon;
	}
}
