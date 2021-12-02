package objects;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import objects.rats.Rat;
import tile.Tile;

/**
 * This class represent the damage counter of a NoEntrySign which will update
 * the damage to a NoEntrySign when a rat tried to walk through it.
 *
 * @author Fahd
 */
public class NoEntrySignCounter extends GameObject {

    private boolean recentlyActivated;
    private NoEntrySign sourceSign;
    private static final int DELAY = 1;

    /**
     * Creates NoEntrySignCounter next to the a NoEntrySign and count damage
     * done.
     *
     * @param standingOn The tile the no entry sign is on.
     * @param sourceSign The original NoEntrySign which it damage will be
     * updated.
     */
    public NoEntrySignCounter(Tile standingOn, NoEntrySign sourceSign) {
        super(standingOn);

        this.sourceSign = sourceSign;
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

            sourceSign.doDamage();

        }
    }
}
