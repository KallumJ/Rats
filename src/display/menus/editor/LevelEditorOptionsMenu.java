package display.menus.editor;

import envrionment.TimeOfDay;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import level.LevelData;
import level.LevelProperties;
import objects.GameObjectType;
import tile.TileType;

import java.util.HashSet;

/**
 * A class to get input from the user for the custom level properties.
 *
 * @author Kallum Jones (2005855)
 * @author Aser (minor updates)
 * @date 2022.02.11
 *
 */
public class LevelEditorOptionsMenu {
    public static final int WINDOW_OFFSET = 800;
    private static final String POP_TO_LOSE_TOOLTIP =
            "The number of rats that cause game failure";
    private static final String EXPECTED_TIME_TOOLTIP =
            "The time the user is expected to beat the level, in seconds";
    private static final String ITEM_INTERVAL_TOOLTIP =
            "The number of seconds between item drops";
    private static final String RAT_MAX_BABIES_TOOLTIP =
            "The maximum number of rats a rat can give birth to";
    private static final String RAT_MIN_BABIES_TOOLTIP =
            "The minimum number of rats a rat can give birth to";
    private static final String ADULT_RAT_SPEED_TOOLTIP =
            "The time between adult rat movements, in milliseconds";
    private static final String BABY_RAT_SPEED_TOOLTIP =
            "The time between baby rat movements, in milliseconds";
    private static final String DEATH_RAT_SPEED_TOOLTIP =
            "The time between death rat movements, in milliseconds";
    private static final String AIRSTRIKE_COST_TOOLTIP =
            "Cost of calling an airstrike, in points";
    private static final String TIME_INTERVAL_TOOLTIP =
            "The time between changes in time, in seconds";
    private static final String LEVEL_NAME_TOOLTIP =
            "Name for your level, must contain at least 1 non number character";
    private static final String ALLOWED_ITEMS_TOOLTIP =
            "Select the items the user is allowed to use in the level";
    private static final String LABEL_STYLE =
            "-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 14;";
    private static final String TEXT_FIELD_STYLE =
            "-fx-background-color: #000000; -fx-border-color: darkgrey; -fx-border-width: 1px; -fx-text-fill: white;";
    private static final String RADIO_BUTTON_STYLE =
            "-fx-text-fill: white; -fx-font-size: 14;";

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
    private final TextField levelNameTextField;
    private final ObjectSelectionGroup objectSelectionGroup;

