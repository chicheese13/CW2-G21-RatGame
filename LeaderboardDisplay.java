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
public class LeaderboardDisplay extends Application {
	// The dimensions of the window
	private static final int WINDOW_WIDTH = 1250;
	private static final int WINDOW_HEIGHT = 670;

	// The dimensions of the canvas
	private static final int CANVAS_WIDTH = 1250;
	private static final int CANVAS_HEIGHT = 670;

	// The width and height (in pixels) of each cell that makes up the game.
	private static final int GRID_CELL_WIDTH = 50;
	private static final int GRID_CELL_HEIGHT = 50;
	
	// The width of the grid in number of cells.
	private static final int GRID_WIDTH = 12;
	
	// The canvas in the GUI. This needs to be a global variable
	// (in this setup) as we need to access it in different methods.
	// We could use FXML to place code in the controller instead.
	private Canvas canvas;
		
	// Loaded images
	private Image playerImage;
	private Image dirtImage;
	private Image iconImage;
	
	// X and Y coordinate of player on the grid.
	private int playerX = 0;
	private int playerY = 0;
	
	// Timeline which will cause tick method to be called periodically.
	private Timeline tickTimeline; 
	
	private Stage gameStage = new Stage();
	
	private Image leaderboardBackground = new Image("Textures/leaderboard-background.png");
	
	/**
	 * Setup the new application.
	 * @param primaryStage The stage that is to be used for the application.
	 */
	public void start(Stage primaryStage) {
		// Load images. Note we use png images with a transparent background

		// Build the GUI 
		Pane root = buildGUI();
		
		// Create a scene from the GUI
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
				
		// Register an event handler for key presses.
		// This causes the processKeyEvent method to be called each time a key is pressed.
		scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> processKeyEvent(event));
				
		// Register a tick method to be called periodically.
		// Make a new timeline with one keyframe that triggers the tick method every half a second.
		 // Loop the timeline forever
		// We start the timeline upon a button press.
		
		// Display the scene on the stage
		drawGame();
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void startGame() {
		this.start(gameStage);
	}
	
	/**
	 * Process a key event due to a key being pressed, e.g., to move the player.
	 * @param event The key event that was pressed.
	 */
	public void processKeyEvent(KeyEvent event) {
		// We change the behaviour depending on the actual key that was pressed.
		switch (event.getCode()) {			
		    case RIGHT:
		    	// Right key was pressed. So move the player right by one cell.
	        	playerX = playerX + 1;
	        	break;		        
	        default:
	        	// Do nothing for all other keys.
	        	break;
		}
		
		// Redraw game as the player may have moved.
		drawGame();
		
		// Consume the event. This means we mark it as dealt with. This stops other GUI nodes (buttons etc) responding to it.
		event.consume();
	}
	
	/**
	 * Draw the game on the canvas.
	 */
	public void drawGame() {
		// Get the Graphic Context of the canvas. This is what we draw on.
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		// Clear canvas
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		// Set the background to gray.
		gc.setFill(Color.GRAY);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		gc.drawImage(leaderboardBackground, 0, 0);
		
		
	}
	
	/**
	 * Reset the player's location and move them back to (0,0). 
	 */
	
	/**
	 * React when an object is dragged onto the canvas. 
	 * @param event The drag event itself which contains data about the drag that occurred.

	
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
		launch(args);
	}
}