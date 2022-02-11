package display.menus.editor;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A class to get input from the user for the custom level properties
 *
 * @author Kallum Jones (2005855) (11/02/22)
 */
public class LevelPropertiesInputMenu {
    private static final Insets PADDING = new Insets(0, 5, 0, 5);

    private final TextField populationToLoseTextField;
    private final TextField expectedTimeTextField;
    private final TextField itemIntervalTextField;
    private final TextField ratMaxBabiesTextField;
    private final TextField ratMinBabiesTextField;
    private final TextField adultRatSpeedTextField;
    private final TextField babyRatSpeedTextField;
    private final TextField deathRatSpeedTextField;

    /**
     * Constructs a LevelPropertiesInputMenu object
     */
    public LevelPropertiesInputMenu() {
        populationToLoseTextField = new TextField();
        expectedTimeTextField = new TextField();
        itemIntervalTextField = new TextField();
        ratMaxBabiesTextField = new TextField();
        ratMinBabiesTextField = new TextField();
        adultRatSpeedTextField = new TextField();
        babyRatSpeedTextField = new TextField();
        deathRatSpeedTextField = new TextField();
    }


    /**
     * Builds the LevelPropertiesInputMenu gui
     * @return the menu as a VBox
     */
    public VBox buildGUI() {
        HBox row1 = new HBox();
        HBox row2 = new HBox();
        HBox row3 = new HBox();
        HBox row4 = new HBox();

        Label populationToLoseLabel = new Label("Population to Lose:");
        populationToLoseLabel.setPadding(PADDING);
        populationToLoseTextField.setPadding(PADDING);

        Label expectedTimeLabel = new Label("Expected Time:");
        expectedTimeLabel.setPadding(PADDING);
        expectedTimeTextField.setPadding(PADDING);

        Label itemIntervalLabel = new Label("Item Interval:");
        itemIntervalLabel.setPadding(PADDING);
        itemIntervalTextField.setPadding(PADDING);

        Label ratMaxBabiesLabel = new Label("Maximum babies:");
        ratMaxBabiesLabel.setPadding(PADDING);
        ratMaxBabiesTextField.setPadding(PADDING);

        Label ratMinBabiesLabel = new Label("Minimum babies:");
        ratMinBabiesLabel.setPadding(PADDING);
        ratMinBabiesTextField.setPadding(PADDING);

        Label adultRatSpeedLabel = new Label("Adult Rat Speed:");
        adultRatSpeedLabel.setPadding(PADDING);
        adultRatSpeedTextField.setPadding(PADDING);

        Label babyRatSpeedLabel = new Label("Baby Rat Speed:");
        babyRatSpeedLabel.setPadding(PADDING);
        babyRatSpeedTextField.setPadding(PADDING);

        Label deathRatSpeedLabel = new Label("Death Rat Speed:");
        deathRatSpeedLabel.setPadding(PADDING);
        deathRatSpeedTextField.setPadding(PADDING);

        row1.getChildren().addAll(
                populationToLoseLabel,
                populationToLoseTextField,
                expectedTimeLabel,
                expectedTimeTextField
        );

        row2.getChildren().addAll(
              ratMinBabiesLabel,
              ratMinBabiesTextField,
              adultRatSpeedLabel,
              adultRatSpeedTextField
        );

        row3.getChildren().addAll(
                deathRatSpeedLabel,
                deathRatSpeedTextField,
                ratMaxBabiesLabel,
                ratMaxBabiesTextField
        );

        row4.getChildren().addAll(
                babyRatSpeedLabel,
                babyRatSpeedTextField,
                itemIntervalLabel,
                itemIntervalTextField
        );

        VBox container = new VBox();
        container.getChildren().addAll(row1, row2, row3, row4);
        container.autosize();

        return container;
    }

    /**
     * Returns the input population to lose
     * @return the input population to lose
     */
    public int getPopulationToLose() {
        return Integer.parseInt(populationToLoseTextField.getText());
    }

    /**
     * Returns the input expected time to finish the level
     * @return the input expected time to finish the level
     */
    public int getExpectedTime() {
        return Integer.parseInt(expectedTimeTextField.getText());
    }

    /**
     * Returns the input item drop interval
     * @return the input item drop interval
     */
    public int getItemInterval() {
        return Integer.parseInt(itemIntervalTextField.getText());
    }

    /**
     * Returns the input maximum number of babies a rat can have
     * @return the input maximum number of babies a rat can have
     */
    public int getRatMaxBabies() {
        return Integer.parseInt(ratMaxBabiesTextField.getText());
    }

    /**
     * Returns the input minimum number of babies a rat can have
     * @return the input minimum number of babies a rat can have
     */
    public int getRatMinBabies() {
        return Integer.parseInt(ratMinBabiesTextField.getText());
    }

    /**
     * Returns the input speed of an adult rat
     * @return the input speed of an adult rat
     */
    public int getAdultRatSpeed() {
        return Integer.parseInt(adultRatSpeedTextField.getText());
    }

    /**
     * Returns the input speed of a baby rat
     * @return the input speed of a baby rat
     */
    public int getBabyRatSpeed() {
        return Integer.parseInt(babyRatSpeedTextField.getText());
    }

    /**
     * Returns the input speed of a death rat
     * @return the input speed of a death rat
     */
    public int getDeathRatSpeed() {
        return Integer.parseInt(deathRatSpeedTextField.getText());
    }
}
