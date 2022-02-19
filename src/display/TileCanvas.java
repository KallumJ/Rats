package display;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import level.LevelData;
import level.LevelProperties;
import objects.GameObject;
import tile.Tile;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A class to draw tiles and objects to a JavaFX canvas
 */
public class TileCanvas {
    private static final String IMAGE_NOT_FOUND = "Image for tile type %s not "
            + "found";

    private final Canvas canvas;
    private final LevelData levelData;

    
    long start = System.currentTimeMillis();
    long elapsedTime = System.currentTimeMillis() - start;
    long elapsedSeconds = elapsedTime / 1000;


    /**
     * Constructs a TileCanvas object
     * @param levelData the level data to draw a canvas for
     */
    public TileCanvas(LevelData levelData) {

        this.levelData = levelData;
        LevelProperties levelProperties = levelData.getLevelProperties();

        // Find the width and the height of the canvas, in pixels for this
        // level
        int width = levelProperties.getLevelWidth() * Tile.TILE_SIZE; // in pixels
        int height = levelProperties.getLevelHeight() * Tile.TILE_SIZE;

        this.canvas = new Canvas(width, height);

        System.out.println(start);
      
        //add a timer here and it will work

        Timer myTimer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                elapsedSeconds++;
            }
        };

        myTimer.scheduleAtFixedRate(task, 1000, 1000);

        updateBoardDisplay();
    }

    /**
     * Returns the TileCanvas as a JavaFX Canvas
     * @return a JavaFX Canvas
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * A method to update the canvas that represents the board.
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
     * Display the objects on the screens
     */
    private void displayObjects() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        List<GameObject> objects = levelData.getObjects();

        // draw all objects
        for (GameObject object : objects) {
            double x = object.getStandingOn().getX() * Tile.TILE_SIZE;
            double y = object.getStandingOn().getY() * Tile.TILE_SIZE;
            gc.drawImage(object.getIcon(), x, y);
        }
    }

/**
     * Displays the tiles in the level on the board
     */
    private void displayTiles() {
        // Get the Graphic Context of the canvas. This is what we draw on.
        GraphicsContext gc = canvas.getGraphicsContext2D();

        List<Tile> tiles = levelData.getTileSet().getAllTiles();

        // Decide what image to display for this tile
        for (Tile tile : tiles) {
            Image tileImage = null;
            switch (tile.getTileType()) {
                case GRASS:
                    if (elapsedSeconds >= 0 && elapsedSeconds <= 60) {
                        tileImage = new Image("file:resources/grass.jpg");
                    } else if (elapsedSeconds >= 60 && elapsedSeconds <= 120) {
                        tileImage = new Image("file:resources/grassnight.png");
                    } else {
                        tileImage = new Image("file:resources/grass.jpg");
                    }
                    break;
                case PATH:
                    if (elapsedSeconds >= 0 && elapsedSeconds <= 60) {
                        tileImage = new Image("file:resources/path.jpg");
                    } else if (elapsedSeconds >= 60 && elapsedSeconds <= 120) {
                        tileImage = new Image("file:resources/nightpath.png");
                    } else {
                        tileImage = new Image("file:resources/path.jpg");
                    }
                    break;
                case TUNNEL:
                    if (elapsedSeconds >= 0 && elapsedSeconds <= 60) {
                        tileImage = new Image("file:resources/tunnelnew.png");
                    } else if (elapsedSeconds >= 60 && elapsedSeconds <= 120) {
                        tileImage = new Image("file:resources/tunnelnew.png"); 
                    } else {
                        tileImage = new Image("file:resources/tunnelnew.png");
                    }
                    break;
                default:
                    throw new RuntimeException(String.format(IMAGE_NOT_FOUND,
                            tile.getTileType()));
            }

            double x = tile.getX() * Tile.TILE_SIZE;
            double y = tile.getY() * Tile.TILE_SIZE;
            gc.drawImage(tileImage, x, y);
        }
    }
}
