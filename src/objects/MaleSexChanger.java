package objects;

import display.Board;
import javafx.scene.image.Image;
import objects.rats.PeacefulRat;
import objects.rats.Rat;
import tile.Tile;

/**
 * This class represent the male sex changer item which will change a rat gender
 * to male.
 *
 * @author Fahd
 *
 */
public class MaleSexChanger extends GameObject {

    private Image maleSexChangerImage;

    /**
     * Create a new male sex changer item on the specified tile.
     *
     * @param standingOn The tile the male sex changer is on.
     */
    public MaleSexChanger(Tile standingOn) {
        super(standingOn);

        maleSexChangerImage = new Image("file:resources/maleChange.png");
        super.setIcon(maleSexChangerImage);
    }

    /**
     * Changes the gender of a rat to male.
     *
     * @param rat The rat which it gender will be changed to male.
     */
    public void beMale(PeacefulRat rat) {

        rat.setGender("m");

        GameObject.getBoard().removeObject(this);
        GameObject.getBoard().updateBoardDisplay();
    }

}
