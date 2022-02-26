package objects;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import objects.rats.PeacefulRat;
import tile.Direction;
import tile.Tile;

import java.util.ArrayList;

/**
 * This class represent the sterilisation item which start the sterilisation
 * effect.
 *
 * @author Fahd
 * @date 2022.02.21
 *
 */
public class Sterilisation extends GameObject implements ObjectStoppable {

    public static final int DEFAULT_DURATION = 5;
    public static final int DELAY_DURING_CONSTRUCTION = 250; // in ms

    private final int duration;
    private final ArrayList<Tile> affectedTiles;
    private final ArrayList<SterilisationEffect> sterilisationEffects;
    private boolean active;
    private Timeline delayConstructionTimeline;
    private Timeline effectTimeline;

    /**
     * Create a new sterilisation item on the specified tile.
     *
     * @param standingOn The tile the sterilisation is on.
     * @param duration   The duration of sterilisation effect when it
     *                   activated.
     */
    public Sterilisation(Tile standingOn, int duration, boolean active) {
        super(standingOn);

        affectedTiles = new ArrayList<>();
        sterilisationEffects = new ArrayList<>();

        this.duration = duration;
        findAffectedTiles();
        Image sterilisationImage =
                new Image(ObjectUtils.getObjectImageUrl(GameObjectType.STERILISATION));
        super.setIcon(sterilisationImage);
        this.active = active;
        if (this.active) {
            delayConstructionTimeline = new Timeline(new KeyFrame(Duration.millis(
                    DELAY_DURING_CONSTRUCTION), event -> createSterilisationEffect()));
            delayConstructionTimeline.play();
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
     * A method to get the duration of the sterilisation.
     *
     * @return the duration of the sterilisation in seconds.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * A method to get whether this sterilisation is active or not.
     *
     * @return true if active, false otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Stops any timelines running in this object.
     */
    @Override
    public void stop() {
        if (delayConstructionTimeline != null) {
            delayConstructionTimeline.pause();
        }

        if (effectTimeline != null) {
            effectTimeline.pause();
        }
    }

    /**
     * Creates the sterilisation effect around the actual Sterilisation item.
     */
    private void createSterilisationEffect() {
        for (int i = 0; i < affectedTiles.size(); i++) {

            SterilisationEffect newEffect =
                    new SterilisationEffect(affectedTiles.get(i));
            sterilisationEffects.add(newEffect);
            GameObject.getBoard().addObject(newEffect);
        }
        effectTimeline = new Timeline(new KeyFrame(Duration.seconds(duration),
                event -> endSterilisationEffect()));
        effectTimeline.play();

    }

    /**
     * End the sterilisation effect.
     */
    private void endSterilisationEffect() {
        GameObject.getBoard().removeObject(this);
        for (int i = 0; i < sterilisationEffects.size(); i++) {

            GameObject.getBoard().removeObject(sterilisationEffects.get(i));
        }

    }

    /**
     * Find the area where the effect should be.
     */
    private void findAffectedTiles() {

        affectedTiles.add(super.getStandingOn());
        affectedTiles.add(super.getStandingOn().getAdjacentTile(Direction.UP));
        affectedTiles.add(super.getStandingOn()
                .getAdjacentTile(Direction.DOWN));
        affectedTiles.add(super.getStandingOn()
                .getAdjacentTile(Direction.RIGHT));
        affectedTiles.add(super.getStandingOn()
                .getAdjacentTile(Direction.LEFT));
    }
}
