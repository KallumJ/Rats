package objects;

import javafx.scene.image.Image;
import objects.rats.Rat;
import tile.Tile;

/**
 * This class represent the poison it which will kill any rat in case of
 * contact.
 *
 * @author Fahd
 */
public class Poison extends GameObject {

    private Image poisonImage;

    /**
     * Creates a new sterilisation effect object on the specified tile.
     *
     * @param standingOn The tile the poison is on.
     *
     */
    public Poison(Tile standingOn) {
        super(standingOn);

        poisonImage = new Image(
                ObjectUtils.getObjectImageUrl(GameObjectType.POISON)
        );
        super.setIcon(poisonImage);
    }

    /**
     * Gives poison to a victim.
     *
     * @param victim The rat which will take the poison and die.
     */
    public void givePoison(Rat victim) {

        GameObject.getBoard().removeObject(victim);
        GameObject.getBoard().removeObject(this);
        GameObject.getBoard().updateBoardDisplay();
    }

}
