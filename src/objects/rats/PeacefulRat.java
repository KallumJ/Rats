package objects.rats;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import objects.GameObject;
import tile.Direction;
import tile.Tile;

import java.util.Random;

/**
 * This class represent the peaceful rats which will mate and reproduce.
 *
 * @author Fahd
 * @date 2022/02/21
 *
 */
public class PeacefulRat extends Rat {

    // Added as being pregnant requires the board to be populated, which it
    // isn't when this is constructed
    private static final int DELAY_TO_MAKE_PREGNANT = 250; // in ms
    private static final int DELAY_TO_MATE = 1; // in seconds
    private final int timeToGiveBirth;
    private final int timeToDevelop;
    private final Image maleRatImage;
    private final Image babyRatImage;
    private final Image femaleRatImage;
    private final Image pregnantFemaleRatImage;
    private boolean sterile;
    private boolean adult;
    private boolean pregnant;
    private String gender;
    private int numberOfBabies;
    private Timeline pregnancyTimeline;
    private Timeline pregnancyDelayTimeline;
    private Timeline developmentTimeline;
    private Timeline delayMating;
    private Timeline delayBirth;

    /**
     * Creates a new rat depending on the rat data.
     *
     * @param standingOn          The tile the rat is standing on.
     * @param sterile             Is the rat able to mate.
     * @param adult               Is the rat adult.
     * @param pregnant            Is the rat pregnant.
     * @param gender              The gender of the rat.
     * @param timeToGiveBirth     The time from the start of the pregnancy to
     *                            giving birth.
     * @param timeToDevelop       Time required for a bay rat to be an adult
     *                            rat.
     * @param speed               Moving speed of the rat.
     * @param directionOfMovement Movement direction of the rat.
     */
    public PeacefulRat(Tile standingOn, boolean sterile, boolean adult,
                       boolean pregnant, String gender, int timeToGiveBirth,
                       int timeToDevelop, int speed,
                       Direction directionOfMovement) {
        super(standingOn, speed, directionOfMovement);

        this.sterile = sterile;
        this.adult = adult;
        this.pregnant = pregnant;
        this.gender = gender;
        this.timeToDevelop = timeToDevelop;
        this.timeToGiveBirth = timeToGiveBirth;

        if (!this.adult) {
            developmentTimeline =
                    new Timeline(new KeyFrame(Duration.seconds(this.timeToDevelop), event -> growUp()));
            developmentTimeline.play();
        }
        if (this.pregnant) {
            pregnancyDelayTimeline =
                    new Timeline(new KeyFrame(Duration.millis(DELAY_TO_MAKE_PREGNANT), event -> bePregnant()));
            pregnancyDelayTimeline.play();
        }

        maleRatImage = new Image("file:resources/maleRat.png");
        babyRatImage = new Image("file:resources/babyRat.png");
        femaleRatImage = new Image("file:resources/femaleRat.png");
        pregnantFemaleRatImage =
                new Image("file:resources/pregnantFemaleRat" + ".png");
        decideIcon();

    }

