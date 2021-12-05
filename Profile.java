import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Profile implements Serializable{

    private String playerName;
    private int unlockedTo;
    private int userIdentifier;
    private static File userFile = new File("src/users.txt");

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
                    Profile profile = new Profile(details[0], Integer.parseInt(details[1]), Integer.parseInt(details[2]));
                    tempProfiles.add(profile);
                }
                in.close();
            } catch (Exception e) {
                System.exit(0);
            }
            
            //get most recent profile
            if (tempProfiles.size() > 0) {
            	 Profile mostRecent = tempProfiles.get(tempProfiles.size()-1);
            	 System.out.println(mostRecent.getName());
            	 this.userIdentifier = mostRecent.getIdentifier()+1;
            } else {
            	this.userIdentifier = 1;
            }
        } else {
        	this.userIdentifier = uniqueID;
        }
        
        
    }

    public int getIdentifier() {
    	return this.userIdentifier;
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
        return playerName + " " + unlockedTo + " " + this.userIdentifier;
    }

    /**
     * @return String
     */
    public String toString() {
        return playerName;
    }
}