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
import java.lang.Math;

/**
 * Sample application that demonstrates the use of JavaFX Canvas for a Game.
 * This class is intentionally not structured very well. This is just a starting
 * point to show how to draw an image on a canvas, respond to arrow key presses,
 * use a tick method that is called periodically, and use drag and drop.
 * 
 * Do not build the whole application in one file. This file should probably
 * remain very small.
 *
 * @author Liam O'Reilly
 */
public class MainItems extends Application {
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
	// testLevel.addRenderObject(new BabyRat(new Position(2,2), false, testLevel));

	// BabyRat testRat = new BabyRat(new Position(2,2), false, testLevel);

	// fetches tile texture images.
	private Image grass = new Image("TestTextures/grass.png");
	private Image path = new Image("TestTextures/path.png");
	private Image tunnel = new Image("TestTextures/tunnel.png");
	
	private Image bomb;
	private Image gas;
	private Image poison;
	private Image noEntrySign;
	private Image sterilisation;
	private Image maleSexChanger;
	private Image femaleSexChanger;
	private Image deathRat;

	/**
	 * Setup the new application.
	 * 
	 * @param primaryStage The stage that is to be used for the application.
	 */
	public void start(Stage primaryStage) {
		bomb = new Image("/items/bomb.png");
		gas = new Image("/items/gas.png");
		poison = new Image("/items/poison.png");
		deathRat = new Image("/items/deathrat.png");
		noEntrySign = new Image("/items/noentrysign.png");
		maleSexChanger = new Image("/items/malesexchanger.png");
		femaleSexChanger = new Image("/items/femalesexchanger.png");
		sterilisation = new Image("/items/sterilisation.png");
		

		// Build the GUI
		Pane root = buildGUI();

		// Create a scene from the GUI
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Register a tick method to be called periodically.
		// Make a new timeline with one keyframe that triggers the tick method every
		// half a second.
		tickTimeline = new Timeline(new KeyFrame(Duration.millis(15), event -> tick()));

		// Loop the timeline forever
		tickTimeline.setCycleCount(Animation.INDEFINITE);

		// We start the timeline upon a button press.
		tickTimeline.play();

		// Display the scene on the stage
		// testLevel.addRenderObject(new BabyRat(new Position(2,2), false, testLevel));
		// testLevel.addRenderObject(new BabyRat(new Position(5,5), false, testLevel));
		testLevel.addRenderObject(new NoEntrySign(new Position(1, 1), testLevel));
		drawGame();
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Draw the game on the canvas. This creates a frame, with all the tiles, items
	 * and rats.
	 */
	public void drawGame() {

		// Get the Graphic Context of the canvas. This is what we draw on.
		GraphicsContext gc = canvas.getGraphicsContext2D();

		// Clear canvas
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		// Set the background to gray.
		gc.setFill(Color.GRAY);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		// this goes through the tile array and generates the tile images on the canvas
		// based on the tiles in the array.
		for (int y = 0; y < testLevel.getTiles().length; y++) {
			for (int x = 0; x < testLevel.getTiles()[0].length; x++) {
				// checks what tile type it is and outputs the image for that tile, in the x and
				// y of that tile.
				if (testLevel.getTiles()[y][x] == "G") {
					gc.drawImage(grass, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
				} else if (testLevel.getTiles()[y][x] == "P") {
					gc.drawImage(path, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
				} else {
					gc.drawImage(tunnel, x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
				}
			}
		}

		for (int i = 0; i < testLevel.getRenderObjects().size(); i++) {
			// System.out.println(testLevel.getRenderObjects().get(i).getSprite());
			// gc.drawImage(testLevel.getRenderObjects().get(i).getSprite(),
			// testLevel.getRenderObjects().get(i).getPosition()[0] * GRID_CELL_WIDTH,
			// testLevel.getRenderObjects().get(i).getPosition()[1] * GRID_CELL_HEIGHT);

			double x = ((testLevel.getRenderObjects().get(i).getObjectPosition()[0] * GRID_CELL_WIDTH));

			double y = ((testLevel.getRenderObjects().get(i).getObjectPosition()[1] * GRID_CELL_HEIGHT));

			gc.drawImage(testLevel.getRenderObjects().get(i).getSprite(), x, y);
		}

	}

	/**
	 * This method is called periodically by the tick timeline and would for,
	 * example move, perform logic in the game, this might cause the bad guys to
	 * move (by e.g., looping over them all and calling their own tick method).
	 */
	public void tick() {
		// Here we will do the tick method for items and rats.
		// Likely to have an array of objects which we call the tick method on.
		for (int i = 0; i < testLevel.getRenderObjects().size(); i++) {
			testLevel.getRenderObjects().get(i).tick();
		}
		// We then redraw the whole canvas.
		drawGame();
	}

	public void bombDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50));
		double y = (Math.floor((event.getY()) / 50));

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		testLevel.addRenderObject(new Bomb(new Position(x, y), testLevel));
		drawGame();
	}
	
	public void gasDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50));
		double y = (Math.floor((event.getY()) / 50));

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		testLevel.addRenderObject(new Gas(new Position(x, y), testLevel));
		drawGame();
	}
	
	public void poisonDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50));
		double y = (Math.floor((event.getY()) / 50));

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		testLevel.addRenderObject(new Poison(new Position(x, y), testLevel));
		drawGame();
	}
	
	public void signDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50));
		double y = (Math.floor((event.getY()) / 50));

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		testLevel.addRenderObject(new NoEntrySign(new Position(x, y), testLevel));
		drawGame();
	}
	
	public void deathRatDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50));
		double y = (Math.floor((event.getY()) / 50));

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		testLevel.addRenderObject(new DeathRatSpawner(new Position(x, y), testLevel));
		drawGame();
	}
	
	public void femaleSexChangerDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50));
		double y = (Math.floor((event.getY()) / 50));

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		testLevel.addRenderObject(new FemaleSexChange(new Position(x, y), testLevel));
		drawGame();
	}
	
	public void maleSexChangerDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50));
		double y = (Math.floor((event.getY()) / 50));

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		testLevel.addRenderObject(new MaleSexChange(new Position(x, y), testLevel));
		drawGame();
	}
	
	public void sterilisationDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50));
		double y = (Math.floor((event.getY()) / 50));

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		testLevel.addRenderObject(new Sterilisation(new Position(x, y), testLevel));
		drawGame();
	}
	
	

	/**
	 * Create the GUI.
	 * 
	 * @return The panel that contains the created GUI.
	 */
	private Pane buildGUI() {
		// Create top-level panel that will hold all GUI nodes.
		BorderPane root = new BorderPane();

		// Create the canvas that we will draw on.
		// We store this as a gloabl variable so other methods can access it.
		canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		root.setCenter(canvas);

		HBox toolbar = new HBox();
		toolbar.setSpacing(10);
		toolbar.setPadding(new Insets(10, 10, 10, 10));
		root.setBottom(toolbar);

		ImageView draggableBombImage = new ImageView();
		draggableBombImage.setImage(bomb);
		toolbar.getChildren().add(draggableBombImage);

		draggableBombImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				// Mark the drag as started.
				// We do not use the transfer mode (this can be used to indicate different forms
				// of drags operations, for example, moving files or copying files).
				Dragboard db = draggableBombImage.startDragAndDrop(TransferMode.ANY);

				// We have to put some content in the clipboard of the drag event.
				// We do not use this, but we could use it to store extra data if we wished.
				ClipboardContent content = new ClipboardContent();
				content.putString("Hello");
				db.setContent(content);

				// Consume the event. This means we mark it as dealt with.
				event.consume();
			}
		});
		
		ImageView draggableGasImage = new ImageView();
		draggableGasImage.setImage(gas);
		toolbar.getChildren().add(draggableGasImage);

		draggableGasImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				// Mark the drag as started.
				// We do not use the transfer mode (this can be used to indicate different forms
				// of drags operations, for example, moving files or copying files).
				Dragboard db = draggableGasImage.startDragAndDrop(TransferMode.ANY);

				// We have to put some content in the clipboard of the drag event.
				// We do not use this, but we could use it to store extra data if we wished.
				ClipboardContent content = new ClipboardContent();
				content.putString("Hello");
				db.setContent(content);

				// Consume the event. This means we mark it as dealt with.
				event.consume();
			}
		});

		ImageView draggablePoisonImage = new ImageView();
		draggablePoisonImage.setImage(poison);
		toolbar.getChildren().add(draggablePoisonImage);

		draggablePoisonImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				// Mark the drag as started.
				// We do not use the transfer mode (this can be used to indicate different forms
				// of drags operations, for example, moving files or copying files).
				Dragboard db = draggablePoisonImage.startDragAndDrop(TransferMode.ANY);

				// We have to put some content in the clipboard of the drag event.
				// We do not use this, but we could use it to store extra data if we wished.
				ClipboardContent content = new ClipboardContent();
				content.putString("Hello");
				db.setContent(content);

				// Consume the event. This means we mark it as dealt with.
				event.consume();
			}
		});
		
		ImageView draggableSignImage = new ImageView();
		draggableSignImage.setImage(noEntrySign);
		toolbar.getChildren().add(draggableSignImage);

		draggableSignImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				// Mark the drag as started.
				// We do not use the transfer mode (this can be used to indicate different forms
				// of drags operations, for example, moving files or copying files).
				Dragboard db = draggableSignImage.startDragAndDrop(TransferMode.ANY);

				// We have to put some content in the clipboard of the drag event.
				// We do not use this, but we could use it to store extra data if we wished.
				ClipboardContent content = new ClipboardContent();
				content.putString("Hello");
				db.setContent(content);

				// Consume the event. This means we mark it as dealt with.
				event.consume();
			}
		});
		
		ImageView draggableDeathRatImage = new ImageView();
		draggableDeathRatImage.setImage(deathRat);
		toolbar.getChildren().add(draggableDeathRatImage);

		draggableDeathRatImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				// Mark the drag as started.
				// We do not use the transfer mode (this can be used to indicate different forms
				// of drags operations, for example, moving files or copying files).
				Dragboard db = draggableDeathRatImage.startDragAndDrop(TransferMode.ANY);

				// We have to put some content in the clipboard of the drag event.
				// We do not use this, but we could use it to store extra data if we wished.
				ClipboardContent content = new ClipboardContent();
				content.putString("Hello");
				db.setContent(content);

				// Consume the event. This means we mark it as dealt with.
				event.consume();
			}
		});
		
		ImageView draggableFemaleSexChangerImage = new ImageView();
		draggableFemaleSexChangerImage.setImage(femaleSexChanger);
		toolbar.getChildren().add(draggableFemaleSexChangerImage);

		draggableFemaleSexChangerImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				// Mark the drag as started.
				// We do not use the transfer mode (this can be used to indicate different forms
				// of drags operations, for example, moving files or copying files).
				Dragboard db = draggableFemaleSexChangerImage.startDragAndDrop(TransferMode.ANY);

				// We have to put some content in the clipboard of the drag event.
				// We do not use this, but we could use it to store extra data if we wished.
				ClipboardContent content = new ClipboardContent();
				content.putString("Hello");
				db.setContent(content);

				// Consume the event. This means we mark it as dealt with.
				event.consume();
			}
		});
		
		ImageView draggableMaleSexChangerImage = new ImageView();
		draggableMaleSexChangerImage.setImage(maleSexChanger);
		toolbar.getChildren().add(draggableMaleSexChangerImage);

		draggableMaleSexChangerImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				// Mark the drag as started.
				// We do not use the transfer mode (this can be used to indicate different forms
				// of drags operations, for example, moving files or copying files).
				Dragboard db = draggableMaleSexChangerImage.startDragAndDrop(TransferMode.ANY);

				// We have to put some content in the clipboard of the drag event.
				// We do not use this, but we could use it to store extra data if we wished.
				ClipboardContent content = new ClipboardContent();
				content.putString("Hello");
				db.setContent(content);

				// Consume the event. This means we mark it as dealt with.
				event.consume();
			}
		});
		
		ImageView draggableSterilisationImage = new ImageView();
		draggableSterilisationImage.setImage(sterilisation);
		toolbar.getChildren().add(draggableSterilisationImage);

		draggableSterilisationImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				// Mark the drag as started.
				// We do not use the transfer mode (this can be used to indicate different forms
				// of drags operations, for example, moving files or copying files).
				Dragboard db = draggableSterilisationImage.startDragAndDrop(TransferMode.ANY);

				// We have to put some content in the clipboard of the drag event.
				// We do not use this, but we could use it to store extra data if we wished.
				ClipboardContent content = new ClipboardContent();
				content.putString("Hello");
				db.setContent(content);

				// Consume the event. This means we mark it as dealt with.
				event.consume();
			}
		});

		// This code allows the canvas to receive a dragged object within its bounds.
		// You probably don't need to change this (unless you wish to do more advanced
		// things).
		canvas.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				// Mark the drag as acceptable if the source was the draggable image.
				// (for example, we don't want to allow the user to drag things or files into
				// our application)
				if (event.getGestureSource() == draggableBombImage) {
					// Mark the drag event as acceptable by the canvas.
					event.acceptTransferModes(TransferMode.ANY);
					// Consume the event. This means we mark it as dealt with.
					event.consume();
				} else if (event.getGestureSource() == draggableGasImage) {
					// Mark the drag event as acceptable by the canvas.
					event.acceptTransferModes(TransferMode.ANY);
					// Consume the event. This means we mark it as dealt with.
					event.consume();
				} else if (event.getGestureSource() == draggablePoisonImage) {
					// Mark the drag event as acceptable by the canvas.
					event.acceptTransferModes(TransferMode.ANY);
					// Consume the event. This means we mark it as dealt with.
					event.consume();
				} else if (event.getGestureSource() == draggableSignImage) {
					// Mark the drag event as acceptable by the canvas.
					event.acceptTransferModes(TransferMode.ANY);
					// Consume the event. This means we mark it as dealt with.
					event.consume();
				} else if (event.getGestureSource() == draggableDeathRatImage) {
					// Mark the drag event as acceptable by the canvas.
					event.acceptTransferModes(TransferMode.ANY);
					// Consume the event. This means we mark it as dealt with.
					event.consume();
				} else if (event.getGestureSource() == draggableFemaleSexChangerImage) {
					// Mark the drag event as acceptable by the canvas.
					event.acceptTransferModes(TransferMode.ANY);
					// Consume the event. This means we mark it as dealt with.
					event.consume();
				} else if (event.getGestureSource() == draggableMaleSexChangerImage) {
					// Mark the drag event as acceptable by the canvas.
					event.acceptTransferModes(TransferMode.ANY);
					// Consume the event. This means we mark it as dealt with.
					event.consume();
				} else if (event.getGestureSource() == draggableSterilisationImage) {
					// Mark the drag event as acceptable by the canvas.
					event.acceptTransferModes(TransferMode.ANY);
					// Consume the event. This means we mark it as dealt with.
					event.consume();
				}
			}
		});

		// This code allows the canvas to react to a dragged object when it is finally
		// dropped.
		// You probably don't need to change this (unless you wish to do more advanced
		// things).
		canvas.setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				// We call this method which is where the bulk of the behaviour takes place.
				if (event.getGestureSource() == draggableBombImage) {
					// Mark the drag event as acceptable by the canvas.
					bombDropOccured(event);
					// Consume the event. This means we mark it as dealt with.
					event.consume();
				} else if (event.getGestureSource() == draggableGasImage) {
					// Mark the drag event as acceptable by the canvas.
					gasDropOccured(event);
					// Consume the event. This means we mark it as dealt with.
					event.consume();
				} else if (event.getGestureSource() == draggablePoisonImage) {
					// Mark the drag event as acceptable by the canvas.
					poisonDropOccured(event);
					// Consume the event. This means we mark it as dealt with.
					event.consume();
				} else if (event.getGestureSource() == draggableSignImage) {
					// Mark the drag event as acceptable by the canvas.
					signDropOccured(event);
					// Consume the event. This means we mark it as dealt with.
					event.consume();
				} else if (event.getGestureSource() == draggableDeathRatImage) {
					// Mark the drag event as acceptable by the canvas.
					deathRatDropOccured(event);
					// Consume the event. This means we mark it as dealt with.
					event.consume();
				} else if (event.getGestureSource() == draggableFemaleSexChangerImage) {
					// Mark the drag event as acceptable by the canvas.
					femaleSexChangerDropOccured(event);
					// Consume the event. This means we mark it as dealt with.
					event.consume();
				} else if (event.getGestureSource() == draggableMaleSexChangerImage) {
					// Mark the drag event as acceptable by the canvas.
					maleSexChangerDropOccured(event);
					// Consume the event. This means we mark it as dealt with.
					event.consume();
				} else if (event.getGestureSource() == draggableSterilisationImage) {
					// Mark the drag event as acceptable by the canvas.
					sterilisationDropOccured(event);
					// Consume the event. This means we mark it as dealt with.
					event.consume();
				} 
			}
		});

		// Finally, return the border pane we built up.
		return root;
	}

	public static void main(String[] args) {

		// launches the game.
		launch(args);
	}
}