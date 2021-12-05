package display.menus;


import display.Board;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import level.LevelData;
import level.LevelDataFactory;
import level.LevelUtils;
import players.PlayerProfileManager;
import players.scores.Player;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * A class to model an item in a MenuBox
 *
 * @author Samhitha Pinisetti 2035196
 */
public abstract class MenuItem extends StackPane {


    private final String name;

    /**
     * Constructs a MenuItem with the provided name
     *
     * @param name the name of the menu item
     */
    public MenuItem(String name) {
        this.name = name;

        LinearGradient gradient =
                new LinearGradient(0, 0, 1, 0,
                        true,
                        CycleMethod.NO_CYCLE,
                        new Stop(0, Color.DARKVIOLET),
                new Stop(0.1, Color.BLACK),
                new Stop(0.9, Color.BLACK),
                new Stop(1, Color.DARKVIOLET));

        Rectangle bg = new Rectangle(200, 30);
        bg.setOpacity(0.4);

        Text text = new Text(name);
        text.setFill(Color.DARKGREY);
        text.setFont(Font.font(
                GameMenu.DEFAULT_FONT, FontWeight.SEMI_BOLD, 22
        ));

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg, text);

        setOnMouseEntered(event -> {
            bg.setFill(gradient);
            text.setFill(Color.WHITE);
        });

        setOnMouseExited(event -> {
            bg.setFill(Color.BLACK);
            text.setFill(Color.DARKGREY);
        });

        setOnMousePressed(event -> bg.setFill(Color.DARKVIOLET));

        setOnMouseReleased(event -> bg.setFill(gradient));


    }

    /**
     * A method to return the name of the MenuItem
     *
     * @return the name of the menu item
     */
    public String getName() {
        return name;
    }
}

/**
 * A class to model the play menu item in the MainMenu
 *
 * @author Kallum Jones 2005855
 */
class PlayMenuItem extends MenuItem {

    /**
     * Constructs a PlayMenuItem
     */
    public PlayMenuItem() {
        super("PLAY");

        Scene scene = new Scene(new LevelMenu().buildMenu());
        setOnMousePressed(event ->
                GameMenu.getStage().setScene(scene));
    }
}

/**
 * A class to model the leaderboard menu item in the MainMenu
 *
 * @author Kallum Jones 2005855
 */
class LeaderboardMenuItem extends MenuItem {

    /**
     * Constructs a LeaderboardMenuItem
     */
    public LeaderboardMenuItem() {
        super("LEADERBOARD");

        Scene scene = new Scene(new LeaderboardLevelSelectMenu().buildMenu());
        setOnMousePressed(event ->
                GameMenu.getStage().setScene(scene));
    }
}

/**
 * A class to model the exit menu item in the MainMenu
 *
 * @author Kallum Jones 2005855
 */
class ExitMenuItem extends MenuItem {

    /**
     * Constructs an ExitMenuItem
     */
    public ExitMenuItem() {
        super("EXIT");

        setOnMousePressed(event -> System.exit(0));
    }
}

/**
 * A class to model the help menu item in the MainMenu
 *
 * @author Kallum Jones 2005855
 */
class HelpMenuItem extends MenuItem {

    /**
     * Constructs a HelpMenuItem
     */
    public HelpMenuItem() {
        super("HELP");

        Scene scene = new Scene(new HelpMenu().buildMenu());
        setOnMousePressed(event ->
                GameMenu.getStage().setScene(scene));
    }
}

/**
 * A class to model the continue menu item in the MainMenu
 *
 * @author Kallum Jones 2005855
 */
class ContinueMenuItem extends MenuItem {
    private static final String NO_SAVED_LEVELS = "No Saved Levels";
    private static final String ALERT_MSG = "You have no saved levels to continue";

    /**
     * Constructs a ContinueMenuItem
     */
    public ContinueMenuItem() {
        super("CONTINUE");
        setOnMousePressed(event -> {
            Player player = PlayerProfileManager.getCurrentlyLoggedInPlayer();
            File mostRecentLevel;
            try {
                // Get the most recent level and start the game
                mostRecentLevel = LevelUtils.getMostRecentLevel(player);

                LevelData levelData =
                        LevelDataFactory.constructLevelDataFromFile(
                                mostRecentLevel
                        );
                Board board = new Board(levelData);

                board.startGame();
                GameMenu.getStage().setScene(new Scene(board.buildGUI()));
            } catch (FileNotFoundException e) {
                // If no saved levels, display alert to user
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(NO_SAVED_LEVELS);
                alert.setHeaderText(ALERT_MSG);
                alert.showAndWait();
            }
        });
    }
}

/**
 * A class to model the load menu item in the MainMenu
 *
 * @author Kallum Jones 2005855
 */
class LoadMenuItem extends MenuItem {
    /**
     * Constructs a LoadMenuItem
     */
    public LoadMenuItem() {
        super("LOAD");

        Scene scene = new Scene(new LoadMenu().buildMenu());
        setOnMousePressed(event ->
                GameMenu.getStage().setScene(scene));
    }
}

/**
 * A class to model the level menu item in the LevelMenu
 *
 * @author Kallum Jones 2005855
 */
class LevelMenuItem extends MenuItem {
    /**
     * Constructs a levelMenuItem
     *
     * @param id the id of the level
     */
    public LevelMenuItem(String id) {
        super(id);

        setOnMousePressed(event -> {
            int levelId = Integer.parseInt(super.getName());
            LevelData levelData = LevelDataFactory.constructLevelData(levelId);

            Board board = new Board(levelData);

            board.startGame();
            Scene scene = new Scene(board.buildGUI());
            GameMenu.getStage().setScene(scene);
        });
    }
}

/**
 * A class to model a menu item for saved levels in the level menu
 *
 * @author Kallum Jones 2005855
 */
class SavedLevelMenuItem extends MenuItem {

    /**
     * Constructs a SavedLevelMenuItem
     *
     * @param id the id of the level
     */
    public SavedLevelMenuItem(String id) {
        super(id);

        setOnMousePressed(event -> {
            LevelData levelData = LevelDataFactory.constructSavedLevelData(
                    PlayerProfileManager.getCurrentlyLoggedInPlayer(), id
            );

            Board board = new Board(levelData);
            board.startGame();

            Scene scene = new Scene(board.buildGUI());
            GameMenu.getStage().setScene(scene);
        });
    }
}

/**
 * A class to model a menu item for level items in the LeaderboardMenu
 */
class LeaderboardLevelMenuItem extends MenuItem {

    /**
     * Constructs a LeaderboardLevelMenuItem
     *
     * @param id the id of the level
     */
    public LeaderboardLevelMenuItem(String id) {
        super(id);

        Scene scene = new Scene(new LeaderBoardMenu(id).buildMenu());
        setOnMousePressed(event ->
                GameMenu.getStage().setScene(scene));
    }
}
