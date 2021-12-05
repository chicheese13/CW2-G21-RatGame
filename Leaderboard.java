
/**
 * LeaderBoard.java
 * @version  1.0
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Leaderboard is a class which manages the high scores of players from past
 * games
 */
public class Leaderboard {
    /**
     * creates an arraylist for storing the scores
     * creates a priority queue to sort the scores
     * declare the index of the level
     * creates a text file for the scores
     */
    private ArrayList<LeaderboardElement> allScores = new ArrayList<>();
    private PriorityQueue<LeaderboardElement> levelScores = new PriorityQueue<>();
    private int level;
    private File scoreFile = new File("src/scores.txt");

    /**
     * Constructs the Leaderboard
     * 
     * @param level
     */
    public Leaderboard(int level) {
        this.level = level;
    }

    /**
     * tries to read the text file with the scores, splitted by ", ". And builds the
     * LeaderboardElement with the data.
     * 
     * @throws Exception e
     */
    private void readBoard() {
        try (Scanner in = new Scanner(scoreFile);) {
            while (in.hasNextLine()) {
                String text = in.nextLine();
                String[] details = text.split(", ");
                LeaderboardElement score = new LeaderboardElement(details[0], Integer.parseInt(details[1]),
                        Integer.parseInt(details[2]));
                allScores.add(score);
            }
        } catch (Exception e) {
            System.out.println("unable to read high scores");
        }
    }

    /**
     * Populates the levelScores array with the highscores from allscores array and
     * removes these from the allScores array
     */
    private void populateLevelScores() {
        for (LeaderboardElement score : allScores) {
            if (score.getLevel() == level) {
                levelScores.add(score);
            }
        }
        for (LeaderboardElement score : levelScores) {
            allScores.remove(score);
        }
    }

    /**
     * updates the top 10 high scores back into the allsScores array, including
     * newly added score
     */
    private void updateAllScores() {
        while (!levelScores.isEmpty()) {
            allScores.add(levelScores.poll());
        }
    }

    /**
     * Writes the allScores array to the textfile.
     */
    private void writeBoard() {
        clearFile();
        try (FileWriter fw = new FileWriter(scoreFile, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw);) {
            for (LeaderboardElement score : allScores) {
                out.println(score);
            }
        } catch (Exception e) {
            // TODO: Catch exception
        }

    }

    /**
     * closes the file.
     */
    private void clearFile() {
        try {
            new FileWriter(scoreFile, false).close();
        } catch (Exception e) {
            // TODO: Catch exception
        }
    }

    /**
     * addds the new score to the levelScore array and trims the levelScore down to
     * the top 10 scores if necessary
     * 
     * @param newScore the LeaderboardElement which contains the new score to add
     */
    private void addScore(LeaderboardElement newScore) {
        levelScores.add(newScore);
        LeaderboardElement[] temp = new LeaderboardElement[10];
        for (int i = 0; i < 10 && !levelScores.isEmpty(); i++) {
            temp[i] = levelScores.poll();
        }
        levelScores.clear();
        for (LeaderboardElement score : temp) {
            if (score != null) {
                levelScores.add(score);
            }
        }
    }

    /**
     * The run method takes a user name and score, and runs through a full sequence
     * that takes the board for the curentlevel from the scores file, adds the new
     * score, updates the new levelScores array up to allScores and then rewrites
     * the scores file to add the changes tht have been made. And finally it returns
     * the levelScore array to be used to display the score to screen
     * 
     * @param name  The name of the user who set the score
     * @param score The score achieved
     * @return PriorityQueue<LeaderboardElement> the final leaderboard showingt the
     *         top10 scores for the completed level
     */
    // runs it
    public PriorityQueue<LeaderboardElement> run(String name, int score) {
        readBoard();
        populateLevelScores();
        LeaderboardElement newScore = new LeaderboardElement(name, score, level);
        addScore(newScore);
        PriorityQueue<LeaderboardElement> tempQueue = new PriorityQueue<>();
        for (LeaderboardElement tempScore : levelScores) {
            tempQueue.add(tempScore);
        }
        updateAllScores();
        writeBoard();
        return tempQueue;
    }

}
