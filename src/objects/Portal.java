package objects;

import java.util.Random;
import javafx.scene.image.Image;
import objects.rats.Rat;
import tile.Tile;
import tile.TileType;

/**
 * This class represent a portal that takes something for one side and gets it
 * out from the other side instantly.
 *
 * @author Fahd
 */
public class Portal extends GameObject {

    private int x;
    private int y;
    private Tile exitPortal;

    /**
     *
     * @param standingOn The tile the entrance will be one.
     * @param x the x axis of the exit portal on the map
     * @param y the y axis of the exit portal on the map
     */
    public Portal(Tile standingOn, int x, int y) {
        super(standingOn);

        Image portalImage = new Image(ObjectUtils.getObjectImageUrl(GameObjectType.PORTAL));
        super.setIcon(portalImage);

        this.x = x;
        this.y = y;

        if (x == 0 && y == 0) {
            findExitPortal();
        }
    }

    /**
     * This method find an exit to the portal randomly.
     */
    private void findExitPortal() {

        int heightOfMap = GameObject.getBoard().getLevelProperties().getLevelHeight();
        int widthOfMap = GameObject.getBoard().getLevelProperties().getLevelWidth();
        do {
            Random rand = new Random();
            int y2 = rand.nextInt(heightOfMap);
            int x2 = rand.nextInt(widthOfMap);

            exitPortal = GameObject.getBoard().getLevelData().getTileSet().getTile(x2, y2);

        } while (!exitPortal.getTileType().equals(TileType.PATH));

    }

    /**
     * This method transport a rat from the entrance portal to the exit portal.
     *
     * @param rat the rat which will be transported.
     */
    public void enterPortal(Rat rat) {

        rat.standOn(exitPortal);
        GameObject.getBoard().removeObject(this);

    }

}
