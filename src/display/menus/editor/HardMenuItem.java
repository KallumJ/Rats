package display.menus.editor;

import display.CustomBoard;
import display.menus.GameMenu;
import display.menus.MenuItem;
import envrionment.TimeOfDay;
import javafx.scene.Scene;
import level.LevelData;
import level.custom.CustomLevelDataFactory;

/**
 * A class to model a menu item for selecting the Hard level preset
 *
 * @author Samhitha Pinisetti
 * @date 2022/02/22
 */
public class HardMenuItem extends MenuItem {
    private static final int POPULATION_TO_LOSE = 20;
    private static final int EXPECTED_TIME = 90;
    private static final int ITEM_INTERVAL = 9;
    private static final int RAT_MAX_BABIES = 5;
    private static final int RAT_MIN_BABIES = 3;
    private static final int ADULT_RAT_SPEED = 1000;
    private static final int BABY_RAT_SPEED = 700;
    private static final int DEATH_RAT_SPEED = 1010;
    private static final boolean INCLUDE_AIRSTRIKE = false;
    private static final int COST_OF_AIRSTRIKE = 50;
    private static final int AIRSTRIKE_NUM_OF_HITS = 6;
    private static final TimeOfDay TIME_OF_DAY = TimeOfDay.BOTH;
    private static final int TIME_INTERVAL = 60;
    private static final int HEIGHT = 12;
    private static final int WIDTH = 12;

    /**
     * Constructs a HardMenuItem
     */
    public HardMenuItem() {
        super(" H A R D ");

        setOnMousePressed(event -> {
            LevelData customLevel = CustomLevelDataFactory.getBlankLevelData(HEIGHT, WIDTH);

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

