package display.menus.editor;

import envrionment.TimeOfDay;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import level.LevelData;
import level.LevelProperties;
import tile.TileType;
import util.TextUtils;

/**
 * A class to get input from the user for the custom level properties
 *
 * @author Kallum Jones (2005855) Aser (minor updates)
 * @date 2022.02.11
 *
 */
public class LevelEditorOptionsMenu {
    public static final int WINDOW_OFFSET = 800;

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

        levelNameTextField = new TextField();
    }

    /**
     * Builds the LevelPropertiesInputMenu gui
     * @return the menu as a VBox
     */
    public VBox buildGUI() {
        //Label for Population to Lose
        Label populationToLoseLabel = new Label("Population to Lose:");
        populationToLoseLabel.setTextFill(Color.WHITE);
        populationToLoseLabel.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 14;");
        Tooltip populationToLoseTooltip = new Tooltip("Population to Lose");
        Tooltip.install(populationToLoseLabel, populationToLoseTooltip);

        populationToLoseLabel.setTranslateX(30);
        populationToLoseLabel.setTranslateY(20);

        //Label for Expected Time 
        Label expectedTimeLabel = new Label("Expected Time:");
        expectedTimeLabel.setTextFill(Color.WHITE);
        expectedTimeLabel.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 14;");

        expectedTimeLabel.setTranslateX(30);
        expectedTimeLabel.setTranslateY(40);

        //Lable for Item Interval
        Label itemIntervalLabel = new Label("Item Interval:");
        itemIntervalLabel.setTextFill(Color.WHITE);
        itemIntervalLabel.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 14;");

        itemIntervalLabel.setTranslateX(30);
        itemIntervalLabel.setTranslateY(60);

        //Label for Maximum number of Rat Babies 
        Label ratMaxBabiesLabel = new Label("Maximum babies:");
        ratMaxBabiesLabel.setTextFill(Color.WHITE);
        ratMaxBabiesLabel.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 14;");

        ratMaxBabiesLabel.setTranslateX(30);
        ratMaxBabiesLabel.setTranslateY(80);

        //Label for Minimum number of Rat Babies 
        Label ratMinBabiesLabel = new Label("Minimum babies:");
        ratMinBabiesLabel.setTextFill(Color.WHITE);
        ratMinBabiesLabel.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 14;");

        ratMinBabiesLabel.setTranslateX(30);
        ratMinBabiesLabel.setTranslateY(100);

        //Label for Adult Rat Speed 
        Label adultRatSpeedLabel = new Label("Adult Rat Speed:");
        adultRatSpeedLabel.setTextFill(Color.WHITE);
        adultRatSpeedLabel.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 14;");

        adultRatSpeedLabel.setTranslateX(30);
        adultRatSpeedLabel.setTranslateY(120);

        //Label for Baby Rat Speed 
        Label babyRatSpeedLabel = new Label("Baby Rat Speed:");
        babyRatSpeedLabel.setTextFill(Color.WHITE);
        babyRatSpeedLabel.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 14;");

        babyRatSpeedLabel.setTranslateX(30);
        babyRatSpeedLabel.setTranslateY(140);

        //Label for Death Rat Speed 
        Label deathRatSpeedLabel = new Label("Death Rat Speed:");
        deathRatSpeedLabel.setTextFill(Color.WHITE);
        deathRatSpeedLabel.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 14;");

        deathRatSpeedLabel.setTranslateX(30);
        deathRatSpeedLabel.setTranslateY(160);

        //Label for Cost of Airstrike 
        Label airstrikeCostLabel = new Label("Required points for airstrike:");
        airstrikeCostLabel.setTextFill(Color.WHITE);
        airstrikeCostLabel.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 14;");

        airstrikeCostLabel.setTranslateX(30);
        airstrikeCostLabel.setTranslateY(180);

        //Label for Number of Airstrike Hits 
        Label airstrikeNumberOfHitsLabel = new Label("Number of targets in an airstrike:");
        airstrikeNumberOfHitsLabel.setTextFill(Color.WHITE);
        airstrikeNumberOfHitsLabel.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 14;");

        airstrikeNumberOfHitsLabel.setTranslateX(30);
        airstrikeNumberOfHitsLabel.setTranslateY(200);

        //Label for Day and Night 
        Label timeDayAndNightLabel = new Label("Period between day and night:");
        timeDayAndNightLabel.setTextFill(Color.WHITE);
        timeDayAndNightLabel.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 14;");

        timeDayAndNightLabel.setTranslateX(30);
        timeDayAndNightLabel.setTranslateY(220);

        Label levelNameLabel = new Label("Level name");
        levelNameLabel.setTextFill(Color.WHITE);
        levelNameLabel.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 14;");

        /*
         * adds a choice box with choice of tiles
         * event listener added to choice box which is activated when new tile is chosen
         */
        tileSelectChoiceBox.getItems().addAll("Grass Tile", "Tunnel Tile", "Path Tile");
        tileSelectChoiceBox.setValue("Grass Tile");

        // Setting style for the textfields
        populationToLoseTextField.setStyle("-fx-background-color: #000000; -fx-border-color: darkgrey; -fx-border-width: 1px; -fx-text-fill: white;");
        expectedTimeTextField.setStyle("-fx-background-color: #000000; -fx-border-color: darkgrey; -fx-border-width: 1px; -fx-text-fill: white;");
        itemIntervalTextField.setStyle("-fx-background-color: #000000; -fx-border-color: darkgrey; -fx-border-width: 1px; -fx-text-fill: white;");
        ratMaxBabiesTextField.setStyle("-fx-background-color: #000000; -fx-border-color: darkgrey; -fx-border-width: 1px; -fx-text-fill: white;");
        ratMinBabiesTextField.setStyle("-fx-background-color: #000000; -fx-border-color: darkgrey; -fx-border-width: 1px; -fx-text-fill: white;");
        adultRatSpeedTextField.setStyle("-fx-background-color: #000000; -fx-border-color: darkgrey; -fx-border-width: 1px; -fx-text-fill: white;");
        babyRatSpeedTextField.setStyle("-fx-background-color: #000000; -fx-border-color: darkgrey; -fx-border-width: 1px; -fx-text-fill: white;");
        deathRatSpeedTextField.setStyle("-fx-background-color: #000000; -fx-border-color: darkgrey; -fx-border-width: 1px; -fx-text-fill: white;");
        airstrikeCostTextField.setStyle("-fx-background-color: #000000; -fx-border-color: darkgrey; -fx-border-width: 1px; -fx-text-fill: white;");
        airstrikeNumberOfHitsTextField.setStyle("-fx-background-color: #000000; -fx-border-color: darkgrey; -fx-border-width: 1px; -fx-text-fill: white;");
        timeIntervalTextField.setStyle("-fx-background-color: #000000; -fx-border-color: darkgrey; -fx-border-width: 1px; -fx-text-fill: white;");
        levelNameTextField.setStyle("-fx-background-color: #000000; -fx-border-color: darkgrey; -fx-border-width: 1px; -fx-text-fill: white;");


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

        includeAirstrike.setStyle("-fx-text-fill: white; -fx-font-size: 14;");
        includeAirstrike.setTranslateX(450);
        includeAirstrike.setTranslateY(-480);

        deleteItems.setStyle("-fx-text-fill: white; -fx-font-size: 14;");
        deleteItems.setTranslateX(450);
        deleteItems.setTranslateY(-460);

        onlyDayTime.setStyle("-fx-text-fill: white; -fx-font-size: 14;");
        onlyDayTime.setTranslateX(450);
        onlyDayTime.setTranslateY(-440);

        onlyNightTime.setStyle("-fx-text-fill: white; -fx-font-size: 14;");
        onlyNightTime.setTranslateX(450);
        onlyNightTime.setTranslateY(-420);

        dayAndNight.setStyle("-fx-text-fill: white; -fx-font-size: 14;");
        dayAndNight.setTranslateX(450);
        dayAndNight.setTranslateY(-400);

        //Vbox containing all the labels, textfields, etc.
        VBox container = new VBox();
        container.setPrefSize(600, 600);
        container.getChildren().addAll(
            populationToLoseLabel, expectedTimeLabel, itemIntervalLabel, ratMaxBabiesLabel, ratMinBabiesLabel, 
            adultRatSpeedLabel, babyRatSpeedLabel, deathRatSpeedLabel, airstrikeCostLabel, airstrikeNumberOfHitsLabel, 
            timeDayAndNightLabel, row1, row2, row3, row4, row5, row6, row7, row8, row9, row10, row11,
            tileSelectChoiceBox, includeAirstrike, deleteItems, onlyDayTime, onlyNightTime, dayAndNight, levelNameLabel, levelNameTextField
        );
        container.setStyle("-fx-background-color: #000000;");

        return container;
    }


    /**
     * Sets all the properties in the provided LevelData to those contained in the menu
     * @param levelData the level data to set the properties for
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
    }

    private String getLevelName() {
        return levelNameTextField.getText();
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

    public void setPopulationToLose(int populationToLose) {
        populationToLoseTextField.setText(String.valueOf(populationToLose));
    }

    public void setExpectedTime(int expectedTime) {
        expectedTimeTextField.setText(String.valueOf(expectedTime));
    }

    public void setItemInterval(int itemInterval) {
        itemIntervalTextField.setText(String.valueOf(itemInterval));
    }

    public void setRatMaxBabies(int ratMaxBabies) {
        ratMaxBabiesTextField.setText(String.valueOf(ratMaxBabies));
    }

    public void setRatMinBabies(int ratMinBabies) {
        ratMinBabiesTextField.setText(String.valueOf(ratMinBabies));
    }

    public void setAdultRatSpeed(int adultRatSpeed) {
        adultRatSpeedTextField.setText(String.valueOf(adultRatSpeed));
    }

    public void setBabyRatSpeed(int babyRatSpeed) {
        babyRatSpeedTextField.setText(String.valueOf(babyRatSpeed));
    }

    public void setDeathRatSpeed(int deathRatSpeed) {
        deathRatSpeedTextField.setText(String.valueOf(deathRatSpeed));
    }

    public void setIncludeAirstrike(boolean include) {
        includeAirstrike.setSelected(include);
    }

    public void setAirstrikeCost(int airstrikeCost) {
        airstrikeCostTextField.setText(String.valueOf(airstrikeCost));
    }

    public void setAirstrikeNumberOfHits(int numOfTiles) {
        airstrikeNumberOfHitsTextField.setText(String.valueOf(numOfTiles));
    }

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

    public void setTimeInterval(int timeInterval) {
        timeIntervalTextField.setText(String.valueOf(timeInterval));
    }
}
