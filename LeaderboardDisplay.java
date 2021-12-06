import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.math.BigDecimal;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;

import java.lang.Math;
import java.util.Scanner;

import java.awt.event.KeyAdapter;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class LeaderboardDisplay {
	private static final int WINDOW_WIDTH = 1250;
	private static final int WINDOW_HEIGHT = 670;
	
	private static final int CANVAS_WIDTH = 1250;
	private static final int CANVAS_HEIGHT = 670;
	
	private Canvas canvas;
	
	private Stage leaderboardStage = new Stage();
	
	
	
	public LeaderboardDisplay() {
		
	}
	
	public void openWindow() {
		this.start(leaderboardStage);
	}
	
	public void start(Stage primaryStage) {
		Pane root = buildGUI();
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		drawLeaderboard();
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void drawLeaderboard() {
		
	}
	
	private Pane buildGUI() {
		BorderPane root = new BorderPane();

		// Create the canvas that we will draw on.
		// We store this as a gloabl variable so other methods can access it.
		canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		root.setCenter(canvas);

		BackgroundImage myBI = new BackgroundImage(new Image("/Textures/hud-bg1.png", 0, 0, false, true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);

		// then you set to your node
		root.setBackground(new Background(myBI));
		
		return root;
	}
}
