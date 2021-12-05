package display.menus;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A class to model a menu in the GUI. This class is extended by all other menus in the system
 *
 * @author Samhitha Pinisetti 2035196
 */
public abstract class GameMenu {
    public static final String DEFAULT_FONT = "Tw Cen MT Condensed";
    private static final String NO_BACKGROUND = "Could not load the menu background image file";
    private static final Path RAT_BG_PATH = Paths.get("resources/ratsBG.jpeg");

    // all in px:
    private static final int DEFAULT_WINDOW_WIDTH = 860;
    private static final int DEFAULT_WINDOW_HEIGHT = 600;
    private static final int TITLE_OFFSET_X = 75;
    private static final int TITLE_OFFSET_Y = 200;
    private static final int MENU_OFFSET_X = 100;
    private static final int MENU_OFFSET_Y = 300;
    private static final int ON_CLICK_OFFSET = 10;

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
     * Builds a menu, with a MenuBox of MenuItems to display in the menu.
     *
     * @param menuBox     the list of menu items to display in the menu
     * @param backHandler the EventHandler for the back button on the page, or
     *                    null if there is no back button required
     * @return the node containing the menu
     */
    public BorderPane buildMenu(MenuTitle menuTitle, MenuBox menuBox, EventHandler<Event> backHandler) {
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
     * @param menuTitle   the title of the menu
     * @param backHandler the EventHandler for the back button on the page,
     *                    or null if there is no back button required
     * @return the node containing the blank menu
     */
    public BorderPane buildBlank(MenuTitle menuTitle, EventHandler<Event> backHandler) {
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
            Button backButton = new Button("Back");
            backButton.setOnMousePressed(backHandler);
            backButton.setTranslateX(pane.getPrefWidth() - 50);
            backButton.setTranslateY(pane.getPrefHeight() - 30);

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
     * @return the node of the created menu layout
     */
    public abstract Parent buildMenu();

    /**
     * Gets the current stage on display
     *
     * @return the current stage on display
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Sets the stage to display
     *
     * @param stage the stage to display
     */
    public static void setStage(Stage stage) {
        GameMenu.stage = stage;
    }

    /**
     * Returns the center of the root BorderPane for subclasses to add controls.
     *
     * @return the center of the BorderPane
     */
    protected Pane getCenter() {
        return (Pane) root.getCenter();
    }
}

/**
 * Models the title of a menu.
 *
 * @author Samhitha Pinisetti 2035196
 */
class MenuTitle extends StackPane {

    private static final int TITLE_WIDTH = 250;
    private static final int TITLE_HEIGHT = 60;
    private static final int BORDER_SIZE = 2;
    private static final int TITLE_FONT_SIZE = 50;
    private static final Font TITLE_FONT = Font.font(GameMenu.DEFAULT_FONT, FontWeight.SEMI_BOLD, TITLE_FONT_SIZE);

    /**
     * Constructor for a MenuTitle.
     *
     * @param name the title text
     */
    public MenuTitle(final String name) {
        Rectangle bg = new Rectangle(TITLE_WIDTH, TITLE_HEIGHT);
        bg.setStroke(Color.WHITE);
        bg.setStrokeWidth(BORDER_SIZE);
        bg.setFill(null);

        Text text = new Text(name);
        text.setFill(Color.WHITE);
        text.setFont(TITLE_FONT);

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg, text);
    }

}
