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
public class TestMain extends Application {
	// The dimensions of the window
	
	private static final int GRID_WIDTH = 15;
	private static final int GRID_HEIGHT = 9;

	private static final int GRID_CELL_WIDTH = 50;
	private static final int GRID_CELL_HEIGHT = 50;
	
	private static final int WINDOW_WIDTH = GRID_WIDTH * GRID_CELL_WIDTH;
	private static final int WINDOW_HEIGHT = GRID_HEIGHT * GRID_CELL_HEIGHT;

	// The dimensions of the canvas
	private static final int CANVAS_WIDTH = WINDOW_WIDTH;
	private static final int CANVAS_HEIGHT = WINDOW_HEIGHT;

	// The width and height (in pixels) of each cell that makes up the game.
	
	// The width of the grid in number of cells.
	
	// The canvas in the GUI. This needs to be a global variable
	// (in this setup) as we need to access it in different methods.
	// We could use FXML to place code in the controller instead.
	private Canvas canvas;
	
	private final Image HUD_BG = new Image("Textures/hud-bg.png");
	private final Image TEST = new Image("Textures/test.png");

	
	// X and Y coordinate of player on the grid.
	private double playerX = 1;
	private double playerY = 1;
	
	// Timeline which will cause tick method to be called periodically.
	private Timeline tickTimeline; 
	
	String[][] tiles = {{"G","G","G","G","G","G","G","G","G","G","G","G","G","G","G"},
						{"G","P","P","P","T","T","P","P","P","T","T","P","P","P","G"},
						{"G","G","G","P","G","G","G","G","P","G","G","P","G","P","G"},
						{"G","P","P","P","G","G","P","P","P","G","G","P","G","P","G"},
						{"G","P","G","G","G","G","G","G","P","G","G","P","G","P","G"},
						{"G","P","P","P","T","T","P","P","P","T","T","P","P","P","G"},
						{"G","G","G","G","G","G","G","G","G","G","G","G","G","G","G"}};
	
	private int tickCounter = 0;
	
	//on the level class
	public boolean isPath (int x, int y, char Direction) {
		
		return false;
	}
	
	//fetch
	TestLevel testLevel = new TestLevel();
	
	
	ConvertLayoutToFiles convertedLayout = new ConvertLayoutToFiles(tiles);
	RenderTile[][] renderTiles = convertedLayout.getTiles();
	
	/**
	 * Setup the new application.
	 * @param primaryStage The stage that is to be used for the application.
	 */
	
