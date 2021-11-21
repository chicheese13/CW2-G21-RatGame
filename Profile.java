public class Profile {
    private String playerName;
    private int unlockedTo;

    public Profile(String playerName) {
        this.playerName = playerName;
        this.unlockedTo = 0;
    }

    public String getName() {
        return playerName;
    }

    public int getLevels() {
        return unlockedTo;
    }

    public void beatLevel() {
        unlockedTo++;
    }
}