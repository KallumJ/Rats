package util;

import javafx.scene.text.Font;


/**
 * A utility class for manipulating Strings
 *
 * @author Kallum Jones (2005855)
 * @date 2022/02/09
 *
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

    /**
     * A method to decode a level id back to the user specified name
     * @param levelId the level id to decode
     * @return the level id, as the name it represents
     */
    public static String levelIdToName(String levelId) {
        try {
            // If the id is less than 100, return it as is, else, decode it
            if (Integer.parseInt(levelId) < 100) {
                return levelId;
            } else {
                return numToName(levelId);
            }
        } catch (NumberFormatException ex) {
            // Exception thrown when id overflows an int,
            // means the id is custom and needs to be decoded
            return numToName(levelId);
        }
    }

    /**
     * A method to turn a number back into the string it represents
     * @param numStr the number to turn back into a string
     * @return the decoded string
     */
    private static String numToName(String numStr) {
        StringBuilder result = new StringBuilder();
        int num = 0;
        for (int i = 0; i < numStr.length(); i++) {
            // Convert number to ascii decimal value
            num = num * 10 + (numStr.charAt(i) - '0');

            // If the ascii value is within the allowed character range
            if (num >= 'A' && num <= 'z') {

                // Convert it to a character
                char ch = (char) num;
                result.append(ch);

                // Reset num
                num = 0;
            }
        }
        return result.toString();
    }

    /**
     * A method to convert the provided level name to an id
     * @param levelName the level name
     * @return the generated id
     */
    public static String levelNameToId(String levelName) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < levelName.length(); i++) {
            int convert = levelName.charAt(i);
            result.append(convert);
        }
        return result.toString();
    }
}
