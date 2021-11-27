import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Level.java
 * @author Lewis Ward, Luca Collicott
 * @version 1.0
 */

/**
 * Creates a board that will be used to store information on the level, such as tile, rat, and item locations.
 */
public class Level {
	Object[][][] board;
	int score;
	double currentTime;
	int parTime;
	short maxRats;
	String[] dataArray;
	
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
			System.out.println("File does not exist");
			System.exit(0);
		}
		
		while (in.hasNextLine()) {
			fileData = fileData + in.nextLine();
		}		
		
		fileData = fileData.substring(0, fileData.indexOf("."));
		dataArray = fileData.split(",");
		
		int x = Integer.parseInt(dataArray[0]);
		int y = Integer.parseInt(dataArray[1]);
		String[] tileTypes = dataArray[2].split("");
		String[] rats = dataArray[3].split("");
		maxRats = Short.parseShort(dataArray[4]);
		parTime = Integer.parseInt(dataArray[5]);
		
		board = new Object[3][x][y];
		
		for(int i=0; i<y;i++) {
			for(int j=0; j<x;j++) {
				board[0][j][i] = tileTypes[i+j];
			}
		}
	}
	
	/**
	 * Updates board
	 */
	public void updateBoard() {
		
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
	public Object[][] getBoard() {
		return board;
	}
	
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
	public Rat[] getRats() {
		return null;
	}
	
	/**
	 * @return 
	 */
	public Item getItem() {
		return null;
	}
}
