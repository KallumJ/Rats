package objects.rats;

import javafx.animation.Animation;
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
 */

public class ZombieRat extends Rat{

    private Timeline disappearingTimeline;
    private int timeToDisappear; // life time for the zombie in seconds

    /**
     * Creats a new zombie rat.
     * @param standingOn The tile the rat is standing on.
     * @param speed Movement speed of the rat.
     * @param directionOfMovement Direction of the rat movement;
     * @param timeToDisappear life time for the zombie in seconds.
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
    private void Disappear (){

        this.timeToDisappear--;
        if (this.timeToDisappear <= 0) {

            GameObject.getBoard().removeObject(this);
        }
    }

    /**
     * Performs a bite action which will turn the victim into a zombie.
     * @param rat The victim.
     */
    public void Bite (PeacefulRat rat){

        GameObject.getBoard().removeObject(rat);
        int speed = GameObject.getBoard()
                .getLevelProperties()
                .getDeathRatSpeed();
        ZombieRat zombie = new ZombieRat(rat.getStandingOn(),speed,
                rat.getDirectionOfMovement(),10);
    }

    /**
     * Method which gives back the time left for the zombie rat to disappear.
     * @return time left for the zombie rat to disappear.
     */
    public int getTimeToDisappear () {

        return this.timeToDisappear;
    }
}