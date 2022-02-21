package display.menus;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import players.PlayerProfileManager;
import players.scores.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A class to model a level select menu for the leaderboard.
 *
 * @author Kallum Jones 2005855
 * @date 2022.02.18
 *
 */
public class LeaderboardLevelSelectMenu extends GameMenu {
	private static final String MENU_TITLE = " H I G H  S C O R E ";

	/**
	 * Creates a Leaderboard level select menu.
	 *
	 * @return the node of the created menu layout
	 */
	@Override
	public Parent buildMenu() {
		ArrayList<MenuItem> menuItems = new ArrayList<>();
		List<Player> allPlayers = PlayerProfileManager.getAllPlayers();

		HashSet<Integer> levelsWithScore = new HashSet<>();

		// Create a set of all the level ids that someone has a score for
		for (Player allPlayer : allPlayers) {
			allPlayer.getHighScores()
					.forEach((level, score) -> levelsWithScore.add(level));
		}

		// Create a menu item for each level
		for (Integer level : levelsWithScore) {
			LeaderboardLevelMenuItem menuItem =
					new LeaderboardLevelMenuItem(String.valueOf(level));
			menuItems.add(menuItem);
		}

		// Add a back button event handler
		EventHandler<Event> backHandler = event -> GameMenu.getStage()
				.setScene(new Scene(new MainMenu().buildMenu()));


		// Add the menu items to the box
		MenuBox menuBox = new MenuBox(menuItems.toArray(new MenuItem[0]));

		return buildMenu(new MenuTitle(MENU_TITLE), menuBox, backHandler);
	}
}
