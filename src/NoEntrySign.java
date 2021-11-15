
import javafx.scene.image.Image;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fahds
 */
public class NoEntrySign extends Item {
    
    private int damageDone;
    private int durability;

    public NoEntrySign(Tile standingOn, Image icon, int damageDone, int durability) {
        super(standingOn, icon);
    }

   @Override
    public void activition(Board board, Rat rat) {
        
        rat.setDirectionOfMovement(rat.turnAround(rat.getDirectionOfMovement()));
        
        this.damageDone = damageDone+1;
        
        if (damageDone >= durability) {
            
            board.removeObject(this);
        }
        
        
    }
    
    public int getDamageDone () {
        
        return this.damageDone;
    }

    @Override
    public void changeIcon(Image icon) {
       
    }
    
}
