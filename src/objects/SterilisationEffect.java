/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

import javafx.scene.image.Image;
import objects.rats.PeacefulRat;
import tile.Tile;

/**
 *
 * @author fahds
 */
public class SterilisationEffect extends GameObject{
    
    private Image SterilisationEffectImage;
    
    /**
     *
     * @param standingOn
     */
    public SterilisationEffect(Tile standingOn){
        super(standingOn);
        
        SterilisationEffectImage = new Image("file:resources/SterilisationEffect.png");
        super.setIcon(SterilisationEffectImage);
    
    }
    
    public void beSterile (PeacefulRat rat) {
        
        rat.setSterilisation(true);
    }
    
}
