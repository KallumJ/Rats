package display;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import motd.MssgOfTheDay;

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

        BorderPane mainMenu = build(new MenuTitle(MENU_TITLE), menuBox);

        Label motdLabel = new Label(MssgOfTheDay.getMotD());
        motdLabel.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 18));
        motdLabel.setTextFill(Color.WHITE);

        mainMenu.setBottom(motdLabel);

        return mainMenu;
    }
}
