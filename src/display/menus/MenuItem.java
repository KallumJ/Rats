package display.menus;

import display.Board;
import display.CustomBoard;
import display.menus.editor.SizeSelectionMenu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import level.LevelData;
import level.LevelDataFactory;
import level.LevelProperties;
import level.LevelUtils;
import level.custom.CustomLevelDataFactory;
import objects.GameObject;
import players.PlayerProfileManager;
import players.scores.Player;
import util.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;

/**
 * A class to model an item in a MenuBox.
 *
 * @author Samhitha Pinisetti 2035196
 * @date 2022.02.21
 *
 */
public abstract class MenuItem extends StackPane {

	private final String name;

	/**
	 * Constructs a MenuItem with the provided name.
	 *
	 * @param name the name of the menu item
	 */
	public MenuItem(String name) {
		this.name = name;

		LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true,
				CycleMethod.NO_CYCLE, new Stop(0, Color.DARKVIOLET),
				new Stop(0.1, Color.BLACK), new Stop(0.9, Color.BLACK),
				new Stop(1, Color.DARKVIOLET));

		Rectangle bg = new Rectangle(200, 30);
		bg.setOpacity(0.4);

		Text text = new Text(name);
		text.setFill(Color.DARKGREY);
		text.setFont(TextUtils.getFont(22));

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
	 * A method to return the name of the MenuItem.
	 *
	 * @return the name of the menu item
	 */
	public String getName() {
		return name;
	}
}

/**
 * A class to model the play menu item in the MainMenu.
 *
 * @author Kallum Jones 2005855
 */
class PlayMenuItem extends MenuItem {

	/**
	 * Constructs a PlayMenuItem.
	 */
	public PlayMenuItem() {
		super("PLAY");

		Scene scene = new Scene(new LevelMenu().buildMenu());
		setOnMousePressed(event -> GameMenu.getStage().setScene(scene));
	}
}

/**
 * A class to model the leaderboard menu item in the MainMenu.
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
		setOnMousePressed(event -> GameMenu.getStage().setScene(scene));
	}
}

/**
 * A class to model the exit menu item in the MainMenu.
 *
 * @author Kallum Jones 2005855
 */
class ExitMenuItem extends MenuItem {

	/**
	 * Constructs an ExitMenuItem.
	 */
	public ExitMenuItem() {
		super("EXIT");

		setOnMousePressed(event -> System.exit(0));
	}
}

/**
 * A class to model the help menu item in the MainMenu.
 *
 * @author Kallum Jones 2005855
 */
class HelpMenuItem extends MenuItem {

	/**
	 * Constructs a HelpMenuItem.
	 */
	public HelpMenuItem() {
		super("HELP");

		Scene scene = new Scene(new HelpMenu().buildMenu());
		setOnMousePressed(event -> GameMenu.getStage().setScene(scene));
	}
}

/**
 * A class to model the -continue menu item in the MainMenu.
 *
 * @author Kallum Jones 2005855
 */
class ContinueMenuItem extends MenuItem {
	private static final String NO_SAVED_LEVELS = "No Saved Levels";
	private static final String ALERT_MSG = "You have no saved levels to " +
			"continue";

	/**
	 * Constructs a ContinueMenuItem.
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
						LevelDataFactory.constructLevelDataFromFile(mostRecentLevel);
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
 * A class to model the load menu item in the MainMenu.
 *
 * @author Kallum Jones 2005855
 */
class LoadMenuItem extends MenuItem {
	/**
	 * Constructs a LoadMenuItem.
	 */
	public LoadMenuItem() {
		super("LOAD");

		Scene scene = new Scene(new LoadMenu().buildMenu());
		setOnMousePressed(event -> GameMenu.getStage().setScene(scene));
	}
}

/**
 * A class to model the level menu item in the LevelMenu.
 *
 * @author Kallum Jones 2005855
 */
class LevelMenuItem extends MenuItem {
	/**
	 * Constructs a levelMenuItem.
	 *
	 * @param id the id of the level
	 */
	public LevelMenuItem(String id) {
		super(id);

		setOnMousePressed(event -> {
			String levelId = super.getName();
			LevelData levelData = LevelDataFactory.constructLevelData(levelId);

			Board board = new Board(levelData);

			board.startGame();
			Scene scene = new Scene(board.buildGUI());
			GameMenu.getStage().setScene(scene);
		});
	}
}

/**
 * A class to model a menu item for saved levels in the level menu.
 *
 * @author Kallum Jones 2005855
 */
class SavedLevelMenuItem extends MenuItem {

