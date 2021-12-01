import java.math.BigDecimal;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
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

/**
	 * This method is called periodically by the tick timeline
	 * and would for, example move, perform logic in the game,
	 * this might cause the bad guys to move (by e.g., looping
	 * over them all and calling their own tick method). 
	 */
	private int counter = 0;
	public void tick() {
		currentLevel.updateBoard();
		drawGame();
	}

	public void bombDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50))+offsetX;
		double y = (Math.floor((event.getY()) / 50))+offsetY;

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		currentLevel.addRenderObject(new Bomb(new Position(BigDecimal.valueOf(x),BigDecimal.valueOf(y)), currentLevel));
		drawGame();
	}
	
	public void gasDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50))+offsetX;
		double y = (Math.floor((event.getY()) / 50))+offsetY;

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		currentLevel.addRenderObject(new Gas(new Position(BigDecimal.valueOf(x),BigDecimal.valueOf(y)), currentLevel));
		drawGame();
	}
	
	public void poisonDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50))+offsetX;
		double y = (Math.floor((event.getY()) / 50))+offsetY;

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		currentLevel.addRenderObject(new Poison(new Position(BigDecimal.valueOf(x),BigDecimal.valueOf(y)), currentLevel));
		drawGame();
	}
	
	public void signDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50))+offsetX;
		double y = (Math.floor((event.getY()) / 50))+offsetY;

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		currentLevel.addRenderObject(new NoEntrySign(new Position(BigDecimal.valueOf(x),BigDecimal.valueOf(y)), currentLevel));
		drawGame();
	}
	
	public void deathRatDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50))+offsetX;
		double y = (Math.floor((event.getY()) / 50))+offsetY;

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		currentLevel.addRenderObject(new DeathRat(new Position(BigDecimal.valueOf(x),BigDecimal.valueOf(y)), currentLevel));
		drawGame();
	}
	
	public void femaleSexChangerDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50))+offsetX;
		double y = (Math.floor((event.getY()) / 50))+offsetY;

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		currentLevel.addRenderObject(new FemaleSexChange(new Position(BigDecimal.valueOf(x),BigDecimal.valueOf(y)), currentLevel));
		drawGame();
	}
	
	public void maleSexChangerDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50))+offsetX;
		double y = (Math.floor((event.getY()) / 50))+offsetY;

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		currentLevel.addRenderObject(new MaleSexChange(new Position(BigDecimal.valueOf(x),BigDecimal.valueOf(y)), currentLevel));
		drawGame();
	}
	
	public void sterilisationDropOccured(DragEvent event) {
		double x = (Math.floor((event.getX()) / 50));
		double y = (Math.floor((event.getY()) / 50));

		// Print a string showing the location.
		String s = String.format("You dropped at (%f, %f) relative to the canvas.", x, y);
		System.out.println(s);

		currentLevel.addRenderObject(new Sterilisation(new Position(BigDecimal.valueOf(x),BigDecimal.valueOf(y)), currentLevel));
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
		
		
		public void processKeyEvent(KeyEvent event) {
			// We change the behaviour depending on the actual key that was pressed.
			switch (event.getCode()) {			
			    case RIGHT:
			    	// Right key was pressed. So move the player right by one cell.
			    	if (offsetX < (currentLevel.getTiles()[0].length) - (CANVAS_WIDTH / GRID_CELL_WIDTH)) {
			    		offsetX++;
			    	}
		        	break;
			    case UP:
			    	if (offsetY > 0) {
			    		offsetY--;
			    	}
			    	break;
			    case DOWN: 
			    	if (offsetY < (currentLevel.getTiles().length) - (CANVAS_HEIGHT / GRID_CELL_HEIGHT)) {
			    		offsetY++;
			    	}	
			    	break;
			    case LEFT:
			    	if (offsetX > 0) {
			    		offsetX--;
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
		

		// Finally, return the border pane we built up.
		return root;
	}