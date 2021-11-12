
import javafx.scene.image.Image;


/**
 *
 * @author fahds
 */
public abstract class Rat extends Object{
    
    // movement speed of the Rat
    private int speed; 
    
    private Direction directionOfMovement;

    public Rat(Tile standingOn, Image icon) {
        super(standingOn, icon);
    }
    
}
