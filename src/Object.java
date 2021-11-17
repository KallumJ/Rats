
import javafx.scene.image.Image;


/**
 *
 * @author fahds
 */
public abstract class Object {
    
    // Loaded icon of the object
    private Image icon;
    
    // The Tile the objetc is currently standing on
    private Tile standingOn;
    
     private static Board board;
    
    protected Object (Tile standingOn){
        
        this.standingOn = standingOn;   
    }
    
    public static void setBoard(Board board) {

        Object.board = board;
    }

    public static Board getBoard() {

        return Object.board;
    }

    public Image getIcon() {
        
        return this.icon;
    }
    
    public abstract void changeIcon (Image icon); 
    
    public void setIcon (Image icon) {
        
        this.icon = icon;
    }
    
    public Tile getStandingOn() {
        
        return this.standingOn;
    }
    
    public void standOn (Tile standingOn) {
        
        this.standingOn = standingOn;
    }
    
}
