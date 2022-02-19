package display.menus.editor;

import display.CustomBoard;
import display.menus.GameMenu;
import display.menus.MenuItem;
import envrionment.TimeOfDay;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import level.LevelData;
import level.custom.CustomLevelDataFactory;

public class HardMenuItem extends MenuItem{
    private static final int HEIGHT_LIMIT = 20;
    private static final int WIDTH_LIMIT = 20;
    private static final String INVALID_SIZE =
            "Please choose a size with a height no greater than " + HEIGHT_LIMIT +
                    "and a width no greater than " + WIDTH_LIMIT;
    private static final int POPULATION_TO_LOSE = 20;
    private static final int EXPECTED_TIME = 90;
    private static final int ITEM_INTERVAL = 9;
    private static final int RAT_MAX_BABIES = 5;
    private static final int RAT_MIN_BABIES = 3;
    private static final int ADULT_RAT_SPEED = 1500;
    private static final int BABY_RAT_SPEED = 500;
    private static final int DEATH_RAT_SPEED = 1250;
    private static final boolean INCLUDE_AIRSTRIKE = false;
    private static final int COST_OF_AIRSTRIKE = 50;
    private static final int AIRSTRIKE_NUM_OF_HITS = 6;
    private static final TimeOfDay TIME_OF_DAY = TimeOfDay.BOTH;
    private static final int TIME_INTERVAL = 60;

    public HardMenuItem() {
        super(" H A R D ");

        setOnMousePressed(event -> {
            int height = 12;
            int width = 12;

            if (height > HEIGHT_LIMIT || width > WIDTH_LIMIT) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(INVALID_SIZE);
                alert.showAndWait();
            } else {
                LevelData customLevel = CustomLevelDataFactory.getBlankLevelData(height, width);

                CustomBoard board = new CustomBoard(customLevel, POPULATION_TO_LOSE,
                        EXPECTED_TIME, ITEM_INTERVAL, RAT_MAX_BABIES, RAT_MIN_BABIES,
                        ADULT_RAT_SPEED, BABY_RAT_SPEED, DEATH_RAT_SPEED, INCLUDE_AIRSTRIKE, COST_OF_AIRSTRIKE,
                        AIRSTRIKE_NUM_OF_HITS, TIME_OF_DAY, TIME_INTERVAL
                );
                Scene scene = new Scene(board.buildGUI());              

                GameMenu.getStage().setScene(scene);
            }
        });
    }

}

