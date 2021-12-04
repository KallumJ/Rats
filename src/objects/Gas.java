package objects;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import level.LevelUtils;
import static objects.Sterilisation.DELAY_DURING_CONSTRUCTION;
import objects.rats.Rat;
import tile.Direction;
import tile.Tile;

/**
 *
 * @author fahds
 */
public class Gas extends GameObject {

    public static final int DEFAULT_DURATION = 16;
    public static final int DEFAULT_RANGE = 4;
    private int duration;
    private int counter;
    private int range;
    private Image gasImage;
    private ArrayList<GasEffect> gasEffects;
     private ArrayList<Rat> ratsInGas;

    public Gas(Tile standingOn, int duration, int range) {
        super(standingOn);

        this.duration = duration;
        this.range = range;
        counter = 0;
        gasEffects = new ArrayList<>();
        ratsInGas = new ArrayList<>();
        gasImage = new Image(
                ObjectUtils.getObjectImageUrl(GameObjectType.GAS)
        );
        super.setIcon(gasImage);
    }

    public void activateGas() {
        GameObject.getBoard().removeObject(this);
        expand(super.getStandingOn());
        gasEffects.add(new GasEffect(super.getStandingOn(), (duration + 2), this));

    }

    private void delayExpand(Tile tile) {

        Timeline delayTimer = new Timeline(new KeyFrame(Duration.seconds(2), event -> expand(tile)));
        delayTimer.play();
    }

    public void expand(Tile tile) {

        for (Direction direction : Direction.values()) {
            Tile adjacentTile = tile.getAdjacentTile(direction);
            List<GameObject> objectsOnBoard = GameObject.getBoard().getObjects();

            if (adjacentTile.isTraversable()) {
                boolean isGasNotPresent =
                        LevelUtils.getObjectsOnTile(adjacentTile, objectsOnBoard)
                                .stream().noneMatch(
                                        object -> object instanceof GasEffect
                                );

                if (isGasNotPresent) {
                    gasEffects.add(new GasEffect(adjacentTile, (duration - ((gasEffects.size() / 4) * 2)), this));
                }

                if (counter < range) {
                    delayExpand(adjacentTile);
                } else {
                    for (GasEffect gasEffect : gasEffects) {

                        gasEffect.disappear();
                    }
                }
            }

        }
        this.counter++;
    }

    public int getDuration() {
        return duration;
    }

    public int getRange() {
        return range;
    }
    
    public void startChoking (Rat rat) {
        
        ratsInGas.add(rat);
        Timeline delay = new Timeline(new KeyFrame(Duration.seconds(3),event -> stillHere(rat)));
        delay.play();
    }
    
    public void stillHere (Rat rat) {
        
        if (stillInGas(rat)){
            GameObject.getBoard().removeObject(rat);
            ratsInGas.remove(rat);
            
        }else {
            ratsInGas.remove(rat);
        }
        
    }
    
    private boolean stillInGas (Rat rat){
        
        boolean answer = false;
       for (GasEffect gasEffect : gasEffects) {
           
           if (gasEffect.getStandingOn().equals(rat.getStandingOn())) {
               answer = true;
           }
       }
       return answer;
    }
    
    public ArrayList<Rat> getRatsInGas () {
        
        return this.ratsInGas;
    }
}
