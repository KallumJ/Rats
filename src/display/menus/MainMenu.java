package display.menus;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import motd.MssgOfTheDay;
import players.PlayerProfileManager;
import players.scores.Player;
import util.TextUtils;

/**
 * A class to model the MainMenu of the application.
 *
 * @author Kallum Jones 2005855
 * @date 2022.02.12
 */
public class MainMenu extends GameMenu {

    private static final String MENU_TITLE = " R A T S ";
    private static final String NAME_LABEL = "Name: %s";
    private static final String DELETE_TEXT = "Delete";
    private static final String LOGOUT_TEXT = "Log out";
    private static final int DELETE_OFFSET = 20;
    private static final int LOGOUT_OFFSET = 10;
    private static final Font TITLE_FONT = TextUtils.getFont(15);
    private static final Font MOTD_LABEL_FONT = TextUtils.getFont(18);
    private static final Font NAME_LABEL_FONT = TextUtils.getFont(20);
    private static final int BUTTON_WIDTH = 150;


    /**
     * A method to create a main menu with all the main menu items we need on
     * it.
     *
     * @return The node containing all the main menu items.
     */
    public Parent buildMenu() {
        // Create a menu box with all the menu items required
        MenuBox menuBox = new MenuBox(new LeaderboardMenuItem(),
                new PlayMenuItem(), new ExitMenuItem(), new HelpMenuItem(),
                new LoadMenuItem(), new CustomLevelsMenuItem());

        // Create the menu
        BorderPane mainMenu = buildMenu(new MenuTitle(MENU_TITLE), menuBox,
                null);

        // Add Motd and user name labels
        // make this scrollable
        Label motdLabel = new Label(MssgOfTheDay.getMotD());
        motdLabel.setFont(MOTD_LABEL_FONT);
        motdLabel.setTextFill(Color.WHITE);

        mainMenu.setBottom(motdLabel);
        applyScrollingEffects(motdLabel);

        // Create a container for name controls
        HBox nameContainer = new HBox();

        // Create name label
        Player player = PlayerProfileManager.getCurrentlyLoggedInPlayer();

        String playerName = player.getPlayerName();
        Label nameLabel = new Label(String.format(NAME_LABEL, playerName));
        nameLabel.setFont(NAME_LABEL_FONT);
        nameLabel.setTextFill(Color.WHITE);

        // Create buttons to delete and logout of the current player profile
        Button deleteButton = new Button(DELETE_TEXT);
        deleteButton.setFont(TITLE_FONT);
        deleteButton.setStyle(GameMenu.BUTTON_STYLE);
        deleteButton.setPrefWidth(BUTTON_WIDTH);

        Button logoutButton = new Button(LOGOUT_TEXT);
        logoutButton.setFont(TITLE_FONT);
        logoutButton.setStyle(GameMenu.BUTTON_STYLE);
        logoutButton.setPrefWidth(BUTTON_WIDTH);

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
     * Apply scrolling effects to the provided label.
     * @param label the label to scroll
     */
    private void applyScrollingEffects(Label label) {
        // Use Timeline, Key Value and Key Frame
        // Scrolling Text Effects for text "ABOUT RATS"
        double sceneWidth = getStage().getWidth();
        double textWidth = label.getLayoutBounds().getWidth();

        KeyValue initKeyValue =
                new KeyValue(label.translateXProperty(),
                        sceneWidth);
        KeyFrame initFrame = new KeyFrame(Duration.ZERO, initKeyValue);
        KeyValue endKeyValue =
                new KeyValue(label.translateXProperty(),
                        -3.5 * textWidth);
        KeyFrame endFrame = new KeyFrame(Duration.seconds(15), endKeyValue);

        Timeline timeline = new Timeline(initFrame, endFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

}
