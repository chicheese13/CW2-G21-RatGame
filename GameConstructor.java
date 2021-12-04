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
	
	private BigDecimal millisecondCount = new BigDecimal("0");

	private Timeline tickTimeline;
	private Canvas canvas;

	ImageView draggableBombImage = new ImageView();
	ImageView draggableGasImage = new ImageView();
	ImageView draggablePoisonImage = new ImageView();
	ImageView draggableSignImage = new ImageView();
	ImageView draggableDeathRatImage = new ImageView();
	ImageView draggableFemaleSexChangerImage = new ImageView();
	ImageView draggableMaleSexChangerImage = new ImageView();
	ImageView draggableSterilisationImage = new ImageView();
	
	private BigDecimal TICK_DURATION = new BigDecimal("15");

	private Image bomb = new Image("/items_icons/bombOFF.png");
	private Image bombOn = new Image("/items_icons/bombON.png");
	private Image gas = new Image("/items_icons/gasOFF.png");
	private Image gasOn = new Image("/items_icons/gasON.png");
	private Image poison = new Image("/items_icons/poisonOFF.png");
	private Image poisonOn = new Image("/items_icons/poisonON.png");
	private Image deathRat = new Image("/items_icons/deathratOFF.png");
	private Image deathRatOn = new Image("/items_icons/deathratON.png");
	private Image noEntrySign = new Image("/items_icons/noentrysignOFF.png");
	private Image noEntrySignOn = new Image("/items_icons/noentrysignON.png");
	private Image maleSexChanger = new Image("/items_icons/maleOFF.png");
	private Image maleSexChangerOn = new Image("/items_icons/maleON.png");
	private Image femaleSexChanger = new Image("/items_icons/femaleOFF.png");
	private Image femaleSexChangerOn = new Image("/items_icons/femaleON.png");
	private Image sterilisation = new Image("/items_icons/sterilisationOFF.png");
	private Image sterilisationOn = new Image("/items_icons/sterilisationON.png");

	private Image availSprite;
	private Image availableSprite = new Image("Textures/available.png");
	private Image unavailableSprite = new Image("Textures/unavailable.png");
	private int focusTileX;
	private int focusTileY;

	private Image gameWonScreen = new Image("Textures/game-won.png");
	private Image gameLostScreen = new Image("Textures/game-over.png");

	private ItemManager items;
	private int currentLevelNumber;

	private Stage gameStage = new Stage();

	private Level currentLevel;
	private boolean showAvailableTile = false;
	private int tickCounter = 0;
	private boolean hasWon = false;
	private boolean hasLost = false;

	private Leaderboard currentLeaderboard;
	private Profile currentUser;

	public GameConstructor(int levelNumber, Profile currentProfile, Leaderboard currentBoard) {
		this.items = new ItemManager();
		this.currentLevelNumber = levelNumber;
		this.currentLevel = new Level("src/Levels/" + levelNumber + ".txt");
		this.currentLeaderboard = currentBoard;
		this.currentUser = currentProfile;
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
		// creates a GUI and starts the tick counter.
		Pane root = buildGUI();

		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> processKeyEvent(event));

		tickTimeline = new Timeline(new KeyFrame(Duration.millis(15), event -> tick()));

		tickTimeline.setCycleCount(Animation.INDEFINITE);

		tickTimeline.play();

		// this.levelMusic = new LevelMusic("level-one");

		drawGame();
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Draw the game on the canvas. This creates a frame, with all the tiles, items
	 * and rats.
	 */
	/**
	 * Draw the game on the canvas. This creates a frame, with all the tiles, items
	 * and rats.
	 */
	public void drawGame() {

		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setFill(Color.GRAY);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		// we need to draw the game

		for (int y = 0; y < (CANVAS_HEIGHT / GRID_CELL_WIDTH); y++) {
			for (int x = 0; x < (CANVAS_WIDTH / GRID_CELL_HEIGHT); x++) {
				gc.drawImage(currentLevel.getRenderTiles()[y + currentLevel.getOffsetY()][x + currentLevel.getOffsetX()]
						.getImage(), x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
			}
		}

		// we need to draw the items
		for (int i = 0; i < currentLevel.getItems().size(); i++) {
			gc.drawImage(currentLevel.getItems().get(i).getSprite(),
					(currentLevel.getItems().get(i).getObjectPosition()[0].doubleValue() - currentLevel.getOffsetX())
							* GRID_CELL_WIDTH,
					(currentLevel.getItems().get(i).getObjectPosition()[1].doubleValue() - currentLevel.getOffsetY())
							* GRID_CELL_HEIGHT);
		}

		// we need to draw the rats

		for (int i = 0; i < currentLevel.getRats().size(); i++) {
			gc.drawImage(currentLevel.getRats().get(i).getSprite(),
					(currentLevel.getRats().get(i).getObjectPosition()[0].doubleValue() - currentLevel.getOffsetX())
							* GRID_CELL_WIDTH,
					(currentLevel.getRats().get(i).getObjectPosition()[1].doubleValue() - currentLevel.getOffsetY())
							* GRID_CELL_HEIGHT);
		}

		// need to draw the temp tiles
		for (int i = 0; i < currentLevel.getTempTiles().size(); i++) {
			gc.drawImage(currentLevel.getTempTiles().get(i).getSprite(),
					(currentLevel.getTempTiles().get(i).getObjectPosition()[0].doubleValue()
							- currentLevel.getOffsetX()) * GRID_CELL_WIDTH,
					(currentLevel.getTempTiles().get(i).getObjectPosition()[1].doubleValue()
							- currentLevel.getOffsetY()) * GRID_CELL_HEIGHT);
		}

		// we need to draw the tunnels

		// draw availability spirte when dragging
		if (showAvailableTile) {
			gc.drawImage(availSprite, focusTileX * GRID_CELL_WIDTH, focusTileY * GRID_CELL_HEIGHT);
		}

		// show win screen
		if (this.hasWon) {
			gc.drawImage(gameWonScreen, 0 * GRID_CELL_WIDTH, 0 * GRID_CELL_HEIGHT);
		}

		// show lose screen
		if (this.hasLost) {
			gc.drawImage(gameLostScreen, 0 * GRID_CELL_WIDTH, 0 * GRID_CELL_HEIGHT);
		}
	}

	public void processKeyEvent(KeyEvent event) {
		// We change the behaviour depending on the actual key that was pressed.
		switch (event.getCode()) {
		case RIGHT:
			// Right key was pressed. So move the player right by one cell.
			if (currentLevel.getOffsetX() < (currentLevel.getRenderTiles()[0].length)
					- (CANVAS_WIDTH / GRID_CELL_WIDTH)) {
				currentLevel.setOffsetX(currentLevel.getOffsetX() + 1);
			}
			break;
		case UP:
			if (currentLevel.getOffsetY() > 0) {
				currentLevel.setOffsetY(currentLevel.getOffsetY() - 1);
			}
			break;
		case DOWN:
			if (currentLevel.getOffsetY() < (currentLevel.getRenderTiles().length)
					- (CANVAS_HEIGHT / GRID_CELL_HEIGHT)) {
				currentLevel.setOffsetY(currentLevel.getOffsetY() + 1);
				;
			}
			break;
		case LEFT:
			if (currentLevel.getOffsetX() > 0) {
				currentLevel.setOffsetX(currentLevel.getOffsetX() - 1);
			}
			break;
		default:
			// Do nothing for all other keys.
			break;
		}

		// Redraw game as the player may have moved.
		drawGame();

		// Consume the event. This means we mark it as dealt with. This stops other GUI
		// nodes (buttons etc) responding to it.
		event.consume();
	}

	/**
	 * This method is called periodically by the tick timeline and would for,
	 * example move, perform logic in the game, this might cause the bad guys to
	 * move (by e.g., looping over them all and calling their own tick method).
	 */
	
	public void calculateTimePoints() {
		BigDecimal timeTakenInSeconds = this.millisecondCount.divide(new BigDecimal("1000"));
		//timeTakenInSeconds < this.currentLevel.getParTime()
		if (timeTakenInSeconds.compareTo(this.currentLevel.getParTime()) == -1) {
			//figure out the difference
			
			
			BigDecimal gainScore = this.currentLevel.getParTime().subtract(timeTakenInSeconds);
			System.out.println("SCORE GAINED");
			System.out.println(gainScore);
			this.currentLevel.incrimentScore((int) Math.floor(gainScore.doubleValue()));
		}
		
	}
	
	public void tick() {

		this.millisecondCount = this.millisecondCount.add(TICK_DURATION);
		
		
		String gameStatus = currentLevel.getGameStatus();

		if (gameStatus == "inprogress") {
			// run game as usual
			currentLevel.updateBoard(this.items);
			// We then redraw the whole canvas.
			drawGame();

			tickCounter++;
			if (tickCounter == 66) {
				showAvailableTile = false;
				tickCounter = 0;
			}
		} else if (gameStatus == "won") {
			//this.currentLeaderboard.run(this.currentUser.getName(), this.currentLevel.getScore());
			calculateTimePoints();
			
			//output the score
			
			// display win art
			this.hasWon = true;
			drawGame();
		} else if (gameStatus == "lost") {
			//this.currentLeaderboard.run(this.currentUser.getName(), this.currentLevel.getScore());
			
			// game is lost, need to append score to leaderboard
			// display lost game screen
			System.out.println("GAME IS LOST");
			this.hasLost = true;
			drawGame();

			currentLevel.updateBoard(this.items);
			// We then redraw the whole canvas.
			drawGame();

			tickCounter++;
			if (tickCounter == 66) {
				showAvailableTile = false;
				tickCounter = 0;
			}
		}
		
		if (items.getItemCount("Bomb") > 0) {
			draggableBombImage.setImage(bombOn);
		}
		
		if (items.getItemCount("Poison") > 0) {
			draggablePoisonImage.setImage(poisonOn);
		}
		
		if (items.getItemCount("Gas") > 0) {
			draggableGasImage.setImage(gasOn);
		}
		
		if (items.getItemCount("Sterilisation") > 0) {
			draggableSterilisationImage.setImage(sterilisationOn);
		}
		
		if (items.getItemCount("MGenderChange") > 0) {
			draggableMaleSexChangerImage.setImage(maleSexChangerOn);
		}
		
		if (items.getItemCount("FGenderChange") > 0) {
			draggableFemaleSexChangerImage.setImage(femaleSexChangerOn);
		}
		
		if (items.getItemCount("DeathRat") > 0) {
			draggableDeathRatImage.setImage(deathRatOn);
		}
		
		if (items.getItemCount("NoEntrySign") > 0) {
			draggableSignImage.setImage(noEntrySignOn);
		}

		System.out.println(this.currentLevel.getScore());
	}

	public void bombDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50)) + currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50)) + currentLevel.getOffsetY();

		// Check if there are more than 0 of the item in the inventory. If not don't let
		// the user drag the item.
		if (items.isItemDepleted("Bomb")) {
			System.out.println("Amount is 0");
		} else {
			currentLevel.spawnItem(new Bomb(new Position(BigDecimal.valueOf(x), BigDecimal.valueOf(y)), currentLevel));
			items.tryReduceItem("Bomb");
			if (items.isItemDepleted("Bomb")) {
				
				draggableBombImage.setImage(bomb);

			}
		}
	}

	public void gasDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50)) + currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50)) + currentLevel.getOffsetY();

		// Check if there are more than 0 of the item in the inventory. If not don't let
		// the user drag the item.
		if (items.isItemDepleted("Gas")) {
			System.out.println("Amount is 0");
		} else {
			currentLevel.spawnItem(new Gas(new Position(BigDecimal.valueOf(x), BigDecimal.valueOf(y)), currentLevel));
			items.tryReduceItem("Gas");
			if (items.isItemDepleted("Gas")) {
				gas = new Image("/items_icons/gasOFF.png");
				draggableGasImage.setImage(gas);

			}
		}
	}

	public void poisonDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50)) + currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50)) + currentLevel.getOffsetY();

		// Check if there are more than 0 of the item in the inventory. If not don't let
		// the user drag the item.
		if (items.isItemDepleted("Poison")) {
			System.out.println("Amount is 0");
		} else {
			currentLevel
					.spawnItem(new Poison(new Position(BigDecimal.valueOf(x), BigDecimal.valueOf(y)), currentLevel));
			items.tryReduceItem("Poison");
			if (items.isItemDepleted("Poison")) {
				poison = new Image("/items_icons/poisonOFF.png");
				draggablePoisonImage.setImage(poison);

			}
		}
	}

	public void signDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50)) + currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50)) + currentLevel.getOffsetY();

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		// Check if there are more than 0 of the item in the inventory. If not don't let
		// the user drag the item.
		if (items.isItemDepleted("NoEntrySign")) {
			System.out.println("Amount is 0");
		} else {
			currentLevel.spawnItem(new NoEntrySign(new Position(new BigDecimal(x), new BigDecimal(y)), currentLevel));
			items.tryReduceItem("NoEntrySign");
			if (items.isItemDepleted("NoEntrySign")) {
				noEntrySign = new Image("/items_icons/noentrysignOFF.png");
				draggableSignImage.setImage(noEntrySign);

			}
		}
		// drawGame();
	}

	public void deathRatDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50)) + currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50)) + currentLevel.getOffsetY();

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		// Check if there are more than 0 of the item in the inventory. If not don't let
		// the user drag the item.
		if (items.isItemDepleted("DeathRat")) {
			System.out.println("Amount is 0");
		} else {
			currentLevel.spawnRat(new DeathRat(new Position(new BigDecimal(x), new BigDecimal(y)), currentLevel));
			items.tryReduceItem("DeathRat");
			if (items.isItemDepleted("DeathRat")) {
				deathRat = new Image("/items_icons/deathratOFF.png");
				draggableDeathRatImage.setImage(deathRat);

			}
		}
		// drawGame();
	}

	public void femaleSexChangerDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50)) + currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50)) + currentLevel.getOffsetY();

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		// Check if there are more than 0 of the item in the inventory. If not don't let
		// the user drag the item.
		if (items.isItemDepleted("FGenderChange")) {
			System.out.println("Amount is 0");
		} else {
			currentLevel.spawnItem(
					new FemaleSexChange(new Position(BigDecimal.valueOf(x), BigDecimal.valueOf(y)), currentLevel));
			items.tryReduceItem("FGenderChange");
			if (items.isItemDepleted("FGenderChange")) {
				femaleSexChanger = new Image("/items_icons/femaleOFF.png");
				draggableFemaleSexChangerImage.setImage(femaleSexChanger);

			}
		}
		// drawGame();
	}

	public void maleSexChangerDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50)) + currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50)) + currentLevel.getOffsetY();

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		// Check if there are more than 0 of the item in the inventory. If not don't let
		// the user drag the item.
		if (items.isItemDepleted("MGenderChange")) {
			System.out.println("Amount is 0");
		} else {
			currentLevel.spawnItem(
					new MaleSexChange(new Position(BigDecimal.valueOf(x), BigDecimal.valueOf(y)), currentLevel));
			items.tryReduceItem("MGenderChange");
			if (items.isItemDepleted("MGenderChange")) {
				maleSexChanger = new Image("/items_icons/maleOFF.png");
				draggableMaleSexChangerImage.setImage(maleSexChanger);

			}
		}
		// drawGame();
	}

	public void sterilisationDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50)) + currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50)) + currentLevel.getOffsetY();

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		// Check if there are more than 0 of the item in the inventory. If not don't let
		// the user drag the item.
		if (items.isItemDepleted("Sterilisation")) {
			System.out.println("Amount is 0");
		} else {
			currentLevel.spawnItem(
					new Sterilisation(new Position(BigDecimal.valueOf(x), BigDecimal.valueOf(y)), currentLevel));
			items.tryReduceItem("Sterilisation");
			if (items.isItemDepleted("Sterilisation")) {
				sterilisation = new Image("/items_icons/sterilisationOFF.png");
				draggableSterilisationImage.setImage(sterilisation);

			}
		}
		// drawGame();
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

		draggableBombImage.setImage(bomb);
		toolbar.getChildren().add(draggableBombImage);

		draggableBombImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				// Mark the drag as started.
				// We do not use the transfer mode (this can be used to indicate different forms
				// of drags operations, for example, moving files or copying files).
				if (items.isItemDepleted("Bomb") == false) {
					Dragboard db = draggableBombImage.startDragAndDrop(TransferMode.ANY);
					ClipboardContent content = new ClipboardContent();
					content.putString("Hello");
					db.setContent(content);

					event.consume();
				} else {

				}

				// We have to put some content in the clipboard of the drag event.
				// We do not use this, but we could use it to store extra data if we wished.

				// Consume the event. This means we mark it as dealt with.

			}
		});

		draggableGasImage.setImage(gas);
		toolbar.getChildren().add(draggableGasImage);

		draggableGasImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {

				if (items.isItemDepleted("Gas") == false) {
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
				} else {

				}
			}
		});

		draggablePoisonImage.setImage(poison);
		toolbar.getChildren().add(draggablePoisonImage);

		draggablePoisonImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {

				if (items.isItemDepleted("Poison") == false) {
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
				} else {

				}
			}
		});

		draggableSignImage.setImage(noEntrySign);
		toolbar.getChildren().add(draggableSignImage);

		draggableSignImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {

				if (items.isItemDepleted("NoEntrySign") == false) {
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
				} else {

				}
			}
		});

		draggableDeathRatImage.setImage(deathRat);
		toolbar.getChildren().add(draggableDeathRatImage);

		draggableDeathRatImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {

				if (items.isItemDepleted("DeathRat") == false) {
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
				} else {

				}
			}
		});

		draggableFemaleSexChangerImage.setImage(femaleSexChanger);
		toolbar.getChildren().add(draggableFemaleSexChangerImage);

		draggableFemaleSexChangerImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {

				if (items.isItemDepleted("FGenderChange") == false) {
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
				} else {

				}
			}
		});

		draggableMaleSexChangerImage.setImage(maleSexChanger);
		toolbar.getChildren().add(draggableMaleSexChangerImage);

		draggableMaleSexChangerImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {

				if (items.isItemDepleted("MGenderChange") == false) {
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
				} else {

				}
			}
		});

		draggableSterilisationImage.setImage(sterilisation);
		toolbar.getChildren().add(draggableSterilisationImage);

		draggableSterilisationImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {

				if (items.isItemDepleted("Sterilisation") == false) {
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
				} else {

				}
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
				double x = (Math.floor((event.getX()) / 50)) + currentLevel.getOffsetX();
				double y = (Math.floor((event.getY()) / 50)) + currentLevel.getOffsetY();

				focusTileX = (int) x - currentLevel.getOffsetX();
				focusTileY = (int) y - currentLevel.getOffsetY();

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
						// here we can display available tiles
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