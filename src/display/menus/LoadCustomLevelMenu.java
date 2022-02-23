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
 * A class to model a menu for loading a players custom levels
 */
public class LoadCustomLevelMenu extends GameMenu {
    private static final String MENU_TITLE = " L O A D ";

    /**
     * Constructs the designed menu
     *
     * @return the constructed menu
     */
    @Override
    public Parent buildMenu() {
        Player currentlyLoggedInPlayer =
                PlayerProfileManager.getCurrentlyLoggedInPlayer();
        ArrayList<MenuItem> menuItems = new ArrayList<>();

        // For every custom level file for this player, add a menu item to the
        // list of menu items
        for (File savedLevel : LevelUtils.getCustomLevels()) {
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
