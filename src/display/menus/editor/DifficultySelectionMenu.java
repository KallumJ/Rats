package display.menus.editor;

import display.menus.GameMenu;
import display.menus.MenuBox;
import display.menus.MenuTitle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * A class for the difficulty selection in the games menu
 *
 * @date 2022/02/22
 */

public class DifficultySelectionMenu extends GameMenu {
    private static final String MENU_TITLE = " D I F F I C U L T Y ";

    @Override
    public Parent buildMenu() {
        // Add a back button event handler
        EventHandler<Event> backHandler = event -> GameMenu.getStage()
                .setScene(new Scene(new SizeSelectionMenu().buildMenu()));

        MenuBox menuBox = new MenuBox(
                new EasyMenuItem(), new MediumMenuItem(),
                new HardMenuItem()
        );

        // Create the menu
        return buildMenu(new MenuTitle(MENU_TITLE), menuBox,
                backHandler);
    }

}
