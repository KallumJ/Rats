
import javafx.scene.image.Image;

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
    

    public DeathRat(Tile standingOn, Image icon, int speed, Direction directionOfMovement,
                   int numberOfKills, int killsTarget) {
        super(standingOn, icon, speed, directionOfMovement);
        
        this.killsTarget = killsTarget;
        this.numberOfKills = numberOfKills;
    }

    @Override
    public void changeIcon(Image icon) {

    }
    
    public void kill (Rat victom, Board board) {
        
        board.removeObject(victom);
        this.numberOfKills = this.numberOfKills + 1;
        
        if (this.killsTarget == this.numberOfKills) {
           
            board.removeObject(this);
        }
        board.showObjects();
        
    }
    
    public int getNumberOfKills () {
        
         return this.numberOfKills;
    }
    
}
