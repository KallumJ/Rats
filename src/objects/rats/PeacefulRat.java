package objects.rats;

import java.util.Random;
import java.util.Timer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.scene.image.Image;
import javafx.util.Duration;
import objects.GameObject;
import tile.Direction;
import tile.Tile;

/**
 *
 * @author fahds
 */
public class PeacefulRat extends Rat {

    private boolean sterile;
    private boolean adult;
    private boolean pregnant;
    private String gender;
    private int timeToGiveBirth;
    private int timeToDevelop;
    private Timer timer;
    private int numberOfBabies;
    private Image maleRatImage;
    private Image babyRatImage;
    private Image femaleRatImage;
    private Image pregnantFemaleRatImage;
    private Timeline pregnancyTimer;
    private Timeline developmentTimer;

    public PeacefulRat(Tile standingOn, boolean sterile, boolean adult, boolean pregnant, String gender,
            int timeToGiveBirth, int timeToDevelop, int speed, Direction directionOfMovement) {
        super(standingOn, speed, directionOfMovement);

        this.sterile = sterile;
        this.adult = adult;
        this.pregnant = pregnant;
        this.gender = gender;
        this.timeToDevelop = timeToDevelop;
        this.timeToGiveBirth = timeToGiveBirth;

        if (!this.adult) {

            developmentTimer = new Timeline(new KeyFrame(Duration.seconds(this.timeToDevelop), event -> growUp()));
            developmentTimer.play();
        }
        if (this.pregnant) {

            this.bePregnant();
        }

        maleRatImage = new Image("file:resources/maleRat.png");
        babyRatImage = new Image("file:resources/babyRat.png");
        femaleRatImage = new Image("file:resources/femaleRat.png");
        pregnantFemaleRatImage = new Image("file:resources/pregnantFemaleRat.png");
        decideIcon(adult, pregnant, gender);

    }

    private void decideIcon(boolean adult, boolean pregnant, String gender) {

        Image decidedIcon;

        if (!adult) {

            decidedIcon = babyRatImage;
        } else if (gender.equalsIgnoreCase("m")) {

            decidedIcon = maleRatImage;
        } else if (pregnant) {

            decidedIcon = pregnantFemaleRatImage;
        } else {

            decidedIcon = femaleRatImage;
        }

        super.setIcon(decidedIcon);
    }

    public boolean isSterile() {

        return this.sterile;
    }

    public boolean isAdult() {

        return this.adult;
    }

    public boolean isPregnant() {

        return this.pregnant;
    }

    public String getGender() {

        return this.gender;
    }

    public void mate(PeacefulRat partner) {

        if (partner.getGender().equalsIgnoreCase("f") && (!partner.isPregnant())) {
            if ((!(this.isSterile() || partner.isSterile())) && (this.isAdult() && partner.isAdult())) {

                partner.bePregnant();
            }
        }

    }

    public void bePregnant() {
        pregnancyTimer = new Timeline(new KeyFrame(Duration.seconds(this.timeToGiveBirth), event -> giveBirth()));

        this.pregnant = true;
        this.decideIcon(adult, pregnant, gender);

        Random random = new Random();
        this.numberOfBabies = random.nextInt((GameObject.getBoard().getLevelProperties().getRatMaxBabies()
                - GameObject.getBoard().getLevelProperties().getRatMinBabies()) + 1)
                + GameObject.getBoard().getLevelProperties().getRatMinBabies();
        pregnancyTimer.play();

    }

    private void giveBirth() {

        this.pregnant = false;
        decideIcon(adult, pregnant, gender);

        for (int i = 0; i < this.numberOfBabies; i++) {
            String newBornGender;

            Random random = new Random();
            int decision = random.nextInt(2);

            if (decision == 1) {

                newBornGender = "m";
            } else {

                newBornGender = "f";
            }

            GameObject newBorn = new PeacefulRat(super.getStandingOn(), this.isSterile(), false, false, newBornGender,
                    this.timeToGiveBirth, this.timeToDevelop, GameObject.getBoard().getLevelProperties()
                    .getBabyRatSpeed(), super.getDirectionOfMovement());

            GameObject.getBoard().addObject(newBorn);
        }

    }

    public void setGender(String gender) {

        this.gender = gender;

        decideIcon(this.adult, this.pregnant, this.gender);
    }

    public void setSterilisation(boolean sterile) {

        this.sterile = sterile;
    }

    public void setAdult(boolean adult) {

        this.adult = adult;

        decideIcon(this.adult, this.pregnant, this.gender);
    }

    private void growUp() {

        this.adult = true;
        decideIcon(adult, pregnant, gender);
        super.setSpeed(GameObject.getBoard().getLevelProperties().getAdultRatSpeed());
    }

    public int getNumberOfBabies() {

        return this.numberOfBabies;
    }
}
