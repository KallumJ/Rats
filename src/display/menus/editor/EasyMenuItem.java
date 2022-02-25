package display.menus.editor;

import display.CustomBoard;
import display.menus.GameMenu;
import display.menus.MenuItem;
import envrionment.TimeOfDay;
import javafx.scene.Scene;
import level.LevelData;
import level.custom.CustomLevelDataFactory;

/**
 * @date 2022/02/22
 */

public class EasyMenuItem extends MenuItem {
    private static final int POPULATION_TO_LOSE = 50;
    private static final int EXPECTED_TIME = 200;
    private static final int ITEM_INTERVAL = 3;
    private static final int RAT_MAX_BABIES = 3;
    private static final int RAT_MIN_BABIES = 1;
    private static final int ADULT_RAT_SPEED = 1000;
    private static final int BABY_RAT_SPEED = 700;
    private static final int DEATH_RAT_SPEED = 1010;
    private static final boolean INCLUDE_AIRSTRIKE = true;
    private static final int COST_OF_AIRSTRIKE = 40;
    private static final int AIRSTRIKE_NUM_OF_HITS = 8;
    private static final TimeOfDay TIME_OF_DAY = TimeOfDay.BOTH;
    private static final int TIME_INTERVAL = 60;
    private static final int height = 12;
    private static final int width = 12;

    public EasyMenuItem() {
        super(" E A S Y ");

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
