package display;

import display.inventory.GameInventory;
import display.inventory.Inventory;
import display.menus.GameMenu;
import display.menus.LoseMenu;
import display.menus.MainMenu;
import display.menus.WinMenu;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import level.LevelData;
import level.LevelProperties;
import level.LevelSaveHandler;
import level.RatPopulation;
import objects.GameObject;
import objects.ObjectInteractionChecker;
import objects.ObjectStoppable;
import objects.rats.PeacefulRat;
import players.PlayerProfileManager;
import players.scores.Player;
import tile.Tile;
import util.TextUtils;
import weather.Thunder;

/**
 * A class to model the current game board
 *
 * @author Fahd Alsahali (2015807)
 */
public class Board {

	private static final int POINTS_ON_A_RAT = 10;
	private static final int INTERACTION_CHECK_INTERVAL = 100; // In ms
	private static final String SAVED_BUTTON_LABEL = "Save and exit";
	private static final int CONTROLS_MARGIN = 5; // in pixels
	private static final String INFORMATION_LABEL_TEXT =
			"Time elapsed: %d " + "seconds. Expected time: %d seconds. Score " + "%d. Population to lose " + "%d.";
	private static final int INFO_LABEL_PADDING = 5; // in pixels

	private LevelData levelData;
	private Label timerLabel;
	private TileCanvas tileCanvas;
	private Inventory inventory;
	private Timeline interactionCheckTimeline;
	private Timeline gameLabelTimeline;
	private int populationToLose;
	private ArrayList<Rectangle> progressBar;
	private Timeline winLoseTimeline;
        private Thunder thunder;

	/**
	 * Constructs a Board object.
	 *
	 * @param levelData the level data for this game board.
	 */
	public Board(LevelData levelData) {
		this.levelData = levelData;
		LevelProperties levelProperties = levelData.getLevelProperties();
		this.tileCanvas = new TileCanvas(levelData);

		// Start an interaction checker to check for interactions between the
		// objects
		interactionCheckTimeline =
				new Timeline(new KeyFrame(Duration.millis(INTERACTION_CHECK_INTERVAL), event -> interactionCheck()));
		interactionCheckTimeline.setCycleCount(Animation.INDEFINITE);
		interactionCheckTimeline.play();


		// Create and start a timeline to update a label containing level
		// information
		int timeElapsed = levelProperties.getTimeElapsed();
		int expectedTime = levelProperties.getExpectedTime();
		int score = levelProperties.getScore();
		populationToLose = levelProperties.getPopulationToLose();

		this.timerLabel = new Label(String.format(INFORMATION_LABEL_TEXT,
				timeElapsed, expectedTime, score, populationToLose));
		gameLabelTimeline = new Timeline(new KeyFrame(Duration.seconds(1),
				event -> updateTimerLabel()));
		gameLabelTimeline.setCycleCount(Animation.INDEFINITE);
		gameLabelTimeline.play();

		// Create a new inventory for the level
		this.inventory = new GameInventory(levelData);

		progressBar = new ArrayList<>(populationToLose);
	}

	/**
	 * Constructs a Board object without data, to allow for constructing a CustomBoard
	 */
	public Board() {
	}

	/**
	 * A method to check for interactions between objects on the board.
	 */
	public void interactionCheck() {

		updateProgressBar();
		List<GameObject> objects = levelData.getObjects();
		for (int i = 0; i < objects.size(); i++) {
			GameObject firstObject = objects.get(i);

			for (int j = 0; j < objects.size(); j++) {
				GameObject secondObject = objects.get(j);

				// If we are comparing 2 objects that are on the same tile,
				// and are not the same object
				Tile firstObjTile = firstObject.getStandingOn();
				Tile secondObjTile = secondObject.getStandingOn();
				boolean diffObjectsOnTile =
						firstObjTile.equals(secondObjTile) && !(firstObject.equals(secondObject));

				if (diffObjectsOnTile) {
					// Check for every interaction case
					ObjectInteractionChecker.checkRatsMating(firstObject,
							secondObject);
					ObjectInteractionChecker.checkDeathRat(firstObject,
							secondObject);
					ObjectInteractionChecker.checkBomb(firstObject,
							secondObject);
					ObjectInteractionChecker.checkNoEntrySign(firstObject,
							secondObject);
					ObjectInteractionChecker.checkFemaleSexChanger(firstObject
							, secondObject);
					ObjectInteractionChecker.checkMaleSexChanger(firstObject,
							secondObject);
					ObjectInteractionChecker.checkPoison(firstObject,
							secondObject);
					ObjectInteractionChecker.checkSterilisation(firstObject,
							secondObject);
					ObjectInteractionChecker.checkSterilisationEffect(firstObject, secondObject);
					ObjectInteractionChecker.checkGas(firstObject,
							secondObject);
					ObjectInteractionChecker.checkGasEffect(firstObject,
							secondObject);
					ObjectInteractionChecker.checkPortal(firstObject,
							secondObject);
					ObjectInteractionChecker.checkZombieRat(firstObject,
							secondObject);
				}
			}
		}
	}