    /**
     * Constructs a LevelPropertiesInputMenu object.
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

        levelNameTextField = new TextField();
        objectSelectionGroup = new ObjectSelectionGroup();
    }

    public void setLevelNameTextField(String text) {
        levelNameTextField.setText(text);
    }

    /**
     * Builds the LevelPropertiesInputMenu gui.
     *
     * @return the menu as a VBox.
     */
    public VBox buildGUI() {
        //Label for Population to Lose
        Label populationToLoseLabel = new Label("Population to Lose:");
        populationToLoseLabel.setTextFill(Color.WHITE);
        populationToLoseLabel.setStyle(LABEL_STYLE);
        Tooltip populationToLoseTooltip = new Tooltip(POP_TO_LOSE_TOOLTIP);
        Tooltip.install(populationToLoseLabel, populationToLoseTooltip);

        populationToLoseLabel.setTranslateX(30);
        populationToLoseLabel.setTranslateY(20);

        //Label for Expected Time 
        Label expectedTimeLabel = new Label("Expected Time:");
        Tooltip expectedTimeTooltip = new Tooltip(EXPECTED_TIME_TOOLTIP);
        Tooltip.install(expectedTimeLabel, expectedTimeTooltip);
        expectedTimeLabel.setTextFill(Color.WHITE);
        expectedTimeLabel.setStyle(LABEL_STYLE);

        expectedTimeLabel.setTranslateX(30);
        expectedTimeLabel.setTranslateY(40);

        //Lable for Item Interval
        Label itemIntervalLabel = new Label("Item Interval:");
        Tooltip itemIntervalTooltip = new Tooltip(ITEM_INTERVAL_TOOLTIP);
        Tooltip.install(itemIntervalLabel, itemIntervalTooltip);
        itemIntervalLabel.setTextFill(Color.WHITE);
        itemIntervalLabel.setStyle(LABEL_STYLE);

        itemIntervalLabel.setTranslateX(30);
        itemIntervalLabel.setTranslateY(60);

        //Label for Maximum number of Rat Babies 
        Label ratMaxBabiesLabel = new Label("Maximum babies:");
        Tooltip ratMaxBabiesTooltip = new Tooltip(RAT_MAX_BABIES_TOOLTIP);
        Tooltip.install(ratMaxBabiesLabel, ratMaxBabiesTooltip);
        ratMaxBabiesLabel.setTextFill(Color.WHITE);
        ratMaxBabiesLabel.setStyle(LABEL_STYLE);

        ratMaxBabiesLabel.setTranslateX(30);
        ratMaxBabiesLabel.setTranslateY(80);

        //Label for Minimum number of Rat Babies 
        Label ratMinBabiesLabel = new Label("Minimum babies:");
        Tooltip ratMinBabiesTooltip = new Tooltip(RAT_MIN_BABIES_TOOLTIP);
        Tooltip.install(ratMinBabiesLabel, ratMinBabiesTooltip);
        ratMinBabiesLabel.setTextFill(Color.WHITE);
        ratMinBabiesLabel.setStyle(LABEL_STYLE);

        ratMinBabiesLabel.setTranslateX(30);
        ratMinBabiesLabel.setTranslateY(100);

        //Label for Adult Rat Speed 
        Label adultRatSpeedLabel = new Label("Adult Rat Speed:");
        Tooltip adultRatSpeedTooltip = new Tooltip(ADULT_RAT_SPEED_TOOLTIP);
        Tooltip.install(adultRatSpeedLabel, adultRatSpeedTooltip);
        adultRatSpeedLabel.setTextFill(Color.WHITE);
        adultRatSpeedLabel.setStyle(LABEL_STYLE);

        adultRatSpeedLabel.setTranslateX(30);
        adultRatSpeedLabel.setTranslateY(120);

        //Label for Baby Rat Speed 
        Label babyRatSpeedLabel = new Label("Baby Rat Speed:");
        Tooltip babyRatSpeedTooltip = new Tooltip(BABY_RAT_SPEED_TOOLTIP);
        Tooltip.install(babyRatSpeedLabel, babyRatSpeedTooltip);
        babyRatSpeedLabel.setTextFill(Color.WHITE);
        babyRatSpeedLabel.setStyle(LABEL_STYLE);

        babyRatSpeedLabel.setTranslateX(30);
        babyRatSpeedLabel.setTranslateY(140);

        //Label for Death Rat Speed 
        Label deathRatSpeedLabel = new Label("Death Rat Speed:");
        Tooltip deathRatSpeedTooltip = new Tooltip(DEATH_RAT_SPEED_TOOLTIP);
        Tooltip.install(deathRatSpeedLabel, deathRatSpeedTooltip);
        deathRatSpeedLabel.setTextFill(Color.WHITE);
        deathRatSpeedLabel.setStyle(LABEL_STYLE);

        deathRatSpeedLabel.setTranslateX(30);
        deathRatSpeedLabel.setTranslateY(160);

        //Label for Cost of Airstrike 
        Label airstrikeCostLabel = new Label("Required points for airstrike:");
        Tooltip airstrikeCostTooltip = new Tooltip(AIRSTRIKE_COST_TOOLTIP);
        Tooltip.install(airstrikeCostLabel, airstrikeCostTooltip);
        airstrikeCostLabel.setTextFill(Color.WHITE);
        airstrikeCostLabel.setStyle(LABEL_STYLE);

        airstrikeCostLabel.setTranslateX(30);
        airstrikeCostLabel.setTranslateY(180);

        //Label for Number of Airstrike Hits 
        Label airstrikeNumberOfHitsLabel = new Label("Number of targets in an airstrike:");
        airstrikeNumberOfHitsLabel.setTextFill(Color.WHITE);
        airstrikeNumberOfHitsLabel.setStyle(LABEL_STYLE);

        airstrikeNumberOfHitsLabel.setTranslateX(30);
        airstrikeNumberOfHitsLabel.setTranslateY(200);

        //Label for Day and Night 
        Label timeDayAndNightLabel = new Label("Period between day and night:");
        Tooltip timePeriodTooltip = new Tooltip(TIME_INTERVAL_TOOLTIP);
        Tooltip.install(timeDayAndNightLabel, timePeriodTooltip);
        timeDayAndNightLabel.setTextFill(Color.WHITE);
        timeDayAndNightLabel.setStyle(LABEL_STYLE);

        timeDayAndNightLabel.setTranslateX(30);
        timeDayAndNightLabel.setTranslateY(220);

        Label levelNameLabel = new Label("Level name");
        Tooltip levelNameTooltip = new Tooltip(LEVEL_NAME_TOOLTIP);
        Tooltip.install(levelNameLabel, levelNameTooltip);
        levelNameLabel.setTextFill(Color.WHITE);
        levelNameLabel.setStyle(LABEL_STYLE);

        Label allowedItemsLabel = new Label("Allowed items");
        Tooltip allowedItemsTooltip = new Tooltip(ALLOWED_ITEMS_TOOLTIP);
        Tooltip.install(allowedItemsLabel, allowedItemsTooltip);
        allowedItemsLabel.setTextFill(Color.WHITE);
        allowedItemsLabel.setStyle(LABEL_STYLE);

        /*
         * adds a choice box with choice of tiles
         * event listener added to choice box which is activated when new tile is chosen
         */
        tileSelectChoiceBox.getItems().addAll("Grass Tile", "Tunnel Tile", "Path Tile");
        tileSelectChoiceBox.setValue("Grass Tile");

        // Setting style for the textfields
        populationToLoseTextField.setStyle(TEXT_FIELD_STYLE);
        expectedTimeTextField.setStyle(TEXT_FIELD_STYLE);
        itemIntervalTextField.setStyle(TEXT_FIELD_STYLE);
        ratMaxBabiesTextField.setStyle(TEXT_FIELD_STYLE);
        ratMinBabiesTextField.setStyle(TEXT_FIELD_STYLE);
        adultRatSpeedTextField.setStyle(TEXT_FIELD_STYLE);
        babyRatSpeedTextField.setStyle(TEXT_FIELD_STYLE);
        deathRatSpeedTextField.setStyle(TEXT_FIELD_STYLE);
        airstrikeCostTextField.setStyle(TEXT_FIELD_STYLE);
        airstrikeNumberOfHitsTextField.setStyle(TEXT_FIELD_STYLE);
        timeIntervalTextField.setStyle(TEXT_FIELD_STYLE);
        levelNameTextField.setStyle(TEXT_FIELD_STYLE);


        //Hbox for text fields to fix the textfields covering the page issue
        HBox row1 = new HBox();
        row1.getChildren().addAll(
                populationToLoseTextField
        );
        row1.setTranslateX(160);
        row1.setTranslateY(-200);

        //Hbox for text fields to fix the textfields covering the page issue
        HBox row2 = new HBox();
        row2.getChildren().addAll(
                expectedTimeTextField
        );
        row2.setTranslateX(160);
        row2.setTranslateY(-190);

        //Hbox for text fields to fix the textfields covering the page issue
        HBox row3 = new HBox();
        row3.getChildren().addAll(
                itemIntervalTextField
        );
        row3.setTranslateX(160);
        row3.setTranslateY(-180);

        //Hbox for text fields to fix the textfields covering the page issue
        HBox row4 = new HBox();
        row4.getChildren().addAll(
                ratMaxBabiesTextField
        );
        row4.setTranslateX(160);
        row4.setTranslateY(-170);

        //Hbox for text fields to fix the textfields covering the page issue
        HBox row5 = new HBox();
        row5.getChildren().addAll(
                ratMinBabiesTextField
        );
        row5.setTranslateX(160);
        row5.setTranslateY(-155);

        //Hbox for text fields to fix the textfields covering the page issue
        HBox row6 = new HBox();
        row6.getChildren().addAll(
                adultRatSpeedTextField
        );
        row6.setTranslateX(160);
        row6.setTranslateY(-145);

        //Hbox for text fields to fix the textfields covering the page issue
        HBox row7 = new HBox();
        row7.getChildren().addAll(
                babyRatSpeedTextField
        );
        row7.setTranslateX(160);
        row7.setTranslateY(-130);

        //Hbox for text fields to fix the textfields covering the page issue
        HBox row8 = new HBox();
        row8.getChildren().addAll(
                deathRatSpeedTextField
        );
        row8.setTranslateX(160);
        row8.setTranslateY(-120);

        //Hbox for text fields to fix the textfields covering the page issue
        HBox row9 = new HBox();
        row9.getChildren().addAll(
                airstrikeCostTextField
        );
        row9.setTranslateX(250);
        row9.setTranslateY(-105);

        //Hbox for text fields to fix the textfields covering the page issue
        HBox row10 = new HBox();
        row10.getChildren().addAll(
                airstrikeNumberOfHitsTextField
        );
        row10.setTranslateX(250);
        row10.setTranslateY(-95);

        //Hbox for text fields to fix the textfields covering the page issue
        HBox row11 = new HBox();
        row11.getChildren().addAll(
                timeIntervalTextField
        );
        row11.setTranslateX(250);
        row11.setTranslateY(-85);

        tileSelectChoiceBox.setTranslateX(450);
        tileSelectChoiceBox.setTranslateY(-505);

        includeAirstrike.setStyle(RADIO_BUTTON_STYLE);
        includeAirstrike.setTranslateX(450);
        includeAirstrike.setTranslateY(-480);

        deleteItems.setStyle(RADIO_BUTTON_STYLE);
        deleteItems.setTranslateX(450);
        deleteItems.setTranslateY(-460);

        onlyDayTime.setStyle(RADIO_BUTTON_STYLE);
        onlyDayTime.setTranslateX(450);
        onlyDayTime.setTranslateY(-440);

        onlyNightTime.setStyle(RADIO_BUTTON_STYLE);
        onlyNightTime.setTranslateX(450);
        onlyNightTime.setTranslateY(-420);

        dayAndNight.setStyle(RADIO_BUTTON_STYLE);
        dayAndNight.setTranslateX(450);
        dayAndNight.setTranslateY(-400);

        //Vbox containing all the labels, textfields, etc.
        VBox container = new VBox();
        container.setPrefSize(600, 600);
        container.getChildren().addAll(
                populationToLoseLabel, expectedTimeLabel, itemIntervalLabel, ratMaxBabiesLabel, ratMinBabiesLabel,
                adultRatSpeedLabel, babyRatSpeedLabel, deathRatSpeedLabel, airstrikeCostLabel, airstrikeNumberOfHitsLabel,
                timeDayAndNightLabel, row1, row2, row3, row4, row5, row6, row7, row8, row9, row10, row11,
                tileSelectChoiceBox, includeAirstrike, deleteItems, onlyDayTime, onlyNightTime, dayAndNight,
                allowedItemsLabel, objectSelectionGroup.getObjectSelectionGroup(), levelNameLabel, levelNameTextField
        );
        container.setStyle("-fx-background-color: #000000;");

        return container;
    }


