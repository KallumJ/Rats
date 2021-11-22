package display;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import level.LevelData;
import level.LevelProperties;
import objects.GameObject;
import sun.awt.image.ImageAccessException;
import tile.Tile;
import tile.TileSet;

/**
 *
 * @author fahds
 */
public class Board {

    private List<GameObject> objects;
    private TileSet map;
    private int pouplationToLose;
    private int score;
    private int currentPouplation;
    private int expectedFinishTime;
    private int startTime;
    private int pointsOnEachRat;
    private Canvas canvas;

    private final static int CANVAS_HEIGHT = 1000; // In pixels
    private final static int CANVAS_WIDTH = 1000;

    public Board(LevelData levelData) {
        LevelProperties levelProperties = levelData.getLevelProperties();

        this.map = levelData.getTileSet();
        this.objects = levelData.getObjects();
        this.pouplationToLose = levelProperties.getPopulationToLose();
        this.expectedFinishTime = levelProperties.getExpectedTime();
        this.canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    }
    
    public void showObjects () {
        
        // Get the Graphic Context of the canvas. This is what we draw on.
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Set the background to white.
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // draw all objects
        for (GameObject object : objects) {
            gc.drawImage(object.getIcon(), object.getStandingOn().getTopLeftX(),
                         object.getStandingOn().getTopLeftY());
        }

    }

    public void addObject(GameObject objectAdded) {
        objects.add(objectAdded);
        showObjects ();
    }

    public void removeObject(GameObject objectRemove) {
        
        objects.remove(objectRemove);
        showObjects ();
    }
    
    public int getCurrentPouplation () {
        
        return this.currentPouplation;
    }
    
    public int getScore () {
        
        return this.score;
    }

    public Pane buildGUI() {
        BorderPane root = new BorderPane();

        root.setCenter(canvas);

        return root;
    }

    public void startGame() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Set the background to white.
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        displayTiles(gc);
    }

    private void displayTiles(GraphicsContext gc) {
        for (Tile tile : map.getAllTiles()) {
            Image tileImage;
            switch (tile.getTileType()) {
                case GRASS:
                    tileImage = new Image("file:grass.jpg");
                    break;
                case PATH:
                    tileImage = new Image("file:path.jpg");
                    break;
                case TUNNEL:
                    tileImage = new Image("file:tunnel.jpg");
                    break;
                default:
                    throw new RuntimeException("Image for tile type" + tile.getTileType() + " not found");
            }

            gc.drawImage(tileImage, tile.getTopLeftX() * 50, tile.getTopLeftY() * 50);
        }
    }

    public List<GameObject> getObjects() {
        return objects;
    }
}
