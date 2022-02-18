package display.menus.editor;

import envrionment.TimeOfDay;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import level.LevelData;
import level.LevelProperties;
import tile.TileType;

/**
 * A class to get input from the user for the custom level properties
 *
 * @author Kallum Jones (2005855) (11/02/22)
 */
public class LevelEditorOptionsMenu {
    private static final Insets PADDING = new Insets(0, 5, 0, 5);
    public static final int WINDOW_OFFSET = 600;

    private final TextField populationToLoseTextField;
    private final TextField expectedTimeTextField;
    private final TextField itemIntervalTextField;
    private final TextField ratMaxBabiesTextField;
    private final TextField ratMinBabiesTextField;
    private final TextField adultRatSpeedTextField;
    private final TextField babyRatSpeedTextField;
    private final TextField deathRatSpeedTextField;
    private final ChoiceBox<String> tileSelectChoiceBox;
    private final RadioButton includeAirstrike;
    private final TextField airstrikeCostTextField;
    private final TextField airstrikeNumberOfHitsTextField;
    private final RadioButton onlyDayTime;
    private final RadioButton onlyNightTime;
    private final RadioButton dayAndNight;
    private final TextField timeIntervalTextField; // period between day and night
    private final RadioButton deleteItems;


    /**
     * Constructs a LevelPropertiesInputMenu object
     */
    public LevelEditorOptionsMenu() {
        populationToLoseTextField = new TextField();
        expectedTimeTextField = new TextField();
        itemIntervalTextField = new TextField();
        ratMaxBabiesTextField = new TextField();
        ratMinBabiesTextField = new TextField();
        adultRatSpeedTextField = new TextField();
        babyRatSpeedTextField = new TextField();
        deathRatSpeedTextField = new TextField();
        tileSelectChoiceBox = new ChoiceBox<>();
        includeAirstrike = new RadioButton("Include airstrike");
        airstrikeCostTextField = new TextField();
        airstrikeNumberOfHitsTextField = new TextField();
        deleteItems = new RadioButton("Delete items on tile");


        timeIntervalTextField = new TextField();
        onlyDayTime = new RadioButton("Always day time");
        onlyNightTime = new RadioButton("Always night time");
        dayAndNight = new RadioButton("Day and night times");

        // makes sure user can't pick more than one option at the same time
        ToggleGroup dayAndNightRadioButtonGroup = new ToggleGroup();
        onlyDayTime.setToggleGroup(dayAndNightRadioButtonGroup);
        onlyDayTime.setSelected(true);

        onlyNightTime.setToggleGroup(dayAndNightRadioButtonGroup);

        dayAndNight.setToggleGroup(dayAndNightRadioButtonGroup);
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
        HBox row5 = new HBox();
        HBox row6 = new HBox();

        Label populationToLoseLabel = new Label("Population to Lose:");
        Tooltip populationToLoseTooltip = new Tooltip("blah blah blah");
        Tooltip.install(populationToLoseLabel, populationToLoseTooltip);
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

        Label airstrikeCostLabel = new Label("Required points for airstrike:");
        airstrikeCostLabel.setPadding(PADDING);
        airstrikeCostTextField.setPadding(PADDING);


        Label airstrikeNumberOfHitsLabel = new Label("Number of targets in an airstrike:");
        airstrikeNumberOfHitsLabel.setPadding(PADDING);
        airstrikeNumberOfHitsTextField.setPadding(PADDING);

        Label timeDayAndNightLabel = new Label("Period between day and night:");
        timeDayAndNightLabel.setPadding(PADDING);
        timeIntervalTextField.setPadding(PADDING);


        /*
         * adds a choice box with choice of tiles
         * event listener added to choice box which is activated when new tile is chosen
         */
        tileSelectChoiceBox.getItems().addAll("Grass Tile", "Tunnel Tile", "Path Tile");
        tileSelectChoiceBox.setValue("Grass Tile");

        row1.getChildren().addAll(
                populationToLoseLabel,
                populationToLoseTextField,
                expectedTimeLabel,
                expectedTimeTextField,
                tileSelectChoiceBox
        );

        row2.getChildren().addAll(
              ratMinBabiesLabel,
              ratMinBabiesTextField,
              adultRatSpeedLabel,
              adultRatSpeedTextField,
              deleteItems
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

        row5.getChildren().addAll(
                airstrikeCostLabel,
                airstrikeCostTextField,
                airstrikeNumberOfHitsLabel,
                airstrikeNumberOfHitsTextField,
                includeAirstrike
        );

        row6.getChildren().addAll(
                timeDayAndNightLabel,
                timeIntervalTextField,
                onlyDayTime,
                onlyNightTime,
                dayAndNight
        );

        VBox container = new VBox();
        container.getChildren().addAll(row1, row2, row3, row4,row5,row6);
        container.autosize();

        return container;
    }

    /**
     * Sets all the properties in the provided LevelData to those contained in the menu
     * @param levelData the level data to set the properties for
     */
    public void setLevelProperties(LevelData levelData) {
        LevelProperties levelProperties = levelData.getLevelProperties();

        levelProperties.setLevelId(1);
        levelProperties.setPopulationToLose(getPopulationToLose());
        levelProperties.setExpectedTime(getExpectedTime());
        levelProperties.setItemInterval(getItemInterval());
        levelProperties.setRatMinBabies(getRatMinBabies());
        levelProperties.setRatMaxBabies(getRatMaxBabies());
        levelProperties.setAdultRatSpeed(getAdultRatSpeed());
        levelProperties.setBabyRatSpeed(getBabyRatSpeed());
        levelProperties.setDeathRatSpeed(getDeathRatSpeed());
        levelProperties.setTimeOfDay(getTimeOfDay());
        levelProperties.setTimeInterval(getTimeInterval());
        levelProperties.setAirstrikeEnabled(getAirstrikeEnabled());
        levelProperties.setCostOfAirstrike(getCostOfAirstrike());
        levelProperties.setNumOfAirstrikeHits(getNumOfAirstrikeHits());
    }

    /**
     * Get the number of airstrike hits for this level
     * @return the number of airstrike hits for this level as a number of tiles
     */
    private int getNumOfAirstrikeHits() {
        return Integer.parseInt(airstrikeNumberOfHitsTextField.getText());
    }

    /**
     * Get the cost of calling an airstrike
     * @return cost of calling an airstrike in points
     */
    private int getCostOfAirstrike() {
        return Integer.parseInt(airstrikeCostTextField.getText());
    }

    /**
     * Get whether an airstrike is enabled
     * @return whether an airstrike is enabled
     */
    private boolean getAirstrikeEnabled() {
        return includeAirstrike.isSelected();
    }

    /**
     * Get the time period between day cycle changes
     * @return the time period between day cycle changes in seconds
     */
    private int getTimeInterval() {
        return Integer.parseInt(timeIntervalTextField.getText());
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

    public boolean getDeleteItemsChecked() {
        return deleteItems.selectedProperty().get();
    }

    /**
     * Returns TileType of selected tile
     * @return TileType of selected tile
     */
    public TileType getTileSelected() {
        switch (tileSelectChoiceBox.getValue()) {
            case "Tunnel Tile":
                return TileType.TUNNEL;
            case "Path Tile":
                return TileType.PATH;
            default:
                return TileType.GRASS;
        }
        
    }

    /**
     * Gets what time of day is selected
     * @return what time of day is selected
     */
    public TimeOfDay getTimeOfDay() {
        if (onlyDayTime.isSelected()) {
            return TimeOfDay.DAY;
        } else if (onlyNightTime.isSelected()) {
            return TimeOfDay.NIGHT;
        } else {
            return TimeOfDay.BOTH;
        }
    }
}