	public AdultRat testRat = new AdultRat(new Position(1,1), false, false, 10, testLevel);
	public void start(Stage primaryStage) {
		// Load images. Note we use png images with a transparent background.
		
		SoundClip ratDeathSound = new SoundClip("rat-death");
		
		//testLevel.addRenderObject(new BabyRat(new Position(1,1), false, testLevel));
		//testLevel.addRenderObject(new BabyRat(new Position(1,1), false, testLevel));
		//testLevel.addRenderObject(new BabyRat(new Position(2,1), false, testLevel));
		//testLevel.addRenderObject(new BabyRat(new Position(2,1), false, testLevel));
		//testLevel.addRenderObject(new BabyRat(new Position(2,1), false, testLevel, 'N'));
		//testLevel.addRenderObject(new BabyRat(new Position(2,1), false, testLevel, 'N'));
		//testLevel.addRenderObject(new BabyRat(new Position(2,1), false, testLevel, 'N'));
		
		//testLevel.addRenderObject(new AdultRat(new Position(2,1), false, false, 10, testLevel));
		
		AdultRat testOne = new AdultRat(new Position(1,1), false, false, 10, testLevel);
		testOne.becomePregnant();
		AdultRat testTwo = new AdultRat(new Position(5,1), false, false, 10, testLevel);
		testTwo.becomePregnant();
		AdultRat testThree = new AdultRat(new Position(7,1), false, false, 10, testLevel);
		testThree.becomePregnant();
		AdultRat testFour = new AdultRat(new Position(4,1), false, false, 10, testLevel);
		testFour.becomePregnant();
		
		AdultRat testFive = new AdultRat(new Position(3,2), false, false, 10, testLevel);
		testFive.becomePregnant();
		AdultRat testSix = new AdultRat(new Position(3,4), false, false, 10, testLevel);
		testSix.becomePregnant();
		AdultRat testSeven = new AdultRat(new Position(8,2), false, false, 10, testLevel);
		testSeven.becomePregnant();
		AdultRat testEight = new AdultRat(new Position(8,4), false, false, 10, testLevel);
		testEight.becomePregnant();
		
		
		//3 8
		
		testLevel.addRenderObject(testRat);
		//testLevel.addRenderObject(testTwo);
		//testLevel.addRenderObject(testThree);
		//testLevel.addRenderObject(testFour);
		//testLevel.addRenderObject(testFive);
		//testLevel.addRenderObject(testSix);
		//testLevel.addRenderObject(testSeven);
		//testLevel.addRenderObject(testEight);
		
		//testLevel.addRenderObject(new RenderScore(new Position(1,1), 10, testLevel));
		// Build the GUI 
		Pane root = buildGUI();
		
		// Create a scene from the GUI
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
				
		// Register an event handler for key presses.
		// This causes the processKeyEvent method to be called each time a key is pressed.
				
		// Register a tick method to be called periodically.
		// Make a new timeline with one keyframe that triggers the tick method every half a second.
		tickTimeline = new Timeline(new KeyFrame(Duration.millis(16.67), event -> tick()));
		 // Loop the timeline forever
		tickTimeline.setCycleCount(Animation.INDEFINITE);
		// We start the timeline upon a button press.
		tickTimeline.play();
		// Display the scene on the stage
		drawGame();
		primaryStage.setScene(scene);
		primaryStage.show();
		
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
		
		// Draw row of dirt images
		// We multiply by the cell width and height to turn a coordinate in our grid into a pixel coordinate.
		// We draw the row at y value 2.
		//for (int y = 0; y < GRID_HEIGHT; y++){
		//	for (int x = 0; x < GRID_WIDTH; x++) {
		//		gc.drawImage(dirtImage, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);	
		//	}
		//}
		
		for (int y = 0; y < renderTiles.length; y++) {
			for (int x = 0; x < renderTiles[y].length; x++) {
				gc.drawImage(renderTiles[y][x].getImage(), x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
			}
		}
		
		//ArrayList for rats/items
		for (int i = 0; i < testLevel.getRenderObjects().size(); i++) {
			//System.out.println(testLevel.getRenderObjects().get(i).getSprite());
			//gc.drawImage(testLevel.getRenderObjects().get(i).getSprite(), testLevel.getRenderObjects().get(i).getPosition()[0] * GRID_CELL_WIDTH, testLevel.getRenderObjects().get(i).getPosition()[1] * GRID_CELL_HEIGHT);
			gc.drawImage(testLevel.getRenderObjects().get(i).getSprite(), testLevel.getRenderObjects().get(i).getObjectPosition()[0] * GRID_CELL_WIDTH, testLevel.getRenderObjects().get(i).getObjectPosition()[1] * GRID_CELL_HEIGHT);
		}
		
		
	
		
		gc.drawImage(HUD_BG, 0 * GRID_CELL_WIDTH, 7 * GRID_CELL_HEIGHT);
		gc.drawImage(TEST, 0 * GRID_CELL_WIDTH, 7 * GRID_CELL_HEIGHT);
	}
	

	
	/**
	 * This method is called periodically by the tick timeline
	 * and would for, example move, perform logic in the game,
	 * this might cause the bad guys to move (by e.g., looping
	 * over them all and calling their own tick method). 
	 */
	public void tick() {
		//System.out.println(testLevel.score);
		
		tickCounter++;
		
		

		if (tickCounter == 75) {
			testRat.becomePregnant();
		}
		
		if (tickCounter == 600) {
			testRat.ratDeath();
		}
		
		//Here we will do the tick method for items and rats.
				//Likely to have an array of objects which we call the tick method on.
				for (int i = 0; i < testLevel.getRenderObjects().size(); i++) {
					testLevel.getRenderObjects().get(i).tick();
				}
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
		//Level testLevel = new Level();
		launch(args);
	}
}