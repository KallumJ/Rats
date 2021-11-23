package objects.rats;

import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import objects.GameObject;
import tile.Direction;
import tile.Tile;


/**
 *
 * @author fahds
 */
/**
 * The class Abstract rat extends game object
 */ 
public abstract class Rat extends GameObject {
    
    // movement speed of the Rat
    private int speed; 
    
    private Timeline tickTimeline; 
    
    private Direction directionOfMovement;

    protected Rat(Tile standingOn, int speed, Direction directionOfMovement) {
        
        super(standingOn);
        this.speed = speed;
        this.directionOfMovement = directionOfMovement;

        tickTimeline = new Timeline(new KeyFrame(Duration.seconds(this.speed), event -> move()));

        // Loop the timeline forever
	    tickTimeline.setCycleCount(Animation.INDEFINITE);
        tickTimeline.play();
    }
    
	/** 
	 * Gets the speed
	 * @return the speed
	 */
    public int getSpeed (){
        
        return this.speed;
    }  
    
	/** 
	 * Sets the speed
	 * @param speed the speed
	 */
    public void setSpeed (int speed) {
        
        this.speed = speed;
    }
    
	/** 
	 * Gets the direction of movement
	 * @return the direction of movement
	 */
    public Direction getDirectionOfMovement () {
        
        return this.directionOfMovement;
    }
    
	/** 
	 * Sets the direction of movement
	 * @param directionOfMovement  the direction of movement
	 */
    public void setDirectionOfMovement (Direction directionOfMovement) {
        
        this.directionOfMovement = directionOfMovement;
    }
    /** 
	 * Move - moves left, right and turn around 
	 * when the rats can otherwise it doesn't move.
	 */
    private void move () {
        
        boolean isLeftTurnPossible = (super.getStandingOn().getAdjacentTile(turnLeft(
                                       directionOfMovement)) .isTraversable());
        
        boolean isRightTurnPossible = (super.getStandingOn().getAdjacentTile(turnRight(
                                        directionOfMovement)).isTraversable());
        
        boolean turningAroundPossible = (super.getStandingOn().getAdjacentTile(turnAround(
                                          directionOfMovement)).isTraversable());
        
        
        //moves forword if possible 
        if (super.getStandingOn().getAdjacentTile(directionOfMovement).isTraversable()) {
            
            super.standOn(super.getStandingOn().getAdjacentTile(directionOfMovement));
        }
        //randomly turning either left or right in case of junction 
        else if (isLeftTurnPossible &&  isRightTurnPossible) {
            
            // generates either 1 or 2
            Random random = new Random(); 
            int decision = random.nextInt(2);
            
            // turn left when decsion equals 1
            if (decision == 1) {
                
                super.standOn(super.getStandingOn().getAdjacentTile(turnLeft(directionOfMovement)));
                this.directionOfMovement = turnLeft(directionOfMovement);  
            }
            // turn right when decsion equals 2
            else {
                
            super.standOn(super.getStandingOn().getAdjacentTile(turnRight(directionOfMovement)));
            this.directionOfMovement = turnRight(directionOfMovement);                
            }
        }
        // turns left
        else if (isLeftTurnPossible) {
            
            super.standOn(super.getStandingOn().getAdjacentTile(turnLeft(directionOfMovement)));
            this.directionOfMovement = turnLeft(directionOfMovement);
        }
        //turns  right
        else if (isRightTurnPossible) {
            
            super.standOn(super.getStandingOn().getAdjacentTile(turnRight(directionOfMovement)));
            this.directionOfMovement = turnRight(directionOfMovement);
        }
        // turns around
        else if (turningAroundPossible){
            
            super.standOn(super.getStandingOn().getAdjacentTile(turnAround(directionOfMovement)));
            this.directionOfMovement = turnAround(directionOfMovement);
        }
        // in case no way out, it doesn't move
        else {
     
        }
        GameObject.getBoard().updateBoardDisplay();
    }
    
	/** 
	 * Turn left
	 * @param directionOfMovement  the direction of movement
	 * @return Direction
	 */
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
    
	/** 
	 * Turn right
	 * @param directionOfMovement  the direction of movement
	 * @return Direction
	 */
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
    
	/** 
	 * Turn around
	 * @param directionOfMovement  the direction of movement
	 * @return Direction
	 */
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
