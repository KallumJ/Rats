package display.menus;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * A class to model a menu in the GUI. This class is extended by all other menus in the system
 *
 * @author Samhitha Pinisetti 2035196
 */
public abstract class GameMenu {
    private static final String NO_BACKGROUND = "Could not load the menu background image file";

    public static Stage stage;
    private final BorderPane root;

    /**
     * Constructs a GameMenu
     */
    public GameMenu() {
        root = new BorderPane();
        root.setPrefSize(860, 600);
    }

    /**
     * A method to build a menu, with a MenuBox of MenuItems to display in the menu
     * @param menuBox the list of menu items to display in the menu
     * @param backHandler The EventHandler for the back button on the page, or null if there is no back button required*
     * @return The Node containing the menu
     */
    public BorderPane buildMenu(MenuTitle menuTitle, MenuBox menuBox, EventHandler<Event> backHandler) {
        // First build a blank menu
        buildBlank(menuTitle, backHandler);

        // Align and add the menu box
        menuBox.setTranslateX(100);
        menuBox.setTranslateY(300);

        getCenter().getChildren().addAll(menuBox);

        return root;
    }

    /**
     * A method to build a menu, with nothing in it, for adding custom controls
     * @param menuTitle The title of the menu
     * @param backHandler The EventHandler for the back button on the page, or null if there is no back button required
     * @return The node containing the blank menu
     */
    public BorderPane buildBlank(MenuTitle menuTitle, EventHandler<Event> backHandler) {
        Pane pane = new Pane();

        // Set a size for the window
        pane.setPrefSize(860, 600);

        // Get and set the background
        try (InputStream is =
                     Files.newInputStream(Paths.get("resources/rats bg.jpeg"))
        ) {
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(860);
            img.setFitHeight(600);
            pane.getChildren().add(img);
        } catch (IOException e) {
            throw new RuntimeException(NO_BACKGROUND);
        }

        // Align and add a title
        menuTitle.setTranslateX(75);
        menuTitle.setTranslateY(200);

        pane.getChildren().add(menuTitle);

        // If an EventHandler for a back button is provided, add one
        if (backHandler != null) {
            Button backButton = new Button("Back");
            backButton.setOnMousePressed(backHandler);
            backButton.setTranslateX(10);
            backButton.setTranslateY(10);

            pane.getChildren().add(backButton);
        }

        // Add to the root
        root.setCenter(pane);
        return root;
    }

    /**
     * An abstract method for subclasses to override and create their menu layouts
     * @return The node of the created menu layout
     */
    public abstract Parent buildMenu();

    /**
     * Returns the center of the root BorderPane for subclasses to add controls
     * @return The center of the BorderPane
     */
    protected Pane getCenter() {
        return (Pane) root.getCenter();
    }
}

/**
 * A class to model the title of a menu
 */
class MenuTitle extends StackPane {

    /**
     * Constructs a MenuTitle
     * @param name the words in the title
     */
    public MenuTitle(String name) {
        Rectangle bg = new Rectangle(250, 60);
        bg.setStroke(Color.WHITE);
        bg.setStrokeWidth(2);
        bg.setFill(null);

        Text text = new Text(name);
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 50));

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg, text);
    }
}
