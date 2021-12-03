
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
 * Leaderboard is a class which manages the Elements that
 */
public class Leaderboard {
    /**
     * creates an arraylist for storing the scores
     * creates a priority queue to sort the scores
     * declare the index of the level
     * creates a text file for the scores
     */
    private ArrayList<LeaderboardElement> allScores = new ArrayList<>();
    private PriorityQueue<LeaderboardElement> levelScores = new PriorityQueue<>(11);
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
     * tries to read the text file with the scores, splitted by " ". And builds the
     * LeaderboardElement with the data.
     * 
     * @throws Exception e
     */
    private void readBoard() {
        try {
            Scanner in = new Scanner(scoreFile);
            while (in.hasNextLine()) {
                String text = in.nextLine();
                String[] details = text.split(" ");
                LeaderboardElement score = new LeaderboardElement(details[0], Integer.parseInt(details[1]),
                        Integer.parseInt(details[2]));
                allScores.add(score);
            }
            in.close();
        } catch (Exception e) {
            // TODO: Catch exception. Lorenzo here-> Hi! Check if this how you wanted the
            // exception to work. I've alse created javaDoc for the LeaderboardElement and
            // checked the MOTDGetter2 comments.
        }
    }

    /**
     * iterates through the ArrayList of scores and if the level is equal it adds
     * the score to the priority queue.
     * iterates through the PriorityQueue and removes them
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
     * updates the scores into the arraylist.
     */
    private void updateAllScores() {
        for (int i = 0; i < 10 && !levelScores.isEmpty(); i++) {
            allScores.add(levelScores.poll());
        }
    }

    /**
     * add the scores into the PriorityQueue.
     * 
     * @param name
     * @param score
     */
    public void addScore(String name, int score) {
        LeaderboardElement newScore = new LeaderboardElement(name, score, level);
        levelScores.add(newScore);
    }

    /**
     * creates the board with the scores. And shows it on screen.
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
     * prints the scores of the PriorityQueue.
     * adds the scores to the PriorityQueue from temp.
     */
    private void displayQueue() {
        LeaderboardElement[] temp = new LeaderboardElement[10];
        for (int i = 0; i < 10 && !levelScores.isEmpty(); i++) {
            temp[i] = levelScores.poll();
            System.out.println(temp[i].toString());
        }
        for (LeaderboardElement score : temp) {
            if (score != null) {
                levelScores.add(score);
            }
        }
    }

    // runs it
    public void run() {
        readBoard();
        populateLevelScores();
        displayQueue();
        updateAllScores();
        writeBoard();
    }

}
