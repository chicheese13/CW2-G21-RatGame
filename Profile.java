public class Profile {

    private String playerName;
    private int unlockedTo;

    public Profile(String playerName, int unlockedTo) {
        this.playerName = playerName;
        this.unlockedTo = unlockedTo;
    }

    /**
     * @return String
     */
    public String getName() {
        return playerName;
    }

    /**
     * @return int
     */
    public int getLevels() {
        return unlockedTo;
    }

    public void beatLevel() {
        unlockedTo++;
    }

    /**
     * @return String
     */
    public String getAppendVersion() {
        return playerName + ", " + unlockedTo;
    }

    /**
     * @return String
     */
    public String toString() {
        return playerName;
    }
}