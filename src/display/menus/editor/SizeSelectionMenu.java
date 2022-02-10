package display.menus.editor;

import display.menus.GameMenu;
import display.menus.MainMenu;
import display.menus.MenuTitle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import players.PlayerProfileManager;
import util.TextUtils;

public class SizeSelectionMenu extends GameMenu {
    private static final String MENU_TITLE = " S I Z E ";
    private static final String START_BUTTON_TEXT = "Start Editing";
    private static final String HEIGHT_LABEL = " S E L E C T  H E I G H T: ";
    private static final String WIDTH_LABEL = " S E L E C T  W I D T H: ";
    private static final int HEIGHT_LIMIT = 20;
    private static final int WIDTH_LIMIT = 20;
    private static final int MIN_SIZE = 5;
    private static final String INVALID_SIZE =
            "Please choose a size with a height no greater than " + HEIGHT_LIMIT +
                    "and a width no greater than " + WIDTH_LIMIT;
    private static final int DEFAULT_PADDING = 10;
    private static final Font TITLE_FONT = TextUtils.getFont(20);

    @Override
    public Parent buildMenu() {
        // Add a back button event handler
		EventHandler<Event> backHandler = event -> GameMenu.getStage()
                .setScene(new Scene(new MainMenu().buildMenu()));

        // Get a blank menu
        BorderPane menu = buildBlank(new MenuTitle(MENU_TITLE), backHandler);

        // Create a controls container containing some spinners to select a size values
        VBox sizeControlsContainer = new VBox();

        //this is the label for heightSpinner
        Label heightLabel = new Label(HEIGHT_LABEL);
        heightLabel.setTextFill(Color.WHITE);
        heightLabel.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 16;");
        
        heightLabel.setTranslateX(120);
        heightLabel.setTranslateY(270);

        //this is the height spinner 
        Spinner<Integer> heightSpinner = new Spinner<>(MIN_SIZE, HEIGHT_LIMIT, MIN_SIZE);
        heightSpinner.setStyle("-fx-background-color: black; -fx-border-color: darkgrey; -fx-border-width: 2px;");

        heightSpinner.setTranslateX(130);
        heightSpinner.setTranslateY(280);

        //this is the label for widthSpinner
        Label widthLabel = new Label(WIDTH_LABEL);
        widthLabel.setTextFill(Color.WHITE);
        widthLabel.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 16;");

        widthLabel.setTranslateX(120);
        widthLabel.setTranslateY(285);

        //this is the width spinner
        Spinner<Integer> widthSpinner = new Spinner<>(MIN_SIZE, WIDTH_LIMIT, MIN_SIZE);
        widthSpinner.setStyle("-fx-background-color: black; -fx-border-color: darkgrey; -fx-border-width: 2px;");

        widthSpinner.setTranslateX(130);
        widthSpinner.setTranslateY(300);

        //this is the 'START EDITING' button
        Button startButton = new Button(START_BUTTON_TEXT);
        startButton.setFont(TITLE_FONT);
        startButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-border-color: darkgrey; -fx-border-width: 1px;");
        startButton.setPrefWidth(170);

        startButton.setTranslateX(120);
        startButton.setTranslateY(350);

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
        widthLabel.setPadding(new Insets(DEFAULT_PADDING, 0, 0, 0));
        getCenter().getChildren().add(sizeControlsContainer);

        // Return the constructed menu
        return menu;
    }
}
