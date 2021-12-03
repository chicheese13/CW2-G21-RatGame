public class LeaderboardElement implements Comparable<LeaderboardElement> {

    private int score;
    private String name;
    private int level;

    public LeaderboardElement(String name, int score, int level) {
        this.score = score;
        this.name = name;
        this.level = level;
    }

    public int compareTo(LeaderboardElement otherScore) {
        if (this.getScore() > otherScore.getScore()) {
            return -1;
        } else if (this.getScore() == otherScore.getScore()) {
            return 0;
        } else {
            return 1;
        }
    }

    private int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public String toString() {
        return name + " " + score + " " + level;
    }

}
