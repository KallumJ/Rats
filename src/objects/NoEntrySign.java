package objects;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import tile.Direction;
import tile.Tile;

/**
 * This class represent the NoEntrySign which will block the way on the rats and
 * make them choose a different direction.
 *
 * @author Fahd
 *
 */
public class NoEntrySign extends GameObject {

    private final Image noEntrySignImage;
    private final Image noEntrySignOneImage;
    private final Image noEntrySignTwoImage;
    private final Image noEntrySignThreeImage;
    private final Image noEntrySignFourImage;
    private final Image noEntrySignFiveImage;
    private int damageDone;
    private final int durability;
    private final ArrayList<NoEntrySignCounter> noEntrySignEffects;
    private final ArrayList<Tile> affectedTiles;

    public static final int DEFAULT_DAMAGE_DONE = 0;
    public static final int DEFAULT_DURABILITY = 6;

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

        noEntrySignImage = new Image(
                ObjectUtils.getObjectImageUrl(GameObjectType.NO_ENTRY_SIGN)
        );
        noEntrySignOneImage = new Image("file:resources/noEntrySign1.png");
        noEntrySignTwoImage = new Image("file:resources/noEntrySign2.png");
        noEntrySignThreeImage = new Image("file:resources/noEntrySign3.png");
        noEntrySignFourImage = new Image("file:resources/noEntrySign4.png");
        noEntrySignFiveImage = new Image("file:resources/noEntrySign5.png");

        this.decideIcon();

        super.getStandingOn().setTraversable(false);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> setUpNoEntrySign()));
        timeline.play();

    }

    private void setUpNoEntrySign() {
        affectedTiles.add(super.getStandingOn().getAdjacentTile(Direction.UP));
        affectedTiles.add(super.getStandingOn().getAdjacentTile(Direction.DOWN));
        affectedTiles.add(super.getStandingOn().getAdjacentTile(Direction.RIGHT));
        affectedTiles.add(super.getStandingOn().getAdjacentTile(Direction.LEFT));

        for (Tile affectedTile : affectedTiles) {
            NoEntrySignCounter newEffect = new NoEntrySignCounter(affectedTile, this);
            noEntrySignEffects.add(newEffect);
            GameObject.getBoard().addObject(newEffect);
        }

    }

    /**
     * A method that decides the icon of the NoEntrySign depending on remaining
     * damage.
     */
    private void decideIcon() {

        switch (durability - damageDone) {

            case 5:
                super.setIcon(noEntrySignFiveImage);
                break;
            case 4:
                super.setIcon(noEntrySignFourImage);
                break;
            case 3:
                super.setIcon(noEntrySignThreeImage);
                break;
            case 2:
                super.setIcon(noEntrySignTwoImage);
                break;
            case 1:
                super.setIcon(noEntrySignOneImage);
                break;
            default:
                super.setIcon(noEntrySignImage);
                break;
        }
    }

    /**
     * A method that do damage to the NoEntrySign when a rat tried to go
     * thorough it.
     */
    public void doDamage() {
        decideIcon();
        GameObject.getBoard().updateBoardDisplay();
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

    /**
     * A method to get the durability remaining on the sign
     *
     * @return the durability remaining on the sign, in hits
     */
    public int getDurability() {
        return durability;
    }
}
