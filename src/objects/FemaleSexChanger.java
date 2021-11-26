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
public class FemaleSexChanger extends GameObject{
    
     private Image femaleSexChangerImage;
	/** 
	* Female sex changer
	* @param standingOn  the standing on
	* @return public
	*/
    public FemaleSexChanger(Tile standingOn) {
        super(standingOn);
        
        femaleSexChangerImage = new Image("file:resources/femaleChange.png");
        super.setIcon(femaleSexChangerImage);
    }

    
	/** 
	* Activation
	* @param rat  the rat
	*/
    public void activationOfFemaleSexChanger(PeacefulRat rat) {
        
        rat.setGender("f");
        
        GameObject.getBoard().removeObject(this); 
        GameObject.getBoard().updateBoardDisplay();
    }

}
