import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MOTDGetter {
    public static String returnText(String text) {
        File messages = new File("messages.txt");

        ArrayList<String> texts = new ArrayList<String>();

        try {
            Scanner in = new Scanner(messages);

            while (in.hasNextLine()) {
                texts.add(in.nextLine());
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Random randomNumber = new Random();

        return texts.get(randomNumber.nextInt(texts.size()));
    }
}
