package display.menus;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.Optional;

public class LoginMenu extends GameMenu {

    private static final String MENU_TITLE = " L O G I N ";


    @Override
    public Parent buildMenu() {
        BorderPane menu = buildBlank(new MenuTitle(MENU_TITLE), Optional.empty());

        HBox nameControlsContainer = new HBox();
        TextField inputBox = new TextField();

        inputBox.setText("Enter your name... ");

        Button loginButton = new Button("Login");
        loginButton.setOnMousePressed(event -> {
            GameMenu.playerName = inputBox.getText();

            GameMenu.stage.setScene(new Scene(new MainMenu().buildMenu()));
        });


        nameControlsContainer.getChildren().addAll(inputBox, loginButton);

        nameControlsContainer.setTranslateX(100);
        nameControlsContainer.setTranslateY(300);

        getCenter().getChildren().add(nameControlsContainer);

        return menu;
    }
}
