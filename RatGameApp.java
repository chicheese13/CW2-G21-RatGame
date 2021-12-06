import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

/**
 * @author Josh and Lorenzo
 * @version 1.0
 * 
 *          This class is the main class for the game.
 *          When running the game the main method here will be run
 */
public class RatGameApp extends Application {

    // Width of the window
    private static final int WIDTH = 1280;
    // Height of the window
    private static final int HEIGHT = 720;
    // Array List of created profiles
    private static ArrayList<Profile> profiles;
    // Currently selected profile
    private static Profile activeUser;
    // currently selected level
    private static Integer selectedLevel = 0;
    // File where user info is stored
    private static File userFile = new File("src/users.txt");

    private static String selectedTexturePack = "Default";

    private static String currentSave = "";

    private static GameConstructor playGame;

    // private static File userFile = new File("src/users.txt");

    // List of pairs to be used when creating the menu, the pair contains the <text
    // to be displayed, and action to take>
    private List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<String, Runnable>("Start Game", RatGameApp::startGame),
            new Pair<String, Runnable>("Select User", RatGameApp::selectUser),
            new Pair<String, Runnable>("Create New User", RatGameApp::addUser),
            new Pair<String, Runnable>("Saves", RatGameApp::fetchSaves),
            new Pair<String, Runnable>("Delete Profile", RatGameApp::deleteProfile));

    private Pane root = new Pane();
    private VBox menuBox = new VBox(-5);
    private Line line;

    public static void deleteProfile() {
        readUserFile(userFile);
        if (profiles.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setContentText("No users currently exist, please create a user before trying to proceed");
            alert.show();
        } else {
            ChoiceDialog<Profile> cd = new ChoiceDialog<>(activeUser, profiles);
            cd.showAndWait();
            Profile deleteProfile;
            deleteProfile = cd.getSelectedItem();
            cd.hide();

            // delete the profile from the text file here, where user id is active profile
            // id
            for (int i = 0; i < profiles.size(); i++) {
                if (profiles.get(i).getIdentifier() == deleteProfile.getIdentifier()) {
                    profiles.remove(i);
                }
            }

            PrintWriter userWrite;
            try {
                userWrite = new PrintWriter("src/users.txt");
                for (int i = 0; i < profiles.size(); i++) {
                    // System.out.println(profiles.get(i).getName() + " " +
                    // profiles.get(i).getLevels() + " " + profiles.get(i).getIdentifier());
                    userWrite.println(profiles.get(i).getName() + " " + profiles.get(i).getLevels() + " "
                            + profiles.get(i).getIdentifier());
                    // userWrite.println(profiles.get(i).toString());
                }

                userWrite.close();

                // delete the folder
                deleteSaveFolder(new File("src/saves/" + deleteProfile.getIdentifier() + "/"));

                System.out.println("src/saves/" + activeUser.getIdentifier() + "/");

            } catch (Exception e) {

            }
        }
    }

    public static void fetchSaves() {
        // if user is not signed in then display error, else check their saves.
        if (activeUser == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setContentText("Please select a player before proceeding");
            alert.show();
        } else {
            try {
                // fetch all the save options
                File folder = new File("src/saves/" + activeUser.getIdentifier() + "/");
                File[] listOfFiles = folder.listFiles();

                ArrayList<String> saveFiles = new ArrayList<String>();

                try {
                    for (int i = 0; i < listOfFiles.length; i++) {
                        if (listOfFiles[i].isDirectory()) {
                            saveFiles.add(listOfFiles[i].getName());
                        }
                    }

                    ChoiceDialog<String> cd = new ChoiceDialog<>("Select Save", saveFiles);
                    cd.showAndWait();
                    cd.hide();
                    currentSave = cd.getSelectedItem();
                } catch (Exception e) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setContentText("No Save files found for this account.");
                    alert.show();
                }

            } finally {
                if (currentSave != "") {
                    System.out.println(currentSave);
                    // grab the selected level from the txt file
                    try {
                        String saveFileDirectory = "src/saves/" + activeUser.getIdentifier() + "/" + currentSave + "/";
                        String saveFileLevel = "src/saves/" + activeUser.getIdentifier() + "/" + currentSave
                                + "/level.txt";

                        System.out.println(saveFileLevel);
                        BufferedReader saveFile = new BufferedReader(new FileReader(saveFileLevel));
                        String levelIn = saveFile.readLine();
                        int level = Integer.parseInt(levelIn.substring(0, levelIn.length() - 1));
                        // System.out.println(saveFile.readLine().length());
                        saveFile.close();

                        // play the game once we have the level
                        playGame = new GameConstructor(level, activeUser, saveFileDirectory);
                        playGame.startGame();
                        currentSave = "";
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                }
            }
        }

    }

    public static void deleteSaveFolder(File saveFolder) {
        // gets the list of files in the directory.
        File[] saveFolderContent = saveFolder.listFiles();

        // checks if the file is null, if not then loop through and remove the contents
        // recursively
        if (!(saveFolderContent == null)) {
            // goes through the contents of each child and call the method to delete them.
            for (int i = 0; i < saveFolderContent.length; i++) {
                deleteSaveFolder(saveFolderContent[i]);
            }
        }
        saveFolder.delete();
    }

    /**
     * createContent is the method that populates the menu screen and begins the
     * menu animation
     * 
     * @return Parent
     */
    private Parent createContent() {
        addBackground();
        addTitle();
        addMOTD();

        double linex = WIDTH / 2 - 100;
        double liney = HEIGHT / 3 + 50;

        addLine(linex, liney);
        addMenu(linex + 5, liney + 5);

        startAnimation();

        return root;
    }

    /**
     * addBackground takes the image to be used for the background and displays it
     * on the menu screen
     */
    private void addBackground() {
        try {
            InputStream is = Files.newInputStream(Paths.get("src/menubgplaceholder.jpg"));
            Image img = new Image(is);
            is.close();

            ImageView imageView = new ImageView(img);

            imageView.setFitWidth(WIDTH);
            imageView.setFitHeight(HEIGHT);

            root.getChildren().add(imageView);

        } catch (Exception e) {

        }
    }

    /**
     * addTitle creates a new title object and places it in the top middle of the
     * screen
     */
    private void addTitle() {
        RatTitle title = new RatTitle("RATS");
        title.setTranslateX(WIDTH / 2 - title.getTitleWidth());
        title.setTranslateY(HEIGHT / 3);

        root.getChildren().add(title);
    }

    /**
     * addMOTD jsut retrieves the Message of the day from the server, adds it to the
     * menu on the left side of the screen with text wrapping
     */
    private void addMOTD() {
        final String MESSAGE = MOTDGetter.getMessage();
        Label message = new Label(MESSAGE);
        message.setFont(Font.font(30));
        message.setTextFill(Color.WHITE);
        message.effectProperty().set(new DropShadow());
        message.setWrapText(true);
        message.setMaxWidth(400);

        message.setTranslateY(HEIGHT / 2 - 50);
        message.setTranslateX(30);

        root.getChildren().add(message);

    }

    /**
     * addLine creates the line to the left of the menu.
     * 
     * @param x the x coordinate of the line
     * @param y the starting y coordinate of the line
     */
    private void addLine(double x, double y) {
        line = new Line(x, y, x, y + 300);
        line.setStrokeWidth(3);
        line.setStroke(Color.color(1, 1, 1, 0.75));
        line.setEffect(new DropShadow(5, Color.BLACK));
        line.setScaleY(0);

        root.getChildren().add(line);
    }

    /**
     * startAnimation begins 2 animations whent the menu is loaded, the line is
     * increased in scale to the full length and the menu items are shifted to the
     * right sequentially from behind the covering rectangle
     */
    private void startAnimation() {
        ScaleTransition st = new ScaleTransition(Duration.seconds(1), line);
        st.setToY(1);
        st.setOnFinished(event -> {
            for (int i = 0; i < menuBox.getChildren().size(); i++) {
                Node n = menuBox.getChildren().get(i);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(1 + i * 0.15), n);
                tt.setToX(0);
                tt.setOnFinished(event2 -> n.setClip(null));
                tt.play();
            }
        });
        st.play();
    }

    /**
     * addMenu creates the menu items from the list of pairs created above. the text
     * on the buttonsis the string in the pair and the on click action is set to
     * whatever runnable was set in the list. There is also a rectangle object
     * created that covers the menu items initially to give the illusion of the
     * items appearing.
     * 
     * @param x
     * @param y
     */
    private void addMenu(double x, double y) {
        menuBox.setTranslateX(x);
        menuBox.setTranslateY(y);
        menuData.forEach(data -> {
            MenuItem item = new MenuItem(data.getKey());
            item.setOnAction(data.getValue());
            item.setTranslateX(-300);

            Rectangle clip = new Rectangle(300, 30);
            clip.translateXProperty().bind(item.translateXProperty().negate());

            item.setClip(clip);

            menuBox.getChildren().add(item);
        });

        root.getChildren().add(menuBox);
    }

    /**
     * selectUser allows the user to choose the profile that they wish to play under
     * the activeUser is set to whichever profile the user has selected in the pop
     * up choice dialog box
     */
    private static void selectUser() {
        readUserFile(userFile);
        if (profiles.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setContentText("No users currently exist, please create a user before trying to proceed");
            alert.show();
        } else {
            ChoiceDialog<Profile> cd = new ChoiceDialog<>(activeUser, profiles);
            cd.showAndWait();
            activeUser = cd.getSelectedItem();
            cd.hide();
        }

    }

    /**
     * addUser is the method to create a new user profile, the user just needs to
     * input their name
     */
    private static void addUser() {
        TextInputDialog tDialog = new TextInputDialog();
        tDialog.showAndWait();
        String name = tDialog.getEditor().getText();

        // makes sure the user's name is not blank
        if (name.replaceAll("\\s", "").length() > 0) {
            Profile newProfile = new Profile(name, 1, -1);
            writeToUserFile(newProfile);
            createSaveDirectory(newProfile.getIdentifier());
            tDialog.hide();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setContentText("Name must not be empty!");
            alert.show();
        }
    }

    public static void createSaveDirectory(int identifier) {
        new File("src/saves/" + identifier + "/").mkdirs();
    }

    /**
     * startGame checks if the user has selected a profile, if not then they are
     * prompted to choose one, otherwise they are presented with a choice of which
     * level to play and then the gameplay is started on the selected level
     */
    private static void startGame() {
        if (activeUser == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setContentText("Please select a player before proceeding");
            alert.show();
        } else {
            ArrayList<Integer> possibleLevels = new ArrayList<>();
            for (int i = 1; i <= activeUser.getLevels(); i++) {
                possibleLevels.add(i);
            }
            ChoiceDialog<Integer> cd = new ChoiceDialog<>(0, possibleLevels);

            cd.showAndWait();
            selectedLevel = cd.getSelectedItem();
            cd.hide();
            // launch game.
            System.out.println("selected level: " + selectedLevel);
            if (selectedLevel != 0) {
                System.out.println("Start the game here");
                playGame = new GameConstructor(selectedLevel, activeUser, "");
                playGame.startGame();
            } else {
                System.out.println("Game start cancelled");
            }

        }
    }

    /**
     * readUserFile takes all the profiles that have been created in the file and
     * populates the profiles array list with all of these users
     * 
     * @param file the users.txt file
     */
    private static void readUserFile(File file) {
        System.out.println("TEST");
        ArrayList<Profile> tempProfiles = new ArrayList<>();
        try {
            Scanner in = new Scanner(file);
            while (in.hasNextLine()) {
                String text = in.nextLine();
                String[] details = text.split(" ");
                Profile profile = new Profile(details[0], Integer.parseInt(details[1]), Integer.parseInt(details[2]));
                tempProfiles.add(profile);
            }
            in.close();
        } catch (Exception e) {
            // TODO: Catch exception
            System.out.println("File error");
        }
        profiles = tempProfiles;
    }

    /**
     * writeToUserFile takes the newly created user profile and appends it to the
     * end of the users.txt file
     * 
     * @param file    the users.txt file
     * @param newUser the profile to be written to the file
     */
    private static void writeToUserFile(Profile newUser) {
        try (FileWriter fw = new FileWriter(userFile, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println(newUser.getAppendVersion());
        } catch (Exception e) {
            // TODO: Catch exception
            System.out.println("HELLO");
        }
    }

    private static void clearFile() {
        try {
            new FileWriter(userFile, false).close();
        } catch (Exception e) { // TODO: handle exception
        }
    }

    /**
     * @param primaryStage
     * @throws Exception
     */
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("RATS menu title");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @return Profile
     */
    public static Profile getActiveUser() {
        return activeUser;
    }

    /**
     * @return Integer
     */
    public static Integer getSelectedLevel() {
        return selectedLevel;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        readUserFile(userFile);
        launch(args);

        activeUser = new Profile("newUser", 0, 6);

        activeUser.overwriteLevel(3);

    }
}