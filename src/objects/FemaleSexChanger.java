package objects;

import display.Board;
import javafx.scene.image.Image;
import objects.rats.PeacefulRat;
import objects.rats.Rat;
import tile.Tile;

/**
 * This class represent the female sex changer item which will change a rat
 * gender to male.
 *
 * @author Fahd
 *
 */
public class FemaleSexChanger extends GameObject {

    private Image femaleSexChangerImage;

    /**
     * Create a new female sex changer item on the specified tile.
     *
     * @param standingOn The tile the female sex changer is on.
     */
    public FemaleSexChanger(Tile standingOn) {
        super(standingOn);

        femaleSexChangerImage = new Image("file:resources/femaleChange.png");
        super.setIcon(femaleSexChangerImage);
    }

    /**
     * Changes the gender of a rat to male.
     *
     * @param rat The rat which it gender will be changed to female.
     */
    public void activationOfFemaleSexChanger(PeacefulRat rat) {

        rat.setGender("f");

        GameObject.getBoard().removeObject(this);
        GameObject.getBoard().updateBoardDisplay();
    }

}
