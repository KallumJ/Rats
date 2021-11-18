package display;

import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.GameObject;
import tile.TileSet;

/**
 *
 * @author fahds
 */
public class Board {

    private ArrayList<GameObject> objects;
    private TileSet map;
    private int pouplationToLose;
    private int score;
    private int currentPouplation;
    private int expectedFinishTime;
    private int startTime;
    private int pointsOnEachRat;
    private Canvas canvas;
    private final ArrayList<GameObject> objectsOnBoard;

    public Board(TileSet map, ArrayList<GameObject> objects, int pouplationToLose, int pointsOnEachRat, int expectedFinishTime) {

        this.map = map;
        this.objects = objects;
        this.pouplationToLose = pouplationToLose;
        this.pointsOnEachRat = pointsOnEachRat;
        this.expectedFinishTime = expectedFinishTime;
        this.objectsOnBoard = new ArrayList<>();

        showObjects ();

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

    public ArrayList<GameObject> getObjects() {

        return this.objects;
    }

}