	/**
	 * A method to update the canvas that represents the board.
	 */
	public void updateBoardDisplay() {
		tileCanvas.updateBoardDisplay();
	}

	/**
	 * Add the provided GameObject to the board.
	 *
	 * @param objectAdded the GameObject to add.
	 */
	public void addObject(GameObject objectAdded) {
		List<GameObject> objects = levelData.getObjects();

		objects.add(objectAdded);
		updateBoardDisplay();
	}

	/**
	 * Remove the provided object from the board.
	 *
	 * @param objectRemove the GameObject to remove.
	 */
	public void removeObject(GameObject objectRemove) {
		// If we are removing a rat, add to the score
		if (objectRemove instanceof PeacefulRat) {
			PeacefulRat killedRat = (PeacefulRat) objectRemove;
			this.addPoints(killedRat);
		}

		// If we are removing a stoppable object, stop it
		if (objectRemove instanceof ObjectStoppable) {
			ObjectStoppable stoppableObj = (ObjectStoppable) objectRemove;
			stoppableObj.stop();
		}

		// Remove from list and update display
		List<GameObject> objects = levelData.getObjects();
		objects.remove(objectRemove);
		updateBoardDisplay();

	}

	/**
	 * Add points for the provided rat to the points.
	 *
	 * @param killedRat the rat that was killed.
	 */
	public void addPoints(PeacefulRat killedRat) {
		int score = levelData.getLevelProperties().getScore();
		score += POINTS_ON_A_RAT;
		if (killedRat.isPregnant()) {
			score = score + (killedRat.getNumberOfBabies() * POINTS_ON_A_RAT);
		}

		levelData.getLevelProperties().setScore(score);
	}

	/**
	 * Get the current population of rats on the board.
	 *
	 * @return a RatPopulation object, holding data on the current population.
	 */
	public RatPopulation getCurrentPopulation() {
		return new RatPopulation(levelData);
	}

	/**
	 * Get the current score of the game.
	 *
	 * @return the score of the game.
	 */
	public int getScore() {
		return levelData.getLevelProperties().getScore();
	}

	/**
	 * Construct the GUI for the board.
	 *
	 * @return a JavaFX Node, containing the GUI.
	 */
	public Pane buildGUI() {
		BorderPane root = new BorderPane();
                Pane pane=new Pane();
                thunder=new Thunder();
                Canvas thunderLayer=thunder.thunderLayer();
                
                tileCanvas.getCanvas().toFront();
		// Place game board on screen
		pane.getChildren().add(thunderLayer);
                pane.getChildren().add(tileCanvas.getCanvas());
                thunder.thunderStart(thunderLayer, tileCanvas.getCanvas());
                
                root.setLeft(pane);

		// Add inventory
		root.setCenter(inventory.buildInventoryGUI());

		// Add save button
		HBox controlsContainer = new HBox();
		controlsContainer.setPadding(new Insets(CONTROLS_MARGIN));
		controlsContainer.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		controlsContainer.setAlignment(Pos.CENTER);

		Button saveButton = new Button(SAVED_BUTTON_LABEL);

		saveButton.setMinWidth(GameMenu.getStage().getWidth());
		saveButton.setAlignment(Pos.CENTER);

		// Save level when mouse is pressed, and stop the current game
		saveButton.setOnMousePressed(event -> {
			levelData.setInventory(inventory.getItemsInInventory());
			LevelSaveHandler.saveLevel(levelData,
					PlayerProfileManager.getCurrentlyLoggedInPlayer());
			this.stopGame();
			GameMenu.getStage().setScene(new Scene(new MainMenu().buildMenu()));
		});

		// Add controls to the screen
		controlsContainer.getChildren().add(saveButton);
		root.setBottom(controlsContainer);

		// Add time label
		HBox labelContainer = new HBox();
		labelContainer.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

		timerLabel.setMinWidth(GameMenu.getStage().getWidth());
		timerLabel.setAlignment(Pos.CENTER);
		timerLabel.setFont(TextUtils.getFont(18));
		timerLabel.setPadding(new Insets(INFO_LABEL_PADDING));

		labelContainer.getChildren().add(timerLabel);
		root.setTop(labelContainer);

		root.setRight(buildProgressBar());

		return root;
	}

