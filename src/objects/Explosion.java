package objects;

import tile.Tile;
import javafx.scene.image.Image;

/**
 * This class represent the explosion item which destroys the surrounding
 * until it reaches the grass.
 *
 * @author Fahd & Aser (javadoc)
 * @date 2022/02/21
 */

public class Explosion extends GameObject {
    public Explosion(Tile standingOn) {
        super(standingOn);

        Image ExplosionEffect = new Image("file:resources/ExplosionEffect.gif");
        super.setIcon(ExplosionEffect);

    }

    public void EndExplosion() {
        try {
            GameObject.getBoard().removeObject(this);
        } catch (NullPointerException e) {

            /**
             *Create a new explosion item on the specified tile.
             *@param standingOn      The tile the explosion is on.
             *@param ExplosionEffect The sound and gif display that comes up when explosion occurs.
             *@param EndExplosion    The after effect of the surrounding and whether a rat has
             *                      been killed or not.
             */

        }
    }
}
