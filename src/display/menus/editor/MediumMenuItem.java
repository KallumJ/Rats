package display.menus.editor;

import display.CustomBoard;
import display.menus.GameMenu;
import display.menus.MenuItem;
import envrionment.TimeOfDay;
import javafx.scene.Scene;
import level.LevelData;
import level.custom.CustomLevelDataFactory;

public class MediumMenuItem extends MenuItem {
    private static final int POPULATION_TO_LOSE = 30;
    private static final int EXPECTED_TIME = 120;
    private static final int ITEM_INTERVAL = 6;
    private static final int RAT_MAX_BABIES = 5;
    private static final int RAT_MIN_BABIES = 2;
    private static final int ADULT_RAT_SPEED = 1250;
    private static final int BABY_RAT_SPEED = 600;
    private static final int DEATH_RAT_SPEED = 1100;
    private static final boolean INCLUDE_AIRSTRIKE = true;
    private static final int COST_OF_AIRSTRIKE = 50;
    private static final int AIRSTRIKE_NUM_OF_HITS = 6;
    private static final TimeOfDay TIME_OF_DAY = TimeOfDay.BOTH;
    private static final int TIME_INTERVAL = 60;
    private static final int height = 12;
    private static final int width = 12;

    public MediumMenuItem() {
        super(" M E D I U M ");

        setOnMousePressed(event -> {
            LevelData customLevel = CustomLevelDataFactory.getBlankLevelData(height, width);

            CustomBoard board = new CustomBoard(customLevel, POPULATION_TO_LOSE,
                    EXPECTED_TIME, ITEM_INTERVAL, RAT_MAX_BABIES, RAT_MIN_BABIES,
                    ADULT_RAT_SPEED, BABY_RAT_SPEED, DEATH_RAT_SPEED, INCLUDE_AIRSTRIKE, COST_OF_AIRSTRIKE,
                    AIRSTRIKE_NUM_OF_HITS, TIME_OF_DAY, TIME_INTERVAL, null, null
            );
            Scene scene = new Scene(board.buildGUI());

            GameMenu.getStage().setScene(scene);
        });
    }

}
