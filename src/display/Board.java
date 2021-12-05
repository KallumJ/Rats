package display;

import display.inventory.Inventory;
import display.menus.GameMenu;
import display.menus.LoseMenu;
import display.menus.MainMenu;
import display.menus.WinMenu;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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

import java.util.List;

/**
 * @author fahds
 */
public class Board {

    private static final int POINTS_ON_A_RAT = 10;
    private static final int INTERACTION_CHECK_INTERVAL = 250; // In ms
    private static final String SAVED_BUTTON_LABEL = "Save and exit";
    private static final int CONTROLS_MARGIN = 5; // in pixels
    private static final String INFORMATION_LABEL_TEXT =
            "Time elapsed: %d seconds. Expected time: %d seconds. Score %d. Population to lose %d.";
    private static final int INFO_LABEL_PADDING = 5; // in pixels

    private final LevelData levelData;
    private final Label timerLabel;
    private final Canvas canvas;
    private final Inventory inventory;
    private final Timeline interactionCheckTimeline;
    private final Timeline gameLabelTimeline;
    private Timeline winLoseTimeline;

    public Board(LevelData levelData) {
        this.levelData = levelData;
        LevelProperties levelProperties = levelData.getLevelProperties();

        // Find the width and the height of the canvas, in pixels for this level
        int width =
                levelProperties.getLevelWidth() * Tile.TILE_SIZE; // in pixels
        int height =
                levelProperties.getLevelHeight() * Tile.TILE_SIZE;

        this.canvas = new Canvas(width, height);

        interactionCheckTimeline = new Timeline(
                new KeyFrame(Duration.millis(INTERACTION_CHECK_INTERVAL),
                        event -> interactionCheck())
        );

        // Loop the interaction check forever
        interactionCheckTimeline.setCycleCount(Animation.INDEFINITE);
        interactionCheckTimeline.play();

        int timeElapsed = levelProperties.getTimeElapsed();
        int expectedTime = levelProperties.getExpectedTime();
        int score = levelProperties.getScore();
        int populationToLose = levelProperties.getPopulationToLose();

        this.timerLabel = new Label(String.format(
                INFORMATION_LABEL_TEXT, timeElapsed, expectedTime, score,
                populationToLose
        ));
        gameLabelTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        event -> updateTimerLabel())
        );
        gameLabelTimeline.setCycleCount(Animation.INDEFINITE);
        gameLabelTimeline.play();

        this.inventory = new Inventory(levelData);


    }

    public void interactionCheck() {
        List<GameObject> objects = levelData.getObjects();

        for (int i = 0; i < objects.size(); i++) {
            GameObject firstObject = objects.get(i);

            for (int j = 0; j < objects.size(); j++) {
                GameObject secondObject = objects.get(j);

                // If we are comparing 2 objects that are on the same tile, and are not the same object
                if (firstObject.getStandingOn().equals(secondObject.getStandingOn()) && !(firstObject.equals(secondObject))) {

                    // Check for every interaction case
                    ObjectInteractionChecker.checkRatsMating(firstObject, secondObject);
                    ObjectInteractionChecker.checkDeathRat(firstObject, secondObject);
                    ObjectInteractionChecker.checkBomb(firstObject, secondObject);
                    ObjectInteractionChecker.checkNoEntrySign(firstObject, secondObject);
                    ObjectInteractionChecker.checkFemaleSexChanger(firstObject, secondObject);
                    ObjectInteractionChecker.checkMaleSexChanger(firstObject, secondObject);
                    ObjectInteractionChecker.checkPoison(firstObject, secondObject);
                    ObjectInteractionChecker.checkSterilisation(firstObject, secondObject);
                    ObjectInteractionChecker.checkSterilisationEffect(firstObject, secondObject);
                    ObjectInteractionChecker.checkGas(firstObject, secondObject);
                    ObjectInteractionChecker.checkGasEffect(firstObject, secondObject);
                }
            }
        }
    }

    /**
     * A method to get the canvas of the board
     *
     * @return the Canvas for the board
     */
    public Canvas getCanvas() {
        return canvas;
    }

    public void updateBoardDisplay() {

        // Get the Graphic Context of the canvas. This is what we draw on.
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Set the background to white.
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Display tiles on screen
        displayTiles();

        // Display the objects on screen
        displayObjects();
    }

    public void addObject(GameObject objectAdded) {
        List<GameObject> objects = levelData.getObjects();

        objects.add(objectAdded);
        updateBoardDisplay();
    }

    public void removeObject(GameObject objectRemove) {
        if (objectRemove instanceof PeacefulRat) {
            PeacefulRat killedRat = (PeacefulRat) objectRemove;
            this.addPoints(killedRat);
        }

        if (objectRemove instanceof ObjectStoppable) {
            ObjectStoppable stoppableObj = (ObjectStoppable) objectRemove;
            stoppableObj.stop();
        }

        List<GameObject> objects = levelData.getObjects();

        objects.remove(objectRemove);
        updateBoardDisplay();

    }

    public void addPoints(PeacefulRat killedRat) {
        int score = levelData.getLevelProperties().getScore();
        score += POINTS_ON_A_RAT;
        if (killedRat.isPregnant()) {
            score = score + (killedRat.getNumberOfBabies() * POINTS_ON_A_RAT);
        }

        levelData.getLevelProperties().setScore(score);
    }

    public RatPopulation getCurrentPopulation() {
        return new RatPopulation(levelData);
    }

    public int getScore() {
        return levelData.getLevelProperties().getScore();
    }

    public Pane buildGUI() {
        BorderPane root = new BorderPane();

        // Place game board on screen
        root.setLeft(canvas);

        // Add inventory
        root.setCenter(inventory.buildInventoryGUI());

        // Add save button
        HBox controlsContainer = new HBox();
        controlsContainer.setPadding(new Insets(CONTROLS_MARGIN));
        controlsContainer.setBackground(
                new Background
                        (new BackgroundFill(
                                Color.LIGHTGRAY,
                                CornerRadii.EMPTY,
                                Insets.EMPTY
                        )));
        controlsContainer.setAlignment(Pos.CENTER);

        Button saveButton = new Button(SAVED_BUTTON_LABEL);

        saveButton.setMinWidth(GameMenu.getStage().getWidth());
        saveButton.setAlignment(Pos.CENTER);
        saveButton.setOnMousePressed(event -> {
            levelData.setInventory(inventory.getItemsInInventory());
            LevelSaveHandler.saveLevel(levelData,
                    PlayerProfileManager.getCurrentlyLoggedInPlayer());
            this.stopGame();
            GameMenu.getStage().setScene(new Scene(new MainMenu().buildMenu()));
        });

        controlsContainer.getChildren().add(saveButton);
        root.setBottom(controlsContainer);

        // Add time label
        HBox labelContainer = new HBox();
        labelContainer.setBackground(
                new Background
                        (new BackgroundFill(
                                Color.LIGHTGRAY,
                                CornerRadii.EMPTY,
                                Insets.EMPTY
                        )));

        timerLabel.setMinWidth(GameMenu.getStage().getWidth());
        timerLabel.setAlignment(Pos.CENTER);
        timerLabel.setFont(
                Font.font(GameMenu.DEFAULT_FONT, 18)
        );
        timerLabel.setPadding(new Insets(INFO_LABEL_PADDING));

        labelContainer.getChildren().add(timerLabel);
        root.setTop(labelContainer);

        //root.setRight(callMethod);

        return root;
    }

    /**
     * Stops the items in the game from running after the game is exited
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

    public void startGame() {
        // Set the board currently in use to this board
        GameObject.setBoard(this);

        // Update board display
        updateBoardDisplay();

        winLoseTimeline = new Timeline(new KeyFrame(Duration.millis(250), event -> checkWinLose()));
        winLoseTimeline.setCycleCount(Animation.INDEFINITE);
        winLoseTimeline.play();
    }

    public List<GameObject> getObjects() {
        return this.levelData.getObjects();
    }

    public LevelProperties getLevelProperties() {
        return levelData.getLevelProperties();
    }

    private void updateTimerLabel() {
        LevelProperties levelProperties = levelData.getLevelProperties();
        int expectedTime = levelProperties.getExpectedTime();
        int elapsedTime = levelProperties.getTimeElapsed();
        int score = levelProperties.getScore();
        int populationToLose = levelProperties.getPopulationToLose();

        elapsedTime++;
        levelData.getLevelProperties().setTimeElapsed(elapsedTime);
        timerLabel.setText(
                String.format(INFORMATION_LABEL_TEXT, elapsedTime, expectedTime,
                        score, populationToLose)
        );
    }

    private void displayObjects() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        List<GameObject> objects = levelData.getObjects();

        // draw all objects
        for (GameObject object : objects) {
            gc.drawImage(object.getIcon(), object.getStandingOn().getTopLeftX() * Tile.TILE_SIZE,
                    object.getStandingOn().getTopLeftY() * Tile.TILE_SIZE);
        }
    }

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

            Player currentPlayer = PlayerProfileManager.getCurrentlyLoggedInPlayer();
            currentPlayer.addScoreIfHighest(levelId, getScore());
            currentPlayer.setMaxLevel(levelId + 1);

            stopGame();
            GameMenu.getStage().setScene(new Scene(new WinMenu().buildMenu()));
        }
    }

    private void displayTiles() {
        // Get the Graphic Context of the canvas. This is what we draw on.
        GraphicsContext gc = canvas.getGraphicsContext2D();

        List<Tile> tiles = levelData.getTileSet().getAllTiles();

        // Decide what image to display for this tile
        for (Tile tile : tiles) {
            Image tileImage;
            switch (tile.getTileType()) {
                case GRASS:
                    tileImage = new Image("file:resources/grass.jpg");
                    break;
                case PATH:
                    tileImage = new Image("file:resources/path.jpg");
                    break;
                case TUNNEL:
                    tileImage = new Image("file:resources/tunnel.jpg");
                    break;
                default:
                    throw new RuntimeException("Image for tile type" + tile.getTileType() + " not found");
            }

            gc.drawImage(tileImage, tile.getTopLeftX() * Tile.TILE_SIZE, tile.getTopLeftY() * Tile.TILE_SIZE);
        }
    }
}
