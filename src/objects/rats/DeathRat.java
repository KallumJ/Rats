package objects.rats;

import javafx.scene.image.Image;
import objects.GameObject;
import tile.Direction;
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
 * The class Death rat extends rat
 */ 
public class DeathRat extends Rat {
    
    private int numberOfKills;
    private int killsTarget;
    private Image deathRatImage;
    
	/** 
	 * Death rat
	 * @param standingOn  the standing on
	 * @param speed  the speed
	 * @param directionOfMovement  the direction of movement
	 * @param int  the int
	 * @param killsTarget  the kills target
	 * @return public
	 */
    public DeathRat(Tile standingOn, int speed, Direction directionOfMovement,
            int numberOfKills, int killsTarget) {
        super(standingOn, speed, directionOfMovement);

       
        this.killsTarget = killsTarget;
        this.numberOfKills = numberOfKills;
        
        deathRatImage = new Image("file:resources/deathRat.png");
        super.setIcon(deathRatImage);
    }

    @Override
	/** 
	 * Change icon
	 * @param icon  the icon
	 */
    public void changeIcon(Image icon) {

    }
    
	/**
	 * Kill
	 * @param victim  the victim
	 */
    public void kill (Rat victim) {
        
        GameObject.getBoard().removeObject(victim);
        this.numberOfKills = this.numberOfKills + 1;
        
        if (this.killsTarget == this.numberOfKills) {
           
            GameObject.getBoard().removeObject(this);
        }
        GameObject.getBoard().updateBoardDisplay();
        
    }
	
    /** 
	 * Gets the number of kills
	 * @return the number of kills
	 */
    public int getNumberOfKills () {
        
         return this.numberOfKills;
    }
    
}
