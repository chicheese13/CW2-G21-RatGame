
/**
 * MOTDGetter2.java
 *
 * @version 2.0
 */

import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * @author Josh and Lorenzo
 * @version 1.0 Class made to connect to the web server and retrieve the message
 *          of the day
 */
public class MOTDGetter {

    /**
     * @return String the message of the day retrieved from the web server
     */
    public static String getMessage() {
        try {
            return MOTDGetter();
        } catch (Exception e) {
            System.out.print(e);
            return "unable to retrieve message of the day";
        }
    }

    // declaring and initialising variables
    static Character[] ALPHABET_ARRAY = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z' };
    static int ALPHABET_LENGTH = 26;

    /**
     * @param URL the URL of the website to retrieve messages from
     * @return String the puzzle string from the server response
     * @throws Exception
     */
    // function which returns a String which was taken from a GET request of a
    // web
    // page.
    public static String getRequest(String URL) throws Exception {
        try {
            URL FETCH_URL = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) FETCH_URL
                    .openConnection();
            connection.setRequestMethod("GET");
            InputStreamReader reader = new InputStreamReader(
                    connection.getInputStream());
            BufferedReader bReader = new BufferedReader(reader);
            return bReader.readLine();
        } catch (Exception e) {
            throw new Exception("Unable to connect to server.");
        }
    }

    /**
     * @return String The final output for message of the day
     * @throws Exception
     */
    public static String MOTDGetter() throws Exception {
        // need to make a HTTP request to get the puzzle string
        try {
            char[] puzzleCharacters = getRequest(
                    "http://cswebcat.swansea.ac.uk/puzzle").toCharArray();
            // char[] puzzleCharacters = {'C', 'A', 'B'};
            int alphabetIndex = -1;
            // go through each character, plus or minusing the letters based on
            // odd and even
            // indexes.
            for (int i = 0; i < puzzleCharacters.length; i++) {
                // get the current index of the alphabet which the current
                // letter is in the
                // string.
                for (int i2 = 0; i2 < ALPHABET_ARRAY.length; i2++) {
                    if (Character.toUpperCase(ALPHABET_ARRAY[i2]) == Character
                            .toUpperCase(puzzleCharacters[i])) {
                        alphabetIndex = i2;
                        i2 = ALPHABET_ARRAY.length;
                    }
                }
                // plus or minus the index
                if (i % 2 == 0) {
                    // minus
                    alphabetIndex = alphabetIndex - (i + 1);
                    if (alphabetIndex < 0) {
                        // adding a minus value which in turn minuses the value.
                        alphabetIndex = 26 + alphabetIndex;
                    }
                } else {
                    // plus
                    alphabetIndex = alphabetIndex + (i + 1);
                    if (alphabetIndex > 25) {
                        alphabetIndex = (alphabetIndex - 25) - 1;
                    }
                }

                // re put the character back into the string.
                puzzleCharacters[i] = Character
                        .toUpperCase(ALPHABET_ARRAY[alphabetIndex]);
            }
            // combine the characters into a string with CS-230 text
            String finalText = new String(puzzleCharacters) + "CS-230";
            // get the length and add it to the start
            String textLength = String.valueOf(finalText.length());
            // combine final message
            finalText = textLength + finalText;

            // request motd with the solution
            return getRequest("http://cswebcat.swansea.ac.uk/message?solution="
                    + finalText);
        } catch (Exception e) {
            throw new Exception("Failed to establish connection with server.");
        }
    }

}
