package objects.rats;

import javafx.scene.image.Image;
import objects.GameObject;
import sfx.SFXManager;
import tile.Direction;
import tile.Tile;

/**
 * The class Death rat extends rat
 *
 *@author fahds
 */
public class DeathRat extends Rat {

    public static final Direction DEFAULT_DIRECTION = Direction.UP;
    public static final int DEFAULT_NUM_OF_KILLS = 0;
    public static final int DEFAULT_KILLS_TARGET = 5;

    private int numberOfKills;
    private final int killsTarget;
    private final Image deathRatImage;

    /**
     * Constructs Death rat
     *
     * @param standingOn the standing on
     * @param speed the speed
     * @param directionOfMovement the direction of movement
     * @param killsTarget the kills target
     */
    public DeathRat(Tile standingOn, int speed, Direction directionOfMovement,
            int numberOfKills, int killsTarget) {
        super(standingOn, speed, directionOfMovement);

        this.killsTarget = killsTarget;
        this.numberOfKills = numberOfKills;

        deathRatImage = new Image("file:resources/deathRat.png");
        super.setIcon(deathRatImage);
    }

    /**
     * Kill's the provided rat
     *
     * @param victim the victim
     */
    public void kill(Rat victim) {

        GameObject.getBoard().removeObject(victim);
        this.numberOfKills = this.numberOfKills + 1;

        if (this.killsTarget <= this.numberOfKills) {
            GameObject.getBoard().removeObject(this);
        }

        SFXManager.playDeathratSFX();
    }

    /**
     * Gets the number of kills
     *
     * @return the number of kills
     */
    public int getNumberOfKills() {

        return this.numberOfKills;
    }
    
    public void showIcon () {
        
        super.setIcon(deathRatImage);
    }

}
