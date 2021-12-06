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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

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
	ImageView bombCounter = new ImageView();
	ImageView gasCounter = new ImageView();
	ImageView poisonCounter = new ImageView();
	ImageView signCounter = new ImageView();
	ImageView deathRatCounter = new ImageView();
	ImageView femaleCounter = new ImageView();
	ImageView maleCounter = new ImageView();
	ImageView sterilisationCounter = new ImageView();

	private static final BigDecimal TICK_DURATION = new BigDecimal("15");
	Font font = Font.loadFont("/fonts/stats.ttf", 45);

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
	private Image pausedBG = new Image("/Textures/paused-background.png");
	private Image quitGameButton = new Image("/Textures/quit-game-button.png");
	private Image quitGameButtonHover = new Image("/Textures/quit-game-button-hover.png");
	private boolean quitGameButtonHovered = false;
	private boolean saveGameButtonHovered = false;
	private Image saveGameButton = new Image("/Textures/save-button.png");
	private Image saveGameButtonHover = new Image("/Textures/save-button-hover.png");

	private Image losing0 = new Image("/losing_the_game/losing0.png");
	private Image losing1 = new Image("/losing_the_game/losing1.png");
	private Image losing2 = new Image("/losing_the_game/losing2.png");

	private Image winning0 = new Image("/winning_the_game/winning0.png");
	private Image winning1 = new Image("/winning_the_game/winning1.png");
	private Image winning2 = new Image("/winning_the_game/winning2.png");

	private Image page = new Image("/Textures/page2.png");
	private Image stats = new Image("/Textures/STATS.png");
	private String saveFile = "";

	private Image availSprite;
	private Image availableSprite = new Image("Textures/available.png");
	private Image unavailableSprite = new Image("Textures/unavailable.png");
	private int focusTileX;
	private int focusTileY;

	private Image blackBackground = new Image("Textures/black.png");

	private Image gameWonScreen = new Image("Textures/game-won.png");
	private Image gameLostScreen = new Image("Textures/game-over.png");
	private Image defaultCounter = new Image("x/x0.png");

	private ItemManager items;
	private int currentLevelNumber;

	private Stage gameStage = new Stage();

	private Level currentLevel;
	private boolean showAvailableTile = false;
	private int tickCounter = 0;
	private boolean hasWon = false;
	private boolean hasLost = false;
	private static final String SRC_FILE_PREFIX = "src/Levels/";

	private boolean isPaused = false;

	private Profile currentUser;

	private static final int QUIT_GAME_BUTTON_X = 10;
	private static final int QUIT_GAME_BUTTON_Y = 4;
	private static final int QUIT_GAME_BUTTON_WIDTH = 2;

	private static final int SAVE_GAME_BUTTON_X = 10;
	private static final int SAVE_GAME_BUTTON_Y = 5;
	private static final int SAVE_GAME_BUTTON_WIDTH = 2;
	private static final Sprites spriteLoader = new Sprites();

	private static final String POISON_STR = "Poison";
	private static final String STERILISATION_STR = "Sterilisation";
	private static final String MGENDERCHANGE_STR = "MGenderChange";
	private static final String FGENDERCHANGE_STR = "FGenderChange";
	private static final String DEATHRAT_STR = "DeathRat";
	private static final String NOENTRYSIGN_STR = "NoEntrySign";
	private static final String GAS_STR = "Gas";
	private static final String BOMB_STR = "Bomb";
	private static final String AMOUNT_0_STR = "Amount is 0";
	private static final String DROP_CONFIRMATION_STR = "You dropped at (%f, %f) relative to the canvas.";

	public static int fetchLevels() {
		// a list of levels in the levels folder,
		// remove the .txt from the level name
		// get the highest number level

		File[] directories = new File(SRC_FILE_PREFIX).listFiles();

		int highestLevel = 0;

		for (int i = 0; i < directories.length; i++) {
			String fileName = directories[i].getName().substring(0, directories[i].getName().length() - 4);

			if (Integer.parseInt(fileName) > highestLevel) {
				highestLevel = Integer.parseInt(fileName);
			}
		}

		return highestLevel;
	}

	public GameConstructor(int levelNumber, Profile currentProfile, String saveFile) {

		this.currentLevelNumber = levelNumber;
		this.currentUser = currentProfile;

		System.out.println("test");

		if (!saveFile.equals("")) {

			// call the load in

			// fetch all the item data from save file.
			String fileName = saveFile + "level.txt";
			String fileData = "";
			File level = new File(fileName);

			try (Scanner in = new Scanner(level);) {
				StringBuilder bld = new StringBuilder();
				while (in.hasNextLine()) {
					bld.append(in.nextLine());
				}
				fileData = bld.toString();

			} catch (FileNotFoundException e) {
				System.out.println(fileName);
				System.out.println("File does not existP");
				System.exit(0);
			}

			String[] fetchData = fileData.split(",");
			String[] itemQuantitiesString = fetchData[1].split(" ");
			String[] timeAndScoreString = fetchData[2].split(" ");
			int[] itemQuantity = new int[8];

			if (itemQuantity.length == itemQuantitiesString.length) {
				for (int i = 0; i < itemQuantity.length; i++) {
					itemQuantity[i] = Integer.parseInt(itemQuantitiesString[i]);
				}
			}

			this.millisecondCount = new BigDecimal(timeAndScoreString[0]);
			this.items = new ItemManager(itemQuantity[0], itemQuantity[1], itemQuantity[2], itemQuantity[3],
					itemQuantity[4], itemQuantity[5], itemQuantity[6], itemQuantity[7]);
			this.saveFile = saveFile;
			this.currentLevel = new Level(SRC_FILE_PREFIX + levelNumber + ".txt", saveFile, this.items, currentUser,
					currentLevelNumber);
			this.currentLevel.incrimentScore(Integer.parseInt(timeAndScoreString[1]));

		} else {
			this.items = new ItemManager(0, 0, 0, 0, 0, 0, 0, 0);
			this.currentLevel = new Level(SRC_FILE_PREFIX + levelNumber + ".txt", "", this.items, currentUser,
					currentLevelNumber);
		}

		// Create leaderboard file if one doesn't already exist.

		boolean check = new File("src/Scores/" + this.currentLevelNumber + ".txt").exists();

		if (!check) {
			try (PrintWriter leaderboardWriter = new PrintWriter("src/Scores/" + this.currentLevelNumber + ".txt");) {

				for (int i = 0; i < 9; i++) {
					leaderboardWriter.println("Null -1,");
				}
			} catch (FileNotFoundException e) {
				System.out.println("File not found");
			}

		}
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

		drawGame();
		primaryStage.setScene(scene);
		primaryStage.show();

		// Close the game properly when they click the "X" in the corner

		primaryStage.setOnCloseRequest(e -> {
			primaryStage.close();
			tickTimeline.stop();

		});
	}

	private Image loadImage(int pictureNumber) {
		return new Image("/x/x" + pictureNumber + ".png");
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

		// pausedBG

		// we need to draw the game

		for (int y = 0; y < (CANVAS_HEIGHT / GRID_CELL_WIDTH); y++) {
			for (int x = 0; x < (CANVAS_WIDTH / GRID_CELL_HEIGHT); x++) {
				gc.drawImage(currentLevel.getRenderTiles()[y + currentLevel.getOffsetY()][x + currentLevel.getOffsetX()]
						.getImage(), x * GRID_CELL_WIDTH, y * GRID_CELL_HEIGHT);
			}
		}

		// we need to draw the items
		for (int i = 0; i < currentLevel.getItems().size(); i++) {
			gc.drawImage(spriteLoader.getImage(currentLevel.getItems().get(i).getSprite()),
					(currentLevel.getItems().get(i).getObjectPosition()[0].doubleValue() - currentLevel.getOffsetX())
							* GRID_CELL_WIDTH,
					(currentLevel.getItems().get(i).getObjectPosition()[1].doubleValue() - currentLevel.getOffsetY())
							* GRID_CELL_HEIGHT);
		}

		// we need to draw the rats

		for (int i = 0; i < currentLevel.getRats().size(); i++) {
			gc.drawImage(spriteLoader.getImage(currentLevel.getRats().get(i).getSprite()),
					(currentLevel.getRats().get(i).getObjectPosition()[0].doubleValue() - currentLevel.getOffsetX())
							* GRID_CELL_WIDTH,
					(currentLevel.getRats().get(i).getObjectPosition()[1].doubleValue() - currentLevel.getOffsetY())
							* GRID_CELL_HEIGHT);
		}

		// need to draw the temp tiles
		for (int i = 0; i < currentLevel.getTempTiles().size(); i++) {
			gc.drawImage(spriteLoader.getImage(currentLevel.getTempTiles().get(i).getSprite()),
					(currentLevel.getTempTiles().get(i).getObjectPosition()[0].doubleValue()
							- currentLevel.getOffsetX()) * GRID_CELL_WIDTH,
					(currentLevel.getTempTiles().get(i).getObjectPosition()[1].doubleValue()
							- currentLevel.getOffsetY()) * GRID_CELL_HEIGHT);
		}

		// we need to draw the tunnels

		if (!this.currentLevel.getRenderTilesAfter().isEmpty()) {
			for (int i = 0; i < this.currentLevel.getRenderTilesAfter().size(); i++) {
				double x = this.currentLevel.getRenderTilesAfterPositions().get(i).getPosition()[0].doubleValue();
				double y = this.currentLevel.getRenderTilesAfterPositions().get(i).getPosition()[1].doubleValue();

				gc.drawImage(currentLevel.getRenderTilesAfter().get(i).getImage(),
						(x * GRID_CELL_WIDTH) - (currentLevel.getOffsetX() * GRID_CELL_WIDTH),
						(y * GRID_CELL_HEIGHT) - (currentLevel.getOffsetY() * GRID_CELL_HEIGHT));
			}
		}

		// draw availability spirte when dragging
		if (showAvailableTile) {
			gc.drawImage(availSprite, focusTileX * GRID_CELL_WIDTH, focusTileY * GRID_CELL_HEIGHT);
		}

		int maleCount = 0;
		int femaleCount = 0;
		int totalCount;

		// Find the number of male rats
		for (int i = 0; i < currentLevel.getRats().size(); i++) {
			if ((currentLevel.getRats().get(i) instanceof NormalRat)
					&& ((NormalRat) currentLevel.getRats().get(i)).getRatGender()) {
				maleCount++;
			}
		}

		// Find the number of female rats
		for (int i = 0; i < currentLevel.getRats().size(); i++) {
			if ((currentLevel.getRats().get(i) instanceof NormalRat)
					&& !(((NormalRat) currentLevel.getRats().get(i)).getRatGender())) {
				femaleCount++;
			}
		}

		// Find the total number of rats
		totalCount = maleCount + femaleCount;

		if (currentLevel.getMaxRats() * 0.6 <= totalCount && currentLevel.getMaxRats() * 0.75 > totalCount) {
			gc.drawImage(losing0, 0 * GRID_CELL_WIDTH, 0 * GRID_CELL_HEIGHT);
		} else if (currentLevel.getMaxRats() * 0.75 <= totalCount && currentLevel.getMaxRats() * 0.9 > totalCount) {
			gc.drawImage(losing1, 0 * GRID_CELL_WIDTH, 0 * GRID_CELL_HEIGHT);
		} else if (currentLevel.getMaxRats() * 0.9 <= totalCount) {
			gc.drawImage(losing2, 0 * GRID_CELL_WIDTH, 0 * GRID_CELL_HEIGHT);
		} else if (currentLevel.getMaxRats() * 0.2 >= totalCount && currentLevel.getMaxRats() * 0.1 < totalCount) {
			gc.drawImage(winning0, 0 * GRID_CELL_WIDTH, 0 * GRID_CELL_HEIGHT);
		} else if (currentLevel.getMaxRats() * 0.1 >= totalCount) {
			gc.drawImage(winning1, 0 * GRID_CELL_WIDTH, 0 * GRID_CELL_HEIGHT);
		}

		// RAT COUNTER
		gc.drawImage(page, 0, 0);
		gc.drawImage(stats, 0, 5);

		gc.setFill(Color.RED);
		gc.setFont(new Font("fonts/jj2.ttf", 13));

		// draw the counters
		gc.fillText(" " + totalCount, 62, 22);
		gc.fillText(" " + maleCount, 121, 44);
		gc.fillText(" " + femaleCount, 101, 66);
		gc.fillText(" " + currentLevel.getMaxRats(), 115, 88);

		// show win screen
		if (this.hasWon) {
			gc.drawImage(blackBackground, 0 * GRID_CELL_WIDTH, 0 * GRID_CELL_HEIGHT);

			Leaderboard getLeaderboad = new Leaderboard(this.currentLevelNumber);
			getLeaderboad.addScore(this.currentUser.getName(), this.currentLevel.getScore());

			// read in the file
			String fileName = "src/scores/" + this.currentLevelNumber + ".txt";
			String fileData = "";
			File leaderboard = new File(fileName);

			try (Scanner in = new Scanner(leaderboard)) {
				StringBuilder bld = new StringBuilder();
				while (in.hasNextLine()) {
					bld.append(in.nextLine());
				}
				fileData = bld.toString();
			} catch (Exception e) {
				System.out.print("ubale to read file");

			}

			String[] dataArray = fileData.split(",");

			int y = 1;
			for (int i = 0; i < dataArray.length; i++) {
				String output = dataArray[i].split(" ")[0] + "           " + dataArray[i].split(" ")[1];
				int x = 6;
				if (Integer.parseInt(dataArray[i].split(" ")[1]) != -1) {
					y++;
					gc.fillText(output, x * GRID_CELL_WIDTH, y * GRID_CELL_WIDTH);
				}
			}

		}

		// show lose screen
		if (this.hasLost) {
			gc.drawImage(gameLostScreen, 0 * GRID_CELL_WIDTH, 0 * GRID_CELL_HEIGHT);

		}

		if (isPaused) {
			gc.drawImage(pausedBG, 0 * GRID_CELL_WIDTH, 0 * GRID_CELL_HEIGHT);
			if (quitGameButtonHovered) {
				gc.drawImage(quitGameButtonHover, QUIT_GAME_BUTTON_X * GRID_CELL_WIDTH,
						QUIT_GAME_BUTTON_Y * GRID_CELL_HEIGHT);
			} else {
				gc.drawImage(quitGameButton, QUIT_GAME_BUTTON_X * GRID_CELL_WIDTH,
						QUIT_GAME_BUTTON_Y * GRID_CELL_HEIGHT);
			}

			if (saveGameButtonHovered) {
				gc.drawImage(saveGameButtonHover, SAVE_GAME_BUTTON_X * GRID_CELL_WIDTH,
						SAVE_GAME_BUTTON_Y * GRID_CELL_HEIGHT);
			} else {
				gc.drawImage(saveGameButton, SAVE_GAME_BUTTON_X * GRID_CELL_WIDTH,
						SAVE_GAME_BUTTON_Y * GRID_CELL_HEIGHT);
			}

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
				}
				break;
			case LEFT:
				if (currentLevel.getOffsetX() > 0) {
					currentLevel.setOffsetX(currentLevel.getOffsetX() - 1);
				}
				break;
			case ESCAPE:
				String gameStatus = currentLevel.getGameStatus();
				if ("inprogress".equals(gameStatus)) {
					togglePause();
					System.out.println("ESCAPE PRESSED");
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

	public void togglePause() {
		if (this.isPaused) {
			drawGame();
			this.isPaused = false;
			this.currentLevel.playAllSound();
			tickTimeline.play();
		} else {
			drawGame();
			this.isPaused = true;
			this.currentLevel.pauseAllSound();
			tickTimeline.pause();
		}
	}

	/**
	 * This method is called periodically by the tick timeline and would for,
	 * example move, perform logic in the game, this might cause the bad guys to
	 * move (by e.g., looping over them all and calling their own tick method).
	 */

	public void calculateTimePoints() {
		BigDecimal timeTakenInSeconds = this.millisecondCount.divide(new BigDecimal("1000"));
		// timeTakenInSeconds < this.currentLevel.getParTime()
		if (timeTakenInSeconds.compareTo(this.currentLevel.getParTime()) < 0) {
			// figure out the difference

			BigDecimal gainScore = this.currentLevel.getParTime().subtract(timeTakenInSeconds);
			System.out.println("SCORE GAINED");
			System.out.println(gainScore);
			this.currentLevel.incrimentScore((int) Math.floor(gainScore.doubleValue()));
		}

	}

	public void tick() {

		this.millisecondCount = this.millisecondCount.add(TICK_DURATION);

		String gameStatus = currentLevel.getGameStatus();

		if ("inprogress".equals(gameStatus)) {
			// run game as usual
			currentLevel.updateBoard();
			// We then redraw the whole canvas.
			drawGame();

			tickCounter++;
			if (tickCounter == 66) {
				showAvailableTile = false;
				tickCounter = 0;
			}
		} else if ("won".equals(gameStatus)) {
			calculateTimePoints();

			// get the current user's maximum level
			// check if the level is equal to the current
			// get the max level in general

			int usersCurrent = this.currentUser.getLevels();

			if (usersCurrent == this.currentLevelNumber && usersCurrent < fetchLevels()) {
				System.out.print("UPGRADE LEVEL ");
				System.out.println("LEVEL");
				System.out.println(this.currentLevelNumber + 1);
				this.currentUser.overwriteLevel(this.currentLevelNumber + 1);
			}

			this.tickTimeline.stop();
			this.hasWon = true;

			drawGame();
		} else if ("lost".equals(gameStatus)) {

			// game is lost, need to append score to leaderboard
			// display lost game screen
			System.out.println("GAME IS LOST");
			this.hasLost = true;
			drawGame();

			currentLevel.updateBoard();
			// We then redraw the whole canvas.
			drawGame();

			tickCounter++;
			if (tickCounter == 66) {
				showAvailableTile = false;
				tickCounter = 0;
			}
		}

		if (items.getItemCount(BOMB_STR) > 0) {
			bombCounter.setImage(loadImage(items.getItemCount(BOMB_STR)));
			draggableBombImage.setImage(bombOn);
		}

		if (items.getItemCount(POISON_STR) > 0) {
			poisonCounter.setImage(loadImage(items.getItemCount(POISON_STR)));
			draggablePoisonImage.setImage(poisonOn);
		}

		if (items.getItemCount(GAS_STR) > 0) {
			gasCounter.setImage(loadImage(items.getItemCount(GAS_STR)));
			draggableGasImage.setImage(gasOn);
		}

		if (items.getItemCount(STERILISATION_STR) > 0) {
			sterilisationCounter.setImage(loadImage(items.getItemCount(STERILISATION_STR)));
			draggableSterilisationImage.setImage(sterilisationOn);
		}

		if (items.getItemCount(MGENDERCHANGE_STR) > 0) {
			maleCounter.setImage(loadImage(items.getItemCount(MGENDERCHANGE_STR)));
			draggableMaleSexChangerImage.setImage(maleSexChangerOn);
		}

		if (items.getItemCount(FGENDERCHANGE_STR) > 0) {
			femaleCounter.setImage(loadImage(items.getItemCount(FGENDERCHANGE_STR)));
			draggableFemaleSexChangerImage.setImage(femaleSexChangerOn);
		}

		if (items.getItemCount(DEATHRAT_STR) > 0) {
			deathRatCounter.setImage(loadImage(items.getItemCount(DEATHRAT_STR)));
			draggableDeathRatImage.setImage(deathRatOn);
		}

		if (items.getItemCount(NOENTRYSIGN_STR) > 0) {
			signCounter.setImage(loadImage(items.getItemCount(NOENTRYSIGN_STR)));
			draggableSignImage.setImage(noEntrySignOn);
		}

		System.out.println(this.currentLevel.getScore());
	}

	public void bombDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50)) + currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50)) + currentLevel.getOffsetY();

		// Check if there are more than 0 of the item in the inventory. If not don't let
		// the user drag the item.
		if (items.isItemDepleted(BOMB_STR)) {
			System.out.println(AMOUNT_0_STR);
		} else {
			currentLevel.spawnItem(new Bomb(new Position(BigDecimal.valueOf(x), BigDecimal.valueOf(y)), currentLevel));
			items.tryReduceItem(BOMB_STR);
			if (items.isItemDepleted(BOMB_STR)) {
				bombCounter.setImage(loadImage(items.getItemCount(BOMB_STR)));
				draggableBombImage.setImage(bomb);

			}
		}
	}

	public void gasDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50)) + currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50)) + currentLevel.getOffsetY();

		// Check if there are more than 0 of the item in the inventory. If not don't let
		// the user drag the item.
		if (items.isItemDepleted(GAS_STR)) {
			System.out.println(AMOUNT_0_STR);
		} else {
			currentLevel.spawnItem(new Gas(new Position(BigDecimal.valueOf(x), BigDecimal.valueOf(y)), currentLevel));
			items.tryReduceItem(GAS_STR);
			if (items.isItemDepleted(GAS_STR)) {
				gasCounter.setImage(loadImage(items.getItemCount(GAS_STR)));
				draggableGasImage.setImage(gas);

			}
		}
	}

	public void poisonDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50)) + currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50)) + currentLevel.getOffsetY();

		// Check if there are more than 0 of the item in the inventory. If not don't let
		// the user drag the item.
		if (items.isItemDepleted(POISON_STR)) {
			System.out.println(AMOUNT_0_STR);
		} else {
			currentLevel
					.spawnItem(new Poison(new Position(BigDecimal.valueOf(x), BigDecimal.valueOf(y)), currentLevel));
			items.tryReduceItem(POISON_STR);
			if (items.isItemDepleted(POISON_STR)) {
				poisonCounter.setImage(loadImage(items.getItemCount(POISON_STR)));
				draggablePoisonImage.setImage(poison);

			}
		}
	}

	public void signDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50)) + currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50)) + currentLevel.getOffsetY();

		// Print a string showing the location.
		String s = String.format(DROP_CONFIRMATION_STR, x, y);
		System.out.println(s);

		// Check if there are more than 0 of the item in the inventory. If not don't let
		// the user drag the item.
		if (items.isItemDepleted(NOENTRYSIGN_STR)) {
			System.out.println(AMOUNT_0_STR);
		} else {
			currentLevel.spawnItem(
					new NoEntrySign(new Position(BigDecimal.valueOf(x), BigDecimal.valueOf(y)), currentLevel));
			items.tryReduceItem(NOENTRYSIGN_STR);
			if (items.isItemDepleted(NOENTRYSIGN_STR)) {
				signCounter.setImage(loadImage(items.getItemCount(NOENTRYSIGN_STR)));
				draggableSignImage.setImage(noEntrySign);

			}
		}
	}

	public void deathRatDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50)) + currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50)) + currentLevel.getOffsetY();

		// Print a string showing the location.
		String s = String.format(DROP_CONFIRMATION_STR, x, y);
		System.out.println(s);

		// Check if there are more than 0 of the item in the inventory. If not don't let
		// the user drag the item.
		if (items.isItemDepleted(DEATHRAT_STR)) {
			System.out.println(AMOUNT_0_STR);
		} else {
			currentLevel
					.spawnRat(new DeathRat(new Position(BigDecimal.valueOf(x), BigDecimal.valueOf(y)), currentLevel));
			items.tryReduceItem(DEATHRAT_STR);
			if (items.isItemDepleted(DEATHRAT_STR)) {
				deathRatCounter.setImage(loadImage(items.getItemCount(DEATHRAT_STR)));
				draggableDeathRatImage.setImage(deathRat);

			}
		}
	}

	public void femaleSexChangerDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50)) + currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50)) + currentLevel.getOffsetY();

		// Print a string showing the location.
		String s = String.format(DROP_CONFIRMATION_STR, x, y);
		System.out.println(s);

		// Check if there are more than 0 of the item in the inventory. If not don't let
		// the user drag the item.
		if (items.isItemDepleted(FGENDERCHANGE_STR)) {
			System.out.println(AMOUNT_0_STR);
		} else {
			currentLevel.spawnItem(
					new FemaleSexChange(new Position(BigDecimal.valueOf(x), BigDecimal.valueOf(y)), currentLevel));
			items.tryReduceItem(FGENDERCHANGE_STR);
			if (items.isItemDepleted(FGENDERCHANGE_STR)) {
				femaleCounter.setImage(loadImage(items.getItemCount(FGENDERCHANGE_STR)));
				draggableFemaleSexChangerImage.setImage(femaleSexChanger);

			}
		}
	}

	public void maleSexChangerDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50)) + currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50)) + currentLevel.getOffsetY();

		// Print a string showing the location.
		String s = String.format(DROP_CONFIRMATION_STR, x, y);
		System.out.println(s);

		// Check if there are more than 0 of the item in the inventory. If not don't let
		// the user drag the item.
		if (items.isItemDepleted(MGENDERCHANGE_STR)) {
			System.out.println(AMOUNT_0_STR);
		} else {
			currentLevel.spawnItem(
					new MaleSexChange(new Position(BigDecimal.valueOf(x), BigDecimal.valueOf(y)), currentLevel));
			items.tryReduceItem(MGENDERCHANGE_STR);
			if (items.isItemDepleted(MGENDERCHANGE_STR)) {
				maleCounter.setImage(loadImage(items.getItemCount(MGENDERCHANGE_STR)));
				draggableMaleSexChangerImage.setImage(maleSexChanger);

			}
		}
	}

	public void sterilisationDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50)) + currentLevel.getOffsetX();
		double y = (Math.floor((event.getY()) / 50)) + currentLevel.getOffsetY();

		// Print a string showing the location.
		String s = String.format(DROP_CONFIRMATION_STR, x, y);
		System.out.println(s);

		// Check if there are more than 0 of the item in the inventory. If not don't let
		// the user drag the item.
		if (items.isItemDepleted(STERILISATION_STR)) {
			System.out.println(AMOUNT_0_STR);
		} else {
			currentLevel.spawnItem(
					new Sterilisation(new Position(BigDecimal.valueOf(x), BigDecimal.valueOf(y)), currentLevel));
			items.tryReduceItem(STERILISATION_STR);
			if (items.isItemDepleted(STERILISATION_STR)) {
				sterilisationCounter.setImage(loadImage(items.getItemCount(STERILISATION_STR)));
				draggableSterilisationImage.setImage(sterilisation);

			}
		}
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

		BackgroundImage myBI = new BackgroundImage(new Image("/Textures/hud-bg1.png", 0, 0, false, true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);

		// then you set to your node
		root.setBackground(new Background(myBI));

		HBox toolbar = new HBox();
		toolbar.setSpacing(2);
		toolbar.setPadding(new Insets(10, 10, 10, 10));
		root.setBottom(toolbar);

		draggableBombImage.setImage(bomb);
		toolbar.getChildren().add(draggableBombImage);
		bombCounter.setImage(defaultCounter);
		toolbar.getChildren().add(bombCounter);

		draggableBombImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (!hasLost && !hasWon && !items.isItemDepleted(BOMB_STR)) {
					// Mark the drag as started.
					// We do not use the transfer mode (this can be used to indicate different forms
					// of drags operations, for example, moving files or copying files).

					Dragboard db = draggableBombImage.startDragAndDrop(TransferMode.ANY);
					ClipboardContent content = new ClipboardContent();
					content.putString("");
					db.setContent(content);

					event.consume();

				}
			}
			// We have to put some content in the clipboard of the drag event.
			// We do not use this, but we could use it to store extra data if we wished.

			// Consume the event. This means we mark it as dealt with.
		});

		draggableGasImage.setImage(gas);
		toolbar.getChildren().add(draggableGasImage);
		gasCounter.setImage(defaultCounter);
		toolbar.getChildren().add(gasCounter);

		draggableGasImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (!hasLost && !hasWon && !items.isItemDepleted(GAS_STR)) {

					// Mark the drag as started.
					// We do not use the transfer mode (this can be used to indicate different forms
					// of drags operations, for example, moving files or copying files).
					Dragboard db = draggableGasImage.startDragAndDrop(TransferMode.ANY);

					// We have to put some content in the clipboard of the drag event.
					// We do not use this, but we could use it to store extra data if we wished.
					ClipboardContent content = new ClipboardContent();
					content.putString("");
					db.setContent(content);

					// Consume the event. This means we mark it as dealt with.
					event.consume();

				}
			}
		});

		draggablePoisonImage.setImage(poison);
		toolbar.getChildren().add(draggablePoisonImage);
		poisonCounter.setImage(defaultCounter);
		toolbar.getChildren().add(poisonCounter);

		draggablePoisonImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (!hasLost && !hasWon && !items.isItemDepleted(POISON_STR)) {

					// Mark the drag as started.
					// We do not use the transfer mode (this can be used to indicate different forms
					// of drags operations, for example, moving files or copying files).
					Dragboard db = draggablePoisonImage.startDragAndDrop(TransferMode.ANY);

					// We have to put some content in the clipboard of the drag event.
					// We do not use this, but we could use it to store extra data if we wished.
					ClipboardContent content = new ClipboardContent();
					content.putString("");
					db.setContent(content);

					// Consume the event. This means we mark it as dealt with.
					event.consume();

				}
			}
		});

		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override

			public void handle(MouseEvent event) {
				if (isPaused) {
					int x = (int) (Math.floor((event.getSceneX()) / 50)) + currentLevel.getOffsetX();
					int y = (int) (Math.floor((event.getSceneY()) / 50)) + currentLevel.getOffsetY();

					for (int i = QUIT_GAME_BUTTON_X + currentLevel.getOffsetX(); i < (QUIT_GAME_BUTTON_X
							+ currentLevel.getOffsetX() + QUIT_GAME_BUTTON_WIDTH); i++) {
						if (x == i && y == QUIT_GAME_BUTTON_Y + currentLevel.getOffsetY()) {
							// method for button click for quit
							tickTimeline.stop();
							gameStage.close();
						}
					}

					for (int i = SAVE_GAME_BUTTON_X + currentLevel.getOffsetX(); i < SAVE_GAME_BUTTON_X
							+ currentLevel.getOffsetX() + SAVE_GAME_BUTTON_WIDTH; i++) {
						if (x == i && y == SAVE_GAME_BUTTON_Y + currentLevel.getOffsetY()) {
							// method for button click for quit
							// save code here
							System.out.println("SAVED");
							currentLevel.saveProgress(millisecondCount);
						}
					}

					// check if the x and y is on a button
				}

			}
		});

		canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				if (isPaused) {
					int x = (int) (Math.floor((event.getSceneX()) / 50)) + currentLevel.getOffsetX();
					int y = (int) (Math.floor((event.getSceneY()) / 50)) + currentLevel.getOffsetY();

					boolean isQuitHoverSave = false;
					boolean isSaveHoverQuit = false;

					for (int i = QUIT_GAME_BUTTON_X + currentLevel.getOffsetX(); i < QUIT_GAME_BUTTON_X
							+ currentLevel.getOffsetX() + QUIT_GAME_BUTTON_WIDTH; i++) {
						if (x == i && y == QUIT_GAME_BUTTON_Y + currentLevel.getOffsetY()) {
							// method for button click
							isQuitHoverSave = true;
						}
					}

					for (int i = SAVE_GAME_BUTTON_X + currentLevel.getOffsetX(); i < SAVE_GAME_BUTTON_X
							+ currentLevel.getOffsetX() + SAVE_GAME_BUTTON_WIDTH; i++) {
						if (x == i && y == SAVE_GAME_BUTTON_Y + currentLevel.getOffsetY()) {
							// method for button click
							isSaveHoverQuit = true;
						}
					}

					if (isQuitHoverSave) {
						quitGameButtonHovered = true;
						drawGame();
					} else {
						quitGameButtonHovered = false;
						drawGame();
					}

					if (isSaveHoverQuit) {
						saveGameButtonHovered = true;
						drawGame();
					} else {
						saveGameButtonHovered = false;
						drawGame();
					}
				}

			}
		});

		draggableSignImage.setImage(noEntrySign);
		toolbar.getChildren().add(draggableSignImage);
		signCounter.setImage(defaultCounter);
		toolbar.getChildren().add(signCounter);

		draggableSignImage.setOnDragDetected(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event) {
				if (!hasLost && !hasWon && !items.isItemDepleted(NOENTRYSIGN_STR)) {
					// Mark the drag as started.
					// We do not use the transfer mode (this can be used to indicate different forms
					// of drags operations, for example, moving files or copying files).
					Dragboard db = draggableSignImage.startDragAndDrop(TransferMode.ANY);

					// We have to put some content in the clipboard of the drag event.
					// We do not use this, but we could use it to store extra data if we wished.
					ClipboardContent content = new ClipboardContent();
					content.putString("");
					db.setContent(content);

					// Consume the event. This means we mark it as dealt with.
					event.consume();
				}
			}
		});

		draggableDeathRatImage.setImage(deathRat);
		toolbar.getChildren().add(draggableDeathRatImage);
		deathRatCounter.setImage(defaultCounter);
		toolbar.getChildren().add(deathRatCounter);

		draggableDeathRatImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (!hasLost && !hasWon && !items.isItemDepleted(DEATHRAT_STR)) {
					// Mark the drag as started.
					// We do not use the transfer mode (this can be used to indicate different forms
					// of drags operations, for example, moving files or copying files).
					Dragboard db = draggableDeathRatImage.startDragAndDrop(TransferMode.ANY);

					// We have to put some content in the clipboard of the drag event.
					// We do not use this, but we could use it to store extra data if we wished.
					ClipboardContent content = new ClipboardContent();
					content.putString("");
					db.setContent(content);

					// Consume the event. This means we mark it as dealt with.
					event.consume();
				}
			}
		});

		draggableFemaleSexChangerImage.setImage(femaleSexChanger);
		toolbar.getChildren().add(draggableFemaleSexChangerImage);
		femaleCounter.setImage(defaultCounter);
		toolbar.getChildren().add(femaleCounter);

		draggableFemaleSexChangerImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (!hasLost && !hasWon && !items.isItemDepleted(FGENDERCHANGE_STR)) {
					// Mark the drag as started.
					// We do not use the transfer mode (this can be used to indicate different forms
					// of drags operations, for example, moving files or copying files).
					Dragboard db = draggableFemaleSexChangerImage.startDragAndDrop(TransferMode.ANY);

					// We have to put some content in the clipboard of the drag event.
					// We do not use this, but we could use it to store extra data if we wished.
					ClipboardContent content = new ClipboardContent();
					content.putString("");
					db.setContent(content);

					// Consume the event. This means we mark it as dealt with.
					event.consume();
				}
			}
		});

		draggableMaleSexChangerImage.setImage(maleSexChanger);
		toolbar.getChildren().add(draggableMaleSexChangerImage);
		maleCounter.setImage(defaultCounter);
		toolbar.getChildren().add(maleCounter);

		draggableMaleSexChangerImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (!hasLost && !hasWon && !items.isItemDepleted(MGENDERCHANGE_STR)) {
					// Mark the drag as started.
					// We do not use the transfer mode (this can be used to indicate different forms
					// of drags operations, for example, moving files or copying files).
					Dragboard db = draggableMaleSexChangerImage.startDragAndDrop(TransferMode.ANY);

					// We have to put some content in the clipboard of the drag event.
					// We do not use this, but we could use it to store extra data if we wished.
					ClipboardContent content = new ClipboardContent();
					content.putString("");
					db.setContent(content);

					// Consume the event. This means we mark it as dealt with.
					event.consume();
				}
			}
		});

		draggableSterilisationImage.setImage(sterilisation);
		toolbar.getChildren().add(draggableSterilisationImage);
		sterilisationCounter.setImage(defaultCounter);
		toolbar.getChildren().add(sterilisationCounter);

		draggableSterilisationImage.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (!hasLost && !hasWon && !items.isItemDepleted(STERILISATION_STR)) {
					// Mark the drag as started.
					// We do not use the transfer mode (this can be used to indicate different forms
					// of drags operations, for example, moving files or copying files).
					Dragboard db = draggableSterilisationImage.startDragAndDrop(TransferMode.ANY);

					// We have to put some content in the clipboard of the drag event.
					// We do not use this, but we could use it to store extra data if we wished.
					ClipboardContent content = new ClipboardContent();
					content.putString("");
					db.setContent(content);

					// Consume the event. This means we mark it as dealt with.
					event.consume();
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

				if (currentLevel.isPlacable(x, y) && event.getGestureSource() != draggableSignImage
						|| (event.getGestureSource() == draggableSignImage && currentLevel.isPlaceableSign(x, y))) {

					availSprite = availableSprite;
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