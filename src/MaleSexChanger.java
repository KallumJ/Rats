
import javafx.scene.image.Image;
import tile.Tile;


/**
 *
 * @author fahds
 */
public class MaleSexChanger extends Item{
    
    public MaleSexChanger(Tile standingOn) {
        super(standingOn, null);
    }

    @Override
    public void activition(Board board, Rat rat) {
        
        PeacefulRat pRat = (PeacefulRat) rat;
        pRat.setGender("Male");
        
        board.removeObject(this);   
    }

    @Override
    public void changeIcon(Image icon) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
