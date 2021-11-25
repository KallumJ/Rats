package objects;

import display.Board;
import javafx.scene.image.Image;
import objects.rats.Rat;
import tile.Tile;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fahds
 */
/**
 * The class Poison extends item
 */ 
public class Poison extends GameObject{

	/** 
	 * Poison
	 * @param standingOn  the standing on
	 * @return public
	 */
    public Poison(Tile standingOn) {
        super(standingOn);
    }

    
	/** 
	 * Activation
	 * @param rat  the rat
	 */
    public void activation(Rat rat) {
        
        GameObject.getBoard().removeObject(rat);
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
