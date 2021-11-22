package objects;

import display.Board;
import objects.rats.Rat;
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