	/**
	 * Constructs a SavedLevelMenuItem.
	 *
	 * @param level the Level file that should open when this menu item is pressed
	 */
	public SavedLevelMenuItem(File level) {
		super(LevelUtils.getFilesLevelId(level));

		setOnMousePressed(event -> {
			LevelData levelData =
					LevelDataFactory.constructLevelDataFromFile(level);

			Board board = new Board(levelData);
			board.startGame();

			Scene scene = new Scene(board.buildGUI());
			GameMenu.getStage().setScene(scene);
		});
	}
}

/**
 * A class to model a menu item for deleting custom levels
 */
class DeleteLevelMenuItem extends MenuItem {

	/**
	 * Constructs a DeleteLevelMenuItem with the name of the provided level.
	 *
	 * @param level the Level file that should be deleted when this menu item is pressed
	 */
	public DeleteLevelMenuItem(File level) {
		super(LevelUtils.getFilesLevelId(level));

		String levelId = LevelUtils.getFilesLevelId(level);
		setOnMousePressed(event -> {
			String contentMsg = "Are you sure you want to delete level %s? " +
					"\n Type %s exactly to confirm and delete";
			TextInputDialog confirmDialog = new TextInputDialog();
			confirmDialog.setTitle("Confirm level deletion");
			confirmDialog.setContentText(String.format(contentMsg, levelId, levelId));

			Optional<String> decision = confirmDialog.showAndWait();
			if (decision.isPresent() && decision.get().equals(levelId)) {
				level.delete();

				// Delete all saves for this level also
				File[] savedLevels = LevelUtils.getSavedCustomLevelFiles();
				for (File savedLevel : savedLevels) {
					if (level.getName().equals(savedLevel.getName())) {
						savedLevel.delete();
					}
				}
				Scene scene = new Scene(new CustomLevelsMenu().buildMenu());
				GameMenu.getStage().setScene(scene);
			}
		});
	}
}

/**
 * A class to model a menu item for level items in the LeaderboardMenu.
 *
 * @author Kallum Jones 2005855
 */
class LeaderboardLevelMenuItem extends MenuItem {

	/**
	 * Constructs a LeaderboardLevelMenuItem.
	 *
	 * @param id the id of the level
	 */
	public LeaderboardLevelMenuItem(String id) {
		super(id);

		Scene scene = new Scene(new LeaderBoardMenu(id).buildMenu());
		setOnMousePressed(event -> GameMenu.getStage().setScene(scene));
	}
}

/**
 * Forward to Custom Levels menu.
 *
 * @author YIMING LI (09/02/22)
 */
class CustomLevelsMenuItem extends MenuItem {

	/**
	 * Constructs a CustomLevelsMenuItem.
	 */
	public CustomLevelsMenuItem() {
		super("CUSTOM LEVELS");

		Scene scene = new Scene(new CustomLevelsMenu().buildMenu());
		setOnMousePressed(event -> GameMenu.getStage().setScene(scene));
	}
}

/**
 * A class to model a menu item for starting a custom level
 *
 * @author Kallum Jones 2005855 (10/02/22)
 */
class StartCustomLevelMenuItem extends MenuItem {
	/**
	 * Constructs a StartNewCustomLevelMenuItem button.
	 */
	public StartCustomLevelMenuItem() {
		super("CREATE CUSTOM LEVEL");

		Scene scene = new Scene(new SizeSelectionMenu().buildMenu());
		setOnMousePressed(event -> GameMenu.getStage().setScene(scene));
	}
}

/**
 * A class to model a menu item for editing a custom level
 *
 * @author Kallum Jones 2005855 (10/02/22)
 */
class EditCustomLevelMenuItem extends MenuItem {

