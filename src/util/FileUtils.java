package util;

import java.io.File;

/**
 * A class to hold utility methods relating to manipulation of Files
 *
 * @author Kallum Jones 2005855
 */
public class FileUtils {

    /**
     * A method to get the file name of the provided file without the file extension
     * @param file The file
     * @return The name of the file, with the extension removed
     */
    public static String getFileNameWithoutExtension(File file) {
        String fileName = file.getName();
        int posOfExtension = fileName.lastIndexOf(".");

        // Return the string, starting from the first character, up to position of the extension
        return fileName.substring(0, posOfExtension);
    }
}
