package objects.rats;

import java.util.Random;

import display.CustomBoard;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import objects.GameObject;
import objects.ObjectStoppable;
import tile.Direction;
import tile.Tile;
import tile.TileType;

/**
 * This class is the gender to male.
 *
 * @author Fahd
 */
public class Rat extends GameObject implements ObjectStoppable {

	// movement speed of the Rat
	private int speed;

	private Timeline moveTimeline;

	private Direction directionOfMovement;

	protected Rat(Tile standingOn, int speed, Direction directionOfMovement) {

		super(standingOn);
		this.speed = speed;
		this.directionOfMovement = directionOfMovement;

		// Only move rats if this is on a normal Board
		if (!(GameObject.getBoard() instanceof CustomBoard)) {
			moveTimeline = new Timeline(new KeyFrame(Duration.millis(this.speed),
					event -> move()));

			// Loop the timeline forever
			moveTimeline.setCycleCount(Animation.INDEFINITE);
			moveTimeline.play();
		}
	}

	/**
	 * Gets the speed
	 *
	 * @return the speed
	 */
	public int getSpeed() {

		return this.speed;
	}

	/**
	 * Sets the speed
	 *
	 * @param speed the speed
	 */
	public void setSpeed(int speed) {
		// Stop the rat moving
		moveTimeline.pause();

		// Set the new speed, and start a new moving timeline
		this.speed = speed;
		moveTimeline = new Timeline(new KeyFrame(Duration.millis(this.speed),
				event -> move()));
		moveTimeline.setCycleCount(Animation.INDEFINITE);
		moveTimeline.play();
	}

	/**
	 * Gets the direction of movement
	 *
	 * @return the direction of movement
	 */
	public Direction getDirectionOfMovement() {

		return this.directionOfMovement;
	}

	/**
	 * Sets the direction of movement
	 *
	 * @param directionOfMovement the direction of movement
	 */
	public void setDirectionOfMovement(Direction directionOfMovement) {

		this.directionOfMovement = directionOfMovement;
	}

	/**
	 * Turn around
	 *
	 * @param directionOfMovement the direction of movement
	 * @return Direction
	 */
	public Direction turnAround(Direction directionOfMovement) {

		Direction oppositeDirection;

		if (directionOfMovement == Direction.UP) {

			oppositeDirection = Direction.DOWN;
		} else if (directionOfMovement == Direction.LEFT) {

			oppositeDirection = Direction.RIGHT;
		} else if (directionOfMovement == Direction.DOWN) {

			oppositeDirection = Direction.UP;
		} else {

			oppositeDirection = Direction.LEFT;
		}

		return oppositeDirection;
	}

	/**
	 * Stops any timelines running in this object
	 */
	@Override
	public void stop() {
		if (moveTimeline != null) {
			moveTimeline.pause();
		}
	}

	/**
	 * Move - moves left, right and turn around when the rats can otherwise it
	 * doesn't move.
	 */
	private void move() {

		boolean isLeftTurnPossible = (super.getStandingOn()
				.getAdjacentTile(turnLeft(directionOfMovement))
				.isTraversable());

		boolean isRightTurnPossible = (super.getStandingOn()
				.getAdjacentTile(turnRight(directionOfMovement))
				.isTraversable());

		boolean isTurningAroundPossible = (super.getStandingOn()
				.getAdjacentTile(turnAround(directionOfMovement))
				.isTraversable());

		boolean isMoveForwardPossible = (super.getStandingOn()
				.getAdjacentTile(directionOfMovement)
				.isTraversable());

		boolean moveSucceeded = false;
		Random random = new Random();

		// If a move that is not turning around is possible, select one at
		// random
		if (isLeftTurnPossible || isRightTurnPossible || isMoveForwardPossible) {
			while (!moveSucceeded) {
				int decision = random.nextInt(3);

				switch (decision) {
					case 0: // Move forward
						if (isMoveForwardPossible) {
							super.standOn(super.getStandingOn()
									.getAdjacentTile(directionOfMovement));
							moveSucceeded = true;
						}
						break;
					case 1: // Move left
						if (isLeftTurnPossible) {
							super.standOn(super.getStandingOn()
									.getAdjacentTile(turnLeft(directionOfMovement)));
							this.directionOfMovement =
									turnLeft(directionOfMovement);
							moveSucceeded = true;
						}
						break;
					case 2: // Move right
						if (isRightTurnPossible) {
							super.standOn(super.getStandingOn()
									.getAdjacentTile(turnRight(directionOfMovement)));
							this.directionOfMovement =
									turnRight(directionOfMovement);
							moveSucceeded = true;
						}
						break;
				}
			}
		} else { // No other direction is possible, turn around
			if (isTurningAroundPossible) {
				super.standOn(super.getStandingOn()
						.getAdjacentTile(turnAround(directionOfMovement)));
				this.directionOfMovement = turnAround(directionOfMovement);
			}
		}

		if (super.getStandingOn().getTileType().equals(TileType.TUNNEL)) {

			super.setIcon(null);
		} else {
			if (this instanceof PeacefulRat) {

				PeacefulRat rat = (PeacefulRat) this;
				rat.decideIcon();
			} else if (this instanceof DeathRat) {

				DeathRat rat = (DeathRat) this;
				rat.showIcon();
			}
		}

		// Update the display
		if (GameObject.getBoard() != null) {
			GameObject.getBoard().updateBoardDisplay();
		}
	}

	/**
	 * Turn left
	 *
	 * @param directionOfMovement the direction of movement
	 * @return Direction
	 */
	private Direction turnLeft(Direction directionOfMovement) {

		Direction leftOfDirection;

		if (directionOfMovement == Direction.UP) {

			leftOfDirection = Direction.LEFT;
		} else if (directionOfMovement == Direction.LEFT) {

			leftOfDirection = Direction.DOWN;
		} else if (directionOfMovement == Direction.DOWN) {

			leftOfDirection = Direction.RIGHT;
		} else {

			leftOfDirection = Direction.UP;
		}

		return leftOfDirection;
	}

	/**
	 * Turn right
	 *
	 * @param directionOfMovement the direction of movement
	 * @return Direction
	 */
	private Direction turnRight(Direction directionOfMovement) {

		Direction rightOfDirection;

		if (directionOfMovement == Direction.UP) {

			rightOfDirection = Direction.RIGHT;
		} else if (directionOfMovement == Direction.LEFT) {

			rightOfDirection = Direction.UP;
		} else if (directionOfMovement == Direction.DOWN) {

			rightOfDirection = Direction.LEFT;
		} else {

			rightOfDirection = Direction.DOWN;
		}

		return rightOfDirection;
	}
}
