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
    
    private Image poisonImage;

	/** 
	 * Poison
	 * @param standingOn  the standing on
	 * @return public
	 */
    public Poison(Tile standingOn) {
        super(standingOn);
        
        poisonImage = new Image("file:resources/poison.png");
        super.setIcon(poisonImage);
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

    
}
