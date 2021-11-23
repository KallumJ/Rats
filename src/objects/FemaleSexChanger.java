package objects;

import display.Board;
import javafx.scene.image.Image;
import objects.rats.PeacefulRat;
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
 * The class Female sex changer extends item
 */ 
public class FemaleSexChanger extends Item{
	/** 
	* Female sex changer
	* @param standingOn  the standing on
	* @return public
	*/
    public FemaleSexChanger(Tile standingOn) {
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
        pRat.setGender("Female");
        
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
