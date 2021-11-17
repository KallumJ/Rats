
import javafx.scene.image.Image;


/**
 *
 * @author fahds
 */
public abstract class Item extends Object{

    protected Item(Tile standingOn) {
        super(standingOn);
    }

    
    public abstract void activition (Board board, Rat rat);   
}
