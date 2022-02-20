package display.menus;

import javafx.scene.Parent;
import level.LevelUtils;
import players.PlayerProfileManager;
import players.scores.Player;

import java.io.File;
import java.util.ArrayList;

/**
 * A class to model the menu for selecting a custom level to edit
 *
 * @author Kallum Jones (2005855) (20/02/22)
 */
public class EditLevelMenu extends GameMenu {
    private static final String MENU_TITLE = " E D I T ";

    /**
     * A method to build the designed menu for editing a selected level
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
                int levelId = LevelUtils.getFilesLevelId(savedLevel);
                LevelEditItem levelMenuItem =
                        new LevelEditItem(String.valueOf(levelId));
                menuItems.add(levelMenuItem);
            }
        }
        MenuItem[] menuItemsArr = menuItems.toArray(new MenuItem[0]);
        MenuBox menuBox = new MenuBox(menuItemsArr);

        return buildMenu(new MenuTitle(MENU_TITLE), menuBox, null);
    }
}
