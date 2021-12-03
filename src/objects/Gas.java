/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

import java.util.ArrayList;
import javafx.scene.image.Image;
import tile.Direction;
import tile.Tile;

/**
 *
 * @author fahds
 */
public class Gas extends GameObject{
    
    private  int duration;
    private int counter;
    private int range;
    private Image gasImage;
    private ArrayList<GasEffect> gasEffects;

    public Gas(Tile standingOn, int duration, int range) {
        super(standingOn);
        
        this.duration =  duration;
        this.range = range;
        counter = 0;
        gasEffects = new ArrayList<>();
        gasImage = new Image ("file:resources/gas.png");
        super.setIcon(gasImage);
    }
    
    public void activateGas () {
        GameObject.getBoard().removeObject(this);
        
        
    }
    
    public void expand (Tile tile){
        
        for (Direction direction : Direction.values()) {
            
            if (tile.getAdjacentTile(direction).isTraversable()){
                gasEffects.add(new GasEffect(tile.getAdjacentTile(direction), duration-(counter*5), this));  
                
                if (counter < range){
                expand(tile.getAdjacentTile(direction));
                } else {
                    // starts timer after timer finish start dissapates 
                }
            }
            this.counter++;
            
        }
    }
    
}
