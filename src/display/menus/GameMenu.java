package display.menus;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A class to model a menu in the GUI. This class is extended by all other menus
 * in the system.
 *
 * @author Samhitha Pinisetti 2035196
 * @date 2022.02.09
 *
 */
public abstract class GameMenu {
    public static final String BUTTON_STYLE =
            "-fx-background-color: black; -fx-text-fill: white; "
                    + "-fx-border-color: darkgrey; -fx-border-width: 1px;";
    private static final String NO_BACKGROUND = "Could not load the menu "
            + "background image file";
    private static final Path RAT_BG_PATH = Paths.get("resources/ratsBG.jpeg");

    // all in px:
    private static final int DEFAULT_WINDOW_WIDTH = 860;
    private static final int DEFAULT_WINDOW_HEIGHT = 600;
    private static final int TITLE_OFFSET_X = 75;
    private static final int TITLE_OFFSET_Y = 200;
    private static final int MENU_OFFSET_X = 100;
    private static final int MENU_OFFSET_Y = 300;
    private static final int BUTTON_OFFSET_X = 70;
    private static final int BUTTON_OFFSET_Y = 30;

    private static Stage stage;
    private final BorderPane root;

    /**
     * Constructs a GameMenu.
     */
    public GameMenu() {
        root = new BorderPane();
        root.setPrefSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
    }

    /**
     * Gets the current stage on display.
     *
     * @return the current stage on display.
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Sets the stage to display.
     *
     * @param stage the stage to display.
     */
    public static void setStage(Stage stage) {
        GameMenu.stage = stage;
    }

    /**
     * Builds a menu, with a MenuBox of MenuItems to display in the menu.
     *
     * @param menuBox     the list of menu items to display in the menu.
     * @param backHandler the EventHandler for the back button on the page, or
     *                    null if there is no back button required.
     * @return the node containing the menu.
     */
    public BorderPane buildMenu(MenuTitle menuTitle, MenuBox menuBox,
                                EventHandler<Event> backHandler) {
        // First build a blank menu
        buildBlank(menuTitle, backHandler);

        // Align and add the menu box
        menuBox.setTranslateX(MENU_OFFSET_X);
        menuBox.setTranslateY(MENU_OFFSET_Y);

        getCenter().getChildren().addAll(menuBox);

        return root;
    }

    /**
     * Returns a menu with nothing in it.
     *
     * @param menuTitle   the title of the menu.
     * @param backHandler the EventHandler for the back button on the page, or
     *                    null if there is no back button required.
     * @return the node containing the blank menu.
     */
    public BorderPane buildBlank(MenuTitle menuTitle,
                                 EventHandler<Event> backHandler) {
        Pane pane = new Pane();

        // Set a size for the window
        pane.setPrefSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);

        // Get and set the background
        try (InputStream imgStream = Files.newInputStream(RAT_BG_PATH)) {
            ImageView img = new ImageView(new Image(imgStream));
            img.setFitWidth(DEFAULT_WINDOW_WIDTH);
            img.setFitHeight(DEFAULT_WINDOW_HEIGHT);
            pane.getChildren().add(img);
        } catch (IOException e) {
            throw new RuntimeException(NO_BACKGROUND);
        }

        // Align and add a title
        if (menuTitle != null) {
            menuTitle.setTranslateX(TITLE_OFFSET_X);
            menuTitle.setTranslateY(TITLE_OFFSET_Y);
            pane.getChildren().add(menuTitle);
        }

        // If an EventHandler for a back button is provided, add one
        if (backHandler != null) {
            Button backButton = new Button("B A C K"); //must change this
            backButton.setStyle(BUTTON_STYLE);
            backButton.setOnMousePressed(backHandler);
            backButton.setTranslateX(pane.getPrefWidth() - BUTTON_OFFSET_X);
            backButton.setTranslateY(pane.getPrefHeight() - BUTTON_OFFSET_Y);

            pane.getChildren().add(backButton);
        }

        // Add to the root
        root.setCenter(pane);
        return root;
    }

    /**
     * An abstract method for subclasses to override and create their menu
     * layouts.
     *
     * @return the node of the created menu layout.
     */
    public abstract Parent buildMenu();

    /**
     * Returns the center of the root BorderPane for subclasses to add
     * controls.
     *
     * @return the center of the BorderPane.
     */
    protected Pane getCenter() {
        return (Pane) root.getCenter();
    }
}

