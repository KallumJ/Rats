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

public class ZombieRat extends Rat{

    private Timeline disappearingTimeline;
    private int timeToDisappear;

    public ZombieRat(Tile standingOn, int speed, Direction directionOfMovement, int timeToDisappear) {
        super(standingOn, speed, directionOfMovement);

        Image zombieImage = new Image(ObjectUtils.getObjectImageUrl(GameObjectType.ZOMBIE_RAT));
        super.setIcon(zombieImage);

        this.timeToDisappear = timeToDisappear;

        disappearingTimeline = new Timeline(new KeyFrame(Duration.millis(this.timeToDisappear),
                event -> Disappear()));
        disappearingTimeline.play();

    }

    private void Disappear (){

        GameObject.getBoard().removeObject(this);
    }

    public void Bite (PeacefulRat rat){

        GameObject.getBoard().removeObject(rat);
        int speed = GameObject.getBoard()
                .getLevelProperties()
                .getDeathRatSpeed();
        ZombieRat zombie = new ZombieRat(rat.getStandingOn(),speed,
                rat.getDirectionOfMovement(),10);
    }

    public int getTimeToDisappear () {

        return this.timeToDisappear;
    }
}