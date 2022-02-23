package display.menus;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import players.PlayerProfileManager;
import util.TextUtils;

/**
 * A class to model the login menu.
 *
 * @author Kallum Jones 2005855
 * @date 2022.02.15
 */
public class LoginMenu extends GameMenu {

    private static final String MENU_TITLE = " L O G I N ";
    private static final String NAME_INPUT_PLACEHOLDER = "Enter your name...";
    private static final String LOGIN_BUTTON_TEXT = "E N T E R";
    private static final int NAME_CHAR_LIMIT = 20;
    private static final int INPUT_BOX_X = 110;
    private static final int INPUT_BOX_Y = 300;
    private static final int LOGIN_BUTTON_X = -25;
    private static final int LOGIN_BUTTON_Y = 335;
    private static final String NAME_TOO_LONG =
            "Please choose a name with " + NAME_CHAR_LIMIT + " characters or " + "less";
    private static final Font TITLE_FONT = TextUtils.getFont(20);

    /**
     * A method to build the login menu.
     *
     * @return the node containing the login menu.
     */
    @Override
    public Parent buildMenu() {
        // Get a blank menu
        BorderPane menu = buildBlank(new MenuTitle(MENU_TITLE), null);

        // Create a controls container containing a button and text box to
        // input the user's name
        HBox nameControlsContainer = new HBox();
        TextField inputBox = new TextField();
        inputBox.setText(NAME_INPUT_PLACEHOLDER);
        inputBox.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-border-color: darkgrey; -fx-border-width: 1px;");
        inputBox.setPrefWidth(170);

        inputBox.setTranslateX(INPUT_BOX_X);
        inputBox.setTranslateY(INPUT_BOX_Y);

        Button loginButton = new Button(LOGIN_BUTTON_TEXT);
        loginButton.setFont(TITLE_FONT);
        loginButton.setStyle("-fx-background-color: black; -fx-text-fill: white;");
        loginButton.setPrefWidth(100);

        loginButton.setOnMouseEntered(event -> {
            loginButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-border-color: darkgrey; -fx-border-width: 1px;");
        });

        loginButton.setTranslateX(LOGIN_BUTTON_X);
        loginButton.setTranslateY(LOGIN_BUTTON_Y);

        // When the login button is pressed, save their name, and change to
        // the new screen
        loginButton.setOnMousePressed(event -> {
            String input = inputBox.getText();

            if (input.length() > NAME_CHAR_LIMIT) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(NAME_TOO_LONG);
                alert.showAndWait();
            } else {
                PlayerProfileManager.loginPlayer(input);
                GameMenu.getStage()
                        .setScene(new Scene(new MainMenu().buildMenu()));
            }
        });


        // Add and align the controls

        nameControlsContainer.getChildren().addAll(inputBox, loginButton);
        getCenter().getChildren().add(nameControlsContainer);

        // Return the constructed menu
        return menu;
    }
}
