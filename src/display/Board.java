package display;

import java.util.List;
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
import objects.rats.PeacefulRat;
import tile.Tile;

/**
 *
 * @author fahds
 */
public class Board {

    private LevelData levelData;
    private Canvas canvas;
    private Timeline tickTimeline; 
    private int score;

    private final static int CANVAS_HEIGHT = 700; // In pixels
    private final static int CANVAS_WIDTH = 700;
    
    private final static int POINTS_ON_A_RAT = 10;

    public Board(LevelData levelData) {
        this.levelData = levelData;
        this.canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        
        tickTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> interactionCheck()));

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
        
        if (objectRemove instanceof PeacefulRat){
            
            PeacefulRat killedRat = (PeacefulRat) objectRemove;
            this.addPoints(killedRat);
        }
    }
    
    public int getCurrentPouplation () {
        //TODO: implement
        return 0;
    }
    
    public int getScore () {
        //TODO: implement
        return 0;
    }
    
    public void addPoints (PeacefulRat killedRat) {
        
        this.score = score + POINTS_ON_A_RAT;
        
        if (killedRat.isPregnant()){
            
            this.score = score + (killedRat.getNumberOfBabies() * POINTS_ON_A_RAT);
        }
        
    }

    public Pane buildGUI() {
        BorderPane root = new BorderPane();

       root.setCenter(canvas);
//       Inventory inventory = new Inventory (levelData);
//       root.setRight(inventory.buildInventoryGUI());

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
