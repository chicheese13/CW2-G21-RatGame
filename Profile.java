import java.io.File;
import java.io.PrintWriter;
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
    
    public void setLevel(int level) {
    	this.unlockedTo = level;
    }

    public void overwriteLevel(int newLevel) {
    	//set the level
    	
    	//output to file
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
        
        for (int i = 0; i < tempProfiles.size(); i++) {
        	if (tempProfiles.get(i).getIdentifier() == this.userIdentifier) {
        		tempProfiles.get(i).setLevel(newLevel);
        	}
        }
        
        //re write the users to the text file
        PrintWriter userWrite;
 		try {
 			userWrite = new PrintWriter("src/users.txt");
 			for (int i = 0; i < tempProfiles.size(); i++) {
 				//System.out.println(profiles.get(i).getName() + " " + profiles.get(i).getLevels() + " " + profiles.get(i).getIdentifier());
 				userWrite.println(tempProfiles.get(i).getName() + " " + tempProfiles.get(i).getLevels() + " " + tempProfiles.get(i).getIdentifier());
 				//userWrite.println(profiles.get(i).toString());
 			}
 			
 			userWrite.close();
 			
 		} catch (Exception e) {
 			
 		}
        
    }
    
    /**
     * @return String
     */
    public String toString() {
        return playerName;
    }
}