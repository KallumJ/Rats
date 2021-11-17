package display;

import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import objects.Object;
import tile.TileSet;
import javafx.scene.paint.Color;

/**
 *
 * @author fahds
 */
public class Board {

    private ArrayList<Object> objects;
    private TileSet map;
    private int pouplationToLose;
    private int score;
    private int currentPouplation;
    private int expectedFinishTime;
    private int startTime;
    private int pointsOnEachRat;
    private Canvas canvas;
    private final ArrayList<Object> objectsOnBoard;

    public Board(TileSet map, ArrayList<Object> objects, int pouplationToLose, int pointsOnEachRat, int expectedFinishTime) {

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
        for (Object object : objects) {
            gc.drawImage(object.getIcon(), object.getStandingOn().getCenterX(),
                         object.getStandingOn().getCenterY());
        }

    }

    public void addObject(Object objectAdded) {
        
        objects.add(objectAdded);
        showObjects ();

    }

    public void removeObject(Object objectRemove) {
        
        objects.remove(objectRemove);
        showObjects ();

    }
    
    public int getCurrentPouplation () {
        
        return this.currentPouplation;
    }
    
    public int getScore () {
        
        return this.score;
    }

    public ArrayList<Object> getObjects() {

        return this.objects;
    }

}
