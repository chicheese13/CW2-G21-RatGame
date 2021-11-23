import java.util.ArrayList;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Sample application that demonstrates the use of JavaFX Canvas for a Game.
 * This class is intentionally not structured very well. This is just a starting point to show
 * how to draw an image on a canvas, respond to arrow key presses, use a tick method that is
 * called periodically, and use drag and drop.
 * 
 * Do not build the whole application in one file. This file should probably remain very small.
 *
 * @author Liam O'Reilly
 */
public class Main extends Application {
	// The dimensions of the window
	private static final int GRID_WIDTH = 15;
	private static final int GRID_HEIGHT = 7;

	private static final int GRID_CELL_WIDTH = 50;
	private static final int GRID_CELL_HEIGHT = 50;
	
	private static final int WINDOW_WIDTH = GRID_WIDTH * GRID_CELL_WIDTH;
	private static final int WINDOW_HEIGHT = GRID_HEIGHT * GRID_CELL_HEIGHT;

	// The dimensions of the canvas
	private static final int CANVAS_WIDTH = WINDOW_WIDTH;
	private static final int CANVAS_HEIGHT = WINDOW_HEIGHT;
	
	// The canvas in the GUI. This needs to be a global variable
	// (in this setup) as we need to access it in different methods.
	private Canvas canvas;
	
	// Timeline which will cause tick method to be called periodically.
	private Timeline tickTimeline; 
	
	
	TestLevel testLevel = new TestLevel();
	//testLevel.addRenderObject(new BabyRat(new Position(2,2), false, testLevel));

	
	//BabyRat testRat = new BabyRat(new Position(2,2), false, testLevel);
	
	//fetches tile texture images.
	private Image grass = new Image("TestTextures/grass.png");
	private Image path = new Image("TestTextures/path.png");
	private Image tunnel = new Image("TestTextures/tunnel.png");
	
	/**
	 * Setup the new application.
	 * @param primaryStage The stage that is to be used for the application.
	 */
	public void start(Stage primaryStage) {		
		

		// Build the GUI 
		Pane root = buildGUI();
		
		// Create a scene from the GUI
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
				
		// Register a tick method to be called periodically.
		// Make a new timeline with one keyframe that triggers the tick method every half a second.
		tickTimeline = new Timeline(new KeyFrame(Duration.millis(2000), event -> tick()));
		
		 // Loop the timeline forever
		tickTimeline.setCycleCount(Animation.INDEFINITE);
		
		// We start the timeline upon a button press.
		tickTimeline.play();
		
		// Display the scene on the stage
		testLevel.addRenderObject(new BabyRat(new Position(2,2), false, testLevel));
		testLevel.addRenderObject(new BabyRat(new Position(5,5), false, testLevel));
		drawGame();
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * Draw the game on the canvas.
	 * This creates a frame, with all the tiles, items and rats.
	 */
	public void drawGame() {
		
		// Get the Graphic Context of the canvas. This is what we draw on.
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		// Clear canvas
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		// Set the background to gray.
		gc.setFill(Color.GRAY);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	
		//this goes through the tile array and generates the tile images on the canvas based on the tiles in the array.
		for (int y = 0; y < testLevel.getTiles().length; y++) {
			for (int x = 0; x < testLevel.getTiles()[0].length; x++) {
				//checks what tile type it is and outputs the image for that tile, in the x and y of that tile.
				if (testLevel.getTiles()[y][x] == "G") {
					gc.drawImage(grass, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
				} else if (testLevel.getTiles()[y][x] ==  "P") {
					gc.drawImage(path, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
				} else {
					gc.drawImage(tunnel, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
				}
			}
		}
		
		for (int i = 0; i < testLevel.getRenderObjects().size(); i++) {
			//System.out.println(testLevel.getRenderObjects().get(i).getSprite());
			//gc.drawImage(testLevel.getRenderObjects().get(i).getSprite(), testLevel.getRenderObjects().get(i).getPosition()[0] * GRID_CELL_WIDTH, testLevel.getRenderObjects().get(i).getPosition()[1] * GRID_CELL_HEIGHT);
			gc.drawImage(testLevel.getRenderObjects().get(i).getSprite(), testLevel.getRenderObjects().get(i).getObjectPosition()[0] * GRID_CELL_WIDTH, testLevel.getRenderObjects().get(i).getObjectPosition()[1] * GRID_CELL_HEIGHT);
		}
		
	}
	
	/**
	 * This method is called periodically by the tick timeline
	 * and would for, example move, perform logic in the game,
	 * this might cause the bad guys to move (by e.g., looping
	 * over them all and calling their own tick method). 
	 */
	public void tick() {
		//Here we will do the tick method for items and rats.
		//Likely to have an array of objects which we call the tick method on.
		
		//We then redraw the whole canvas.
		drawGame();
	}
	
	/**
	 * Create the GUI.
	 * @return The panel that contains the created GUI.
	 */
	private Pane buildGUI() {
		// Create top-level panel that will hold all GUI nodes.
		BorderPane root = new BorderPane();
				
		// Create the canvas that we will draw on.
		// We store this as a gloabl variable so other methods can access it.
		canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		root.setCenter(canvas);
        
		// Finally, return the border pane we built up.
        return root;
	}
	        	
	public static void main(String[] args) {
		
		//launches the game.
		launch(args);
	}
}