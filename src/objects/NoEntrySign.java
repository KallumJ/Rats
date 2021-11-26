package objects;

import display.Board;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import objects.rats.Rat;
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
 * The class Number entry sign extends item
 */ 
public class NoEntrySign extends GameObject {
    
    private Image noEntrySignImage;
    private int damageDone;
    private int durability;
    private boolean recentlyActivated;
    private static final int DELAY = 1;

    public static final int DEFAULT_DAMAGE_DONE = 0;
    public static final int DEFAULT_DURABILITY = 5;

	/** 
	 * Number entry sign
	 * @param standingOn  the standing on
	 * @param damageDone  the damage done
	 * @param durability  the durability
	 */
    public NoEntrySign(Tile standingOn, int damageDone, int durability) {
        super(standingOn);
        
        this.damageDone = damageDone;
        this.durability = durability;
        
        noEntrySignImage = new Image("file:resources/noEntrySign.png");
        super.setIcon(noEntrySignImage);
    }

    /** 
	 * Activation
	 * @param rat  the rat
	 */
    public void activation(Rat rat) {

        // If we haven't been activated recently
        if (!recentlyActivated) {
            recentlyActivated = true;

            // after the specified delay has passed, allow damage to be done again
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(DELAY), event -> recentlyActivated = false));
            timeline.play();

            rat.setDirectionOfMovement(rat.turnAround(rat.getDirectionOfMovement()));
            this.damageDone = damageDone+1;

            if (damageDone >= durability) {

                GameObject.getBoard().removeObject(this);

            }
            GameObject.getBoard().updateBoardDisplay();
        }
    }
    /** 
	 * Gets the damage done
	 * @return the damage done
	 */
    public int getDamageDone () {
        
        return this.damageDone;
    }
    
}
