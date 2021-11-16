
import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;


/**
 *
 * @author fahds
 */
public abstract class Rat extends Object{
    
    // movement speed of the Rat
    private int speed; 
    
    private Timeline tickTimeline; 
    
    private Direction directionOfMovement;

    protected Rat(Tile standingOn, Image icon, int speed, Direction directionOfMovement) {
        
        super(standingOn, icon);
        this.speed = speed;
        this.directionOfMovement = directionOfMovement;   
        
        tickTimeline = new Timeline(new KeyFrame(Duration.millis(this.speed), event -> move()));
        
        // Loop the timeline forever
	tickTimeline.setCycleCount(Animation.INDEFINITE);
        tickTimeline.play();
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
    
    public void setDirectionOfMovement (Direction directionOfMovement) {
        
        this.directionOfMovement = directionOfMovement;
    }
    
    public void move () {
        
        boolean isLeftTurnPossible = (super.getStandingOn().getAdjecentTile(turnLeft(
                                       directionOfMovement)) .isTraverable());
        
        boolean isRightTurnPossible = (super.getStandingOn().getAdjecentTile(turnRight(
                                        directionOfMovement)).isTraverable());
        
        boolean turningAroundPossible = (super.getStandingOn().getAdjecentTile(turnAround(
                                          directionOfMovement)).isTraverable());
        
        
        //moves forword if possible 
        if (super.getStandingOn().getAdjecentTile(directionOfMovement).isTraverable()) {
            
            super.standOn(super.getStandingOn().getAdjecentTile(directionOfMovement));
        }
        //randomly turning either left or right in case of junction 
        else if (isLeftTurnPossible &&  isRightTurnPossible) {
            
            // generates either 1 or 2
            Random random = new Random(); 
            int decision = random.nextInt(2);
            
            // turn left when decsion equals 1
            if (decision == 1) {
                
                super.standOn(super.getStandingOn().getAdjecentTile(turnLeft(directionOfMovement)));
                this.directionOfMovement = turnLeft(directionOfMovement);  
            }
            // turn right when decsion equals 2
            else {
                
            super.standOn(super.getStandingOn().getAdjecentTile(turnRight(directionOfMovement)));
            this.directionOfMovement = turnRight(directionOfMovement);                
            }
        }
        // turns left
        else if (isLeftTurnPossible) {
            
            super.standOn(super.getStandingOn().getAdjecentTile(turnLeft(directionOfMovement)));
            this.directionOfMovement = turnLeft(directionOfMovement);
        }
        //turns  right
        else if (isRightTurnPossible) {
            
            super.standOn(super.getStandingOn().getAdjecentTile(turnRight(directionOfMovement)));
            this.directionOfMovement = turnRight(directionOfMovement);
        }
        // turns around
        else if (turningAroundPossible){
            
            super.standOn(super.getStandingOn().getAdjecentTile(turnAround(directionOfMovement)));
            this.directionOfMovement = turnAround(directionOfMovement);
        }
        // in case no way out, it doesn't move
        else {
     
        }
    }
    
    private Direction turnLeft (Direction directionOfMovement) {
        
        Direction leftOfDirection;
        
        if (directionOfMovement == Direction.UP){
            
            leftOfDirection = Direction.LEFT;
        }
        else if (directionOfMovement == Direction.LEFT) {
            
            leftOfDirection = Direction.DOWN;
        }
        else if (directionOfMovement == Direction.DOWN) {
            
            leftOfDirection = Direction.RIGHT;
        }
        else {
             
            leftOfDirection = Direction.UP;
        }
        
        return leftOfDirection;                   
    }
    
    private Direction turnRight (Direction directionOfMovement) {
        
        Direction rightOfDirection;
        
        if (directionOfMovement == Direction.UP){
            
            rightOfDirection = Direction.RIGHT;
        }
        else if (directionOfMovement == Direction.LEFT) {
            
            rightOfDirection = Direction.UP;
        }
        else if (directionOfMovement == Direction.DOWN) {
            
            rightOfDirection = Direction.LEFT;
        }
        else {
             
            rightOfDirection = Direction.DOWN;
        }
        
        return rightOfDirection;                   
    }    
    
    public Direction turnAround (Direction directionOfMovement) {
        
        Direction oppositeDirection;
        
        if (directionOfMovement == Direction.UP){
            
            oppositeDirection = Direction.DOWN;
        }
        else if (directionOfMovement == Direction.LEFT) {
            
            oppositeDirection = Direction.RIGHT;
        }
        else if (directionOfMovement == Direction.DOWN) {
            
            oppositeDirection = Direction.UP;
        }
        else {
             
            oppositeDirection = Direction.LEFT;
        }
        
        return oppositeDirection;                   
    }       
}
