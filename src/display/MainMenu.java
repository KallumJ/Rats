package display;

import javafx.scene.Parent;

/**
 * A class to model the MainMenu of the application
 */
public class MainMenu extends GameMenu {

    private static final String MENU_TITLE = " R A T S ";

    /**
     * A method to create a main menu with all the main menu items we need on it
     * @return The node containing all the main menu items
     */
    public Parent buildMenu() {
        MenuBox menuBox = new MenuBox(
                new LeaderboardMenuItem(),
                new PlayMenuItem(),
                new ExitMenuItem(),
                new HelpMenuItem(),
                new ContinueMenuItem(),
                new LoadMenuItem());

        return build(new MenuTitle(MENU_TITLE), menuBox);
    }
}
