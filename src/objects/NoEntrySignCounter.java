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
 * @date 2022/02/15
 *
 */
public class NoEntrySignCounter extends GameObject implements ObjectStoppable {

    private static final int DELAY = 1;
    private final NoEntrySign sourceSign;
    private boolean recentlyActivated;
    private Timeline resetActivatedTimeline;

    /**
     * Creates NoEntrySignCounter next to the NoEntrySign and count damage
     * done.
     *
     * @param standingOn The tile the no entry sign is on.
     * @param sourceSign The original NoEntrySign which it damages will be
     *                   updated.
     */
    public NoEntrySignCounter(Tile standingOn, NoEntrySign sourceSign) {
        super(standingOn);

        this.sourceSign = sourceSign;
    }

    /**
     * Blocks the rats way and make them change direction.
     *
     * @param rat The rat which its direction will be changed in case of
     *            contact.
     */
    public void blockPath(Rat rat) {

        // If we haven't been activated recently
        if (!recentlyActivated) {
            recentlyActivated = true;

            // after the specified delay has passed, allow damage to be done
            // again
            resetActivatedTimeline =
                    new Timeline(new KeyFrame(Duration.seconds(DELAY),
                            event -> recentlyActivated = false));
            resetActivatedTimeline.play();

            sourceSign.doDamage();

        }
    }

    /**
     * Stops any timelines running in this object.
     */
    @Override
    public void stop() {
        if (resetActivatedTimeline != null) {
            resetActivatedTimeline.pause();
        }
    }
}
