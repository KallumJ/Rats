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
    
	/** 
	 * Sets the board
	 * @param board  the board
	 */
    public static void setBoard(Board board) {
        GameObject.board = board;
    }

	/** 
	 * Gets the board
	 * @return the board
	 */
    public static Board getBoard() {

        return GameObject.board;
    }
	/** 
	 * Gets the icon
	 * @return the icon
	 */
    public Image getIcon() {
        
        return this.icon;
    }
    
    public abstract void changeIcon (Image icon); 
    
	/** 
	 * Change icon
	 * @param icon  the icon
	 */
    public void setIcon (Image icon) {
        
        this.icon = icon;
    }
	/** 
	 * Gets the standing on
	 * @return the standing on
	 */
    public Tile getStandingOn() {
        
        return this.standingOn;
    }
    
	/** 
	 * Stand on
	 * @param standingOn  the standing on
	 */
    public void standOn (Tile standingOn) {
        
        this.standingOn = standingOn;
    }
    
}
