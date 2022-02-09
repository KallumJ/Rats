package util;

public class TextUtils {
    public static String fancyFormat(String input) {
        String output = input.replaceAll("_", " ");

        return output;
    }
}
