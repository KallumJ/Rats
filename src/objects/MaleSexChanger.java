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
/**
 * The class Male sex changer extends item
 */ 
public class MaleSexChanger extends Item{
    /**  
	 * Male sex changer
	 * @param standingOn  the standing on
	 * @return public
	 */

    public MaleSexChanger(Tile standingOn) {
        super(standingOn);
    }

    @Override
	/** 
	 * Activation
	 * @param board  the board
	 * @param rat  the rat
	 */
    public void activation(Board board, Rat rat) {
        
        PeacefulRat pRat = (PeacefulRat) rat;
        pRat.setGender("Male");
        
        board.removeObject(this); 
        board.updateBoardDisplay();
    }

    @Override
	/**
	 * Change icon
	 * @param icon  the icon
	 */
    public void changeIcon(Image icon) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
