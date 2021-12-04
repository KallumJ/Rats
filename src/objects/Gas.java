package objects;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import level.LevelUtils;
import objects.rats.Rat;
import tile.Direction;
import tile.Tile;

/**
 *
 * @author fahds
 */
public class Gas extends GameObject implements ObjectStoppable {

    public static final int DEFAULT_DURATION = 16;
    public static final int DEFAULT_RANGE = 4;
    public static final boolean DEFAULT_ACTIVATION = false;
    private final int duration;
    private int counter;
    private final int range;
    private final ArrayList<GasEffect> gasEffects;
    private final ArrayList<Rat> ratsInGas;
    private Timeline expandTimeline;
    private Timeline chokingTimeline;
    private Timeline checkEmptyTimeline;
    private Timeline delayConstructionTimeline;
    private boolean isActive;

    public Gas(Tile standingOn, boolean active) {
        super(standingOn);

        this.duration = DEFAULT_DURATION;
        this.range = DEFAULT_RANGE;
        counter = 0;
        gasEffects = new ArrayList<>();
        ratsInGas = new ArrayList<>();

        Image gasImage = new Image(
                ObjectUtils.getObjectImageUrl(GameObjectType.GAS)
        );
        super.setIcon(gasImage);

        if (active) {
            delayConstructionTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> activateGas()));
            delayConstructionTimeline.play();
        }
    }

    public void activateGas() {
        if (!isActive) {
            isActive = true;
            expand(super.getStandingOn());

            checkEmptyTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> checkEmpty()));
            checkEmptyTimeline.setCycleCount(Animation.INDEFINITE);
            checkEmptyTimeline.play();
        }

    }

    private void checkEmpty() {
        if (gasEffects.isEmpty()) {
            GameObject.getBoard().removeObject(this);
            checkEmptyTimeline.pause();
        }
    }

    private void delayExpand(Tile tile) {
        expandTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> expand(tile)));
        expandTimeline.play();
    }

    public void expand(Tile tile) {
        if (GameObject.getBoard() == null) {
             return;
        }

        for (Direction direction : Direction.values()) {
            Tile adjacentTile = tile.getAdjacentTile(direction);
            List<GameObject> objectsOnBoard = GameObject.getBoard().getObjects();

            if (adjacentTile.isTraversable()) {
                boolean isGasNotPresent =
                        LevelUtils.getObjectsOnTile(adjacentTile, objectsOnBoard)
                                .stream().noneMatch(
                                        object -> object instanceof GasEffect
                                );

                if (isGasNotPresent) {
                    gasEffects.add(new GasEffect(adjacentTile, (duration - ((gasEffects.size() / 4) * 2)), this));
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

    public int getDuration() {
        return duration;
    }

    public int getRange() {
        return range;
    }
    
    public void startChoking (Rat rat) {
        ratsInGas.add(rat);
        chokingTimeline = new Timeline(new KeyFrame(Duration.seconds(4), event -> stillHere(rat)));
        chokingTimeline.play();
    }
    
    public void stillHere (Rat rat) {
        
        if (stillInGas(rat)){
            GameObject.getBoard().removeObject(rat);
        }
        ratsInGas.remove(rat);

    }
    
    private boolean stillInGas (Rat rat){
        // Return true if the rat is stood in a gas effect
        for (GasEffect gasEffect : gasEffects) {
           if (gasEffect.getStandingOn().equals(rat.getStandingOn())) {
               return true;
           }
       }

        // If the rat is not stood in a gas effect, return true if it is stood in a gas
        // Else, it is stood in neither a gas or gas effect return false
        return rat.getStandingOn().equals(this.getStandingOn());
    }
    
    public ArrayList<Rat> getRatsInGas () {
        
        return this.ratsInGas;
    }

    public ArrayList<GasEffect> getGasEffects() {
        return gasEffects;
    }

    /**
     * Stops any timelines running in this object
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
     * Gets whether this gas is active
     * @return true if the gas is active, false otherwise
     */
    public boolean isActive() {
        return isActive;
    }
}
