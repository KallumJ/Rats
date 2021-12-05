package display.menus;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import motd.MssgOfTheDay;
import players.PlayerProfileManager;
import players.scores.Player;

/**
 * A class to model the MainMenu of the application.
 *
 * @author Kallum Jones 2005855
 */
public class MainMenu extends GameMenu {

	private static final String MENU_TITLE = " R A T S ";
	private static final String NAME_LABEL = "Name: %s";
	private static final String DELETE_TEXT = "Delete";
	private static final String LOGOUT_TEXT = "Log out";
	private static final int DELETE_OFFSET = 20;
	private static final int LOGOUT_OFFSET = 10;

	/**
	 * A method to create a main menu with all the main menu items we need on
	 * it.
	 *
	 * @return The node containing all the main menu items
	 */
	public Parent buildMenu() {
		// Create a menu box with all the menu items required
		MenuBox menuBox = new MenuBox(new LeaderboardMenuItem(),
				new PlayMenuItem(), new ExitMenuItem(), new HelpMenuItem(),
				new ContinueMenuItem(), new LoadMenuItem());

		// Create the menu
		BorderPane mainMenu = buildMenu(new MenuTitle(MENU_TITLE), menuBox,
				null);

		// Add Motd and user name labels
		Label motdLabel = new Label(MssgOfTheDay.getMotD());
		motdLabel.setFont(Font.font(DEFAULT_FONT, FontWeight.SEMI_BOLD, 18));
		motdLabel.setTextFill(Color.WHITE);

		mainMenu.setBottom(motdLabel);

		// Create a container for name controls
		HBox nameContainer = new HBox();

		// Create name label
		Player player = PlayerProfileManager.getCurrentlyLoggedInPlayer();

		String playerName = player.getPlayerName();
		Label nameLabel = new Label(String.format(NAME_LABEL, playerName));
		nameLabel.setFont(Font.font(DEFAULT_FONT, FontWeight.SEMI_BOLD, 18));
		nameLabel.setTextFill(Color.WHITE);

		// Create buttons to delete and logout of the current player profile
		Button deleteButton = new Button(DELETE_TEXT);
		Button logoutButton = new Button(LOGOUT_TEXT);
		logoutButton.setTranslateX(LOGOUT_OFFSET);
		deleteButton.setTranslateX(DELETE_OFFSET);

		logoutButton.setOnMousePressed(event -> {
			Scene scene = new Scene(new LoginMenu().buildMenu());
			GameMenu.getStage().setScene(scene);
		});

		deleteButton.setOnMousePressed(event -> {
			PlayerProfileManager.deleteCurrentPlayer();
			Scene scene = new Scene(new LoginMenu().buildMenu());
			GameMenu.getStage().setScene(scene);
		});

		// Add the controls to the menu
		nameContainer.getChildren()
				.addAll(nameLabel, logoutButton, deleteButton);
		getCenter().getChildren().add(nameContainer);

		// Return the constructed menu
		return mainMenu;
	}
}
