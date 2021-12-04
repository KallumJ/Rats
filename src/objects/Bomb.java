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
public class Bomb extends GameObject {

    private int timerLength; // in seconds
    private boolean timerStarted;
    private int timerRemainingTime;
    private Timeline tickTimeline1;
    private ArrayList<Tile> affectedTiles;
    private Image bombImage;
    private Image bombOneSecondImage;
    private Image bombTwoSecondsImage;
    private Image bombThreeSecondsImage;
    private Image bombFourImage;
    private Image bombFiveImage;

    public static final int DEFAULT_LENGTH = 5;

    /**
     * Create a new bomb item on the specified tile.
     *
     * @param standingOn The tile the bomb is on.
     * @param timerLength The Timer length to explode after it activation.
     * @param timerStarted Indicates if the bomb has been activated.
     */
    public Bomb(Tile standingOn, int timerLength, boolean timerStarted) {
        super(standingOn);

        this.timerLength = timerLength;
        this.timerStarted = timerStarted;
        this.timerRemainingTime = timerLength;
        this.affectedTiles = new ArrayList<Tile>();
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
        tickTimeline1 = new Timeline(new KeyFrame(Duration.millis(1000), event -> tick()));
        // Loop the timeline forever
        tickTimeline1.setCycleCount(this.timerLength);

        tickTimeline1.play();
        this.timerStarted = true;

    }

    /**
     * Shows the countdown of the last five second before exploding and explode
     * when timer finish.
     */
    private void tick() {

        switch (timerRemainingTime) {

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

        this.timerRemainingTime = timerRemainingTime - 1;

        if (timerRemainingTime < 1) {

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
     * Gets the horizontal and vertical tiles until reaches grass.
     *
     * @return The horizontal and vertical tiles until reaches grass.
     */
    public ArrayList<Tile> getAffectedTiles() {

        return this.affectedTiles;
    }

    /**
     * Sets the horizontal and vertical tiles until reaches grass.
     *
     * @param affectedTiles The horizontal and vertical tiles until reaches
     * grass.
     */
    public void setAffectedTiles(ArrayList<Tile> affectedTiles) {

        this.affectedTiles = affectedTiles;
    }

    /**
     * A method to get the amount of time left on the timer.
     * @return the amount of time left on the timer in seconds
     */
    public int getTimerLength() {
        return timerLength;
    }
}
