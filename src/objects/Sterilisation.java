/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import objects.rats.PeacefulRat;
import tile.Direction;
import tile.Tile;

/**
 *
 * @author fahds
 */
public class Sterilisation extends GameObject{
    
    private int radius;
    private int duration;
    private ArrayList<Tile> affectedTiles;
    private Image SterilisationImage;
    private ArrayList<SterilisationEffect> sterilisationEffects;
    private Timeline effectTimer;

    public Sterilisation(Tile standingOn,int duration) {
        super(standingOn);
        
        affectedTiles = new ArrayList<>();
        sterilisationEffects = new ArrayList<>();
        
        this.duration = duration;
        findAffectedTiles ();
        SterilisationImage = new Image("file:resources/sterilistation.png");
        super.setIcon(SterilisationImage);
    }
    
    public void beSterile (PeacefulRat rat) {
        
        rat.setSterilisation(true);
        createSterilisationEffect();
    }
    
    private void createSterilisationEffect () {
        GameObject.getBoard().removeObject(this);
        
        for (int i = 0; i < affectedTiles.size(); i++){
            
            SterilisationEffect newEffect = new SterilisationEffect(affectedTiles.get(i));
            sterilisationEffects.add(newEffect);
            GameObject.getBoard().addObject(newEffect);
        }
        effectTimer = new Timeline(new KeyFrame(Duration.seconds(duration), event -> endSterilisationEffect ()));
        effectTimer.play();
        
    }
    private void endSterilisationEffect () {
        
        for (int i = 0; i < sterilisationEffects.size(); i++){
            
            GameObject.getBoard().removeObject(sterilisationEffects.get(i));
        }
        
    }
    
    
    private void findAffectedTiles () {
        
        affectedTiles.add(super.getStandingOn());
        affectedTiles.add(super.getStandingOn().getAdjacentTile(Direction.UP));
        affectedTiles.add(super.getStandingOn().getAdjacentTile(Direction.DOWN));
        affectedTiles.add(super.getStandingOn().getAdjacentTile(Direction.RIGHT));
        affectedTiles.add(super.getStandingOn().getAdjacentTile(Direction.LEFT));    
    }
    
    
    
    
    
}
