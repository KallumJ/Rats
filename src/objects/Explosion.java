package objects;

import tile.Tile;
import javafx.scene.image.Image;

/**
 * This class represent the explosion effect of any explosive object.
 *
 * @author Fahd
 * @date 2022/02/23
 */

public class Explosion extends GameObject {
    /**
     *Create a new explosion item on the specified tile.
     *@param standingOn      The tile the explosion effect is on.
     *
     */
    public Explosion(Tile standingOn) {
        super(standingOn);

        Image explosionEffect = new Image("file:resources/ExplosionEffect.gif");
        super.setIcon(explosionEffect);

    }

    /**
     * Removes the explosion effect when called.
     */
    public void endExplosion() {
        try {
            GameObject.getBoard().removeObject(this);
        }
        catch (NullPointerException e) {


        }
    }
}
