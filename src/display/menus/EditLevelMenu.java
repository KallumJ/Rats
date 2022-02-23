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
 * A class to model the menu for selecting a custom level to edit
 *
 * @author Kallum Jones (2005855)
 * @date 2022.02.20
 */
public class EditLevelMenu extends GameMenu {
    private static final String MENU_TITLE = " E D I T ";

    /**
     * A method to build the designed menu for editing a selected level
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
                String levelId = LevelUtils.getFilesLevelId(savedLevel);
                LevelEditItem levelMenuItem =
                        new LevelEditItem(levelId);
                menuItems.add(levelMenuItem);
            }
        }
        MenuItem[] menuItemsArr = menuItems.toArray(new MenuItem[0]);
        MenuBox menuBox = new MenuBox(menuItemsArr);

        // Add a back button event handler
        EventHandler<Event> backHandler = event -> GameMenu.getStage()
                .setScene(new Scene(new CustomLevelsMenu().buildMenu()));

        return buildMenu(new MenuTitle(MENU_TITLE), menuBox, backHandler);
    }
}
