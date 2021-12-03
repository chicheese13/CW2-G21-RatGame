import java.util.ArrayList;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.lang.Math;
import java.util.Scanner;

import java.awt.event.KeyAdapter;
import javax.swing.JFrame;
import javax.swing.JTextField;

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
public class GameConstructor extends Application {
	// The dimensions of the window
	private static final int GRID_WIDTH = 30;
	private static final int GRID_HEIGHT = 17;

	private static final int GRID_CELL_WIDTH = 50;
	private static final int GRID_CELL_HEIGHT = 50;

	private static final int WINDOW_WIDTH = 1250;
	private static final int WINDOW_HEIGHT = 670;

	// The dimensions of the canvas
	private static final int CANVAS_WIDTH = 1250;
	private static final int CANVAS_HEIGHT = 600;
	
	
	private Timeline tickTimeline;
	private Canvas canvas;
	
	
	private Image bomb = new Image("/items/bomb.png");
	private Image gas = new Image("/items/gas.png");
	private Image poison = new Image("/items/poison.png");
	private Image deathRat = new Image("/items/deathrat.png");
	private Image noEntrySign = new Image("/items/noentrysign.png");
	private Image maleSexChanger = new Image("/items/malesexchanger.png");
	private Image femaleSexChanger = new Image("/items/femalesexchanger.png");
	private Image sterilisation = new Image("/items/sterilisation.png");
	
	private Image grass = new Image("TestTextures/grass.png");
	private Image path = new Image("TestTextures/path.png");
	private Image tunnel = new Image("TestTextures/tunnel.png");
	
	private Image availSprite;
	private Image availableSprite = new Image("Textures/available.png");
	private Image unavailableSprite = new Image("Textures/unavailable.png");
	private int focusTileX;
	private int focusTileY;
	
	private int currentLevelNumber;
	
	private Stage gameStage = new Stage();
	
	private Level currentLevel;
	private boolean showAvailableTile = false;
	private int tickCounter = 0;
	
	public GameConstructor(int levelNumber) {
		this.currentLevelNumber = levelNumber;
		this.currentLevel = new Level("src/Levels/" + levelNumber + ".txt");
		
	}
	
	public void startGame() {
		this.start(gameStage);
	}

	/**
	 * Setup the new application.
	 * 
	 * @param primaryStage The stage that is to be used for the application.
	 */
	public void start(Stage primaryStage) {
		//creates a GUI and starts the tick counter. 
		Pane root = buildGUI();

		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
				
		scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> processKeyEvent(event));
	
		tickTimeline = new Timeline(new KeyFrame(Duration.millis(15), event -> tick()));
				
		tickTimeline.setCycleCount(Animation.INDEFINITE);
				
		tickTimeline.play();
		
		//this.levelMusic = new LevelMusic("level-one");
		
