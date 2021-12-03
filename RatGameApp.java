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

public class RatGameApp extends Application {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static ArrayList<Profile> profiles;
    private static Profile activeUser;
    private static Integer selectedLevel;
    private static File userFile = new File("src/users.txt");

    private List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<String, Runnable>("Start Game", RatGameApp::startGame),
            new Pair<String, Runnable>("Select User", RatGameApp::selectUser),
            new Pair<String, Runnable>("Create New User", RatGameApp::addUser));

    private Pane root = new Pane();
    private VBox menuBox = new VBox(-5);
    private Line line;

    /**
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
            // TODO: handle exception
        }
    }

    private void addTitle() {
        RatTitle title = new RatTitle("RATS");
        title.setTranslateX(WIDTH / 2 - title.getTitleWidth());
        title.setTranslateY(HEIGHT / 3);

        root.getChildren().add(title);
    }

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
     * @param x
     * @param y
     */
    private void addLine(double x, double y) {
        line = new Line(x, y, x, y + 300);
        line.setStrokeWidth(3);
        line.setStroke(Color.color(1, 1, 1, 0.75));
        line.setEffect(new DropShadow(5, Color.BLACK));
        line.setScaleY(0);

        root.getChildren().add(line);
    }

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

    private static void addUser() {
        TextInputDialog tDialog = new TextInputDialog();
        tDialog.showAndWait();
        String name = tDialog.getEditor().getText();
        writeToUserFile(userFile, name);
        tDialog.hide();
    }

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
            GameConstructor playGame = new GameConstructor(selectedLevel);
            playGame.startGame();

            // TODO: Add method to show leaderboard
        }
    }

    /**
     * @param file
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
     * @param file
     * @param name
     */
    private static void writeToUserFile(File file, String name) {
        try (FileWriter fw = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println(new Profile(name, 1).getAppendVersion());
        } catch (Exception e) {
            // TODO: Catch exception
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
     * @param args
     */
    public static void main(String[] args) {
        readUserFile(userFile);
        Leaderboard board = new Leaderboard(1);
        board.run();
        launch(args);
    }
}