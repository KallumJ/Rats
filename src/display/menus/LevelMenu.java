package display.menus;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import level.LevelUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

/**
 * A class to model the level selection menu
 *
 * @author Kallum Jones 2005855
 */
public class LevelMenu extends GameMenu {
    public static final String MENU_TITLE = " L E V E L S ";

    /**
     * A method to build a menu containing all the levels in the level directory as selectable options
     * @return the Node containing the menu items
     */
    @Override
    public Parent buildMenu() {
        ArrayList<MenuItem> menuItems = new ArrayList<>();

        for (File levelFile : LevelUtils.getFilesInLevelDirectory()) {
            int levelId = LevelUtils.getFilesLevelId(levelFile);
            LevelMenuItem levelMenuItem = new LevelMenuItem(String.valueOf(levelId));
            menuItems.add(levelMenuItem);
        }

        MenuItem[] menuItemsArr = menuItems.toArray(new MenuItem[0]);
        MenuBox menuBox = new MenuBox(menuItemsArr);

        EventHandler<Event> backHandler = event -> GameMenu.stage.setScene(new Scene(new MainMenu().buildMenu()));

        return buildMenu(new MenuTitle(MENU_TITLE), menuBox, Optional.of(backHandler));
    }

}
