package objects.rats;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import objects.GameObject;
import objects.ObjectStoppable;
import sfx.SFXManager;
import tile.Direction;
import tile.Tile;

import java.sql.Time;

/**
 * The class Death rat extends rat
 *
 * @author fahds
 */
public class DeathRat extends Rat implements ObjectStoppable {

	public static final Direction DEFAULT_DIRECTION = Direction.UP;
	public static final int DEFAULT_NUM_OF_KILLS = 0;
	public static final int DEFAULT_KILLS_TARGET = 5;
	private static final int DELAY_MOVE = 1;
	private final int killsTarget;
	private final Image deathRatImage;
	private final Timeline delaySpeed;
	private int numberOfKills;

	/**
	 * Constructs Death rat
	 *
	 * @param standingOn          the standing on
	 * @param speed               the speed
	 * @param directionOfMovement the direction of movement
	 * @param killsTarget         the kills target
	 */
	public DeathRat(Tile standingOn, int speed, Direction directionOfMovement,
					int numberOfKills, int killsTarget) {
		super(standingOn, speed, directionOfMovement);

		this.killsTarget = killsTarget;
		this.numberOfKills = numberOfKills;

		deathRatImage = new Image("file:resources/deathRat.png");
		super.setIcon(deathRatImage);

		super.setSpeed(0);
		delaySpeed = new Timeline(new KeyFrame(Duration.seconds(DELAY_MOVE),
				event -> resetSpeed()));
		delaySpeed.play();
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

	public void showIcon() {

		super.setIcon(deathRatImage);
	}

	/**
	 * Stops any timelines that are in progress
	 */
	@Override
	public void stop() {
		if (delaySpeed != null) {
			delaySpeed.pause();
		}
	}

	/**
	 * Resets the speed of the death rat
	 */
	private void resetSpeed() {
		int speed = GameObject.getBoard()
				.getLevelProperties()
				.getDeathRatSpeed();
		super.setSpeed(speed);
	}
}
