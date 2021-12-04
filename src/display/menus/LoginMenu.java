package display.menus;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import players.PlayerProfileManager;

/**
 * A class to model the login menu
 *
 * @author Kallum Jones 2005855
 */
public class LoginMenu extends GameMenu {

    private static final String MENU_TITLE = " L O G I N ";
    private static final String NAME_INPUT_PLACEHOLDER = "Enter your name...";
    private static final String LOGIN_BUTTON_TEXT = "Login";

    /**
     * A method to build the login menu
     * @return The node containing the login menu
     */
    @Override
    public Parent buildMenu() {
        // Get a blank menu
        BorderPane menu = buildBlank(new MenuTitle(MENU_TITLE), null);

        // Create a controls container containing a button and text box to input the user's name
        HBox nameControlsContainer = new HBox();
        TextField inputBox = new TextField();

        inputBox.setText(NAME_INPUT_PLACEHOLDER);

        Button loginButton = new Button(LOGIN_BUTTON_TEXT);

        // When the login button is pressed, save their name, and change to the new screen
        loginButton.setOnMousePressed(event -> {
            PlayerProfileManager.loginPlayer(inputBox.getText());

            GameMenu.getStage().setScene(new Scene(new MainMenu().buildMenu()));
        });


        // Add and align the controls
        nameControlsContainer.getChildren().addAll(inputBox, loginButton);

        nameControlsContainer.setTranslateX(100);
        nameControlsContainer.setTranslateY(300);

        getCenter().getChildren().add(nameControlsContainer);

        // Return the constructed menu
        return menu;
    }
}
