package level;

import io.XMLFileReader;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * A class to load LevelData objects from disk
 *
 * @author Kallum Jones 2005855
 */
public class LevelDataFactory {
    private static final String LEVELS_PATH = "levels";

    /**
     * A method to construct the LevelData object for a given level id
     * @param id The id of the level that needs constructing
     * @return The complete LevelData object
     */
    public static LevelData constructLevelData(int id) {
        File levelsDirectory = new File(LEVELS_PATH);
        File[] filesInLevelDirectory = levelsDirectory.listFiles();

        if (filesInLevelDirectory == null) {
            throw new RuntimeException("The provided levels directory is empty");
        }

        // Find a file with a matching id to what was passed to the method
        File selectedLevel = null;
        for (File file : filesInLevelDirectory) {
            int fileId = getFilesLevelId(file);

            if (fileId == id) {
                selectedLevel = file;
            }
        }

        if (selectedLevel == null) {
            throw new RuntimeException("No level was found matching the provided level id");
        }

        XMLFileReader xmlFileReader = new XMLFileReader(selectedLevel);

        Element levelPropertiesElement = xmlFileReader.drilldownToElement("levelProperties");
        Element tileSetElement = xmlFileReader.drilldownToElement("tileSet");

        LevelProperties levelProperties = readLevelProperties(levelPropertiesElement);
        String[][] tileSet = readTileSet(tileSetElement, levelProperties.getLevelHeight(), levelProperties.getLevelWidth());

        return new LevelData(levelProperties, tileSet);

    }

    /**
     * A method to create a tile.Tile[][] for the provided tileSet element
     * @param tileSetElement the tileSet element
     * @param height the height of the level
     * @param width the width of the level
     * @return a tile.Tile[][] representing the tiles in the tile set element
     */
    private static String[][] readTileSet(Element tileSetElement, int height, int width) {
        // TODO: reimplement this to work for upcoming tile object, and all possible attributes
        String[][] tileSet = new String[height][width];

        NodeList tileRows = tileSetElement.getElementsByTagName("tileRow");

        // For every row in the tileSet element in the level file
        for (int i = 0; i < tileRows.getLength(); i++) {
            Element tileRow = (Element) tileRows.item(i);

            NodeList tiles = tileRow.getElementsByTagName("tile");

            // For every tile in the row
            for (int j = 0; j < tiles.getLength(); j++) {
                Node tile = tiles.item(j);
                NamedNodeMap attributes = tile.getAttributes();

                if (attributes != null) {
                    for (int k = 0; k < attributes.getLength(); k++) {
                        System.out.println(attributes.item(k).getTextContent());
                    }
                }

                // Store tile type to the array
                tileSet[i][j] = tile.getTextContent();
            }
        }


        return tileSet;
    }

    /**
     * A method to read the properties from the provided level properties element
     * @param levelProperties the levelProperties elememt
     * @return a LevelProperties object containing the data read from the element
     */
    private static LevelProperties readLevelProperties(Element levelProperties) {
        // Load properties into variables
        int height = getPropertyInt(levelProperties, "levelHeight");
        int width = getPropertyInt(levelProperties, "levelWidth");
        int failPopulation = getPropertyInt(levelProperties, "populationToLose");
        int allowedTime = getPropertyInt(levelProperties, "expectedTime");
        int id = getPropertyInt(levelProperties, "id");
        int itemDropInterval = getPropertyInt(levelProperties, "itemInterval");

        return new LevelProperties(id, height, width, failPopulation, allowedTime, itemDropInterval);
    }

    /**
     * A utility method to get the property of type integer from the properties element provided, with the provided property name
     * @param propertiesElement the levelProperties element to read from
     * @param propertyName The property to read
     * @return The read property, of type int
     */
    private static int getPropertyInt(Element propertiesElement, String propertyName) {
        return Integer.parseInt(propertiesElement.getElementsByTagName(propertyName).item(0).getTextContent());
    }

    /**
     * A utility method to get the level id for a provided file
     * @param file the file to get the id from
     * @return the found id
     */
    private static int getFilesLevelId(File file) {
        XMLFileReader fileReader = new XMLFileReader(file);
        Element idElement = fileReader.drilldownToElement("levelProperties", "id");

        return Integer.parseInt(idElement.getTextContent());
    }
}
