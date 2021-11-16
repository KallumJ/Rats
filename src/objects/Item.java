package objects;

import javafx.scene.image.Image;
import tile.Tile;


/**
 *
 * @author fahds
 */
public abstract class Item extends Object{

    protected Item(Tile standingOn, Image icon) {
        super(standingOn, icon);
    }

    
    public abstract void activition (Board board, Rat rat);   
}