    /**
     * Sets all the properties in the provided LevelData to those contained in the menu.
     *
     * @param levelData the level data to set the properties for.
     */
    public void setLevelProperties(LevelData levelData) {
        LevelProperties levelProperties = levelData.getLevelProperties();

        levelProperties.setLevelId(getLevelName());
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
        levelProperties.setAllowedItems(getSelectedObjects());
    }

    //Validation Rules
    // 1. No textfields can be empty 
    // 2. They can only be integers
    // 3. Sensible range can only be entered

    private String getLevelName() {
        String validLevelName = levelNameTextField.getText();

        if (validLevelName.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please Enter Valid Input");
            alert.showAndWait();
        } else {
            return validLevelName;
        }
        return validLevelName;
    }

    /**
     * Get the number of airstrike hits for this level.
     *
     * @return the number of airstrike hits for this level as a number of tiles.
     */
    private int getNumOfAirstrikeHits() {
        int validNumAirstrikes = Integer.parseInt(airstrikeNumberOfHitsTextField.getText());

        if (airstrikeNumberOfHitsTextField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please Enter Valid Input");
            alert.showAndWait();
        } else {
            return validNumAirstrikes;
        }
        
        if (airstrikeNumberOfHitsTextField.getText().matches("[10-20]+")) {
            return validNumAirstrikes;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please Enter Valid Input");
            alert.showAndWait();
        }
        return validNumAirstrikes;
    }

