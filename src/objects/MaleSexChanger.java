package objects;

import display.Board;
import javafx.scene.image.Image;
import objects.rats.PeacefulRat;
import objects.rats.Rat;
import tile.Tile;


/**
 *
 * @author fahds
 */
public class MaleSexChanger extends Item{
    
    public MaleSexChanger(Tile standingOn) {
        super(standingOn);
    }

    @Override
    public void activation(Board board, Rat rat) {
        
        PeacefulRat pRat = (PeacefulRat) rat;
        pRat.setGender("Male");
        
        board.removeObject(this); 
        board.updateBoardDisplay();
    }

    @Override
    public void changeIcon(Image icon) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
