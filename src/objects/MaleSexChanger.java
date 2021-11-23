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
public class MaleSexChanger extends GameObject{
    /**  
	 * Male sex changer
	 * @param standingOn  the standing on
	 * @return public
	 */

    public MaleSexChanger(Tile standingOn) {
        super(standingOn);
    }

    
	/** 
	 * Activation
	 * @param rat  the rat
	 */
    public void activation(PeacefulRat rat) {
        
        rat.setGender("m");
        
        GameObject.getBoard().removeObject(this); 
        GameObject.getBoard().updateBoardDisplay();
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
