import java.time.*;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.temporal.ChronoField;
import java.io.*; // Import the File class

/**
 * Scores all of the information on the level, such as tile locations and all of
 * the rats and items.
 * 
 * @author Lewis Ward, Luca Collicott
 * @version 1.0
 */
public class Level implements Serializable {
	private static final long serialVersionUID = 1L;
	private int score;
	private double currentTime;
	private BigDecimal parTime;
	private short maxRats;
	private String[] dataArray;
	private static char tiles[][];

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

	// ArrayLists for Rats & Items
	private ArrayList<RenderObject> renderRats = new ArrayList<RenderObject>();
	private ArrayList<RenderObject> renderItems = new ArrayList<RenderObject>();
	private ArrayList<RenderObject> renderTempTiles = new ArrayList<RenderObject>();
	private transient ArrayList<RenderTile> renderAfterTiles;
	private transient ArrayList<Position> renderAfterTilesPosition;
	private ArrayList<SoundClip> renderSound = new ArrayList<SoundClip>();
	private transient RenderTile[][] renderTiles;

	private int offsetX = 0;
	private int offsetY = 0;
	private transient ItemManager itemManager;
	private transient Profile currentUser;
	private int currentLevelNumber;

	/**
	 * Constructor for level class
	 * 
	 * @param fileName           directory of level file
	 * @param saveFile           directory of save file, if there is no save file
	 *                           then use
	 *                           ""
	 * @param items              Items on board
	 * @param currentUser        Current profile being used
	 * @param currentLevelNumber Level number
	 */
	@SuppressWarnings("unchecked")
	public Level(String fileName, String saveFile, ItemManager items,
			Profile currentUser, int currentLevelNumber) {
		this.itemManager = items;
		this.currentLevelNumber = currentLevelNumber;
		this.currentUser = currentUser;
		String fileData = "";
		File level = new File(fileName);
		Scanner in = null;

		try {
			in = new Scanner(level);
		} catch (Exception e) {
			System.out.print("EERORO");
		}

		while (in.hasNextLine()) {
			fileData = fileData + in.nextLine();
		}

		fileData = fileData.substring(0, fileData.indexOf("."));
		dataArray = fileData.split(",");

		// Sets variables for each piece of data to make it easier to read
		int x = Integer.parseInt(dataArray[0]);
		int y = Integer.parseInt(dataArray[1]);
		String[] tileTypes = dataArray[2].split("");
		String[] rats = dataArray[3].split("");
		maxRats = Short.parseShort(dataArray[4]);
		parTime = new BigDecimal(dataArray[5]);
		String[] itemIntervalsString = dataArray[6].split(" ");

		for (int i = 0; i < itemIntervalsString.length; i++) {
			ITEM_GAIN_INTERVAL_POISON = Integer
					.parseInt(itemIntervalsString[0]);
			ITEM_GAIN_INTERVAL_GAS = Integer.parseInt(itemIntervalsString[1]);
			ITEM_GAIN_INTERVAL_BOMB = Integer.parseInt(itemIntervalsString[2]);
			ITEM_GAIN_INTERVAL_NOENTRY = Integer
					.parseInt(itemIntervalsString[3]);
			ITEM_GAIN_INTERVAL_STERIL = Integer
					.parseInt(itemIntervalsString[4]);
			ITEM_GAIN_INTERVAL_MGCHANGE = Integer
					.parseInt(itemIntervalsString[5]);
			ITEM_GAIN_INTERVAL_FGCHANGE = Integer
					.parseInt(itemIntervalsString[6]);
			ITEM_GAIN_INTERVAL_DEATHRAT = Integer
					.parseInt(itemIntervalsString[7]);
		}
		Level.tiles = new char[y][x];

		// Adds tile types into correct position
		int count = 0;
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				tiles[i][j] = tileTypes[count].charAt(0);
				count++;
			}
		}

		if (saveFile.equals("")) {
			count = 0;
			for (int i = 0; i < y; i++) {
				for (int j = 0; j < x; j++) {
					if (rats[count].charAt(0) == 'M') {
						this.spawnRat(
								new BabyRat(
										new Position(new BigDecimal(j),
												new BigDecimal(i)),
										true, this, 'N'));
					} else if (rats[count].charAt(0) == 'F') {
						this.spawnRat(
								new BabyRat(
										new Position(new BigDecimal(j),
												new BigDecimal(i)),
										false, this, 'N'));
					}
					count++;
				}
			}
		} else {
			ArrayList<RenderObject> tempArray = new ArrayList<RenderObject>();
			try {
				FileInputStream readData = new FileInputStream(
						saveFile + "rats.ser");
				ObjectInputStream readStream = new ObjectInputStream(readData);

				this.renderRats = (ArrayList<RenderObject>) readStream
						.readObject();
				readStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				FileInputStream readData = new FileInputStream(
						saveFile + "sounds.ser");
				ObjectInputStream readStream = new ObjectInputStream(readData);
				this.renderSound = (ArrayList<SoundClip>) readStream
						.readObject();
				readStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				FileInputStream readData = new FileInputStream(
						saveFile + "item-tiles.ser");
				ObjectInputStream readStream = new ObjectInputStream(readData);
				tempArray = (ArrayList<RenderObject>) readStream.readObject();
				readStream.close();

				for (int i = 0; i < tempArray.size(); i++) {
					if (tempArray.get(i) instanceof GasSpread) {
						this.renderTempTiles.add(tempArray.get(i));
					} else if (tempArray.get(i) instanceof Explosion) {
						this.renderTempTiles.add(tempArray.get(i));
					} else if (tempArray.get(i) instanceof SterileRadius) {
						this.renderTempTiles.add(tempArray.get(i));
					} else {
						this.renderItems.add(tempArray.get(i));
					}
				}

				// overwrites previous level with this level
				for (int i = 0; i < renderTempTiles.size(); i++) {
					renderTempTiles.get(i).setLevel(this);
				}
				for (int i = 0; i < renderItems.size(); i++) {
					renderItems.get(i).setLevel(this);
				}
				for (int i = 0; i < renderRats.size(); i++) {
					renderRats.get(i).setLevel(this);
				}
				for (int i = 0; i < renderSound.size(); i++) {
					renderSound.get(i).resume();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// generate render tiles
		ConvertLayoutToTiles convertedLayout = new ConvertLayoutToTiles(tiles);
		this.renderTiles = convertedLayout.getTiles();
		this.renderAfterTiles = convertedLayout.getAfterList();
		this.renderAfterTilesPosition = convertedLayout.getAfterPositionList();
	}

	/**
	 * @param itemType
	 * @param items
	 * @param gameCounter
	 * @param ITEM_GAIN_INTERVAL
	 * @return
	 */
	public double itemInterval(String itemType, ItemManager items,
			double gameCounter, double ITEM_GAIN_INTERVAL) {
		if (gameCounter == ITEM_GAIN_INTERVAL) {
			items.tryIncreaseItem(itemType);
			gameCounter = 0;
		} else {
			gameCounter++;
		}
		return gameCounter;
	}

	/**
	 * Updates board. Should be called every tick
	 */
	public void updateBoard() {
		gameCounterBomb = itemInterval("Bomb", this.itemManager,
				gameCounterBomb, ITEM_GAIN_INTERVAL_BOMB);
		gameCounterPoison = itemInterval("Poison", this.itemManager,
				gameCounterPoison, ITEM_GAIN_INTERVAL_POISON);
		gameCounterGas = itemInterval("Gas", this.itemManager, gameCounterGas,
				ITEM_GAIN_INTERVAL_GAS);
		gameCounterSteril = itemInterval("Sterilisation", this.itemManager,
				gameCounterSteril, ITEM_GAIN_INTERVAL_STERIL);
		gameCounterMGChange = itemInterval("MGenderChange", this.itemManager,
				gameCounterMGChange, ITEM_GAIN_INTERVAL_MGCHANGE);
		gameCounterFGChange = itemInterval("FGenderChange", this.itemManager,
				gameCounterFGChange, ITEM_GAIN_INTERVAL_FGCHANGE);
		gameCounterDeathRat = itemInterval("DeathRat", this.itemManager,
				gameCounterDeathRat, ITEM_GAIN_INTERVAL_DEATHRAT);
		gameCounterNoEntry = itemInterval("NoEntrySign", this.itemManager,
				gameCounterNoEntry, ITEM_GAIN_INTERVAL_NOENTRY);

		for (int i = 0; i < renderRats.size(); i++) {
			double xMinus = renderRats.get(i).getObjectPosition()[0]
					.doubleValue() - 0.5;
			double yMinus = renderRats.get(i).getObjectPosition()[1]
					.doubleValue() - 0.5;
			double xPlus = renderRats.get(i).getObjectPosition()[0]
					.doubleValue() + 0.5;
			double yPlus = renderRats.get(i).getObjectPosition()[1]
					.doubleValue() + 0.5;

			// checking if the the render objects are both rats and are not the
			// same rat.
			for (int i2 = 0; i2 < renderRats.size(); i2++) {
				boolean xCollide = false;
				boolean yCollide = false;

				if (renderRats.get(i) != renderRats.get(i2)) {
					double compareX = renderRats.get(i2).getObjectPosition()[0]
							.doubleValue();
					double compareY = renderRats.get(i2).getObjectPosition()[1]
							.doubleValue();
					if (compareX > xMinus && compareX < xPlus) {
						xCollide = true;
					}
					if (compareY > yMinus && compareY < yPlus) {
						yCollide = true;
					}
					if (xCollide == true && yCollide == true) {
						((Rat) renderRats.get(i))
								.collision((Rat) renderRats.get(i2));
					}
				}
			}
		}

		// Handles collisions between Items and Rats
		for (int i = 0; i < renderItems.size(); i++) {
			double xMinus = renderItems.get(i).getObjectPosition()[0]
					.doubleValue() - 0.5;
			double yMinus = renderItems.get(i).getObjectPosition()[1]
					.doubleValue() - 0.5;
			double xPlus = renderItems.get(i).getObjectPosition()[0]
					.doubleValue() + 0.5;
			double yPlus = renderItems.get(i).getObjectPosition()[1]
					.doubleValue() + 0.5;

			// checking if the the render objects are both rats and are not the
			// same rat.
			for (int i2 = 0; i2 < renderRats.size(); i2++) {

				boolean xCollide = false;
				boolean yCollide = false;
				double compareX = renderRats.get(i2).getObjectPosition()[0]
						.doubleValue();
				double compareY = renderRats.get(i2).getObjectPosition()[1]
						.doubleValue();

				if (compareX > xMinus && compareX < xPlus) {
					xCollide = true;
				}
				if (compareY > yMinus && compareY < yPlus) {
					yCollide = true;
				}
				if (xCollide == true && yCollide == true) {
					if (renderItems.get(i) instanceof RenderObject) {
						((RenderObject) renderItems.get(i))
								.collision((Rat) renderRats.get(i2));
					}
				}
			}
		}

		for (int i = 0; i < renderTempTiles.size(); i++) {
			double xMinus = renderTempTiles.get(i).getObjectPosition()[0]
					.doubleValue() - 0.5;
			double yMinus = renderTempTiles.get(i).getObjectPosition()[1]
					.doubleValue() - 0.5;
			double xPlus = renderTempTiles.get(i).getObjectPosition()[0]
					.doubleValue() + 0.5;
			double yPlus = renderTempTiles.get(i).getObjectPosition()[1]
					.doubleValue() + 0.5;

			for (int i2 = 0; i2 < renderRats.size(); i2++) {

				boolean xCollide = false;
				boolean yCollide = false;
				double compareX = renderRats.get(i2).getObjectPosition()[0]
						.doubleValue();
				double compareY = renderRats.get(i2).getObjectPosition()[1]
						.doubleValue();

				if (compareX > xMinus && compareX < xPlus) {
					xCollide = true;
				}
				if (compareY > yMinus && compareY < yPlus) {
					yCollide = true;
				}
				if (xCollide == true && yCollide == true) {
					if (renderTempTiles.get(i) instanceof RenderObject) {
						((RenderObject) renderTempTiles.get(i))
								.collision((Rat) renderRats.get(i2));
					}
				}
			}

			for (int i2 = 0; i2 < renderItems.size(); i2++) {
				// testLevel.getRenderObjects().get(i).getObjectPosition()[0]

				boolean xCollide = false;
				boolean yCollide = false;
				double compareX = renderItems.get(i2).getObjectPosition()[0]
						.doubleValue();
				double compareY = renderItems.get(i2).getObjectPosition()[1]
						.doubleValue();

				if (compareX > xMinus && compareX < xPlus) {
					xCollide = true;
				}

				if (compareY > yMinus && compareY < yPlus) {
					yCollide = true;
				}

				if (xCollide == true && yCollide == true) {
					if (renderItems.get(i2) instanceof Gas && (renderTempTiles
							.get(i) instanceof GasSpread) == false) {
						// dissipate the gas.
						((Gas) renderItems.get(i2)).instantDissapate();

					} else if ((renderItems.get(i2) instanceof Gas) == false
							&& (renderItems
									.get(i2) instanceof Sterilisation) == false
							&& (renderTempTiles
									.get(i) instanceof Explosion) == true) {
						this.despawnItem(((RenderObject) renderItems.get(i2)));
					} else if ((renderItems
							.get(i2) instanceof Sterilisation) == true
							&& (renderTempTiles
									.get(i) instanceof Explosion) == true) {
						((Sterilisation) renderItems.get(i2)).instantRemove();
					}
				}
			}
		}

		// goes through all rats and items and does executes their tick methods
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

	/**
	 * Adds tile to be rendered
	 * 
	 * @param tempTile new tile
	 */
	public void spawnTempTile(RenderObject tempTile) {
		this.renderTempTiles.add(tempTile);
	}

	/**
	 * Removes tile
	 * 
	 * @param tempTile tile to be removed
	 */
	public void despawnTempTile(RenderObject tempTile) {
		for (int i = 0; i < renderTempTiles.size(); i++) {
			if (renderTempTiles.get(i) == tempTile) {
				renderTempTiles.remove(i);
			}
		}
	}

	/**
	 * Spawns rat on board
	 * 
	 * @param rat Rat to be added to board
	 */
	public void spawnRat(Rat rat) {
		renderRats.add(rat);
	}

	/**
	 * Removes rat from level
	 * 
	 * @param rat rat to be removed
	 */
	public void despawnRat(Rat rat) {
		for (int i = 0; i < renderRats.size(); i++) {
			if (renderRats.get(i) == rat) {
				renderRats.remove(i);
			}
		}
	}

	/**
	 * Removes item from level
	 * 
	 * @param item item to be removed
	 */
	public void despawnItem(RenderObject item) {
		for (int i = 0; i < renderItems.size(); i++) {
			if (renderItems.get(i) == item) {
				renderItems.remove(i);
			}
		}
	}

	public boolean isPlacable(double xIn, double yIn) {
		// need to check if there's any rat's colliding with that tile, or items
		// and if the tile is grass or not
		int x = (int) xIn;
		int y = (int) yIn;

		if (tiles[y][x] == 'G' || tiles[y][x] == 'T') {
			return false;
		}

		for (int i2 = 0; i2 < renderItems.size(); i2++) {
			if (renderItems.get(i2).getObjectPosition()[0].doubleValue() == xIn
					&& renderItems.get(i2).getObjectPosition()[1]
							.doubleValue() == yIn) {
				return false;
			}
		}
		return true;
	}

	public boolean isPlaceableSign(double xIn, double yIn) {
		// check for collisions
		int x = (int) xIn;
		int y = (int) yIn;
		if (tiles[y][x] == 'G' || tiles[y][x] == 'T') {
			return false;
		}

		double xMinus = xIn - 0.5;
		double yMinus = yIn - 0.5;
		double xPlus = xIn + 0.5;
		double yPlus = yIn + 0.5;

		for (int i2 = 0; i2 < renderRats.size(); i2++) {
			// testLevel.getRenderObjects().get(i).getObjectPosition()[0]

			boolean xCollide = false;
			boolean yCollide = false;

			double compareX = renderRats.get(i2).getObjectPosition()[0]
					.doubleValue();
			double compareY = renderRats.get(i2).getObjectPosition()[1]
					.doubleValue();

			if (compareX > xMinus && compareX < xPlus) {
				xCollide = true;
			}

			if (compareY > yMinus && compareY < yPlus) {
				yCollide = true;
			}

			if (xCollide == true && yCollide == true) {
				return false;
			}
		}

		for (int i2 = 0; i2 < renderItems.size(); i2++) {
			if (renderItems.get(i2).getObjectPosition()[0].doubleValue() == xIn
					&& renderItems.get(i2).getObjectPosition()[1]
							.doubleValue() == yIn) {
				return false;
			}
		}
		return true;
	}

	public boolean tileAvailable(BigDecimal xIn, BigDecimal yIn,
			char direction) {

		int x = (int) Math.round(xIn.doubleValue());
		int y = (int) Math.round(yIn.doubleValue());

		if (direction == 'N') {
			if (y > 0) {
				if (tiles[y - 1][x] == 'P' || tiles[y - 1][x] == 'T') {
					return true;
				}
			}
		} else if (direction == 'E') {
			if (x > 0) {
				if (tiles[y][x + 1] == 'P' || tiles[y][x + 1] == 'T') {
					return true;
				}
			}
		} else if (direction == 'S') {
			if (y < (tiles.length - 1)) {
				if (tiles[y + 1][x] == 'P' || tiles[y + 1][x] == 'T') {
					return true;
				}
			}
		} else {
			if (x > 0) {
				if (tiles[y][x - 1] == 'P' || tiles[y][x - 1] == 'T') {
					return true;
				}
			}
		}
		return false;
	}

	public boolean checkGas(Position position) {
		for (int i = 0; i < this.renderTempTiles.size(); i++) {
			if (this.renderTempTiles.get(i) instanceof GasSpread) {
				if (((GasSpread) this.renderTempTiles.get(i))
						.getObjectPosition()[0].doubleValue()
						- offsetX == position.getPosition()[0].doubleValue()
								- offsetX
						&& ((GasSpread) this.renderTempTiles.get(i))
								.getObjectPosition()[1].doubleValue()
								- offsetY == position.getPosition()[1]
										.doubleValue() - offsetY) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Adds sound
	 * 
	 * @param soundName Sound file
	 */
	public void spawnSound(String soundName) {
		renderSound.add(new SoundClip(soundName, this));
	}

	/**
	 * Pauses all current sounds playing
	 */
	public void pauseAllSound() {
		for (int i = 0; i < renderSound.size(); i++) {
			renderSound.get(i).pause();
		}
	}

	/**
	 * Resumes playing all sounds
	 */
	public void playAllSound() {
		for (int i = 0; i < renderSound.size(); i++) {
			renderSound.get(i).resume();
		}
	}

	/**
	 * Removes sound
	 * 
	 * @param sound sound to be removed
	 */
	public void despawnSound(SoundClip sound) {
		for (int i = 0; i < renderSound.size(); i++) {
			if (renderSound.get(i) == sound) {
				renderSound.remove(i);
			}
		}
	}

	public static void saveArrayToFile(ArrayList<RenderObject> saveArray,
			String saveLocation) {
		try {
			FileOutputStream writeData = new FileOutputStream(saveLocation);
			ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

			writeStream.writeObject(saveArray);
			writeStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void saveSoundArray(ArrayList<SoundClip> saveArray,
			String saveLocation) {
		try {
			FileOutputStream writeData = new FileOutputStream(saveLocation);
			ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

			writeStream.writeObject(saveArray);
			writeStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void saveProgress(BigDecimal time) {
		String userDirectory = "src/saves/" + this.currentUser.getIdentifier()
				+ "/";
		String levelName = "Level" + this.currentLevelNumber + "Date-";

		LocalDateTime currentTime = LocalDateTime.now();
		String currentYear = String.valueOf(currentTime.getYear());
		String currentMonth = String.valueOf(currentTime.getMonthValue());
		String currentDay = String.valueOf(currentTime.getDayOfMonth());
		String currentHour = String.valueOf(currentTime.getHour());
		String currentMinute = String.valueOf(currentTime.getMinute());
		String currentSecond = String.valueOf(currentTime.getSecond());
		String currentMillisecond = String
				.valueOf(currentTime.get(ChronoField.MILLI_OF_SECOND));

		String dateString = currentDay + "-" + currentMonth + "-" + currentYear
				+ "-" + currentHour + "-" + currentMinute + "-" + currentSecond
				+ "-" + currentMillisecond;

		String saveDirectory = userDirectory + levelName + dateString;
		new File(saveDirectory).mkdirs();

		// create a level.txt
		PrintWriter levelWriter;
		try {
			levelWriter = new PrintWriter(saveDirectory + "/level.txt");
			levelWriter.println(this.currentLevelNumber + ",");
			levelWriter.println(this.itemManager.printItems() + ",");
			levelWriter.println(time + " " + this.score);
			levelWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ArrayList<RenderObject> tempArray = new ArrayList<RenderObject>();

		for (int i = 0; i < renderItems.size(); i++) {
			tempArray.add(renderItems.get(i));
		}

		for (int i = 0; i < renderTempTiles.size(); i++) {
			tempArray.add(renderTempTiles.get(i));
		}
		for (int i = 0; i < renderSound.size(); i++) {
			renderSound.get(i).setSer();
		}

		saveArrayToFile(tempArray, saveDirectory + "/item-tiles.ser");
		saveArrayToFile(this.renderRats, saveDirectory + "/rats.ser");
		saveSoundArray(this.renderSound, saveDirectory + "/sounds.ser");
	}

	// Getters
	/**
	 * Checks total rats and whether the game has been won or lost
	 * 
	 * @return game status as either won, lost or in progress
	 */
	public String getGameStatus() {
		int ratCount = 0;
		for (int i = 0; i < renderRats.size(); i++) {
			if (renderRats.get(i) instanceof NormalRat) {
				ratCount++;
			}
		}

		if (ratCount >= maxRats) {
			return "lost";
		} else if (ratCount == 0) {
			return "won";
		}

		return "inprogress";

	}

	/**
	 * @return Par completion time of level
	 */
	public BigDecimal getParTime() {
		return parTime;
	}

	/**
	 * @return time between gaining a bomb
	 */
	public int getItemInterval() {
		return this.ITEM_GAIN_INTERVAL_BOMB;
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
	 * @return all temp tiles
	 */
	public ArrayList<RenderObject> getTempTiles() {
		return this.renderTempTiles;
	}

	/**
	 * @return all items in level
	 */
	public ArrayList<RenderObject> getItems() {
		return this.renderItems;
	}

	/**
	 * @return current score
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * @return current X offset
	 */
	public int getOffsetX() {
		return this.offsetX;
	}

	/**
	 * @return current Y offset
	 */
	public int getOffsetY() {
		return this.offsetY;
	}

	/**
	 * @return render tiles
	 */
	public RenderTile[][] getRenderTiles() {
		return this.renderTiles;
	}

	/**
	 * @return
	 */
	public ArrayList<RenderTile> getRenderTilesAfter() {
		return this.renderAfterTiles;
	}

	/**
	 * @return
	 */
	public ArrayList<Position> getRenderTilesAfterPositions() {
		return this.renderAfterTilesPosition;
	}

	/**
	 * @return All tiles in level
	 */
	public char[][] getTiles() {
		return Level.tiles;
	}

	// Setters and adders/spawners
	/**
	 * Spawns item in level
	 * 
	 * @param spawnItem Item to be spawned
	 */
	public void spawnItem(RenderObject item) {
		this.renderItems.add(item);
	}

	/**
	 * Adds to score
	 * 
	 * @param scoreIncrease Amount to add to score
	 */
	public void incrimentScore(int scoreIncrease) {
		this.score = this.score + scoreIncrease;
	}

	/**
	 * @param x new X offset
	 */
	public void setOffsetX(int x) {
		this.offsetX = x;
	}

	/**
	 * @param y new Y offset
	 */
	public void setOffsetY(int y) {
		this.offsetY = y;
	}
}
