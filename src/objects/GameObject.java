package objects;

import display.Board;
import javafx.scene.image.Image;
import tile.Tile;

/**
 * Superclass for GameObjects.
 *
 * @author Fahd
 * @date 2022/02/20
 */
public class GameObject {

    private static Board board;

    // Loaded icon of the object
    private Image icon;

    // The Tile the object is currently standing on
    private Tile standingOn;

    protected GameObject(Tile standingOn) {

        this.standingOn = standingOn;
    }

    /**
     * Gets the board.
     *
     * @return the board
     */
    public static Board getBoard() {

        return GameObject.board;
    }

    /**
     * Sets the board.
     *
     * @param board the board
     */
    public static void setBoard(Board board) {
        GameObject.board = board;
    }

    /**
     * Gets the icon.
     *
     * @return the icon
     */
    public Image getIcon() {

        return this.icon;
    }

    /**
     * Change icon.
     *
     * @param icon the icon
     */
    public void setIcon(Image icon) {

        this.icon = icon;
    }

    /**
     * Gets the standing on.
     *
     * @return the standing on
     */
    public Tile getStandingOn() {

        return this.standingOn;
    }

    /**
     * Stand on.
     *
     * @param standingOn the standing on
     */
    public void standOn(Tile standingOn) {

        this.standingOn = standingOn;
    }
}
