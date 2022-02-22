package objects;

import javafx.scene.image.Image;
import objects.rats.PeacefulRat;
import tile.Tile;

/**
 * This class represent the sterilisation effect which will make any rat sterile
 * when contact happens.
 *
 * @author Fahd
 * @date 2022.02.21
 */
public class SterilisationEffect extends GameObject {

    /**
     * Creates a new sterilisation effect object on the specified tile.
     *
     * @param standingOn The tile the sterilisation effect will be active on.
     */
    public SterilisationEffect(Tile standingOn) {
        super(standingOn);

        Image sterilisationEffectImage = new Image("file:resources" +
                "/SterilisationEffect.png");
        super.setIcon(sterilisationEffectImage);

    }

    /**
     * Makes a rat sterile.
     *
     * @param rat The Rat which will be sterile.
     */
    public void beSterile(PeacefulRat rat) {

        rat.setSterilisation(true);
    }

}
