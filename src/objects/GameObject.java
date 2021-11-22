package objects;

import display.Board;
import javafx.scene.image.Image;
import tile.Tile;


/**
 *
 * @author fahds
 */
public abstract class GameObject {
    
    // Loaded icon of the object
    private Image icon;
    
    // The tile.Tile the objetc is currently standing on
    private Tile standingOn;
    
     private static Board board;
    
    protected GameObject (Tile standingOn){
        
        this.standingOn = standingOn;   
    }
    
    public static void setBoard(Board board) {
        GameObject.board = board;
    }

    public static Board getBoard() {

        return GameObject.board;
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