	/**
	 * Constructs a EditCustomLevelMenuItem with the provided name.
	 */
	public EditCustomLevelMenuItem() {
		super("EDIT CUSTOM LEVEL");
		Scene scene = new Scene(new EditLevelMenu().buildMenu());
		setOnMousePressed(event -> GameMenu.getStage().setScene(scene));
	}
}

/**
 * A class to model a menu item for loading a custom level
 *
 * @author Kallum Jones 2005855 (10/02/22)
 */
class LoadCustomLevelMenuItem extends MenuItem {

	/**
	 * Constructs a MenuItem with the provided name.
	 */
	public LoadCustomLevelMenuItem() {
		super("LOAD CUSTOM LEVEL");

		setOnMousePressed(event -> {
			Scene scene = new Scene(new LoadCustomLevelMenu().buildMenu());
			GameMenu.getStage().setScene(scene);
		});
	}
}

/**
 * A class to model a menu item for deleting a custom level
 *
 * @author Kallum Jones 2005855 (10/02/22)
 */
class DeleteCustomLevelMenuItem extends MenuItem {

	/**
	 * Constructs a DeleteCustomLevelMenuItem with the provided name.
	 */
	public DeleteCustomLevelMenuItem() {
		super("DELETE CUSTOM LEVEL");

		setOnMousePressed(event -> {
			Scene scene = new Scene(new DeleteCustomLevelMenu().buildMenu());
			GameMenu.getStage().setScene(scene);
		});
	}
}

/**
 * A class to model a menu item for selecting a custom level to edit
 *
 * @author Kallum Jones 2005855 (20/02/22)
 */
class LevelEditItem extends MenuItem {

	/**
	 * Constructs a LevelEditItem with the provided name.
	 */
	public LevelEditItem(String id) {
		super(id);
		setOnMousePressed(event -> {
			LevelData levelData =
					CustomLevelDataFactory.constructCustomLevelData(PlayerProfileManager.getCurrentlyLoggedInPlayer(), id);
			LevelProperties levelProperties = levelData.getLevelProperties();
			// Rats should appear in the editor as adults
			levelData.makeRatsAdults();
			CustomBoard customBoard = 
					new CustomBoard(levelData, levelProperties.getPopulationToLose(), levelProperties.getExpectedTime(), 
					levelProperties.getItemInterval(), levelProperties.getRatMaxBabies(), levelProperties.getRatMinBabies(), 
					levelProperties.getAdultRatSpeed(), levelProperties.getBabyRatSpeed(), levelProperties.getDeathRatSpeed(), 
					levelProperties.isAirstrikeEnabled(), levelProperties.getCostOfAirstrike(), levelProperties.getNumOfAirstrikeHits(), 
					levelProperties.getTimeOfDay(), levelProperties.getTimeInterval(), levelProperties.getAllowedItems(), levelProperties.getLevelId());
			GameObject.setBoard(customBoard);
			Scene scene = new Scene(customBoard.buildGUI());
			GameMenu.getStage().setScene(scene);
		});
	}
}

/**
 * A class to model a menu item for resuming an in progress custom level
 * @author Kallum Jones 2005855
 * @date 22/02/22
 */
class ResumeCustomLevelMenuItem extends MenuItem {

	/**
	 * Constructs a ResumeCustomLevelMenuItem with the provided name.
	 */
	public ResumeCustomLevelMenuItem() {
		super("RESUME CUSTOM LEVEL");

		setOnMousePressed(event -> {
			Scene scene = new Scene(new ResumeCustomLevelMenu().buildMenu());
			GameMenu.getStage().setScene(scene);
		});
	}
}