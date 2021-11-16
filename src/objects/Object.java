package objects;

import javafx.scene.image.Image;
import tile.Tile;


/**
 *
 * @author fahds
 */
public abstract class Object {
    
    // Loaded icon of the object
    private Image icon;
    
    // The tile.Tile the objetc is currently standing on
    private Tile standingOn;
    
    protected Object (Tile standingOn, Image icon){
        
        this.icon = icon;
        this.standingOn = standingOn;   
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
