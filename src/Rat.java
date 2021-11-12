
import javafx.scene.image.Image;


/**
 *
 * @author fahds
 */
public abstract class Rat extends Object{
    
    // movement speed of the Rat
    private int speed; 
    
    private Direction directionOfMovement;

    public Rat(Tile standingOn, Image icon, int speed, Direction directionOfMovement) {
        
        super(standingOn, icon);
        this.speed = speed;
        this.directionOfMovement = directionOfMovement;        
    }
    
    public int getSpeed (){
        
        return this.speed;
    }  
    
    public void setSpeed (int speed) {
        
        this.speed = speed;
    }
    
    public Direction getDirectionOfMovement () {
        
        return this.directionOfMovement;
    }
}
