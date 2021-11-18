package objects;

import display.Board;
import javafx.scene.image.Image;
import tile.Tile;


/**
 *
 * @author fahds
 */
public abstract class Item extends GameObject{

    protected Item(Tile standingOn) {
        super(standingOn);
    }

    
    public abstract void activation(Board board, Rat rat);
}
