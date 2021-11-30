package level;

import io.XMLFileReader;
import org.w3c.dom.Element;
import tile.Direction;
import tile.TileType;

import java.io.File;
import java.util.ArrayList;

/**
 * A utility file to help with general functions around finding levels from file
 *
 * @author Kallum Jones 2005855
 */
public class LevelUtils {

    private static final String LEVELS_PATH = "levels";
    private static final String INVALID_TILE_TYPE = "%s is an invalid tile type";
    private static final String INVALID_DIRECTION_ERROR = "%s is an unknown direction";

    /**
     * A method to return an array of File objects of all the files in the level directory
     * @return the array of files in the inventory
     */
    public static File[] getFilesInLevelDirectory() {
        File levelsDirectory = new File(LEVELS_PATH);
        File[] filesInLevelDirectory = levelsDirectory.listFiles();

        if (filesInLevelDirectory == null) {
            throw new RuntimeException("The provided levels directory is empty");
        }

        ArrayList<File> nonDirectoryFiles = new ArrayList<>();
        for (File file : filesInLevelDirectory) {
            if (!file.isDirectory()) {
                nonDirectoryFiles.add(file);
            }
        }

        return nonDirectoryFiles.toArray(new File[0]);
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


    /**
     * A method to convert tile types to a string
     * @param tileType the tile type
     * @return the converted string
     */
    public static String getTileTypeString(TileType tileType) {
        switch (tileType) {
            case GRASS:
                return "g";
            case TUNNEL:
                return "t";
            case PATH:
                return "p";
            default:
                throw new IllegalArgumentException(String.format(INVALID_TILE_TYPE, tileType));
        }
    }

    /**
     * A method to get the string from a direction
     * @param directionOfMovement the direction
     * @return the string for this direction
     */
    public static String getStringFromDirection(Direction directionOfMovement) {
        switch (directionOfMovement) {
            case UP:
                return "up";
            case DOWN:
                return "down";
            case LEFT:
                return "left";
            case RIGHT:
                return "right";
            default:
                throw new IllegalArgumentException(String.format(INVALID_DIRECTION_ERROR, directionOfMovement));
        }
    }
}
