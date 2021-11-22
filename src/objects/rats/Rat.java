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
    
    private void move () {
        
        boolean isLeftTurnPossible = (super.getStandingOn().getAdjacentTile(turnLeft(
                                       directionOfMovement)) .isTraversable());
        
        boolean isRightTurnPossible = (super.getStandingOn().getAdjacentTile(turnRight(
                                        directionOfMovement)).isTraversable());
        
        boolean isTurningAroundPossible = (super.getStandingOn().getAdjacentTile(turnAround(
                                          directionOfMovement)).isTraversable());

        boolean isMoveForwardPossible = (super.getStandingOn().getAdjacentTile(directionOfMovement).isTraversable());

        boolean moveSucceeded = false;
        Random random = new Random();

        // If a move that is not turning around is possible, select one at random
        if (isLeftTurnPossible || isRightTurnPossible || isMoveForwardPossible) {
            while (!moveSucceeded) {
                int decision = random.nextInt(3);

                switch (decision) {
                    case 0: // Move forward
                        if (isMoveForwardPossible) {
                            super.standOn(super.getStandingOn().getAdjacentTile(directionOfMovement));
                            moveSucceeded = true;
                        }
                        break;
                    case 1: // Move left
                        if (isLeftTurnPossible) {
                            super.standOn(super.getStandingOn().getAdjacentTile(turnLeft(directionOfMovement)));
                            this.directionOfMovement = turnLeft(directionOfMovement);
                            moveSucceeded = true;
                        }
                        break;
                    case 2: // Move right
                        if (isRightTurnPossible) {
                            super.standOn(super.getStandingOn().getAdjacentTile(turnRight(directionOfMovement)));
                            this.directionOfMovement = turnRight(directionOfMovement);
                            moveSucceeded = true;
                        }
                        break;
                }
            }
        } else { // No other direction is possible, turn around
            if (isTurningAroundPossible) {
                super.standOn(super.getStandingOn().getAdjacentTile(turnAround(directionOfMovement)));
                this.directionOfMovement = turnAround(directionOfMovement);
            }
        }

        // Update the display
        GameObject.getBoard().updateBoardDisplay();
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
