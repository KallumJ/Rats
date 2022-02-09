package util;

import javafx.scene.text.Font;


public class TextUtils {
    public static String fancyFormat(String input) {
        return input.replaceAll("_", " ");
    }

    public static Font getFont(int size) {
        return Font.loadFont("file:resources/font.ttf", size);
    }
}
