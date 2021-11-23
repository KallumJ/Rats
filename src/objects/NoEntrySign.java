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
 * The class Number entry sign extends item
 */ 
public class NoEntrySign extends GameObject {
    
    private int damageDone;
    private int durability;

	/** 
	 * Number entry sign
	 * @param standingOn  the standing on
	 * @param icon  the icon
	 * @param damageDone  the damage done
	 * @param durability  the durability
	 * @return public
	 */
    public NoEntrySign(Tile standingOn, Image icon, int damageDone, int durability) {
        super(standingOn);
    }

    /** 
	 * Activation
	 * @param rat  the rat
	 */
    public void activation(Rat rat) {
        
        rat.setDirectionOfMovement(rat.turnAround(rat.getDirectionOfMovement()));
        
        this.damageDone = damageDone+1;
        
        if (damageDone >= durability) {
            
            GameObject.getBoard().removeObject(this);
            
        }
        GameObject.getBoard().updateBoardDisplay();
        
        
    }
    /** 
	 * Gets the damage done
	 * @return the damage done
	 */
    public int getDamageDone () {
        
        return this.damageDone;
    }

    @Override
	/** 
	 * Change icon
	 * @param icon  the icon
	 */
    public void changeIcon(Image icon) {
       
    }
    
}
