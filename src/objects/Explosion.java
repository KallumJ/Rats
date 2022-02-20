package objects;

import tile.Tile;
import javafx.scene.image.Image;

public class Explosion extends GameObject{
    public Explosion(Tile standingOn) {
        super(standingOn);

        Image ExplosionEffect = new Image( "file:resources/ExplosionEffect.gif");
        super.setIcon(ExplosionEffect);

    }

    public void EndExplosion (){
        try {
            GameObject.getBoard().removeObject(this);
        }catch (NullPointerException e){

        }
    }
}
