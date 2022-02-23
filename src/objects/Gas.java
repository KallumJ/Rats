package objects;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import level.LevelUtils;
import objects.rats.Rat;
import tile.Direction;
import tile.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fahds
 * @date 2022/02/15
 *
 */
public class Gas extends GameObject implements ObjectStoppable {

    public static final int DEFAULT_DURATION = 12;
    public static final int DEFAULT_RANGE = 3;
    public static final boolean DEFAULT_ACTIVATION = false;
    private static final int EXPANSION_RATE = 4;
    private final int duration;
    private final int range;
    private final ArrayList<GasEffect> gasEffects;
    private final ArrayList<Rat> ratsInGas;
    private int counter;
    private Timeline expandTimeline;
    private Timeline chokingTimeline;
    private Timeline checkEmptyTimeline;
    private Timeline delayConstructionTimeline;
    private boolean isActive;

    /**
     * A method to construct the gas object.
     */
    public Gas(Tile standingOn, boolean active) {
        super(standingOn);

        this.duration = DEFAULT_DURATION;
        this.range = DEFAULT_RANGE;
        counter = 0;
        gasEffects = new ArrayList<>();
        ratsInGas = new ArrayList<>();

        Image gasImage =
                new Image(ObjectUtils.getObjectImageUrl(GameObjectType.GAS));
        super.setIcon(gasImage);

        if (active) {
            delayConstructionTimeline =
                    new Timeline(new KeyFrame(Duration.seconds(1),
                            event -> activateGas()));
            delayConstructionTimeline.play();
        }
    }

    /**
     * A method to activate the object gas.
     */
    public void activateGas() {
        if (!isActive) {
            isActive = true;
            expand(super.getStandingOn());

            checkEmptyTimeline =
                    new Timeline(new KeyFrame(Duration.millis(100),
                            event -> checkEmpty()));
            checkEmptyTimeline.setCycleCount(Animation.INDEFINITE);
            checkEmptyTimeline.play();
        }

    }

    /**
     * A method to expand the gas object across tiles.
     */
    public void expand(Tile tile) {
        if (GameObject.getBoard() == null) {
            return;
        }

        for (Direction direction : Direction.values()) {
            Tile adjacentTile = tile.getAdjacentTile(direction);
            List<GameObject> objectsOnBoard = GameObject.getBoard()
                    .getObjects();

            if (adjacentTile.isTraversable()) {
                boolean isGasNotPresent =
                        LevelUtils.getObjectsOnTile(adjacentTile,
                                        objectsOnBoard)
                                .stream()
                                .noneMatch(object -> object instanceof GasEffect);

                if (isGasNotPresent) {
                    gasEffects.add(new GasEffect(adjacentTile,
                            (duration - ((gasEffects.size() / 4) * 2)), this));
                }

                if (counter < range) {
                    delayExpand(adjacentTile);
                } else {
                    for (GasEffect gasEffect : gasEffects) {
                        gasEffect.disappear();
                    }
                }
            }

        }
        this.counter++;
    }

    /**
     * A method to get the time of object on the board.
     *
     * @return duration.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * A method to get the range of object on the board.
     *
     * @return range.
     */
    public int getRange() {
        return range;
    }

    /**
     * A method to find out when the rat is choking from the gas.
     */
    public void startChoking(Rat rat) {
        ratsInGas.add(rat);
        chokingTimeline = new Timeline(new KeyFrame(Duration.seconds(4),
                event -> stillHere(rat)));
        chokingTimeline.play();
    }

    /**
     * A method to get check is the rat is still alive.
     */
    public void stillHere(Rat rat) {

        if (stillInGas(rat) && GameObject.getBoard() != null) {
            GameObject.getBoard().removeObject(rat);
        }
        ratsInGas.remove(rat);

    }

    /**
     * A method to get the list of the RatsInGas (Rats effected by the gas).
     *
     * @return this.ratsInGas.
     */
    public ArrayList<Rat> getRatsInGas() {

        return this.ratsInGas;
    }

    /**
     * A method to get the list of the gas effect objects.
     *
     * @return gasEffect.
     */
    public ArrayList<GasEffect> getGasEffects() {
        return gasEffects;
    }

    /**
     * Stops any timelines running in this object.
     */
    @Override
    public void stop() {
        if (expandTimeline != null) {
            expandTimeline.pause();
        }

        if (chokingTimeline != null) {
            chokingTimeline.pause();
        }

        if (checkEmptyTimeline != null) {
            checkEmptyTimeline.pause();
        }

        if (delayConstructionTimeline != null) {
            delayConstructionTimeline.pause();
        }
    }

    /**
     * Gets whether this gas is active.
     *
     * @return true if the gas is active, false otherwise.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * A method to check if the inventory is empty of the object 'Gas'.
     */
    private void checkEmpty() {
        if (gasEffects.isEmpty()) {
            if (GameObject.getBoard() != null) {
                GameObject.getBoard().removeObject(this);
                checkEmptyTimeline.pause();
            }
        }
    }

    /**
     * A method to delay the expanding of the gas object.
     */
    private void delayExpand(Tile tile) {
        expandTimeline =
                new Timeline(new KeyFrame(Duration.seconds(EXPANSION_RATE),
                        event -> expand(tile)));
        expandTimeline.play();
    }

    /**
     * A method to check if the rat is still in gas effect.
     *
     * @return boolean.
     */
    private boolean stillInGas(Rat rat) {
        // Return true if the rat is stood in a gas effect
        for (GasEffect gasEffect : gasEffects) {
            if (gasEffect.getStandingOn().equals(rat.getStandingOn())) {
                return true;
            }
        }

        // If the rat is not stood in a gas effect, return true if it is stood
        // in a gas
        // Else, it is stood in neither a gas or gas effect return false
        return rat.getStandingOn().equals(this.getStandingOn());
    }
}
