package display.menus.editor;

import display.CustomBoard;
import display.menus.CustomLevelsMenu;
import display.menus.GameMenu;
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
import level.LevelData;
import level.custom.CustomLevelDataFactory;
import objects.GameObject;
import util.TextUtils;
import javafx.scene.control.Button;


public class SizeSelectionMenu extends GameMenu {
    private static final String MENU_TITLE = " C R E A T E ";
    private static final String DIFFICULTY_BUTTON_TEXT = "Select Difficulty";
    private static final String START_BUTTON_TEXT = "Start Editing";
    private static final String HEIGHT_LABEL = " S E L E C T  H E I G H T: ";
    private static final String WIDTH_LABEL = " S E L E C T  W I D T H: ";
    private static final String OR_LABEL = " Or ";
    private static final int HEIGHT_LIMIT = 15;
    private static final int WIDTH_LIMIT = 15;
    private static final int MIN_SIZE = 10;
    private static final String INVALID_SIZE =
            "Please choose a size with a height no greater than " + HEIGHT_LIMIT +
                    "and a width no greater than " + WIDTH_LIMIT;
    private static final int DEFAULT_PADDING = 10;
    private static final Font TITLE_FONT = TextUtils.getFont(20);

    @Override
    public Parent buildMenu() {
        // Add a back button event handler
		EventHandler<Event> backHandler = event -> GameMenu.getStage()
                .setScene(new Scene(new CustomLevelsMenu().buildMenu()));

        // Get a blank menu
        BorderPane menu = buildBlank(new MenuTitle(MENU_TITLE), backHandler);

        // Create a controls container containing some spinners to select a size values
        VBox sizeControlsContainer = new VBox();

        //this is the label for heightSpinner
        Label heightLabel = new Label(HEIGHT_LABEL);
        heightLabel.setTextFill(Color.WHITE);
        heightLabel.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 16;");
        
        heightLabel.setTranslateX(120);
        heightLabel.setTranslateY(280);

        //this is the height spinner
        Spinner<Integer> heightSpinner = new Spinner<>(MIN_SIZE, HEIGHT_LIMIT, MIN_SIZE);
        heightSpinner.setStyle("-fx-background-color: black; -fx-border-color: darkgrey; -fx-border-width: 2px;");

        heightSpinner.setTranslateX(130);
        heightSpinner.setTranslateY(290);

        //this is the label for widthSpinner
        Label widthLabel = new Label(WIDTH_LABEL);
        widthLabel.setTextFill(Color.WHITE);
        widthLabel.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 16;");

        widthLabel.setTranslateX(120);
        widthLabel.setTranslateY(300);

        //this is the width spinner
        Spinner<Integer> widthSpinner = new Spinner<>(MIN_SIZE, WIDTH_LIMIT, MIN_SIZE);
        widthSpinner.setStyle("-fx-background-color: black; -fx-border-color: darkgrey; -fx-border-width: 2px;");

        widthSpinner.setTranslateX(130);
        widthSpinner.setTranslateY(310);

        //this is the 'START EDITING' button
        Button startButton = new Button(START_BUTTON_TEXT);
        startButton.setFont(TITLE_FONT);
        startButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-border-color: darkgrey; -fx-border-width: 1px;");
        startButton.setPrefWidth(170);

        startButton.setTranslateX(30);
        startButton.setTranslateY(330);

        //this is the label for 'OR'
        Label orLabel = new Label(OR_LABEL);
        orLabel.setTextFill(Color.WHITE);
        orLabel.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 16;");

        orLabel.setTranslateX(205);
        orLabel.setTranslateY(260);

        //this is the 'SELECT DIFFICULTY' button 
        Button difficultyButton = new Button(DIFFICULTY_BUTTON_TEXT);
        difficultyButton.setFont(TITLE_FONT);
        difficultyButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-border-color: darkgrey; -fx-border-width: 1px;");
        difficultyButton.setPrefWidth(170);

        difficultyButton.setTranslateX(240);
        difficultyButton.setTranslateY(290);

        // When the start button is pressed, they are taken to a page to start creating their level
        startButton.setOnMousePressed(event -> {
            int height = heightSpinner.getValue();
            int width = widthSpinner.getValue();

            if (height > HEIGHT_LIMIT || width > WIDTH_LIMIT) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(INVALID_SIZE);
                alert.showAndWait();
            } else {
                LevelData customLevel = CustomLevelDataFactory.getBlankLevelData(height, width);

                CustomBoard board = new CustomBoard(customLevel);
                Scene scene = new Scene(board.buildGUI());
                GameObject.setBoard(board);
                GameMenu.getStage().setScene(scene);
            }
        });

        // When the 'SELECT DIFFICULTY' button is presses, they are taken to a new Border Pane which displays three more buttons
        difficultyButton.setOnMousePressed(event -> {
            Scene scene = new Scene(new DifficultySelectionMenu().buildMenu());
            GameMenu.getStage().setScene(scene);
        });

        // Add and align the controls
        sizeControlsContainer.getChildren().addAll(heightLabel, heightSpinner, widthLabel, widthSpinner, startButton, difficultyButton, orLabel);
        widthLabel.setPadding(new Insets(DEFAULT_PADDING, 0, 0, 0));
        getCenter().getChildren().add(sizeControlsContainer);

        // Return the constructed menu
        return menu;
    }
}
