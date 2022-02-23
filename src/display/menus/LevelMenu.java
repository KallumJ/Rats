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
 * A class to model the level selection menu.
 *
 * @author Kallum Jones 2005855
 * @date 2022.02.21
 */
public class LevelMenu extends GameMenu {
    public static final String MENU_TITLE = " L E V E L S ";

    /**
     * A method to build a menu containing all the levels in the level
     * directory as selectable options.
     *
     * @return the Node containing the menu items
     */
    @Override
    public Parent buildMenu() {
        ArrayList<MenuItem> menuItems = new ArrayList<>();

        // For every level file, add a menu item to the list of menu items
        for (File levelFile : LevelUtils.getFilesInLevelDirectory()) {
            String levelId = LevelUtils.getFilesLevelId(levelFile);
            Player currentlyLoggedInPlayer =
                    PlayerProfileManager.getCurrentlyLoggedInPlayer();

            // Only add levels the player has unlocked
            try {
                int id = Integer.parseInt(levelId);
                if (currentlyLoggedInPlayer.getMaxLevel() >= id) {
                    LevelMenuItem levelMenuItem =
                            new LevelMenuItem(levelId);
                    menuItems.add(levelMenuItem);
                }
            } catch (NumberFormatException ex) {
                break;
            }

        }

        // Add these menu items to a menu box
        MenuItem[] menuItemsArr = menuItems.toArray(new MenuItem[0]);
        MenuBox menuBox = new MenuBox(menuItemsArr);

        // Add a back button event handler
        EventHandler<Event> backHandler = event -> GameMenu.getStage()
                .setScene(new Scene(new MainMenu().buildMenu()));

        // Return the constructed menu
        return buildMenu(new MenuTitle(MENU_TITLE), menuBox, backHandler);
    }

}