    /**
     * This method will decide the icon suitable for the rat.
     */
    public void decideIcon() {

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

    /**
     * This method is only called by a male rat and might make the other rat
     * pregnant depending on some features.
     *
     * @param partner The other which will be pregnant depending on some
     *                features.
     */
    public void mate(PeacefulRat partner) {
        if (partner.getGender()
                .equalsIgnoreCase("f") && (!partner.isPregnant())) {
            if ((!(this.isSterile() || partner.isSterile())) && (this.isAdult() && partner.isAdult())) {
                partner.setPregnant(true);
                this.setSpeed(0);
                partner.setSpeed(0);
                delayMating =
                        new Timeline(new KeyFrame(Duration.seconds(DELAY_TO_MATE), event -> resetSpeed(partner)));
                delayMating.play();
            }
        }
    }

    /**
     * This method will make a rat pregnant and decide the number of babies.
     */
    public void bePregnant() {
        pregnancyTimeline =
                new Timeline(new KeyFrame(Duration.seconds(this.timeToGiveBirth), event -> giveBirth()));

        this.decideIcon();

        Random random = new Random();
        this.numberOfBabies = random.nextInt((GameObject.getBoard()
                .getLevelProperties()
                .getRatMaxBabies() - GameObject.getBoard()
                .getLevelProperties()
                .getRatMinBabies()) + 1) + GameObject.getBoard()
                .getLevelProperties()
                .getRatMinBabies();
        pregnancyTimeline.play();

    }

    /**
     * A method to get the gender of the rat.
     *
     * @return The gender of the rat.
     */
    public String getGender() {

        return this.gender;
    }

    /**
     * This method will set the gender of the rat.
     *
     * @param gender The new gender of the rat
     */
    public void setGender(String gender) {

        this.gender = gender;

        decideIcon();
    }

    /**
     * This method will set the sterile state of a rat.
     *
     * @param sterile The sterile state of a rat.
     */
    public void setSterilisation(boolean sterile) {

        this.sterile = sterile;
    }

    /**
     * A method will return the sterile state of a rat.
     *
     * @return The sterile state of a rat.
     */
    public boolean isSterile() {

        return this.sterile;
    }

    /**
     * A method to check if a rat is adult or not.
     *
     * @return If the rat adult or not.
     */
    public boolean isAdult() {

        return this.adult;
    }

    /**
     * This method sets if a rat is adult or not.
     *
     * @param adult Is the rat adult.
     */
    public void setAdult(boolean adult) {

        this.adult = adult;

        decideIcon();
    }

    /**
     * A method to check if a rat is pregnant or not.
     *
     * @return If the rat pregnant or not.
     */
    public boolean isPregnant() {

        return this.pregnant;
    }

    /**
     * Sets whether this rat is pregnant.
     *
     * @param pregnant true if pregnant, false otherwise.
     */
    public void setPregnant(boolean pregnant) {
        this.pregnant = pregnant;
    }

    /**
     * A method to get number of babies the rat will give birth to.
     *
     * @return number of babies the rat will give birth to
     */
    public int getNumberOfBabies() {

        return this.numberOfBabies;
    }

    /**
     * A method to get the time til this rat gives birth.
     *
     * @return the time til this rat gives birth in seconds.
     */
    public int getTimeToGiveBirth() {
        return timeToGiveBirth;
    }

    /**
     * A method to get the time for this rat to grow up.
     *
     * @return the time for this rat to grow up in seconds.
     */
    public int getTimeToDevelop() {
        return timeToDevelop;
    }

    /**
     * Stops any timelines running in this object.
     */
    @Override
    public void stop() {
        if (developmentTimeline != null) {
            developmentTimeline.pause();
        }

        if (pregnancyDelayTimeline != null) {
            pregnancyDelayTimeline.pause();
        }

        if (pregnancyTimeline != null) {
            pregnancyTimeline.pause();
        }

        if (delayMating != null) {
            delayMating.pause();
        }

        if (delayBirth != null) {
            delayBirth.pause();
        }
    }

    /**
     * Resets the speed of this rat and their partner.
     *
     * @param partner their partner.
     */
    private void resetSpeed(PeacefulRat partner) {
        int speed = GameObject.getBoard()
                .getLevelProperties()
                .getAdultRatSpeed();
        partner.setSpeed(speed);
        this.setSpeed(speed);
        partner.bePregnant();
    }

    /**
     * This method will make a pregnant rat give birth and gets it babies to
     * life.
     */
    private void giveBirth() {

        // randomly deciding the gender of each baby.
        delayBirth =
                new Timeline(new KeyFrame(Duration.millis(super.getSpeed()), event -> spawnBaby()));
        delayBirth.setCycleCount(this.numberOfBabies);
        delayBirth.play();
    }

    /**
     * Spawns a baby behind its mother.
     */
    private void spawnBaby() {
        try {
            String newBornGender;
            Random random = new Random();
            int decision = random.nextInt(2);

            if (decision == 1) {

                newBornGender = "m";
            } else {

                newBornGender = "f";
            }
            Direction oppositeDirection =
                    turnAround(super.getDirectionOfMovement());
            Tile tile = super.getStandingOn().getAdjacentTile(oppositeDirection);
            GameObject newBorn = new PeacefulRat(tile, this.isSterile(), false,
                    false, newBornGender, this.timeToGiveBirth, this.timeToDevelop,
                    GameObject.getBoard().getLevelProperties().getBabyRatSpeed(),
                    oppositeDirection);

            GameObject.getBoard().addObject(newBorn);

            numberOfBabies = numberOfBabies - 1;
            if (numberOfBabies == 0) {
                this.pregnant = false;
                decideIcon();
            }
        // Rats might try and spawn after game has ended, and board is gone
        } catch (NullPointerException ignored) {

        }

    }

    /**
     * Turns a baby rat into an adult rat.
     */
    private void growUp() {

        this.adult = true;
        decideIcon();
        try {
            super.setSpeed(GameObject.getBoard()
                    .getLevelProperties()
                    .getAdultRatSpeed());
        } catch (NullPointerException ignored) {

        }
    }
}
