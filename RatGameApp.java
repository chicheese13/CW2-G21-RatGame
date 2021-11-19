import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RatGameApp extends Application {

    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        root.setPrefSize(800, 600);

        InputStream is = Files.newInputStream(Paths.get("src/menubgplaceholder.jpg"));
        Image img = new Image(is);
        is.close();

        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(800);
        imgView.setFitHeight(600);

        root.getChildren().addAll(imgView);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public RatGameApp() {

    }

    private void createUser() {

    }

    private void loadUser() {

    }

    private void saveUser() {

    }

    private int startGame(Profile user, int level) {
        return 0;
    }
}