    /**
     * Get the cost of calling an airstrike.
     *
     * @return cost of calling an airstrike in points.
     */
    private int getCostOfAirstrike() {
        int validCostAirstrike =  Integer.parseInt(airstrikeCostTextField.getText());

        if (airstrikeCostTextField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please Enter Valid Input");
            alert.showAndWait();
        } else {
            return validCostAirstrike;
        }
        
        if (airstrikeCostTextField.getText().matches("[10-100]+")) {
            return validCostAirstrike;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please Enter Valid Input");
            alert.showAndWait();
        }
        return validCostAirstrike;
    }

    /**
     * Get whether an airstrike is enabled.
     *
     * @return whether an airstrike is enabled.
     */
    private boolean getAirstrikeEnabled() {
        return includeAirstrike.isSelected();
    }

    /**
     * Get the time period between day cycle changes.
     *
     * @return the time period between day cycle changes in seconds.
     */
    private int getTimeInterval() {
        int validTimeInterval = Integer.parseInt(timeIntervalTextField.getText());

        if (timeIntervalTextField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please Enter Valid Input");
            alert.showAndWait();
        } else {
            return validTimeInterval;
        }
        
        if (timeIntervalTextField.getText().matches("[30-100]+")) {
            return validTimeInterval;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please Enter Valid Input");
            alert.showAndWait();
        }
        return validTimeInterval;
    }

