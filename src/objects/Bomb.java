package objects;

import java.util.ArrayList;

import display.Board;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import objects.rats.Rat;
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
 * The class Bomb extends Item
 */ 
public class Bomb extends Item {

    private int timerLength; // in seconds
    private boolean timerStarted; 
    private int timerRemainingTime;
    private Timeline tickTimeline;
    private ArrayList<Tile> affectedTiles;
	
	/** 
	* Bomb
	* @param standingOn  the standing on
	* @param timerLength  the timer length
	* @param timerStarted  the timer started
	* @return public
	*/
    public Bomb(Tile standingOn, int timerLength, boolean timerStarted) {
        super(standingOn);

        this.timerLength = timerLength;
        this.timerStarted = timerStarted;
        this.timerRemainingTime = timerLength;
        findAffectedTiles();

    }
	/** 
	* Start timer
	* @param board  the board
	*/
    private void startTimer(Board board) {
        tickTimeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> tick(board)));
        // Loop the timeline forever
        tickTimeline.setCycleCount(this.timerLength);

        tickTimeline.play();
        this.timerStarted = true;

    }
    
	/** 
	* Tick
	* @param board  the board
	*/
    private void tick (Board board) {
        
        this.timerRemainingTime = timerRemainingTime - 1;
        
        if (timerRemainingTime <= 1) {
            
            explode(board);
        }
        
    }
    /** 
	* Explode
	* @param board  the board
	*/
    private void explode (Board board) {
        
        for (int i = 0; i < affectedTiles.size(); i++) {
            
            for (int j = 0; j < board.getObjects().size(); j++) {
                
                if (board.getObjects().get(j).getStandingOn().equals(affectedTiles.get(i))) {
                
                    board.removeObject(board.getObjects().get(j));
                }
            }
        }
        board.removeObject(this);
        board.updateBoardDisplay();
        
    }
    
	/** 
	* Gets the affected tiles
	* @return the affected tiles
	*/
    public ArrayList<Tile> getAffectedTiles () {
        
        return this.affectedTiles;
    }
    
	/** 
	* Sets the affected tiles
	* @param affectedTiles  the affected tiles
	*/
    public void setAffectedTiles (ArrayList<Tile> affectedTiles) {
        
        this.affectedTiles = affectedTiles;
    }
	
	/** 
	* Find affected tiles
	*/
    private void findAffectedTiles() {

        findAffectedTilesUp(Direction.UP, super.getStandingOn());
        findAffectedTilesDown(Direction.DOWN, super.getStandingOn());
        findAffectedTilesRight(Direction.RIGHT, super.getStandingOn());
        findAffectedTilesLeft(Direction.LEFT, super.getStandingOn());
    }
	
	/** 
	* Find affected tiles up
	* @param direction  the direction
	* @param currentTile  the current tile
	*/
    private void findAffectedTilesUp(Direction direction, Tile currentTile) {

        affectedTiles.add(currentTile);

        if (currentTile.getAdjacentTile(direction).isTraversable()) {

            findAffectedTilesUp(direction, currentTile.getAdjacentTile(direction));

        }
    }
	
	/** 
	* Find affected tiles down
	* @param direction  the direction
	* @param currentTile  the current tile
	*/
    private void findAffectedTilesDown(Direction direction, Tile currentTile) {

        affectedTiles.add(currentTile);

        if (currentTile.getAdjacentTile(direction).isTraversable()) {

            findAffectedTilesUp(direction, currentTile.getAdjacentTile(direction));

        }
    }
	
	/** 
	* Find affected tiles right
	* @param direction  the direction
	* @param currentTile  the current tile
	*/
    private void findAffectedTilesRight(Direction direction, Tile currentTile) {

        affectedTiles.add(currentTile);

        if (currentTile.getAdjacentTile(direction).isTraversable()) {

            findAffectedTilesUp(direction, currentTile.getAdjacentTile(direction));

        }
    }

	/** 
	* Find affected tiles left
	* @param direction  the direction
	* @param currentTile  the current tile
	*/
    private void findAffectedTilesLeft(Direction direction, Tile currentTile) {

        affectedTiles.add(currentTile);

        if (currentTile.getAdjacentTile(direction).isTraversable()) {

            findAffectedTilesUp(direction, currentTile.getAdjacentTile(direction));
        }
    }

    @Override
	
	/**
	* Activation
	* @param board  the board
	* @param rat  the rat
	*/
    public void activation(Board board, Rat rat) {

        if (!this.timerStarted) {
            startTimer(board);
        }
    }

    @Override
	/** 
	* Change icon
	* @param icon  the icon
	*/
    public void changeIcon(Image icon) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
