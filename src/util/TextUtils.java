package util;

import javafx.scene.text.Font;


/**
 * A utility class for manipulating Strings
 *
 * @author Kallum Jones (2005855) (09/02/22)
 */
public class TextUtils {
    /**
     * A method to remove all underscores and replace them with spaces
     * @param input the String to remove underscores from
     * @return the amended string
     */
    public static String removeUnderscores(String input) {
        return input.replaceAll("_", " ");
    }

    /**
     * A method to load our chosen font from file
     * @param size the size of the font to load
     * @return the loaded font
     */
    public static Font getFont(int size) {
        return Font.loadFont("file:resources/font.ttf", size);
    }
}
