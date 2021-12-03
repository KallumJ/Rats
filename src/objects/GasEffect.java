/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

import javafx.scene.image.Image;
import tile.Tile;

/**
 *
 * @author fahds
 */
public class GasEffect extends GameObject{
    
    private int duration;
    private Gas sourceGas;
    private Image gasEffectImage;

    public GasEffect(Tile standingOn, int duration, Gas sourceGas) {
        super(standingOn);
        
        this.duration = duration;
        this.sourceGas = sourceGas;
        
        gasEffectImage = new Image ("file:resources/gasEffect.png");
        super.setIcon(gasEffectImage);
        
        GameObject.getBoard().addObject(this);
    }
    
}
