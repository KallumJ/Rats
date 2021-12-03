package objects;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import tile.Direction;
import tile.Tile;

/**
 *
 * @author fahds
 */
public class Gas extends GameObject {

    public static final int DEFAULT_DURATION = 16;
    public static final int DEFAULT_RANGE = 4;
    private int duration;
    private int counter;
    private int range;
    private Image gasImage;
    private ArrayList<GasEffect> gasEffects;

    public Gas(Tile standingOn, int duration, int range) {
        super(standingOn);

        this.duration = duration;
        this.range = range;
        counter = 0;
        gasEffects = new ArrayList<>();
        gasImage = new Image("file:resources/gas.png");
        super.setIcon(gasImage);
    }

    public void activateGas() {
        GameObject.getBoard().removeObject(this);
        expand(super.getStandingOn());
        gasEffects.add(new GasEffect(super.getStandingOn(), (duration + 2), this));

    }

    private void delayExpand(Tile tile) {

        Timeline delayTimer = new Timeline(new KeyFrame(Duration.seconds(2), event -> expand(tile)));
        delayTimer.play();
    }

    public void expand(Tile tile) {

        for (Direction direction : Direction.values()) {

            if (tile.getAdjacentTile(direction).isTraversable()) {
                gasEffects.add(new GasEffect(tile.getAdjacentTile(direction), (duration - ((gasEffects.size() / 4) * 2)), this));

                if (counter < range) {
                    delayExpand(tile.getAdjacentTile(direction));
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
}
