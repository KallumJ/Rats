
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
    
    protected Object (Tile standingOn, Image icon){
        
        this.icon = icon;
        this.standingOn = standingOn;   
    }
    
}
