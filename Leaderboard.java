import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Stores scores 
 * @author Josh Hall 
 * @version 1.0
 */
public class Leaderboard {
	// take in a text file
	String file[] = { "Name1 10", "Name2 7", "Name3 7", "Name4 5", "Name5 4",
			"Name6 3", "Name7 3", "Name8 3", "Name9 2", "Name10 2" };

	private int level;
	private String[] names = new String[10];
	private int[][] scores = new int[10][2];
	
	/**
	 * Constructor
	 * @param level level to create leaderboard for
	 */
	public Leaderboard(int level) {
		this.level = level;

		// read in the file
		String fileName = "src/scores/" + level + ".txt";
		String fileData = "";
		File leaderboard = new File(fileName);
		Scanner in = null;

		try {
			in = new Scanner(leaderboard);
		} catch (Exception e) {
			PrintWriter leaderboardWriter;
			try {
				leaderboardWriter = new PrintWriter(
						"src/Scores/" + level + ".txt");
				for (int i = 0; i < 9; i++) {
					leaderboardWriter.println("Null -1,");
				}
				leaderboardWriter.close();
			} catch (FileNotFoundException e1) {
				e.printStackTrace();
			}
		}

		while (in.hasNextLine()) {
			fileData = fileData + in.nextLine();
		}

		String[] dataArray = fileData.split(",");

		for (int i = 0; i < dataArray.length; i++) {
			names[i] = dataArray[i].split(" ")[0];
			scores[i][0] = i;
			scores[i][1] = Integer.parseInt(dataArray[i].split(" ")[1]);
		}
	}

	/**
	 * Adds score to leaderboard
	 * @param name name of profile that got the score
	 * @param Score score achieved
	 */
	public void addScore(String name, int Score) {
		scores = checkScore(Score, scores);

		// output the contents back to the file.

		PrintWriter leaderboardWriter;
		try {
			leaderboardWriter = new PrintWriter("src/Scores/" + level + ".txt");

			for (int i = 0; i < scores.length; i++) {
				// Name Score
				if (scores[i][0] == -1) {
					leaderboardWriter.println(name + " " + scores[i][1] + ",");
				} else {
					if (names[scores[i][0]] == "") {
						leaderboardWriter.println("Null " + scores[i][1] + ",");
					} else {
						leaderboardWriter.println(
								names[scores[i][0]] + " " + scores[i][1] + ",");
					}
				}
			}

			leaderboardWriter.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Checks the scores
	 * @param score Score to be checked
	 * @param checkArray list of scores in leaderboard
	 * @return new list of scores
	 */
	public int[][] checkScore(int score, int[][] checkArray) {
		// iterate through the array, check the scores and shift if needed.
		boolean inserted = false;
		for (int i = 0; i < checkArray.length; i++) {
			if (score > checkArray[i][1] && inserted == false) {
				shift(i, checkArray);
				checkArray[i][0] = -1;
				checkArray[i][1] = score;
				inserted = true;
			}
		}
		return checkArray;
	}

	/**
	 * Shifts scores in leaderboard down to make room for new score
	 * @param startIndex Where to perform shift
	 * @param shiftArray Array to perform shift on
	 * @return New score array
	 */
	public int[][] shift(int startIndex, int[][] shiftArray) {

		for (int i = shiftArray.length - 1; i > 0; i--) {
			if (i > startIndex) {
				shiftArray[i][0] = shiftArray[i - 1][0];
				shiftArray[i][1] = shiftArray[i - 1][1];
			}
		}

		return shiftArray;
	}

}
