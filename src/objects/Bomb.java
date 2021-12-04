package objects;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import sfx.SFXManager;
import tile.Direction;
import tile.Tile;
import tile.TileType;

/**
 * This class represent the bomb item which explode and destroy anything
 * horizontally and vertically until reaches grass.
 *
 * @author Fahd
 */
public class Bomb extends GameObject implements ObjectStoppable {

    public static final int DEFAULT_DURATION = 5;

    private final int duration; // in seconds
    private boolean timerStarted;
    private int timeRemaining;
    private final ArrayList<Tile> affectedTiles;
    private final Image bombImage;
    private final Image bombOneSecondImage;
    private final Image bombTwoSecondsImage;
    private final Image bombThreeSecondsImage;
    private final Image bombFourImage;
    private final Image bombFiveImage;
    private final Timeline bombTimer;

    /**
     * Create a new bomb item on the specified tile.
     *
     * @param standingOn The tile the bomb is on.
     * @param duration The Timer length to explode after its activation.
     * @param timeRemaining the time remaining on the bomb
     * @param timerStarted Indicates if the bomb has been activated.
     */
    public Bomb(Tile standingOn, int duration, int timeRemaining, boolean timerStarted) {
        super(standingOn);

        this.duration = duration;
        this.timerStarted = timerStarted;
        this.timeRemaining = timeRemaining;

        this.affectedTiles = new ArrayList<>();
        findAffectedTiles();

        bombImage = new Image(
                ObjectUtils.getObjectImageUrl(GameObjectType.BOMB)
        );
        super.setIcon(bombImage);
        bombOneSecondImage = new Image("file:resources/bombOneSecond.png");
        bombTwoSecondsImage = new Image("file:resources/bombTwoSeconds.png");
        bombThreeSecondsImage = new Image("file:resources/bombThreeSeconds.png");
        bombFourImage = new Image("file:resources/bombFourSeconds.png");
        bombFiveImage = new Image("file:resources/bombFiveSeconds.png");

        bombTimer = new Timeline(new KeyFrame(Duration.millis(1000), event -> tick()));
        bombTimer.setCycleCount(this.timeRemaining);

        if (this.timerStarted) {
            this.startTimer();
        }

    }

    /**
     * Finds the tiles horizontally and vertically from the start tile until a
     * grass.
     */
    private void findAffectedTiles() {
        for (Direction direction : Direction.values()) {
            findAffectedTilesRecurse(direction, super.getStandingOn());
        }
    }

    private void findAffectedTilesRecurse(Direction direction, Tile currentTile) {
        affectedTiles.add(currentTile);
        if (!currentTile.getAdjacentTile(direction).getTileType().equals(TileType.GRASS)) {
            findAffectedTilesRecurse(direction, currentTile.getAdjacentTile(direction));
        }
    }

    /**
     * Activates and starts the timer of the bomb.
     */
    public void activationOfBomb() {
        if (!this.timerStarted) {
            startTimer();
        }
    }

    /**
     * start the timer of the bomb.
     */
    private void startTimer() {
        bombTimer.play();
        this.timerStarted = true;

    }

    /**
     * Shows the countdown of the last five second before exploding and explode
     * when timer finish.
     */
    private void tick() {
        switch (timeRemaining) {
            case 6:
                super.setIcon(bombFiveImage);
                break;
            case 5:
                super.setIcon(bombFourImage);
                break;
            case 4:
                super.setIcon(bombThreeSecondsImage);
                break;
            case 3:
                super.setIcon(bombTwoSecondsImage);
                break;
            case 2:
                super.setIcon(bombOneSecondImage);
                break;
            default:
                super.setIcon(bombImage);
                break;
        }
        GameObject.getBoard().updateBoardDisplay();

        this.timeRemaining = timeRemaining - 1;

        if (timeRemaining < 1) {
            explode();
        }

    }

    /**
     * destroy anything horizontally and vertically until reaches grass.
     *
     */
    private void explode() {
        for (int i = 0; i < affectedTiles.size(); i++) {
            for (int j = 0; j < GameObject.getBoard().getObjects().size(); j++) {
                if (GameObject.getBoard().getObjects().get(j).getStandingOn().equals(affectedTiles.get(i))) {
                    GameObject.getBoard().removeObject(GameObject.getBoard().getObjects().get(j));
                }
            }
        }

        SFXManager.playBombSFX();
        GameObject.getBoard().removeObject(this);
        GameObject.getBoard().updateBoardDisplay();

    }
    /**
     * A method to get the amount of time left on the timer.
     * @return the amount of time left on the timer in seconds
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Returns whether the timer has started or not
     * @return true if the timer has started, false otherwise
     */
    public boolean isTimerStarted() {
        return timerStarted;
    }

    /**
     * Gets the time remaining on this bomb before explosion
     * @return the time remaining, in seconds
     */
    public int getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * Stops any timelines running in this object
     */
    @Override
    public void stop() {
        if (bombTimer != null) {
            bombTimer.pause();
        }
    }
}
