package display;

import display.inventory.Inventory;

import java.util.List;

import display.menus.GameMenu;
import display.menus.MainMenu;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import level.LevelData;
import level.LevelProperties;
import level.LevelSaveHandler;
import objects.*;
import objects.rats.PeacefulRat;
import players.PlayerProfileManager;
import tile.Tile;

/**
 *
 * @author fahds
 */
public class Board {

    private static final int POINTS_ON_A_RAT = 10;
    private static final int INTERACTION_CHECK_INTERVAL = 250; // In ms
    private static final String SAVED_BUTTON_LABEL = "Save and exit";
    private static final int CONTROLS_MARGIN = 5; // in pixels

    private LevelData levelData;
    private Canvas canvas;
    private Timeline tickTimeline;
    private int score;

    public Board(LevelData levelData) {
        this.levelData = levelData;

        // Find the width and the height of the canvas, in pixels for this level
        int width = levelData.getLevelProperties().getLevelWidth() * Tile.TILE_SIZE; // in pixels
        int height = levelData.getLevelProperties().getLevelHeight() * Tile.TILE_SIZE;

        this.canvas = new Canvas(width, height);

        tickTimeline = new Timeline(new KeyFrame(Duration.millis(INTERACTION_CHECK_INTERVAL), event -> interactionCheck()));

        // Loop the timeline forever
        tickTimeline.setCycleCount(Animation.INDEFINITE);
        tickTimeline.play();
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

    private void displayObjects() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        List<GameObject> objects = levelData.getObjects();

        // draw all objects
        for (GameObject object : objects) {
            gc.drawImage(object.getIcon(), object.getStandingOn().getTopLeftX() * Tile.TILE_SIZE,
                    object.getStandingOn().getTopLeftY() * Tile.TILE_SIZE);
        }
    }

    public void addObject(GameObject objectAdded) {
        List<GameObject> objects = levelData.getObjects();

        objects.add(objectAdded);
        updateBoardDisplay();
    }

    public void removeObject(GameObject objectRemove) {
        List<GameObject> objects = levelData.getObjects();

        objects.remove(objectRemove);
        updateBoardDisplay();
        if (objectRemove instanceof PeacefulRat) {

            PeacefulRat killedRat = (PeacefulRat) objectRemove;
            this.addPoints(killedRat);
        }
    }

    public void addPoints(PeacefulRat killedRat) {

        this.score = score + POINTS_ON_A_RAT;

        if (killedRat.isPregnant()) {

            this.score = score + (killedRat.getNumberOfBabies() * POINTS_ON_A_RAT);
        }

    }

    public int getCurrentPouplation() {
        //TODO: implement
        return 0;
    }

    public int getScore() {

        return this.score;
    }

    public Pane buildGUI() {
        BorderPane root = new BorderPane();

        root.setCenter(canvas);

        Inventory inventory = new Inventory(levelData);
        root.setRight(inventory.buildInventoryGUI());

        HBox controlsContainer = new HBox();
        controlsContainer.setPadding(new Insets(CONTROLS_MARGIN, CONTROLS_MARGIN, CONTROLS_MARGIN, CONTROLS_MARGIN));

        Button saveButton = new Button(SAVED_BUTTON_LABEL);

        saveButton.setOnMousePressed(event -> {
            LevelSaveHandler.saveLevel(levelData, PlayerProfileManager.getCurrentlyLoggedInPlayer());
            GameMenu.stage.setScene(new Scene(new MainMenu().buildMenu()));
        });

        controlsContainer.getChildren().add(saveButton);

        root.setBottom(controlsContainer);

        return root;
    }

    public void startGame() {
        // Set the board currently in use to this board
        GameObject.setBoard(this);

        // Update board display
        updateBoardDisplay();
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

    public List<GameObject> getObjects() {
        return this.levelData.getObjects();
    }

    public LevelData getLevelData() {
        return levelData;
    }

    public LevelProperties getLevelProperties() {
        return levelData.getLevelProperties();
    }
}