	/**
	 * Stops the items in the game from running after the game is exited.
	 */
	public void stopGame() {
		// Stop every object that needs stopping currently on the board
		for (GameObject object : levelData.getObjects()) {
			if (object instanceof ObjectStoppable) {
				ObjectStoppable objectToBeStopped = (ObjectStoppable) object;
				objectToBeStopped.stop();
			}
		}

		interactionCheckTimeline.pause();
		gameLabelTimeline.pause();
		winLoseTimeline.pause();
		levelData.setObjects(null);
		GameObject.setBoard(null);
	}

	/**
	 * A method to start the game.
	 */
	public void startGame() {
		// Set the board currently in use to this board
		GameObject.setBoard(this);

		// Update board display
		updateBoardDisplay();

		// Starts checking whether the player has won or lost
		winLoseTimeline = new Timeline(new KeyFrame(Duration.millis(250),
				event -> checkWinLose()));
		winLoseTimeline.setCycleCount(Animation.INDEFINITE);
		winLoseTimeline.play();
	}

	/**
	 * Get the objects currently in play.
	 *
	 * @return the List of objects currently in play.
	 */
	public List<GameObject> getObjects() {
		return this.levelData.getObjects();
	}

	/**
	 * Get the level properties for the level in play.
	 *
	 * @return the LevelProperties for this level.
	 */
	public LevelProperties getLevelProperties() {
		return levelData.getLevelProperties();
	}

	/**
	 * This method builds the GUI for the rats population bar
	 *
	 * @return the GUI of the rats population bar
	 */
	private Pane buildProgressBar() {

		Pane ratPopulation = new Pane();
		double height = tileCanvas.getCanvas().getHeight() / populationToLose;
		for (int i = 0; i < populationToLose; i++) {
			Rectangle cell = new Rectangle(Tile.TILE_SIZE, height);
			cell.setFill(Color.GREY);
			progressBar.add(cell);
			cell.setTranslateY(i * height);
			ratPopulation.getChildren().add(cell);
		}

		return ratPopulation;
	}

	/**
	 * This method updates the rats population bar
	 */
	private void updateProgressBar() {

		for (int i = 0; i < getCurrentPopulation().malePopulation(); i++) {
			progressBar.get(i).setFill(Color.valueOf("#0000FF"));

		}
		for (int i = 0; i < getCurrentPopulation().femalePopulation(); i++) {
			progressBar.get(getCurrentPopulation().malePopulation() + i)
					.setFill(Color.valueOf("#F800B8"));

		}
		for (int i = 0; i < populationToLose - getCurrentPopulation().getTotalPopulation(); i++) {
			progressBar.get(getCurrentPopulation().femalePopulation() + getCurrentPopulation().malePopulation() + i)
					.setFill(Color.GREY);

		}
	}

	/**
	 * A method to update the timer label with the current data.
	 */
	private void updateTimerLabel() {
		LevelProperties levelProperties = levelData.getLevelProperties();
		int expectedTime = levelProperties.getExpectedTime();
		int elapsedTime = levelProperties.getTimeElapsed();
		int score = levelProperties.getScore();
		int populationToLose = levelProperties.getPopulationToLose();

		elapsedTime++;
		levelData.getLevelProperties().setTimeElapsed(elapsedTime);
		timerLabel.setText(String.format(INFORMATION_LABEL_TEXT, elapsedTime,
				expectedTime, score, populationToLose));
	}

	/**
	 * Checks whether the game has been won or lost at the moment the method is
	 * called.
	 */
	private void checkWinLose() {
		LevelProperties levelProperties = levelData.getLevelProperties();
		int populationToLose = levelProperties.getPopulationToLose();
		int currentPopulation = getCurrentPopulation().getTotalPopulation();

		// The player has lost
		if (currentPopulation >= populationToLose) {
			stopGame();
			GameMenu.getStage().setScene(new Scene(new LoseMenu().buildMenu()));
		}

		// The player has won
		if (currentPopulation <= 0) {
			int levelId = levelProperties.getLevelId();

			Player currentPlayer =
					PlayerProfileManager.getCurrentlyLoggedInPlayer();

			// Calculate bonus score
			int elapsedTime = levelProperties.getTimeElapsed();
			int expectedTime = levelProperties.getExpectedTime();

			int bonusScore = expectedTime - elapsedTime;

			if (bonusScore > 0) {
				int score = getScore();
				levelProperties.setScore(score + bonusScore);
			}


			currentPlayer.winGame(levelId, getScore());

			stopGame();
			GameMenu.getStage().setScene(new Scene(new WinMenu().buildMenu()));
		}
	}

	/**
	 * Returns the canvas for this board as a JavaFX Canvas
	 * @return the JavaFX canvas for this board
	 */
	public Canvas getCanvas() {
		return tileCanvas.getCanvas();
	}

	/**
	 * Get the level data for this board
	 * @return the level data for this board
	 */
	public LevelData getLevelData (){
		return levelData;
	}
}
