/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import tile.Tile;

/**
 *
 * @author fahds
 */
public class GasEffect extends GameObject {

    private int duration;
    private Gas sourceGas;
    private Image gasEffectImage;
    private Timeline disappearTimer;

    public GasEffect(Tile standingOn, int duration, Gas sourceGas) {
        super(standingOn);

        this.duration = duration;
        this.sourceGas = sourceGas;

        gasEffectImage = new Image(
                ObjectUtils.getObjectImageUrl(GameObjectType.GAS)
        );
        super.setIcon(gasEffectImage);

        GameObject.getBoard().addObject(this);
    }

    public void disappear() {

        disappearTimer = new Timeline(new KeyFrame(Duration.seconds(duration), event -> removeEffect()));
        disappearTimer.play();
    }

    private void removeEffect() {

        GameObject.getBoard().removeObject(this);
    }

}
