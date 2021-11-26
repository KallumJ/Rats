package display;

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
import tile.Tile;
import tile.TileSet;

/**
 *
 * @author fahds
 */
 
 /**
 * The class Board
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
	/** 
	* Constructing Board with level data 
	* @param levelData  the level data
	*/
    public Board(LevelData levelData) {
        LevelProperties levelProperties = levelData.getLevelProperties();

        this.map = levelData.getTileSet();
        this.objects = levelData.getObjects();
        this.pouplationToLose = levelProperties.getPopulationToLose();
        this.expectedFinishTime = levelProperties.getExpectedTime();
        this.canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    }
    
	/**
	* Update board display
	*/
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
	
	/**
	* Display objects
	*/
    private void displayObjects() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // draw all objects
        for (GameObject object : objects) {
            gc.drawImage(object.getIcon(), object.getStandingOn().getTopLeftX() * Tile.TILE_SIZE,
                    object.getStandingOn().getTopLeftY() * Tile.TILE_SIZE);
        }
    }
	
	/** 
	* Add object and the board display will be updated 
	* with added object
	* @param objectAdded  the object added
	*/
    public void addObject(GameObject objectAdded) {
        objects.add(objectAdded);
        updateBoardDisplay();
    }
	
	/** 
	* Remove object and the board display will be updated 
	* with removed object
	* @param objectRemove  the object remove
	*/
    public void removeObject(GameObject objectRemove) {
        
        objects.remove(objectRemove);
        updateBoardDisplay();
    }
    
	/** 
	* Gets the current population
	* @return the current pouplation
	*/
    public int getCurrentPouplation () {
        
        return this.currentPouplation;
    }
    
	/** 
	* Gets the score
	* @return the score
	*/
    public int getScore () {
        
        return this.score;
    }

	/** 
	* Build GUI
	* @return Pane
	*/
    public Pane buildGUI() {
        BorderPane root = new BorderPane();

        root.setCenter(canvas);

        return root;
    }
	/** 
	* Start game and updates the board display
	*/
    public void startGame() {
        // Set the board currently in use to this board
        GameObject.setBoard(this);

        // Update board display
        updateBoardDisplay();
    }
	/** 
	* Display tiles
	*/
    private void displayTiles() {
        // Get the Graphic Context of the canvas. This is what we draw on.
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Decide what image to display for this tile
        for (Tile tile : map.getAllTiles()) {
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
	
	/** 
	* Gets the objects
	* @return the objects
	*/
    public List<GameObject> getObjects() {
        return objects;
    }
}
