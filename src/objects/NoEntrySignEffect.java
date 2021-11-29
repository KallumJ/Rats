/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import objects.rats.Rat;
import tile.Tile;

/**
 *
 * @author fahds
 */
public class NoEntrySignEffect extends GameObject{
    
     private boolean recentlyActivated;
     private NoEntrySign x;
    private static final int DELAY = 1;

    public NoEntrySignEffect(Tile standingOn, NoEntrySign x) {
        super(standingOn);
        
        
        this.x = x;
    }
        /**
     * Blocks the rats way and make them change direction.
     *
     * @param rat The rat which it direction will be changed in case of contact.
     */
    public void blockPath(Rat rat) {

        // If we haven't been activated recently
        if (!recentlyActivated) {
            recentlyActivated = true;

            // after the specified delay has passed, allow damage to be done again
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(DELAY), event -> recentlyActivated = false));
            timeline.play();

            x.doDamage();
            
           
        }
    }
    }
    

