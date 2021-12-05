import java.time.*;
import java.util.Arrays;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.temporal.ChronoField;
import java.io.*;  // Import the File class

/**
 * Level.java
 * @author Lewis Ward, Luca Collicott
 * @version 1.0
 */

/**
 * Creates a board that will be used to store information on the level, such as tile, rat, and item locations.
 */
public class Level implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Object[][][] board;
	int score;
	double currentTime;
	BigDecimal parTime;
	short maxRats;
	String[] dataArray;
	
	private double gameCounterPoison = 0;
	private double gameCounterGas = 0;
	private double gameCounterBomb = 0;
	private double gameCounterNoEntry = 0;
	private double gameCounterSteril = 0;
	private double gameCounterMGChange = 0;
	private double gameCounterFGChange = 0;
	private double gameCounterDeathRat = 0;
	
	private int ITEM_GAIN_INTERVAL_POISON;
	private int ITEM_GAIN_INTERVAL_GAS;
	private int ITEM_GAIN_INTERVAL_BOMB;
	private int ITEM_GAIN_INTERVAL_NOENTRY;
	private int ITEM_GAIN_INTERVAL_STERIL;
	private int ITEM_GAIN_INTERVAL_MGCHANGE;
	private int ITEM_GAIN_INTERVAL_FGCHANGE;
	private int ITEM_GAIN_INTERVAL_DEATHRAT;
	
	
	//private String[][] tiles;
	private static char tiles [][];

	
	//ArrayLists for Rats & Items
	ArrayList<RenderObject> renderRats = new ArrayList<RenderObject>();
	ArrayList<RenderObject> renderItems = new ArrayList<RenderObject>();
	ArrayList<RenderObject> renderTempTiles = new ArrayList<RenderObject>();
	transient ArrayList<RenderTile> renderAfterTiles;
	transient ArrayList<Position> renderAfterTilesPosition;
	ArrayList<SoundClip> renderSound = new ArrayList<SoundClip>();
	private transient RenderTile[][] renderTiles;
	
	private int offsetX = 0;
	private int offsetY = 0;
	private transient ItemManager itemManager;
	private transient Profile currentUser;
	private String fileName;
	private int currentLevelNumber;
	
	
	/**
	 * Constructor for level class. Creates board from given text file
	 * @param fileName Name of the file that stores information on the level
	 */
	public Level (String fileName, String saveFile, ItemManager items, Profile currentUser, int currentLevelNumber) {
		ArrayStorage tst = new ArrayStorage();
		
		this.itemManager = items;
		this.fileName = fileName;
		this.currentLevelNumber = currentLevelNumber;
		this.currentUser = currentUser;
		String fileData = "";
		File level = new File(fileName);
		Scanner in = null;
		
		try {
			in = new Scanner(level);
		} catch(Exception e) {
			System.out.print("EERORO");
		}
		
		while (in.hasNextLine()) {
			fileData = fileData + in.nextLine();
		}		
		
		if (saveFile != "") {
			//need to load in the file and populate the arrays.
		}
		
		fileData = fileData.substring(0, fileData.indexOf("."));
		dataArray = fileData.split(",");
		
		
		//Sets variables for each piece of data to make it easier to read
		int x = Integer.parseInt(dataArray[0]);
		int y = Integer.parseInt(dataArray[1]);
		String[] tileTypes = dataArray[2].split("");
		String[] rats = dataArray[3].split("");
		maxRats = Short.parseShort(dataArray[4]);
		parTime = new BigDecimal(dataArray[5]);
		String[] itemIntervalsString = dataArray[6].split(" ");
		
		System.out.println("LENGTH OF ITEM INTERVALS");
		System.out.println(itemIntervalsString.length);
		
		for (int i = 0; i < itemIntervalsString.length; i++) {
			ITEM_GAIN_INTERVAL_POISON = Integer.parseInt(itemIntervalsString[0]);
			ITEM_GAIN_INTERVAL_GAS = Integer.parseInt(itemIntervalsString[1]);
			ITEM_GAIN_INTERVAL_BOMB = Integer.parseInt(itemIntervalsString[2]);
			ITEM_GAIN_INTERVAL_NOENTRY = Integer.parseInt(itemIntervalsString[3]);
			ITEM_GAIN_INTERVAL_STERIL = Integer.parseInt(itemIntervalsString[4]);
			ITEM_GAIN_INTERVAL_MGCHANGE = Integer.parseInt(itemIntervalsString[5]);
			ITEM_GAIN_INTERVAL_FGCHANGE = Integer.parseInt(itemIntervalsString[6]);
			ITEM_GAIN_INTERVAL_DEATHRAT = Integer.parseInt(itemIntervalsString[7]);
		}
		
		//board = new Object[y][x][3];
		
		this.tiles = new char[y][x];
		
		//Adds tile types into correct position and sets up ArrayLists
		int count = 0;
		for(int i=0; i<y;i++) {
			for(int j=0; j<x;j++) {
				tiles[i][j] = tileTypes[count].charAt(0);
				count ++;
			}
		}
		
		if (saveFile.equals("")) {
			count = 0;
			for(int i=0; i<y;i++) {
				for(int j=0; j<x;j++) {
					if (rats[count].charAt(0) == 'M') {
						this.spawnRat(new BabyRat(new Position(new BigDecimal(j), new BigDecimal(i)), true, this, 'N'));
					} else if (rats[count].charAt(0) == 'F') {
						this.spawnRat(new BabyRat(new Position(new BigDecimal(j), new BigDecimal(i)), false, this, 'N'));
					}
					count ++;
				}
			}
		} else {
			//spawn the rats
			
			ArrayList<RenderObject> tempArray = new ArrayList<RenderObject>();
			
			System.out.println(saveFile);
			try{
			    FileInputStream readData = new FileInputStream(saveFile+"rats.ser");
			    ObjectInputStream readStream = new ObjectInputStream(readData);

			    this.renderRats = (ArrayList<RenderObject>) readStream.readObject();
			    readStream.close();
			}catch (Exception e) {
			    e.printStackTrace();
			}
			
			try{
			    FileInputStream readData = new FileInputStream(saveFile+"item-tiles.ser");
			    ObjectInputStream readStream = new ObjectInputStream(readData);

			    tempArray = (ArrayList<RenderObject>) readStream.readObject();
			    readStream.close();
			    
			    for (int i = 0; i < tempArray.size(); i++) {
			    	if (tempArray.get(i) instanceof GasSpread) {
			    		this.renderTempTiles.add(tempArray.get(i));
			    	} else if (tempArray.get(i) instanceof Explosion) {
			    		this.renderTempTiles.add(tempArray.get(i));
			    	} else {
			    		this.renderItems.add(tempArray.get(i));
			    	}
			    }
			    
			    //overwrites previous level with this level
			    for (int i = 0; i < renderTempTiles.size(); i++) {
			    	renderTempTiles.get(i).setLevel(this);
			    }
			    
			    for (int i = 0; i < renderItems.size(); i++) {
			    	renderItems.get(i).setLevel(this);
			    }
			    
			    for (int i = 0; i < renderRats.size(); i++) {
			    	renderRats.get(i).setLevel(this);
			    }
			}catch (Exception e) {
			    e.printStackTrace();
			}
		}
		
	
		
		System.out.println(x);
		System.out.println(y);
		System.out.println(Arrays.deepToString(tiles));
		//Adds rats in correct position
		
		//Position position, boolean gender, TestLevel currentLevel, char directionFacing
		
		
		
		//generate render tiles
		ConvertLayoutToTiles convertedLayout = new ConvertLayoutToTiles(tiles);
		this.renderTiles = convertedLayout.getTiles();
		this.renderAfterTiles = convertedLayout.getAfterList();
		this.renderAfterTilesPosition = convertedLayout.getAfterPositionList();
		
		//add test rats
		//this.spawnRat(new BabyRat(new Position(new BigDecimal("1"), new BigDecimal("1")), false, this, 'N'));
		//this.spawnRat(new BabyRat(new Position(new BigDecimal("1"), new BigDecimal("1")), true, this, 'N'));
		
		//this.spawnRat(new AdultRat(new Position(new BigDecimal("1"), new BigDecimal("1")), true, false, 100, 'N', this));
		//this.spawnRat(new AdultRat(new Position(new BigDecimal("1"), new BigDecimal("1")), false, false, 100, 'N', this));
		
		if (saveFile != "") {
			//fetch the data from the arrays and overwrite the existing arrays
			
			//overwrite current time and score
			
			//overwrite item manager
			
		}
		
	}
	
	public int getScore() {
		return this.score;
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
	
	public char[][] getTiles(){
		return this.tiles;
	}
	
	public void spawnItem(Item spawnItem) {
		this.renderItems.add(spawnItem);
	}
	
	public ArrayList<RenderObject> getItems(){
		return this.renderItems;
	}
	
	public String getGameStatus() {
		int ratCount = 0;
		for (int i = 0; i < renderRats.size(); i++) {
			if (renderRats.get(i) instanceof NormalRat) {
				ratCount++;
			}
		}
		
		System.out.println(ratCount);
		if (ratCount >= maxRats) {
			return "lost";
		} else if (ratCount == 0) {
			return "won";
		}
		
		return "inprogress";
		
	}
	
	public double itemInterval(String itemType, ItemManager items, double gameCounter, double ITEM_GAIN_INTERVAL) {
		if (gameCounter == ITEM_GAIN_INTERVAL) {
			items.tryIncreaseItem(itemType);
			gameCounter = 0;
			System.out.println("BOMB INCREASE");
		} else {
			gameCounter++;
		}
		return gameCounter;
	}
	
	public void incrimentScore(int scoreIncrease) {
		this.score = this.score + scoreIncrease;
	}
	
	/**
	 * Updates board
	 */
	public void updateBoard() {
		
		gameCounterBomb = itemInterval("Bomb", this.itemManager, gameCounterBomb, ITEM_GAIN_INTERVAL_BOMB);
		gameCounterPoison = itemInterval("Poison", this.itemManager, gameCounterPoison, ITEM_GAIN_INTERVAL_POISON);
		gameCounterGas = itemInterval("Gas", this.itemManager, gameCounterGas, ITEM_GAIN_INTERVAL_GAS);
		gameCounterSteril = itemInterval("Sterilisation", this.itemManager, gameCounterSteril, ITEM_GAIN_INTERVAL_STERIL);
		gameCounterMGChange = itemInterval("MGenderChange", this.itemManager, gameCounterMGChange, ITEM_GAIN_INTERVAL_MGCHANGE);
		gameCounterFGChange = itemInterval("FGenderChange", this.itemManager, gameCounterFGChange, ITEM_GAIN_INTERVAL_FGCHANGE);
		gameCounterDeathRat = itemInterval("DeathRat", this.itemManager, gameCounterDeathRat, ITEM_GAIN_INTERVAL_DEATHRAT);
		gameCounterNoEntry = itemInterval("NoEntrySign", this.itemManager, gameCounterNoEntry, ITEM_GAIN_INTERVAL_NOENTRY);

		
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
			
			for (int i2 = 0; i2 < renderItems.size(); i2++) {
				//testLevel.getRenderObjects().get(i).getObjectPosition()[0]
				
				boolean xCollide = false;
				boolean yCollide = false;
				
					
					double compareX = renderItems.get(i2).getObjectPosition()[0].doubleValue();
					double compareY = renderItems.get(i2).getObjectPosition()[1].doubleValue();
					
					if (compareX > xMinus && compareX < xPlus) {
						xCollide = true;
						//System.out.println("X COLLIDE");
					}
					
					if (compareY > yMinus && compareY < yPlus) {
						yCollide = true;
						//System.out.println("Y COLLIDE");
					}
					
					if (xCollide == true && yCollide == true) {
						if (renderItems.get(i2) instanceof Gas && (renderTempTiles.get(i) instanceof GasSpread) == false) {
							//dissipate the gas.
							((Gas) renderItems.get(i2)).instantDissapate();
							
						} else if ((renderItems.get(i2) instanceof Gas) == false && (renderItems.get(i2) instanceof Sterilisation) == false  && (renderTempTiles.get(i) instanceof Explosion) == true) {
							this.despawnItem(((Item) renderItems.get(i2)));
						} else if ((renderItems.get(i2) instanceof Sterilisation) == true && (renderTempTiles.get(i) instanceof Explosion) == true) {
							((Sterilisation) renderItems.get(i2)).instantRemove();
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
			
		for (int i = 0; i < renderSound.size(); i++) {
			renderSound.get(i).tick();
		}
		
	}
	
	public void spawnTempTile(CollideItem tempTile) {
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
		//need to write the level number
		
		//item quantity currently
		
		//time & score
		
		//all rats array
		
		//all items array
		
		//all temp tiles array
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
		
		if (tiles[y][x] == 'G' || tiles[y][x] == 'T') {
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
		if (tiles[y][x] == 'G' || tiles[y][x] == 'T') {
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
	public BigDecimal getParTime() {
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
				if (tiles[y-1][x] == 'P' || tiles[y-1][x] == 'T') {
					return true;
				}
			}
		} else if (direction == 'E'){
			if (x > 0) {
				if (tiles[y][x+1] == 'P' || tiles[y][x+1] == 'T') {
					return true;
				}
			}
		} else if (direction == 'S') {
			if (y < (tiles.length-1)) {
				if (tiles[y+1][x] == 'P' || tiles[y+1][x] == 'T') {
					return true;
				}
			}
		} else {
			if (x > 0) {
				if (tiles[y][x-1] == 'P' || tiles[y][x-1] == 'T') {
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
		
		return this.ITEM_GAIN_INTERVAL_BOMB;
	}
	
	public void spawnSound(String soundName) {
		renderSound.add(new SoundClip(soundName, this));
	}
	
	public void pauseAllSound() {
		for (int i = 0; i < renderSound.size(); i++) {
			renderSound.get(i).pause();
		}
	}
	
	public void playAllSound() {
		for (int i = 0; i < renderSound.size(); i++) {
			renderSound.get(i).resume();
		}
	}
	
	public void despawnSound(SoundClip sound) {
		for (int i = 0; i < renderSound.size(); i++) {
			if (renderSound.get(i) == sound) {
				renderSound.remove(i);
			}
		}
	}
	
	public static void saveArrayToFile(ArrayList<RenderObject> saveArray, String saveLocation) {
		try{
			
		    FileOutputStream writeData = new FileOutputStream(saveLocation);
		    ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
		    
		    writeStream.writeObject(saveArray);
		    writeStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void saveProgress(BigDecimal time) {
		String userDirectory = "src/saves/"+this.currentUser.getIdentifier()+"/";
		String levelName = "Level" + this.currentLevelNumber + "Date-";
		
		LocalDateTime currentTime = LocalDateTime.now();
		String currentYear = String.valueOf(currentTime.getYear());
		String currentMonth = String.valueOf(currentTime.getMonthValue());
		String currentDay = String.valueOf(currentTime.getDayOfMonth());
		String currentHour = String.valueOf(currentTime.getHour());
		String currentMinute = String.valueOf(currentTime.getMinute());
		String currentSecond = String.valueOf(currentTime.getSecond());
		String currentMillisecond = String.valueOf(currentTime.get(ChronoField.MILLI_OF_SECOND));
		
		String dateString = currentDay + "-" + currentMonth + "-" + currentYear + "-" + currentHour + "-" + currentMinute + "-" + currentSecond + "-" + currentMillisecond;

		String saveDirectory = userDirectory + levelName + dateString;
		System.out.println(saveDirectory);
		new File(saveDirectory).mkdirs();
		
		//create a level.txt
		PrintWriter levelWriter;
		try {
			levelWriter = new PrintWriter(saveDirectory+"/level.txt");
			levelWriter.println(this.currentLevelNumber + ",");
			levelWriter.println(this.itemManager.printItems() + ",");
			levelWriter.println(time + " " + this.score);
			levelWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//create array files
		//saveArrayToFile(this.renderRats, saveDirectory);
				
		ArrayList<RenderObject> tempArray = new ArrayList<RenderObject>();
		
		for (int i = 0; i < renderItems.size(); i++) {
			tempArray.add(renderItems.get(i));
		}
		
		for (int i = 0; i < renderTempTiles.size(); i++) {
			tempArray.add(renderTempTiles.get(i));
		}
		//System.currentTimeMillis()
		//go the person's active directory
		//create a new folder with the level name, day, month, year, hour, minute, second
		//create files for the array
		saveArrayToFile(tempArray, saveDirectory+"/item-tiles.ser");
		saveArrayToFile(this.renderRats, saveDirectory+"/rats.ser");
		//saveArrayToFile(this.renderItems, saveDirectory+"/items.ser");
		//saveArrayToFile(this.renderTempTiles, saveDirectory+"/temp-tiles.ser");
	
	}
	
}
