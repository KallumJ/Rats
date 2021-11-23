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
public class Bomb extends GameObject {

    private int timerLength; // in seconds
    private boolean timerStarted; 
    private int timerRemainingTime;
    private Timeline tickTimeline1;
    private ArrayList<Tile> affectedTiles;
    private Image bombImage;

    public Bomb(Tile standingOn, int timerLength, boolean timerStarted) {
        super(standingOn);

        this.timerLength = timerLength;
        this.timerStarted = timerStarted;
        this.timerRemainingTime = timerLength;
        this.affectedTiles = new ArrayList <Tile>();
        findAffectedTiles();
        
        bombImage = new Image("file:resources/bomb.png");
        super.setIcon(bombImage);

    }

    private void startTimer() {
        tickTimeline1 = new Timeline(new KeyFrame(Duration.millis(1000), event -> tick()));
        // Loop the timeline forever
        tickTimeline1.setCycleCount(this.timerLength);

        tickTimeline1.play();
        this.timerStarted = true;

    }
    
    private void tick () {
        
        this.timerRemainingTime = timerRemainingTime - 1;
        System.out.println(timerRemainingTime);
        if (timerRemainingTime < 1) {
            
            explode();
        }
        
    }
    
    private void explode () {
        
        for (int i = 0; i < affectedTiles.size(); i++) {
            
            for (int j = 0; j < GameObject.getBoard().getObjects().size(); j++) {
                
                if (GameObject.getBoard().getObjects().get(j).getStandingOn().equals(affectedTiles.get(i))) {
                
                    GameObject.getBoard().removeObject(GameObject.getBoard().getObjects().get(j));
                }
            }
        }
        GameObject.getBoard().removeObject(this);
        GameObject.getBoard().updateBoardDisplay();
        
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

    
    public void activationOfBomb() {

        if (!this.timerStarted) {
            startTimer();
        }
    }

    @Override
    public void changeIcon(Image icon) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
