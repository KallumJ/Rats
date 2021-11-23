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
import objects.Bomb;
import objects.GameObject;
import objects.rats.DeathRat;
import objects.rats.PeacefulRat;
import objects.rats.Rat;
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
    private Timeline tickTimeline; 

    private final static int CANVAS_HEIGHT = 1000; // In pixels
    private final static int CANVAS_WIDTH = 1000;

    public Board(LevelData levelData) {
        LevelProperties levelProperties = levelData.getLevelProperties();

        this.map = levelData.getTileSet();
        this.objects = levelData.getObjects();
        this.pouplationToLose = levelProperties.getPopulationToLose();
        this.expectedFinishTime = levelProperties.getExpectedTime();
        this.canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        
        tickTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> intersactionCheck()));

        // Loop the timeline forever
	tickTimeline.setCycleCount(Animation.INDEFINITE);
        tickTimeline.play();
    }
    
    public void intersactionCheck() {
        for (int i = 0; i < objects.size(); i++) {
            for (int j = 0; j < objects.size(); j++) {

                if ((objects.get(i).getStandingOn().equals(objects.get(j).getStandingOn())) && (!objects.get(i).equals(objects.get(j)))) {

                    if (objects.get(i).getClass().getName().equalsIgnoreCase("objects.rats.PeacefulRat")) {

                        if (objects.get(j).getClass().getName().equalsIgnoreCase("objects.rats.PeacefulRat")) {

                            PeacefulRat firstRat = (PeacefulRat) objects.get(i);
                            PeacefulRat secondRat = (PeacefulRat) objects.get(j);

                            if (firstRat.getGender().equalsIgnoreCase("m")) {

                                firstRat.mate(secondRat);
                            }
                        }
                    } //objects.get(I) instanceof PeacefulRat

                    else if (objects.get(i).getClass().getName().equalsIgnoreCase("objects.rats.DeathRat")) {

                        if ( objects.get(j).getClass().getName().equalsIgnoreCase("objects.rats.PeacefulRat") || 
                             objects.get(j).getClass().getName().equalsIgnoreCase("objects.rats.DeathRat")) {

                            DeathRat firstRat = (DeathRat) objects.get(i);
                            Rat secondRat = (Rat) objects.get(j);
                            firstRat.kill(secondRat);

                        }

                    }
                    else {
                        Bomb bomb = (Bomb) objects.get(i);
                        Rat victomRat = (Rat) objects.get(j);
                        bomb.activation(this, victomRat);
                        
                    }

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

        // draw all objects
        for (GameObject object : objects) {
            gc.drawImage(object.getIcon(), object.getStandingOn().getTopLeftX() * Tile.TILE_SIZE,
                    object.getStandingOn().getTopLeftY() * Tile.TILE_SIZE);
        }
    }

    public void addObject(GameObject objectAdded) {
        objects.add(objectAdded);
        updateBoardDisplay();
    }

    public void removeObject(GameObject objectRemove) {
        
        objects.remove(objectRemove);
        updateBoardDisplay();
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
        // Set the board currently in use to this board
        GameObject.setBoard(this);

        // Update board display
        updateBoardDisplay();
    }

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

    public List<GameObject> getObjects() {
        return objects;
    }
}
