import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

public class RatGameApp extends Application {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private List<Pair<String, Runnable>> menuData = Arrays.asList(new Pair<String, Runnable>("Start Game", () -> {
    }), new Pair<String, Runnable>("Leaderboard", () -> {
    }), new Pair<String, Runnable>("Select User", () -> {
    }), new Pair<String, Runnable>("Create New User", () -> {
    }));

    private Pane root = new Pane();
    private VBox menuBox = new VBox(-5);
    private Line line;

    private Parent createContent() {
        addBackground();
        addTitle();

        double linex = WIDTH / 2 - 100;
        double liney = HEIGHT / 3 + 50;

        addLine(linex, liney);
        addMenu(linex + 5, liney + 5);

        startAnimation();

        return root;
    }

    private void addBackground() {
        try {
            InputStream is = Files.newInputStream(Paths.get("src/menubgplaceholder.png"));
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

    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("RATS menu title");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}