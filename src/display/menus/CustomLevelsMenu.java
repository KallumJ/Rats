package display.menus;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class CustomLevelsMenu extends GameMenu {
    private static final String MENU_TITLE = " C U S T O M ";

    @Override
    public Parent buildMenu() {
        MenuBox menuBox = new MenuBox(
               new StartCustomLevelMenuItem(), new EditCustomLevelMenuItem(),
                new LoadCustomLevelMenuItem(), new DeleteCustomLevelMenuItem()
        );

        // Add a back button event handler
        EventHandler<Event> backHandler = event -> GameMenu.getStage()
                .setScene(new Scene(new MainMenu().buildMenu()));

        // Create the menu
        return buildMenu(new MenuTitle(MENU_TITLE), menuBox,
                backHandler);
    }
}
