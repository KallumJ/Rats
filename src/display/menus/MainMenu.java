package display.menus;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import motd.MssgOfTheDay;
import players.PlayerProfileManager;
import players.scores.Player;
import util.TextUtils;

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
	private static final Font TITLE_FONT = Font.font(GameMenu.DEFAULT_FONT,
		FontWeight.SEMI_BOLD, 15);


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
				new ContinueMenuItem(), new LoadMenuItem(), new LevelEditorMenuItem());

		// Create the menu
		BorderPane mainMenu = buildMenu(new MenuTitle(MENU_TITLE), menuBox,
				null);

		// Add Motd and user name labels
		// make this scrollable 
		Label motdLabel = new Label(MssgOfTheDay.getMotD());
		motdLabel.setFont(Font.font(DEFAULT_FONT, FontWeight.SEMI_BOLD, 18));
		motdLabel.setTextFill(Color.WHITE);

		mainMenu.setBottom(motdLabel);
		applyScrollingEffects(motdLabel);

		// Create a container for name controls
		HBox nameContainer = new HBox();

		// Create name label
		Player player = PlayerProfileManager.getCurrentlyLoggedInPlayer();

		String playerName = player.getPlayerName();
		Label nameLabel = new Label(String.format(NAME_LABEL, playerName));
		nameLabel.setFont(TextUtils.getFont(20));
		nameLabel.setTextFill(Color.WHITE);

		// Create buttons to delete and logout of the current player profile
		Button deleteButton = new Button(DELETE_TEXT);
		deleteButton.setFont(TITLE_FONT);
		deleteButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-border-color: darkgrey; -fx-border-width: 1px;");
		deleteButton.setPrefWidth(150);

		Button logoutButton = new Button(LOGOUT_TEXT);
		logoutButton.setFont(TITLE_FONT);
		logoutButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-border-color: darkgrey; -fx-border-width: 1px;");
		logoutButton.setPrefWidth(150);

		logoutButton.setTranslateX(LOGOUT_OFFSET);
		logoutButton.setTranslateY(1);
		deleteButton.setTranslateX(DELETE_OFFSET);
		deleteButton.setTranslateY(1);

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
	
	/**
	 * Apply scrolling effects to the provided motd label.
	 */
	private void applyScrollingEffects(Label motdLabel) {
		// Use Timeline, Key Value and Key Frame
		// Scrolling Text Effects for text "ABOUT RATS"
		double sceneWidth = getStage().getWidth();
		double textWidth = motdLabel.getLayoutBounds().getWidth();

		KeyValue initKeyValue =
				new KeyValue(motdLabel.translateXProperty(),
						sceneWidth);
		KeyFrame initFrame = new KeyFrame(Duration.ZERO, initKeyValue);
		KeyValue endKeyValue =
				new KeyValue(motdLabel.translateXProperty(),
						-3.5 * textWidth);
		KeyFrame endFrame = new KeyFrame(Duration.seconds(15), endKeyValue);

		Timeline timeline = new Timeline(initFrame, endFrame);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

}