    public void setTimeInterval(int timeInterval) {
        timeIntervalTextField.setText(String.valueOf(timeInterval));
    }

    /**
     * Returns the input population to lose.
     *
     * @return the input population to lose.
     */
    public int getPopulationToLose() {
        int validPopulation = Integer.parseInt(populationToLoseTextField.getText());

        if (populationToLoseTextField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please Enter Valid Input");
            alert.showAndWait();
        } else {
            return validPopulation;
        }
        
        if (populationToLoseTextField.getText().matches("[10-100]+")) {
            return validPopulation;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please Enter Valid Input");
            alert.showAndWait();
        }
        return validPopulation;
    }

    public void setPopulationToLose(int populationToLose) {
        populationToLoseTextField.setText(String.valueOf(populationToLose));
    }

    /**
     * Returns the input expected time to finish the level.
     *
     * @return the input expected time to finish the level.
     */
    public int getExpectedTime() {
        int validTime = Integer.parseInt(expectedTimeTextField.getText());

        if (expectedTimeTextField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please Enter Valid Input");
            alert.showAndWait();
        } else {
            return validTime;
        }
        
        if (expectedTimeTextField.getText().matches("[10-300]+")) {
            return validTime;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please Enter Valid Input");
            alert.showAndWait();
        }
        return validTime;
    }

    public void setExpectedTime(int expectedTime) {
        expectedTimeTextField.setText(String.valueOf(expectedTime));
    }

    /**
     * Returns the input item drop interval.
     *
     * @return the input item drop interval.
     */
    public int getItemInterval() {
        int validTimeInterval = Integer.parseInt(itemIntervalTextField.getText());

        if (itemIntervalTextField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please Enter Valid Input");
            alert.showAndWait();
        } else {
            return validTimeInterval;
        }
        
        if (itemIntervalTextField.getText().matches("[1-3]+")) {
            return validTimeInterval;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please Enter Valid Input");
            alert.showAndWait();
        }
        return validTimeInterval;
    }

    public void setItemInterval(int itemInterval) {
        itemIntervalTextField.setText(String.valueOf(itemInterval));
    }

    /**
     * Returns the input maximum number of babies a rat can have.
     *
     * @return the input maximum number of babies a rat can have.
     */
    public int getRatMaxBabies() {
        int validMaxRatBabies = Integer.parseInt(ratMaxBabiesTextField.getText());

        if (ratMaxBabiesTextField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please Enter Valid Input");
            alert.showAndWait();
        } else {
            return validMaxRatBabies;
        }
        
        if (ratMaxBabiesTextField.getText().matches("[10-20]+")) {
            return validMaxRatBabies;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please Enter Valid Input");
            alert.showAndWait();
        }
        return validMaxRatBabies;
    }

    public void setRatMaxBabies(int ratMaxBabies) {
        ratMaxBabiesTextField.setText(String.valueOf(ratMaxBabies));
    }

    /**
     * Returns the input minimum number of babies a rat can have.
     *
     * @return the input minimum number of babies a rat can have.
     */
    public int getRatMinBabies() {
        int validMinRatBabies = Integer.parseInt(ratMinBabiesTextField.getText());

        if (ratMinBabiesTextField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please Enter Valid Input");
            alert.showAndWait();
        } else {
            return validMinRatBabies;
        }
        
        if (ratMinBabiesTextField.getText().matches("[10-20]+")) {
            return validMinRatBabies;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please Enter Valid Input");
            alert.showAndWait();
        }
        return validMinRatBabies;
    }

