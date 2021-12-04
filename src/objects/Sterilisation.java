package objects;

import java.security.Key;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import objects.rats.PeacefulRat;
import tile.Direction;
import tile.Tile;

/**
 * This class represent the sterilisation item which start the sterilisation effect.
 *
 * @author Fahd
 */
public class Sterilisation extends GameObject {

    private final int duration;
    private final ArrayList<Tile> affectedTiles;
    private final ArrayList<SterilisationEffect> sterilisationEffects;
    private boolean active;

    public static final int DEFAULT_DURATION = 5;
    public static final int DELAY_DURING_CONSTRUCTION = 250; // in ms

    /**
     * Create a new sterilisation item on the specified tile.
     *
     * @param standingOn The tile the sterilisation is on.
     * @param duration The duration of sterilisation effect when it activated.
     */
    public Sterilisation(Tile standingOn, int duration, boolean active) {
        super(standingOn);

        affectedTiles = new ArrayList<>();
        sterilisationEffects = new ArrayList<>();

        this.duration = duration;
        findAffectedTiles();
        Image sterilisationImage = new Image(
                ObjectUtils.getObjectImageUrl(GameObjectType.STERILISATION)
        );
        super.setIcon(sterilisationImage);
        this.active = active;
        if (this.active) {
            Timeline delay = new Timeline(
                    new KeyFrame(Duration.millis(DELAY_DURING_CONSTRUCTION),
                    event -> createSterilisationEffect())
            );
            delay.play();
        }
    }

    /**
     * Makes a rat sterile.
     *
     * @param rat The Rat which will be sterile.
     */
    public void beSterile(PeacefulRat rat) {

        rat.setSterilisation(true);
        if (!this.active) {
            createSterilisationEffect();
        }
        this.active = true;
    }

    /**
     * Creates the sterilisation effect around the actual Sterilisation item.
     */
    private void createSterilisationEffect() {
        for (int i = 0; i < affectedTiles.size(); i++) {

            SterilisationEffect newEffect = new SterilisationEffect(affectedTiles.get(i));
            sterilisationEffects.add(newEffect);
            GameObject.getBoard().addObject(newEffect);
        }
        Timeline effectTimer = new Timeline(new KeyFrame(Duration.seconds(duration), event -> endSterilisationEffect()));
        effectTimer.play();

    }

    /**
     * End the sterilisation effect.
     */
    private void endSterilisationEffect() {
        GameObject.getBoard().removeObject(this);
        for (int i = 0; i < sterilisationEffects.size(); i++) {

            GameObject.getBoard().removeObject(sterilisationEffects.get(i));
        }
        this.active = false;
    }

    /**
     * Find the area where the effect should be.
     */
    private void findAffectedTiles() {

        affectedTiles.add(super.getStandingOn());
        affectedTiles.add(super.getStandingOn().getAdjacentTile(Direction.UP));
        affectedTiles.add(super.getStandingOn().getAdjacentTile(Direction.DOWN));
        affectedTiles.add(super.getStandingOn().getAdjacentTile(Direction.RIGHT));
        affectedTiles.add(super.getStandingOn().getAdjacentTile(Direction.LEFT));
    }

    /**
     * A method to get the duration of the sterilisation
     * @return the duration of the sterilisation in seconds
     */
    public int getDuration() {
        return duration;
    }

    /**
     * A method to get whether this sterilisation is active or not
     * @return true if active, false otherwise
     */
    public boolean isActive() {
        return active;
    }
}
