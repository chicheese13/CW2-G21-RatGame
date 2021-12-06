import java.io.File;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Josh and Lorenzo
 * @version 1.0
 * 
 *          class to represent the profiles used when playing the game
 */
public class Profile implements Serializable {

    private String playerName;
    private int unlockedTo;
    private int userIdentifier;
    private static File userFile = new File("src/users.txt");

    /**
     * @param playerName The name of the player
     * @param unlockedTo The maximum level that player is able to play
     * @param uniqueID   The unique ID of the profile
     */
    public Profile(String playerName, int unlockedTo, int uniqueID) {
        this.playerName = playerName;
        this.unlockedTo = unlockedTo;

        if (uniqueID == -1) {
            ArrayList<Profile> tempProfiles = new ArrayList<>();
            try {
                Scanner in = new Scanner(userFile);
                while (in.hasNextLine()) {
                    String text = in.nextLine();
                    String[] details = text.split(" ");
                    Profile profile = new Profile(details[0],
                            Integer.parseInt(details[1]),
                            Integer.parseInt(details[2]));
                    tempProfiles.add(profile);
                }
                in.close();
            } catch (Exception e) {
                System.exit(0);
            }

            // get most recent profile
            if (!tempProfiles.isEmpty()) {
                Profile mostRecent = tempProfiles.get(tempProfiles.size() - 1);
                System.out.println(mostRecent.getName());
                this.userIdentifier = mostRecent.getIdentifier() + 1;
            } else {
                this.userIdentifier = 1;
            }
        } else {
            this.userIdentifier = uniqueID;
        }

    }

    /**
     * @return int the user's ID number
     */
    public int getIdentifier() {
        return this.userIdentifier;
    }

    /**
     * @return String the name of the profile
     */
    public String getName() {
        return playerName;
    }

    /**
     * @return int the maximum level available to this user
     */
    public int getLevels() {
        return unlockedTo;
    }

    /**
     * increments the maximum level available up to the number of available
     * levels
     */
    public void beatLevel() {
        if (unlockedTo < 3) {
            unlockedTo++;
        }
    }

    /**
     * @return String the version of the profile that is used to append onto the
     *         save file.
     */
    public String getAppendVersion() {
        return playerName + " " + unlockedTo + " " + this.userIdentifier;
    }

    /**
     * @param level sets the highest level available to the player
     */
    public void setLevel(int level) {
        this.unlockedTo = level;
    }

    /**
     * overwrites the highest level achieved in the user file
     * 
     * @param newLevel the level to be placed into the file
     */
    public void overwriteLevel(int newLevel) {
        // set the level

        // output to file
        ArrayList<Profile> tempProfiles = new ArrayList<>();
        try {
            Scanner in = new Scanner(userFile);
            while (in.hasNextLine()) {
                String text = in.nextLine();
                String[] details = text.split(" ");
                Profile profile = new Profile(details[0],
                        Integer.parseInt(details[1]),
                        Integer.parseInt(details[2]));
                tempProfiles.add(profile);
            }
            in.close();
        } catch (Exception e) {
            System.exit(0);
        }

        for (int i = 0; i < tempProfiles.size(); i++) {
            if (tempProfiles.get(i).getIdentifier() == this.userIdentifier) {
                tempProfiles.get(i).setLevel(newLevel);
            }
        }

        // re write the users to the text file
        PrintWriter userWrite;
        try {
            userWrite = new PrintWriter("src/users.txt");
            for (int i = 0; i < tempProfiles.size(); i++) {
                // System.out.println(profiles.get(i).getName() + " " +
                // profiles.get(i).getLevels() + " " +
                // profiles.get(i).getIdentifier());
                userWrite.println(tempProfiles.get(i).getName() + " "
                        + tempProfiles.get(i).getLevels() + " "
                        + tempProfiles.get(i).getIdentifier());
                // userWrite.println(profiles.get(i).toString());
            }

            userWrite.close();

        } catch (Exception e) {
            System.out.println("File not found");
        }

    }

    /**
     * @return String the player's name
     */
    public String toString() {
        return playerName;
    }
}
