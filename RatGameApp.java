import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
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
    private static Integer selectedLevel;
    // File where user info is stored
    private static File userFile = new File("src/users.txt");

    // List of pairs to be used when creating the menu, the pair contains the <text
    // to be displayed, and action to take>
    private List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<String, Runnable>("Start Game", RatGameApp::startGame),
            new Pair<String, Runnable>("Select User", RatGameApp::selectUser),
            new Pair<String, Runnable>("Create New User", RatGameApp::addUser));

    private Pane root = new Pane();
    private VBox menuBox = new VBox(-5);
    private Line line;
    private static Leaderboard board;

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
        final String MESSAGE = MOTDGetter2.getMessage();
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
        beatLevel();

    }

    /**
     * addUser is the method to create a new user profile, the user just needs to
     * input their name
     */
    private static void addUser() {
        TextInputDialog tDialog = new TextInputDialog();
        tDialog.showAndWait();
        String name = tDialog.getEditor().getText();
        Profile newProfile = new Profile(name, 1);
        writeToUserFile(newProfile);
        tDialog.hide();
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
            ChoiceDialog<Integer> cd = new ChoiceDialog<>(1, possibleLevels);
            cd.showAndWait();
            selectedLevel = cd.getSelectedItem();
            cd.hide();

            // launch game.
            System.out.println("Start the game here");
            GameConstructor playGame = new GameConstructor(selectedLevel, activeUser, board);
            playGame.startGame();
        }
    }

    /**
     * readUserFile takes all the profiles that have been created in the file and
     * populates the profiles array list with all of these users
     * 
     * @param file the users.txt file
     */
    private static void readUserFile(File file) {
        ArrayList<Profile> tempProfiles = new ArrayList<>();
        try {
            Scanner in = new Scanner(file);
            while (in.hasNextLine()) {
                String text = in.nextLine();
                String[] details = text.split(" ");
                Profile profile = new Profile(details[0], Integer.parseInt(details[1]));
                tempProfiles.add(profile);
            }
            in.close();
        } catch (Exception e) {
            // TODO: Catch exception
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
        }
    }

    private static void clearFile() {
        try {
            new FileWriter(userFile, false).close();
        } catch (Exception e) { // TODO: handle exception
        }
    }

    public static void beatLevel() {
        clearFile();
        try (FileWriter fw = new FileWriter(userFile, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            activeUser.beatLevel();
            for (Profile profile : profiles) {
                out.println(profile.getAppendVersion());
            }
        } catch (Exception e) {

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
    }
}