package objects;

import javafx.scene.image.Image;
import objects.rats.Rat;
import tile.Tile;
import tile.TileType;

import java.util.Random;

/**
 * This class represent a portal that takes something for one side and gets it
 * out from the other side instantly.
 *
 * @author Fahd
 * @date 2022.02.21
 *
 */
public class Portal extends GameObject {

    /**
     * @param standingOn The tile the entrance will be one.
     */
    public Portal(Tile standingOn) {
        super(standingOn);

        Image portalImage = new Image(ObjectUtils.getObjectImageUrl(GameObjectType.PORTAL));
        super.setIcon(portalImage);
    }

    /**
     * This method transport a rat from the entrance portal to the exit portal.
     *
     * @param rat the rat which will be transported.
     */
    public void enterPortal(Rat rat) {
        int heightOfMap = GameObject.getBoard().getLevelProperties().getLevelHeight();
        int widthOfMap = GameObject.getBoard().getLevelProperties().getLevelWidth();
        Tile exitTile;
        do {
            Random rand = new Random();
            int y2 = rand.nextInt(heightOfMap);
            int x2 = rand.nextInt(widthOfMap);

            exitTile = GameObject.getBoard().getLevelData().getTileSet().getTile(x2, y2);

        } while (!exitTile.getTileType().equals(TileType.PATH));

        rat.standOn(exitTile);
        GameObject.getBoard().removeObject(this);

    }

}
