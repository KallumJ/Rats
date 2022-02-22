package objects.rats;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import objects.GameObject;
import objects.GameObjectType;
import objects.ObjectUtils;
import tile.Direction;
import tile.Tile;

/**
 * This class represent the zombie rats which will bit other rats and turn them into zombies.
 *
 * @author Fahd
 * @date 2022/02/21
 */

public class ZombieRat extends Rat {

    private Timeline disappearingTimeline;
    private int timeToDisappear; // lifetime for the zombie in seconds

    /**
     * Creates a new zombie rat.
     *
     * @param standingOn          The tile the rat is standing on.
     * @param speed               Movement speed of the rat.
     * @param directionOfMovement Direction of the rat movement;
     * @param timeToDisappear     lifetime for the zombie in seconds.
     */
    public ZombieRat(Tile standingOn, int speed, Direction directionOfMovement, int timeToDisappear) {
        super(standingOn, speed, directionOfMovement);

        Image zombieImage = new Image(ObjectUtils.getObjectImageUrl(GameObjectType.ZOMBIE_RAT));
        super.setIcon(zombieImage);

        this.timeToDisappear = timeToDisappear;

        disappearingTimeline = new Timeline(new KeyFrame(Duration.millis(1000),
                event -> Disappear()));
        disappearingTimeline.setCycleCount(this.timeToDisappear);
        disappearingTimeline.play();

    }

    /**
     * Method which will make the rat disappear.
     */
    private void Disappear() {

        this.timeToDisappear--;
        if (this.timeToDisappear <= 0) {

            try {
                GameObject.getBoard().removeObject(this);
            } catch (NullPointerException e) {


            }
        }
    }

    /**
     * Performs a bite action which will turn the victim into a zombie.
     *
     * @param rat The victim.
     */
    public void Bite(PeacefulRat rat) {

        GameObject.getBoard().removeObject(rat);
        int speed = GameObject.getBoard()
                .getLevelProperties()
                .getDeathRatSpeed();
        ZombieRat zombie = new ZombieRat(rat.getStandingOn(), speed,
                rat.getDirectionOfMovement(), this.timeToDisappear);
        GameObject.getBoard().addObject(zombie);
    }

    /**
     * Method which gives back the time left for the zombie rat to disappear.
     *
     * @return time left for the zombie rat to disappear.
     */
    public int getTimeToDisappear() {

        return this.timeToDisappear;
    }
}