package display.menus.editor;

import display.menus.GameMenu;
import display.menus.MainMenu;
import display.menus.MenuTitle;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import players.PlayerProfileManager;

public class SizeSelectionMenu extends GameMenu {
    private static final String MENU_TITLE = " S I Z E ";
    private static final String START_BUTTON_TEXT = "Start Editing";
    private static final String HEIGHT_LABEL = "Select a height";
    private static final String WIDTH_LABEL = "Select a width";
    private static final int HEIGHT_LIMIT = 30;
    private static final int WIDTH_LIMIT = 30;
    private static final int MIN_SIZE = 5;
    private static final String INVALID_SIZE =
            "Please choose a size with a height no greater than " + HEIGHT_LIMIT +
                    "and a width no greater than " + WIDTH_LIMIT;
    private static final int DEFAULT_PADDING = 10;

    @Override
    public Parent buildMenu() {
        // Get a blank menu
        BorderPane menu = buildBlank(new MenuTitle(MENU_TITLE), null);

        // Create a controls container containing some spinners to select a size values
        VBox sizeControlsContainer = new VBox();
        Spinner<Integer> heightSpinner = new Spinner<>(MIN_SIZE, HEIGHT_LIMIT, MIN_SIZE);
        Spinner<Integer> widthSpinner = new Spinner<>(MIN_SIZE, WIDTH_LIMIT, MIN_SIZE);

        Label heightLabel = new Label(HEIGHT_LABEL);
        heightLabel.setTextFill(Color.WHITE);
        Label widthLabel = new Label(WIDTH_LABEL);
        widthLabel.setTextFill(Color.WHITE);

        Button startButton = new Button(START_BUTTON_TEXT);

        // When the start button is pressed, they are taken to a page to start creating their level
        startButton.setOnMousePressed(event -> {
            int height = heightSpinner.getValue();
            int width = widthSpinner.getValue();

            if (height > HEIGHT_LIMIT || width > WIDTH_LIMIT) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(INVALID_SIZE);
                alert.showAndWait();
            } else {
                System.out.println("SUCCESS");
            }
        });


        // Add and align the controls
        sizeControlsContainer.getChildren().addAll(heightLabel, heightSpinner, widthLabel, widthSpinner, startButton);

        sizeControlsContainer.setTranslateX(100);
        sizeControlsContainer.setTranslateY(300);

        widthLabel.setPadding(new Insets(DEFAULT_PADDING, 0, 0, 0));

        getCenter().getChildren().add(sizeControlsContainer);

        // Return the constructed menu
        return menu;
    }
}
