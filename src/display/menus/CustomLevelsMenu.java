package display.menus;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * A class to show the menu section for the custom levels.
 *
 * @author Kallum Jones 2005855
 * @date 2022/02/22
 *
 */
public class CustomLevelsMenu extends GameMenu {
    private static final String MENU_TITLE = " C U S T O M ";

    /**
     * A method to construct the designed CustomLevelsMenu.
     *
     * @return the designed menu.
     */
    @Override
    public Parent buildMenu() {
        MenuBox menuBox = new MenuBox(
                new StartCustomLevelMenuItem(), new EditCustomLevelMenuItem(),
                new LoadCustomLevelMenuItem(), new DeleteCustomLevelMenuItem(),
                new ResumeCustomLevelMenuItem()
        );

        // Add a back button event handler
        EventHandler<Event> backHandler = event -> GameMenu.getStage()
                .setScene(new Scene(new MainMenu().buildMenu()));

        // Create the menu
        return buildMenu(new MenuTitle(MENU_TITLE), menuBox,
                backHandler);
    }
}
