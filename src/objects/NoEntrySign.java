package objects;

import display.Board;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import objects.rats.Rat;
import tile.Tile;

/**
 * This class represent the no entry sign which will block the way on the rats
 * and make them choose a different direction.
 *
 * @author Fahd
 *
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
     * Creates a new no entry sign item on the specified tile
     *
     * @param standingOn The tile the no entry sign is on.
     * @param damageDone The damage done to the entry sign.
     * @param durability The durability of the sign before it breaks.
     */
    public NoEntrySign(Tile standingOn, int damageDone, int durability) {
        super(standingOn);

        this.damageDone = damageDone;
        this.durability = durability;

        noEntrySignImage = new Image("file:resources/noEntrySign.png");
        super.setIcon(noEntrySignImage);
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

            rat.setDirectionOfMovement(rat.turnAround(rat.getDirectionOfMovement()));
            this.damageDone = damageDone + 1;

            if (damageDone >= durability) {

                GameObject.getBoard().removeObject(this);

            }
            GameObject.getBoard().updateBoardDisplay();
        }
    }

    /**
     * Gets the damage done to the sign
     *
     * @return The damage done to the sign.
     */
    public int getDamageDone() {

        return this.damageDone;
    }

}
