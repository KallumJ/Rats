package display.menus;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import level.LevelUtils;
import players.PlayerProfileManager;
import players.scores.Player;

import java.io.File;
import java.util.ArrayList;

/**
 * A class to model a menu to resume in progress custom levels
 */
public class ResumeCustomLevelMenu extends GameMenu {
    private static final String MENU_TITLE = " R E S U M E ";

    /**
     * A method to build the designed menu
     * @return the designed menu
     */
    @Override
    public Parent buildMenu() {
        Player currentlyLoggedInPlayer =
                PlayerProfileManager.getCurrentlyLoggedInPlayer();
        ArrayList<MenuItem> menuItems = new ArrayList<>();

        // For every saved level file for this player, add a menu item to the
        // list of menu items
        for (File savedLevel : LevelUtils.getSavedCustomLevelFiles()) {
            String playerForLevel =
                    LevelUtils.getPlayerNameFromSavedLevel(savedLevel);
            String loggedInPlayerName =
                    currentlyLoggedInPlayer.getPlayerName();

            if (playerForLevel.equals(loggedInPlayerName)) {
                SavedLevelMenuItem levelMenuItem =
                        new SavedLevelMenuItem(savedLevel);
                menuItems.add(levelMenuItem);
            }
        }

        // Add these menu items to a menu box
        MenuItem[] menuItemsArr = menuItems.toArray(new MenuItem[0]);
        MenuBox menuBox = new MenuBox(menuItemsArr);

        // Add a back button event handler
        EventHandler<Event> backHandler = event -> GameMenu.getStage()
                .setScene(new Scene(new CustomLevelsMenu().buildMenu()));

        // Return the constructed menu
        return buildMenu(new MenuTitle(MENU_TITLE), menuBox, backHandler);
    }
}
