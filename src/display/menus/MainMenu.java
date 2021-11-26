package display.menus;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import motd.MssgOfTheDay;

import java.util.Optional;

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

        BorderPane mainMenu = buildMenu(new MenuTitle(MENU_TITLE), menuBox, Optional.empty());

        Label motdLabel = new Label(MssgOfTheDay.getMotD());
        motdLabel.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 18));
        motdLabel.setTextFill(Color.WHITE);

        mainMenu.setBottom(motdLabel);

        Label nameLabel = new Label("Name: " + GameMenu.playerName);
        nameLabel.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 18));
        nameLabel.setTextFill(Color.WHITE);

        getCenter().getChildren().add(nameLabel);

        return mainMenu;
    }
}
