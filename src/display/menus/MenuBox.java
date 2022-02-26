package display.menus;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * A class to model the box containing all the menu items.
 *
 * @author Samhitha Pinisetti 2035196
 * @date 2022.02.21
 */
public class MenuBox extends VBox {

    private static final int LINE_SEPARATOR = 200;

    /**
     * Constructor for {@code MenuBox}.
     *
     * @param items the items to add within the {@code MenuBox}.
     */
    public MenuBox(MenuItem... items) {
        getChildren().add(createSeparator());

        for (MenuItem item : items) {
            getChildren().addAll(item, createSeparator());
        }
    }

    /**
     * Creates the line separator for placing between menu items.
     *
     * @return the line.
     */
    private Line createSeparator() {
        Line sep = new Line();
        sep.setEndX(LINE_SEPARATOR);
        sep.setStroke(Color.DARKGREY);
        return sep;
    }
}
