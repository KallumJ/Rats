package level;

import io.XMLFileReader;
import org.w3c.dom.Element;

import java.io.File;

/**
 * A utility file to help with general functions around finding levels from file
 *
 * @author Kallum Jones 2005855
 */
public class LevelUtils {

    private static final String LEVELS_PATH = "levels";

    /**
     * A method to return an array of File objects of all the files in the level directory
     * @return
     */
    public static File[] getFilesInLevelDirectory() {
        File levelsDirectory = new File(LEVELS_PATH);
        File[] filesInLevelDirectory = levelsDirectory.listFiles();

        if (filesInLevelDirectory == null) {
            throw new RuntimeException("The provided levels directory is empty");
        }

        return filesInLevelDirectory;
    }


    /**
     * A method to get the LevelFile by the provided id
     * @param id the level id
     */
    public static File getLevelFileById(int id) {

        // Find a file with a matching id to what was passed to the method
        File selectedLevel = null;
        for (File file : getFilesInLevelDirectory()) {
            int fileId = getFilesLevelId(file);

            if (fileId == id) {
                selectedLevel = file;
            }
        }

        if (selectedLevel == null) {
            throw new IllegalArgumentException("No level was found matching the provided level id");
        }

        return selectedLevel;
    }


    /**
     * A utility method to get the level id for a provided file
     *
     * @param file the file to get the id from
     * @return the found id
     */
    public static int getFilesLevelId(File file) {
        XMLFileReader fileReader = new XMLFileReader(file);
        Element idElement = fileReader.drilldownToElement("levelProperties", "id");

        return Integer.parseInt(idElement.getTextContent());
    }
}