    public void setRatMinBabies(int ratMinBabies) {
        ratMinBabiesTextField.setText(String.valueOf(ratMinBabies));
    }

    /**
     * Returns the input speed of an adult rat.
     *
     * @return the input speed of an adult rat.
     */
    public int getAdultRatSpeed() {
        int validAdultSpeed = Integer.parseInt(adultRatSpeedTextField.getText());

        if (adultRatSpeedTextField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please Enter Valid Input");
            alert.showAndWait();
        } else {
            return validAdultSpeed;
        }
        
        if (adultRatSpeedTextField.getText().matches("[100-5000]+")) {
            return validAdultSpeed;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please Enter Valid Input");
            alert.showAndWait();
        }
        return validAdultSpeed;
    }

    public void setAdultRatSpeed(int adultRatSpeed) {
        adultRatSpeedTextField.setText(String.valueOf(adultRatSpeed));
    }

    /**
     * Returns the input speed of a baby rat.
     *
     * @return the input speed of a baby rat.
     */
    public int getBabyRatSpeed() {
        int validBabySpeed = Integer.parseInt(babyRatSpeedTextField.getText());

        if (babyRatSpeedTextField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please Enter Valid Input");
            alert.showAndWait();
        } else {
            return validBabySpeed;
        }
        
        if (babyRatSpeedTextField.getText().matches("[100-5000]+")) {
            return validBabySpeed;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please Enter Valid Input");
            alert.showAndWait();
        }
        return validBabySpeed;
    }

    public void setBabyRatSpeed(int babyRatSpeed) {
        babyRatSpeedTextField.setText(String.valueOf(babyRatSpeed));
    }

    /**
     * Returns the input speed of a death rat.
     *
     * @return the input speed of a death rat.
     */
    public int getDeathRatSpeed() {
        int validDeathSpeed = Integer.parseInt(deathRatSpeedTextField.getText());

        if (deathRatSpeedTextField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please Enter Valid Input");
            alert.showAndWait();
        } else {
            return validDeathSpeed;
        }
        
        if (deathRatSpeedTextField.getText().matches("[100-5000]+")) {
            return validDeathSpeed;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please Enter Valid Input");
            alert.showAndWait();
        }
        return validDeathSpeed;
    }

    public void setDeathRatSpeed(int deathRatSpeed) {
        deathRatSpeedTextField.setText(String.valueOf(deathRatSpeed));
    }

    public boolean getDeleteItemsChecked() {
        return deleteItems.selectedProperty().get();
    }

    /**
     * Returns TileType of selected tile.
     *
     * @return TileType of selected tile.
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
     * Gets what time of day is selected.
     *
     * @return what time of day is selected.
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

    /**
     * Sets the time of day in the options menu
     *
     * @param timeOfDay The TimeOfDay to set
     */
    public void setTimeOfDay(TimeOfDay timeOfDay) {
        switch (timeOfDay) {
            case DAY:
                onlyDayTime.setSelected(true);
                break;
            case NIGHT:
                onlyNightTime.setSelected(true);
                break;
            case BOTH:
                dayAndNight.setSelected(true);
                break;
        }
    }

    /**
     * Sets whether air strike is included in the level
     *
     * @param include true if included, false otherwise
     */
    public void setIncludeAirstrike(boolean include) {
        includeAirstrike.setSelected(include);
    }

    /**
     * Sets the cost of an airstrike
     *
     * @param airstrikeCost the cost of an airstrike
     */
    public void setAirstrikeCost(int airstrikeCost) {
        airstrikeCostTextField.setText(String.valueOf(airstrikeCost));
    }

    /**
     * Sets the number of airstrike hits
     *
     * @param numOfTiles the number of tiles an airstrike hits
     */
    public void setAirstrikeNumberOfHits(int numOfTiles) {
        airstrikeNumberOfHitsTextField.setText(String.valueOf(numOfTiles));
    }

    /**
     * Gets the selected allowed objects
     *
     * @return the set of allowed objects
     */
    public HashSet<GameObjectType> getSelectedObjects() {
        return objectSelectionGroup.getSelectedObjects();
    }

    /**
     * Sets the set of selected objects
     *
     * @param allowedItems the set of selected objects
     */
    public void setSelectedObjects(HashSet<GameObjectType> allowedItems) {
        objectSelectionGroup.setSelectedObjectsValues(allowedItems);
    }
}
