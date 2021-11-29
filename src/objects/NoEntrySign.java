package objects;

import display.Board;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import objects.rats.Rat;
import tile.Direction;
import tile.Tile;

/**
 * This class represent the no entry sign which will block the way on the rats
 * and make them choose a different direction.
 *
 * @author Fahd
 *
 */
public class NoEntrySign extends GameObject {

    private Image noEntrySignImage;
    private int damageDone;
    private int durability;
    private ArrayList<NoEntrySignEffect> noEntrySignEffects;
    private ArrayList<Tile> affectedTiles;
    private boolean recentlyActivated;
    private static final int DELAY = 1;

    public static final int DEFAULT_DAMAGE_DONE = 0;
    public static final int DEFAULT_DURABILITY = 5;

    /**
     * Creates a new no entry sign item on the specified tile
     *
     * @param standingOn The tile the no entry sign is on.
     * @param damageDone The damage done to the entry sign.
     * @param durability The durability of the sign before it breaks.
     */
    public NoEntrySign(Tile standingOn, int damageDone, int durability) {
        super(standingOn);

        noEntrySignEffects = new ArrayList<>();
        affectedTiles = new ArrayList<>();
        this.damageDone = damageDone;
        this.durability = durability;

        noEntrySignImage = new Image("file:resources/noEntrySign.png");
        super.setIcon(noEntrySignImage);

        super.getStandingOn().setTraversable(false);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> XXXX()));
        timeline.play();

    }

    private void XXXX() {
        affectedTiles.add(super.getStandingOn().getAdjacentTile(Direction.UP));
        affectedTiles.add(super.getStandingOn().getAdjacentTile(Direction.DOWN));
        affectedTiles.add(super.getStandingOn().getAdjacentTile(Direction.RIGHT));
        affectedTiles.add(super.getStandingOn().getAdjacentTile(Direction.LEFT));

        for (Tile affectedTile : affectedTiles) {
            NoEntrySignEffect newEffect = new NoEntrySignEffect(affectedTile, this);
            noEntrySignEffects.add(newEffect);
            GameObject.getBoard().addObject(newEffect);
        }

    }

    public void doDamage() {
        this.damageDone = damageDone + 1;

        if (damageDone >= durability) {
            super.getStandingOn().setTraversable(true);
            GameObject.getBoard().removeObject(this);

            for (int i = 0; i < noEntrySignEffects.size(); i++) {
                GameObject.getBoard().removeObject(noEntrySignEffects.get(i));
            }

        }
        GameObject.getBoard().updateBoardDisplay();
    }

    /**
     * Gets the damage done to the sign
     *
     * @return The damage done to the sign.
     */
    public int getDamageDone() {

        return this.damageDone;
    }

}
