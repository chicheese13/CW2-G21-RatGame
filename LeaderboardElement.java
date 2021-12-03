/**
 * LeaderboardElement.java
 * @version 1.0
 */

/**
 * LeaderboardElement is a class which store the score.
 */
public class  implements Comparable<LeaderboardElement> {

    private int score;
    private String name;
    private int level;

/**
 * Element that contains the data: name,score,level
 * @param name
 * @param score
 * @param level
 */
public LeaderboardElement(String name, int score, int level) {
        this.score = score;
        this.name = name;
        this.level = level;
    }

/**
 * Returns instruction for a priority queue
 * @param otherScore
 */
public int compareTo(LeaderboardElement otherScore) {
        if (this.getScore() > otherScore.getScore()) {
            return -1;
        } else if (this.getScore() == otherScore.getScore()) {
            return 0;
        } else {
            return 1;
        }
    }
    //gets the score
    private int getScore() {
        return score;
    }
    //gets the name
    public String getName() {
        return name;
    }
    //gets the level (index)
    public int getLevel() {
        return level;
    }
/**
 * returns everything into a string.
 */
    public String toString() {
        return name + " " + score + " " + level;
    }

}
