package objects;

import java.util.ArrayList;

import display.Board;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
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
public class Bomb extends Item {

    private int timerLength; // in seconds
    private boolean timerStarted; 
    private int timerRemainingTime;
    private Timeline tickTimeline;
    private ArrayList<Tile> affectedTiles;

    public Bomb(Tile standingOn, int timerLength, boolean timerStarted) {
        super(standingOn, null);

        this.timerLength = timerLength;
        this.timerStarted = timerStarted;
        this.timerRemainingTime = timerLength;
        findAffectedTiles();

    }

    private void startTimer(Board board) {
        tickTimeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> tick(board)));
        // Loop the timeline forever
        tickTimeline.setCycleCount(this.timerLength);

        tickTimeline.play();
        this.timerStarted = true;

    }
    
    private void tick (Board board) {
        
        this.timerRemainingTime = timerRemainingTime - 1;
        
        if (timerRemainingTime <= 1) {
            
            explode(board);
        }
        
    }
    
    private void explode (Board board) {
        
        for (int i = 0; i < affectedTiles.size(); i++) {
            
            for (int j = 0; j < board.getObjects().size(); j++) {
                
                if (board.getObjects().get(j).getStandingOn().equals(affectedTiles.get(i))) {
                
                    board.removeObject(board.getObjects().get(j));
                }
            }
        }
        board.removeObject(this);
        
    }
    
    public ArrayList<Tile> getAffectedTiles () {
        
        return this.affectedTiles;
    }
    
    public void setAffectedTiles (ArrayList<Tile> affectedTiles) {
        
        this.affectedTiles = affectedTiles;
    }

    private void findAffectedTiles() {

        findAffectedTilesUp(Direction.UP, super.getStandingOn());
        findAffectedTilesDown(Direction.DOWN, super.getStandingOn());
        findAffectedTilesRight(Direction.RIGHT, super.getStandingOn());
        findAffectedTilesLeft(Direction.LEFT, super.getStandingOn());
    }

    private void findAffectedTilesUp(Direction direction, Tile currentTile) {

        affectedTiles.add(currentTile);

        if (currentTile.getAdjacentTile(direction).isTraversable()) {

            findAffectedTilesUp(direction, currentTile.getAdjacentTile(direction));

        }
    }

    private void findAffectedTilesDown(Direction direction, Tile currentTile) {

        affectedTiles.add(currentTile);

        if (currentTile.getAdjacentTile(direction).isTraversable()) {

            findAffectedTilesUp(direction, currentTile.getAdjacentTile(direction));

        }
    }

    private void findAffectedTilesRight(Direction direction, Tile currentTile) {

        affectedTiles.add(currentTile);

        if (currentTile.getAdjacentTile(direction).isTraversable()) {

            findAffectedTilesUp(direction, currentTile.getAdjacentTile(direction));

        }
    }

    private void findAffectedTilesLeft(Direction direction, Tile currentTile) {

        affectedTiles.add(currentTile);

        if (currentTile.getAdjacentTile(direction).isTraversable()) {

            findAffectedTilesUp(direction, currentTile.getAdjacentTile(direction));
        }
    }

    @Override
    public void activation(Board board, Rat rat) {
       
        startTimer(board);
    }

    @Override
    public void changeIcon(Image icon) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
