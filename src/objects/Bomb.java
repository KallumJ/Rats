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
    private Image bombOneSecondImage;
    private Image bombTwoSecondsImage;
    private Image bombThreeSecondsImage;
    private Image bombFourImage;
    private Image bombFiveImage;
    

    public Bomb(Tile standingOn, int timerLength, boolean timerStarted) {
        super(standingOn);

        this.timerLength = timerLength;
        this.timerStarted = timerStarted;
        this.timerRemainingTime = timerLength;
        this.affectedTiles = new ArrayList <Tile>();
        findAffectedTiles();
     
        bombImage = new Image("file:resources/bomb.png");
        super.setIcon(bombImage);
        bombOneSecondImage = new Image("file:resources/bombOneSecond.png");
        bombTwoSecondsImage = new Image("file:resources/bombTwoSeconds.png");
        bombThreeSecondsImage = new Image("file:resources/bombThreeSeconds.png");
        bombFourImage = new Image("file:resources/bombFourSeconds.png");
        bombFiveImage = new Image("file:resources/bombFiveSeconds.png");
        
        if (this.timerStarted) {
            
            this.startTimer();
        }

    }

    private void startTimer() {
        tickTimeline1 = new Timeline(new KeyFrame(Duration.millis(1000), event -> tick()));
        // Loop the timeline forever
        tickTimeline1.setCycleCount(this.timerLength);

        tickTimeline1.play();
        this.timerStarted = true;

    }
    
    private void tick () {
        
        switch(timerRemainingTime) {
            
            case 6:
                super.setIcon(bombFiveImage);
                break;
            case 5:
                super.setIcon(bombFourImage);
                break;
            case 4:
                super.setIcon(bombThreeSecondsImage);
                break;
            case 3:
                super.setIcon(bombTwoSecondsImage);
                break;
            case 2:
                super.setIcon(bombOneSecondImage);
                break;
            default:
                super.setIcon(bombImage);
                break;
        } 
        GameObject.getBoard().updateBoardDisplay();
        
        this.timerRemainingTime = timerRemainingTime - 1;
        
        if (timerRemainingTime  < 1) {
            
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
        for (Direction direction : Direction.values()) {
            findAffectedTilesRecurse(direction, super.getStandingOn());
        }
    }

    private void findAffectedTilesRecurse(Direction direction, Tile currentTile) {
        affectedTiles.add(currentTile);
        if (currentTile.getAdjacentTile(direction).isTraversable()) {
            findAffectedTilesRecurse(direction, currentTile.getAdjacentTile(direction));
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
