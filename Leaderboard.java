import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Leaderboard {
    private ArrayList<LeaderboardElement> allScores = new ArrayList<>();
    private PriorityQueue<LeaderboardElement> levelScores = new PriorityQueue<>(11);
    private int level;
    private File scoreFile = new File("src/scores.txt");

    public Leaderboard(int level) {
        this.level = level;
    }

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
            // TODO: Catch exception
        }
    }

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

    private void updateAllScores() {
        for (int i = 0; i < 10 && !levelScores.isEmpty(); i++) {
            allScores.add(levelScores.poll());
        }
    }

    public void addScore(String name, int score) {
        LeaderboardElement newScore = new LeaderboardElement(name, score, level);
        levelScores.add(newScore);
    }

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

    private void clearFile() {
        try {
            new FileWriter(scoreFile, false).close();
        } catch (Exception e) {
            // TODO: Catch exception
        }
    }

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

    public void run() {
        readBoard();
        populateLevelScores();
        addScore("Gareth", 70);
        displayQueue();
        updateAllScores();
        writeBoard();
    }

}
