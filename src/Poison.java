
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
public class Poison extends Item{

    public Poison(Tile standingOn) {
        super(standingOn, null);
    }

    @Override
    public void activition(Board board, Rat rat) {
        
        board.removeObject(rat);
        board.removeObject(this);
    }

    @Override
    public void changeIcon(Image icon) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