		drawGame();
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Draw the game on the canvas. This creates a frame, with all the tiles, items
	 * and rats.
	 */
	/**
	 * Draw the game on the canvas.
	 * This creates a frame, with all the tiles, items and rats.
	 */
	public void drawGame() {
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setFill(Color.GRAY);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		//we need to draw the game
		
		for (int y = 0; y < (CANVAS_HEIGHT/GRID_CELL_WIDTH); y++) {
			for (int x = 0; x < (CANVAS_WIDTH/GRID_CELL_HEIGHT); x++) {
				gc.drawImage(currentLevel.getRenderTiles()[y+currentLevel.getOffsetY()][x+currentLevel.getOffsetX()].getImage(), x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
			}
		}
		
		//we need to draw the items
		for (int i = 0; i < currentLevel.getItems().size(); i++) {
			gc.drawImage(currentLevel.getItems().get(i).getSprite(), (currentLevel.getItems().get(i).getObjectPosition()[0].doubleValue()-currentLevel.getOffsetX())  * GRID_CELL_WIDTH, (currentLevel.getItems().get(i).getObjectPosition()[1].doubleValue()-currentLevel.getOffsetY()) * GRID_CELL_HEIGHT);
		}
		
		//we need to draw the rats
		
		for (int i = 0; i < currentLevel.getRats().size(); i++) {
			gc.drawImage(currentLevel.getRats().get(i).getSprite(), (currentLevel.getRats().get(i).getObjectPosition()[0].doubleValue()-currentLevel.getOffsetX())  * GRID_CELL_WIDTH, (currentLevel.getRats().get(i).getObjectPosition()[1].doubleValue()-currentLevel.getOffsetY()) * GRID_CELL_HEIGHT);
		}
		
		
		//need to draw the temp tiles
		for (int i = 0; i < currentLevel.getTempTiles().size(); i++) {
			gc.drawImage(currentLevel.getTempTiles().get(i).getSprite(), (currentLevel.getTempTiles().get(i).getObjectPosition()[0].doubleValue()-currentLevel.getOffsetX())  * GRID_CELL_WIDTH, (currentLevel.getTempTiles().get(i).getObjectPosition()[1].doubleValue()-currentLevel.getOffsetY()) * GRID_CELL_HEIGHT);
		}
		
		//we need to draw the tunnels
		
		//draw availability spirte when dragging
		if (showAvailableTile) {
			gc.drawImage(availSprite, focusTileX * GRID_CELL_WIDTH, focusTileY * GRID_CELL_HEIGHT);
		}
		
	}	
	
	public void processKeyEvent(KeyEvent event) {
		// We change the behaviour depending on the actual key that was pressed.
		switch (event.getCode()) {			
		    case RIGHT:
		    	// Right key was pressed. So move the player right by one cell.
		    	if (currentLevel.getOffsetX() < (currentLevel.getRenderTiles()[0].length) - (CANVAS_WIDTH / GRID_CELL_WIDTH)) {
		    		currentLevel.setOffsetX(currentLevel.getOffsetX()+1);
		    	}
	        	break;
		    case UP:
		    	if (currentLevel.getOffsetY() > 0) {
		    		currentLevel.setOffsetY(currentLevel.getOffsetY()-1);
		    	}
		    	break;
		    case DOWN: 
		    	if (currentLevel.getOffsetY() < (currentLevel.getRenderTiles().length) - (CANVAS_HEIGHT / GRID_CELL_HEIGHT)) {
		    		currentLevel.setOffsetY(currentLevel.getOffsetY()+1);;
		    	}	
		    	break;
		    case LEFT:
		    	if (currentLevel.getOffsetX() > 0) {
		    		currentLevel.setOffsetX(currentLevel.getOffsetX()-1);
		    	}
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
	 * This method is called periodically by the tick timeline
	 * and would for, example move, perform logic in the game,
	 * this might cause the bad guys to move (by e.g., looping
	 * over them all and calling their own tick method). 
	 */
	public void tick() {
		currentLevel.updateBoard();
		//We then redraw the whole canvas.
		drawGame();
		
		tickCounter++;
		if (tickCounter == 66) {
			showAvailableTile = false;
			tickCounter = 0;
		}
	}

	public void bombDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50))+currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50))+currentLevel.getOffsetY();

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);

		currentLevel.spawnItem(new Bomb(new Position(BigDecimal.valueOf(x),BigDecimal.valueOf(y)), currentLevel));
		//drawGame();
	}
	
	public void gasDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50))+currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50))+currentLevel.getOffsetY();

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		currentLevel.spawnItem(new Gas(new Position(BigDecimal.valueOf(x),BigDecimal.valueOf(y)), currentLevel));
		//drawGame();
	}
	
	public void poisonDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50))+currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50))+currentLevel.getOffsetY();

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		currentLevel.spawnItem(new Poison(new Position(BigDecimal.valueOf(x),BigDecimal.valueOf(y)), currentLevel));
		//drawGame();
	}
	
	public void signDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50))+currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50))+currentLevel.getOffsetY();

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		currentLevel.spawnItem(new NoEntrySign(new Position(new BigDecimal(x), new BigDecimal(y)), currentLevel));
		//drawGame();
	}
	
	public void deathRatDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50))+currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50))+currentLevel.getOffsetY();

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		currentLevel.spawnRat(new DeathRat(new Position(new BigDecimal(x), new BigDecimal(y)), currentLevel));
		//drawGame();
	}
	
	public void femaleSexChangerDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50))+currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50))+currentLevel.getOffsetY();

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		currentLevel.spawnItem(new FemaleSexChange(new Position(BigDecimal.valueOf(x),BigDecimal.valueOf(y)), currentLevel));
		//drawGame();
	}
	
	public void maleSexChangerDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50))+currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50))+currentLevel.getOffsetY();

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		currentLevel.spawnItem(new MaleSexChange(new Position(BigDecimal.valueOf(x),BigDecimal.valueOf(y)), currentLevel));
		//drawGame();
	}
	
	public void sterilisationDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50))+currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50))+currentLevel.getOffsetY();

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		currentLevel.spawnItem(new Sterilisation(new Position(BigDecimal.valueOf(x),BigDecimal.valueOf(y)), currentLevel));
		//drawGame();
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
				System.out.println("TEST");
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
				double x = (Math.floor((event.getX()) / 50))+currentLevel.getOffsetX();
				double y = (Math.floor((event.getY()) / 50))+currentLevel.getOffsetY();
				
				focusTileX = (int) x-currentLevel.getOffsetX();
				focusTileY = (int) y-currentLevel.getOffsetY();
				
				showAvailableTile = true;
			
				
				if (currentLevel.isPlacable(x, y) && event.getGestureSource() != draggableSignImage) {	
					
					availSprite = availableSprite;
					
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
						//here we can display available tiles
						// Consume the event. This means we mark it as dealt with.
						event.consume();
					} else if (event.getGestureSource() == draggableSterilisationImage) {
						// Mark the drag event as acceptable by the canvas.
						event.acceptTransferModes(TransferMode.ANY);
						// Consume the event. This means we mark it as dealt with.
						event.consume();
					}
				} else if (event.getGestureSource() == draggableSignImage && currentLevel.isPlaceableSign(x, y)) {
					availSprite = availableSprite;
					// Mark the drag event as acceptable by the canvas.
					event.acceptTransferModes(TransferMode.ANY);
					// Consume the event. This means we mark it as dealt with.
					event.consume();
				} else {
					availSprite = unavailableSprite;
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
}