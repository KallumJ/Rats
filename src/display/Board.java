package display;

import java.util.List;

import display.inventory.Inventory;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import level.LevelData;
import level.LevelProperties;
import objects.*;
import tile.Tile;

/**
 *
 * @author fahds
 */
public class Board {

    private LevelData levelData;
    private Canvas canvas;
    private Timeline tickTimeline;

    private final static int POINTS_ON_A_RAT = 10;
    private final static int INTERACTION_CHECK_INTERVAL = 250; // In ms

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
    }

    public int getCurrentPouplation() {
        //TODO: implement
        return 0;
    }

    public int getScore() {
        //TODO: implement
        return 0;
    }

    public Pane buildGUI() {
        BorderPane root = new BorderPane();

        root.setCenter(canvas);

        Inventory inventory = new Inventory(levelData);
        root.setRight(inventory.buildInventoryGUI());

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
