import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Level.java
 * @author Lewis Ward, Luca Collicott
 * @version 1.0
 */

/**
 * Creates a board that will be used to store information on the level, such as tile, rat, and item locations.
 */
public class Level {
	//Object[][][] board;
	int score;
	double currentTime;
	int parTime;
	short maxRats = 50;
	String[] dataArray;
	private final int ITEM_GAIN_INTERVAL = 660;
	
	//private String[][] tiles;
	private static String tiles [][] = {{"G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G","G"},
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

	
	//ArrayLists for Rats & Items
	ArrayList<RenderObject> renderRats = new ArrayList<RenderObject>();
	ArrayList<RenderObject> renderItems = new ArrayList<RenderObject>();
	ArrayList<RenderObject> renderTempTiles = new ArrayList<RenderObject>();
	ArrayList<RenderTile> renderAfterTiles;
	ArrayList<Position> renderAfterTilesPosition;
	private RenderTile[][] renderTiles;
	
	private int offsetX = 0;
	private int offsetY = 0;
	
	/**
	 * Constructor for level class. Creates board from given text file
	 * @param fileName Name of the file that stores information on the level
	 */
	public Level (String fileName) {
		String fileData = "";
		File level = new File(fileName);
		Scanner in = null;
		
		try {
			in = new Scanner(level);
		} catch(FileNotFoundException e) {
			System.out.println(fileName);
			System.out.println("File does not exist");
			System.exit(0);
		}
		
		while (in.hasNextLine()) {
			fileData = fileData + in.nextLine();
		}		
		
		fileData = fileData.substring(0, fileData.indexOf("."));
		dataArray = fileData.split(",");
		
		//Sets variables for each piece of data to make it easier to read
		int x = Integer.parseInt(dataArray[0]);
		int y = Integer.parseInt(dataArray[1]);
		String[] tileTypes = dataArray[2].split("");
		String[] rats = dataArray[3].split("");
		maxRats = Short.parseShort(dataArray[4]);
		parTime = Integer.parseInt(dataArray[5]);
		
		//board = new Object[y][x][3];
		
		
		
		//Adds tile types into correct position and sets up ArrayLists
		int count = 0;
		for(int i=0; i<y;i++) {
			for(int j=0; j<x;j++) {
				//board[i][j][0] = tileTypes[count];
				//board[i][j][1] = new ArrayList<Rat>();
				//board[i][j][2] = new ArrayList<Item>();
				count ++;
			}
		}
		
		//Adds rats in correct position
		
		//Position position, boolean gender, TestLevel currentLevel, char directionFacing
		
		count = 0;
		for(int i=0; i<y;i++) {
			for(int j=0; j<x;j++) {
				if (rats[count] == "M") {
					System.out.println("test1");
					renderRats.add(new BabyRat(new Position(new BigDecimal(i), new BigDecimal(j)), true, this, 'N'));
				} else if (rats[count] == "F") {
					System.out.println("test2");
					renderRats.add(new BabyRat(new Position(new BigDecimal(i), new BigDecimal(j)), false, this, 'N'));
				}
				count ++;
			}
		}	
		
		//generate render tiles
		ConvertLayoutToTiles convertedLayout = new ConvertLayoutToTiles(tiles);
		this.renderTiles = convertedLayout.getTiles();
		this.renderAfterTiles = convertedLayout.getAfterList();
		this.renderAfterTilesPosition = convertedLayout.getAfterPositionList();
		
		//add test rats
		//this.spawnRat(new BabyRat(new Position(new BigDecimal("1"), new BigDecimal("1")), false, this, 'N'));
		//this.spawnRat(new BabyRat(new Position(new BigDecimal("1"), new BigDecimal("1")), true, this, 'N'));
		
		this.spawnRat(new AdultRat(new Position(new BigDecimal("1"), new BigDecimal("1")), true, false, 100, 'N', this));
		this.spawnRat(new AdultRat(new Position(new BigDecimal("1"), new BigDecimal("1")), false, false, 100, 'N', this));
		
	}
	
	public int getOffsetX() {
		return this.offsetX;
	}
	
	public int getOffsetY() {
		return this.offsetY;
	}
	
	public void setOffsetX(int x) {
		this.offsetX = x;
	}
	
	public void setOffsetY(int y) {
		this.offsetY = y;
	}
	
	public RenderTile[][] getRenderTiles() {
		return this.renderTiles;
	}
	
	public ArrayList<RenderTile> getRenderTilesAfter() {
		return this.renderAfterTiles;
	}
	
	public ArrayList<Position> getRenderTilesAfterPositions() {
		return this.renderAfterTilesPosition;
	}
	
	public String[][] getTiles(){
		return this.tiles;
	}
	
	public void spawnItem(Item spawnItem) {
		this.renderItems.add(spawnItem);
	}
	
	public ArrayList<RenderObject> getItems(){
		return this.renderItems;
	}
	
	/**
	 * Updates board
	 */
	public void updateBoard() {
		//happens every tick?
		
		//Handles collisions between Rats and Rats
		
		
		
		for (int i = 0; i < renderRats.size(); i++) {
			double xMinus = renderRats.get(i).getObjectPosition()[0].doubleValue() - 0.5;
			double yMinus = renderRats.get(i).getObjectPosition()[1].doubleValue() - 0.5;
			double xPlus = renderRats.get(i).getObjectPosition()[0].doubleValue() + 0.5;
			double yPlus = renderRats.get(i).getObjectPosition()[1].doubleValue() + 0.5;
			
			//checking if the the render objects are both rats and are not the same rat.
			for (int i2 = 0; i2 < renderRats.size(); i2++) {
				//testLevel.getRenderObjects().get(i).getObjectPosition()[0]
				
				boolean xCollide = false;
				boolean yCollide = false;
				
				if (renderRats.get(i) != renderRats.get(i2)) {
					
					double compareX = renderRats.get(i2).getObjectPosition()[0].doubleValue();
					double compareY = renderRats.get(i2).getObjectPosition()[1].doubleValue();
					
					if (compareX > xMinus && compareX < xPlus) {
						xCollide = true;
						//System.out.println("X COLLIDE");
					}
					
					if (compareY > yMinus && compareY < yPlus) {
						yCollide = true;
						//System.out.println("Y COLLIDE");
					}
					
					if (xCollide == true && yCollide == true) {
							((Rat) renderRats.get(i)).collision((Rat) renderRats.get(i2));
					}
					
				}
				
			}
		}
		
		//Handles collisions between Items and Rats
		for (int i = 0; i < renderItems.size(); i++) {
			double xMinus = renderItems.get(i).getObjectPosition()[0].doubleValue() - 0.5;
			double yMinus = renderItems.get(i).getObjectPosition()[1].doubleValue() - 0.5;
			double xPlus = renderItems.get(i).getObjectPosition()[0].doubleValue() + 0.5;
			double yPlus = renderItems.get(i).getObjectPosition()[1].doubleValue() + 0.5;
			
			//checking if the the render objects are both rats and are not the same rat.
			for (int i2 = 0; i2 < renderRats.size(); i2++) {
				//testLevel.getRenderObjects().get(i).getObjectPosition()[0]
				
				boolean xCollide = false;
				boolean yCollide = false;
				
					
					double compareX = renderRats.get(i2).getObjectPosition()[0].doubleValue();
					double compareY = renderRats.get(i2).getObjectPosition()[1].doubleValue();
					
					if (compareX > xMinus && compareX < xPlus) {
						xCollide = true;
						//System.out.println("X COLLIDE");
					}
					
					if (compareY > yMinus && compareY < yPlus) {
						yCollide = true;
						//System.out.println("Y COLLIDE");
					}
					
					if (xCollide == true && yCollide == true) {
						if (renderItems.get(i) instanceof CollideItem) {
							System.out.println("ITEM COLLISION DETECTED");
							((CollideItem) renderItems.get(i)).collision((Rat) renderRats.get(i2));
						}
					}
					
				
			}
		}
		
		
		for (int i = 0; i < renderTempTiles.size(); i++) {
			double xMinus = renderTempTiles.get(i).getObjectPosition()[0].doubleValue() - 0.5;
			double yMinus = renderTempTiles.get(i).getObjectPosition()[1].doubleValue() - 0.5;
			double xPlus = renderTempTiles.get(i).getObjectPosition()[0].doubleValue() + 0.5;
			double yPlus = renderTempTiles.get(i).getObjectPosition()[1].doubleValue() + 0.5;
			
			//checking if the the render objects are both rats and are not the same rat.
			for (int i2 = 0; i2 < renderRats.size(); i2++) {
				//testLevel.getRenderObjects().get(i).getObjectPosition()[0]
				
				boolean xCollide = false;
				boolean yCollide = false;
				
					
					double compareX = renderRats.get(i2).getObjectPosition()[0].doubleValue();
					double compareY = renderRats.get(i2).getObjectPosition()[1].doubleValue();
					
					if (compareX > xMinus && compareX < xPlus) {
						xCollide = true;
						//System.out.println("X COLLIDE");
					}
					
					if (compareY > yMinus && compareY < yPlus) {
						yCollide = true;
						//System.out.println("Y COLLIDE");
					}
					
					if (xCollide == true && yCollide == true) {
						if (renderTempTiles.get(i) instanceof CollideItem) {
							System.out.println("TEMP TILE COLLISION");
							((CollideItem) renderTempTiles.get(i)).collision((Rat) renderRats.get(i2));
						}
					}
					
				
			}
		}
		
		
		//goes through all rats and items and does executes their tick methods
				for (int i = 0; i < renderRats.size(); i++) {
					renderRats.get(i).tick();
				}
				
				for (int i = 0; i < renderItems.size(); i++) {
					renderItems.get(i).tick();
				}
				
				for (int i = 0; i < renderTempTiles.size(); i++) {
					renderTempTiles.get(i).tick();
				}
		
	}
	
	public void spawnTempTile(RenderObject tempTile) {
		this.renderTempTiles.add(tempTile);
	}
	
	public void despawnTempTile(RenderObject tempTile) {
		for (int i = 0; i < renderTempTiles.size(); i++) {
			if (renderTempTiles.get(i) == tempTile) {
				renderTempTiles.remove(i);
			}
		}
	}
	
	public ArrayList<RenderObject> getTempTiles() {
		return this.renderTempTiles;
	}
	
	/**
	 * Saves board to text file
	 */
	public void saveBoard() {
		
	}
	
	/**
	 * Spawns rat on board
	 * @param rat Rat to be added to board
	 */
	public void spawnRat(Rat rat) {
		renderRats.add(rat);
	}
	
	public void despawnRat(Rat rat) {
		for (int i = 0; i < renderRats.size(); i++) {
			if (renderRats.get(i) == rat) {
				renderRats.remove(i);
			}
		}
	}
	
	public void despawnItem(Item item) {
		for (int i = 0; i < renderItems.size(); i++) {
			if (renderItems.get(i) == item) {
				renderItems.remove(i);
			}
		}
	}
	
	public boolean isPlacable(double xIn, double yIn) {
		//need to check if there's any rat's colliding with that tile, or items and if the tile is grass or not
		int x = (int) xIn;
		int y = (int) yIn;
		
		System.out.println(x);
		System.out.println(y);
		System.out.println("---");
		
		if (tiles[y][x] == "G" || tiles[y][x] == "T") {
			return false;
		}
		
		for (int i2 = 0; i2 < renderItems.size(); i2++) {
			if (renderItems.get(i2).getObjectPosition()[0].doubleValue() == xIn
				&& renderItems.get(i2).getObjectPosition()[1].doubleValue() == yIn) {
					return false;
			}
		}
		
		return true;
	}
	
	public boolean isPlaceableSign(double xIn, double yIn) {
		//check for collisions
		int x = (int) xIn;
		int y = (int) yIn;
		if (tiles[y][x] == "G" || tiles[y][x] == "T") {
			return false;
		}
		
				double xMinus = xIn-0.5;
				double yMinus = yIn-0.5;
				double xPlus = xIn+0.5;
				double yPlus = yIn+0.5;
				
				for (int i2 = 0; i2 < renderRats.size(); i2++) {
					//testLevel.getRenderObjects().get(i).getObjectPosition()[0]
					
					boolean xCollide = false;
					boolean yCollide = false;
					
						
						double compareX = renderRats.get(i2).getObjectPosition()[0].doubleValue();
						double compareY = renderRats.get(i2).getObjectPosition()[1].doubleValue();
						
						if (compareX > xMinus && compareX < xPlus) {
							xCollide = true;
							//System.out.println("X COLLIDE");
						}
						
						if (compareY > yMinus && compareY < yPlus) {
							yCollide = true;
							//System.out.println("Y COLLIDE");
						}
						
						if (xCollide == true && yCollide == true) {
							return false;
						}
				}
				
				for (int i2 = 0; i2 < renderItems.size(); i2++) {
					if (renderItems.get(i2).getObjectPosition()[0].doubleValue() == xIn
						&& renderItems.get(i2).getObjectPosition()[1].doubleValue() == yIn) {
							return false;
					}
				}
				return true;
	}
	
	/**
	 * Places item on board
	 * @param item Item to be placed
	 * @param x Desired X coordinate of item
	 * @param y Desired Y coordinate of item
	 */
	public void placeItem (Item item, int x, int y) {
		
	}
	
	//Getters
	
	/**
	 * @return The board the level is being played on
	 */
	//public Object[][][] getBoard() {
		//return board;
	//}
	
	/**
	 * @return Par time to complete level
	 */
	public int getParTime() {
		return parTime;
	}
	
	/**
	 * @return How much time has currently passed in the level
	 */
	public double getCurrentTime() {
		return currentTime;
	}
	
	/**
	 * @return The number of rats that will result in game over
	 */
	public short getMaxRats() {
		return maxRats;
	}
	/**
	 * @return All rats on the board
	 */
	public ArrayList<RenderObject> getRats() {
		return this.renderRats;
	}
	
	/**
	 * @return 
	 */
	public Item getItem() {
		return null;
	}
	
	public boolean tileAvailable (BigDecimal xIn, BigDecimal yIn, char direction){
		
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
	
	public boolean checkGas(Position position) {
		for (int i = 0; i < this.renderTempTiles.size(); i++) {
			if (this.renderTempTiles.get(i) instanceof GasSpread) {
				if (((GasSpread) this.renderTempTiles.get(i)).getObjectPosition()[0].doubleValue()-offsetX == position.getPosition()[0].doubleValue()-offsetX
					&& ((GasSpread) this.renderTempTiles.get(i)).getObjectPosition()[1].doubleValue()-offsetY == position.getPosition()[1].doubleValue()-offsetY){
						return false;
					}
			}
		}		
		return true;
	}
	
	public int getItemInterval() {
		return this.ITEM_GAIN_INTERVAL;
	}
	
}
