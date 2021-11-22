package objects.rats;

import javafx.scene.image.Image;
import objects.GameObject;
import objects.Rat;
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
public class DeathRat extends Rat {
    
    private int numberOfKills;
    private int killsTarget;
     private Image deathRatImage;
    

    public DeathRat(Tile standingOn, int speed, Direction directionOfMovement,
            int numberOfKills, int killsTarget) {
        super(standingOn, speed, directionOfMovement);

       
        this.killsTarget = killsTarget;
        this.numberOfKills = numberOfKills;
        
        deathRatImage = new Image("resources/deathRat.png");
        super.setIcon(deathRatImage);
    }

    @Override
    public void changeIcon(Image icon) {

    }
    
    public void kill (Rat victim) {
        
        GameObject.getBoard().removeObject(victim);
        this.numberOfKills = this.numberOfKills + 1;
        
        if (this.killsTarget == this.numberOfKills) {
           
            GameObject.getBoard().removeObject(this);
        }
        GameObject.getBoard().updateBoardDisplay();
        
    }
    
    public int getNumberOfKills () {
        
         return this.numberOfKills;
    }
    
}
