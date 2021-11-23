package objects;

import display.Board;
import objects.rats.Rat;
import tile.Tile;


/**
 *
 * @author fahds
 */
 /**
  * The class Abstract item extends game object
  */ 
public abstract class Item extends GameObject{

    protected Item(Tile standingOn) {
        super(standingOn);
    }

    
    public abstract void activation(Board board, Rat rat);
}
