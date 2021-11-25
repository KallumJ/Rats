package display;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A class to model a menu in the GUI
 *
 * @author Samhitha Pinisetti 2035196
 */
public abstract class GameMenu {
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
     * A method to build the GUI
     * @param menuBox the list of menu items to display in the menu
     * @return The Node containing the menu
     */
    public BorderPane build(MenuTitle menuTitle, MenuBox menuBox) {
        Pane pane = new Pane();

        pane.setPrefSize(860, 600);

        try (InputStream is = Files.newInputStream(Paths.get("resources/rats bg.jpeg"))) {
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(860);
            img.setFitHeight(600);
            pane.getChildren().add(img);
        } catch (IOException e) {
            System.out.println("Couldn't load image");
        }

        menuTitle.setTranslateX(75);
        menuTitle.setTranslateY(200);

        menuBox.setTranslateX(100);
        menuBox.setTranslateY(300);

        pane.getChildren().addAll(menuTitle, menuBox);

        root.setCenter(pane);
        return root;
    }

    /**
     * An abstract method for subclasses to override and create their menu layouts
     * @return The node of the created menu layout
     */
    public abstract Parent buildMenu();

    /**
     * A method to add a node to the bottom of the menu
     * @param node the node to add to the bottom
     */
    public void setBottom(Node node) {
        root.setBottom(node);
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
