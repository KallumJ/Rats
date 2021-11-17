package level;

import io.XMLFileReader;
import objects.Object;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import tile.Tile;
import tile.TileSet;
import tile.TileType;

import java.io.File;
import java.util.ArrayList;

/**
 * A class to load LevelData objects from disk
 *
 * @author Kallum Jones 2005855
 */
public class LevelDataFactory {
    //TODO: handle inventory

    private static final String LEVELS_PATH = "levels";

    /**
     * A method to construct the LevelData object for a given level id
     *
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
        TileSet tileSet = readTileSet(tileSetElement);
        ArrayList<Object> objects = readObjects(tileSet);

        return new LevelData(levelProperties, tileSet, objects);

    }

    /**
     * A method to read the objects that are stored on the tiles
     * @param tileSet An instance of TileSet with all the tiles to read from
     * @return an ArrayList of Objects with the required information
     */
    private static ArrayList<Object> readObjects(TileSet tileSet) {
        ArrayList<Object> objects = new ArrayList<>();

        for (Tile tile : tileSet.getAllTiles()) {
            if (tile.hasInitalAttributes()) {
                NamedNodeMap attributes = tile.getInitialAttributes();

                // For every attribute on the tile TODO: Does this need to be a loop?
                for (int i = 0; i < attributes.getLength(); i++) {
                    Node attribute = attributes.item(i);
                    String attributeName = attribute.getNodeName();
                    String attributeValue = attribute.getNodeValue();

                    // Read the object from the provided attribute
                    objects.add(TileAttributeReader.getObjectFromAttribute(attributeName, attributeValue, tile));
                }
            }
        }

        return objects;
    }

    /**
     * A method to create a tile.Tile[][] for the provided tileSet element
     *
     * @param tileSetElement the tileSet element
     * @return a TileSet representing the tiles in the tile set element
     */
    private static TileSet readTileSet(Element tileSetElement) {
        TileSet tileSet = new TileSet();
        NodeList tileRows = tileSetElement.getElementsByTagName("tileRow");

        // For every row in the tileSet element in the level file
        for (int i = 0; i < tileRows.getLength(); i++) {
            Element tileRow = (Element) tileRows.item(i);

            NodeList tiles = tileRow.getElementsByTagName("tile");

            // For every tile in the row
            for (int j = 0; j < tiles.getLength(); j++) {
                Node tile = tiles.item(j);

                // Store tile type to the array
                String tileText = tile.getTextContent();
                switch (tileText) {
                    case "g":
                        tileSet.putTile(TileType.GRASS, j, i);
                        break;
                    case "t":
                        tileSet.putTile(TileType.TUNNEL, j, i);
                        break;
                    case "p":
                        tileSet.putTile(TileType.PATH, j, i);
                        break;
                    default:
                        throw new RuntimeException("An invalid tile type was read from file");
                }
                NamedNodeMap attributes = tile.getAttributes();

                // If there are attributes present, store them in the tile
                if (attributes.getLength() > 0) {
                    tileSet.getTile(j, i).setInitialAttributes(attributes);
                }

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
     *
     * @param file the file to get the id from
     * @return the found id
     */
    private static int getFilesLevelId(File file) {
        XMLFileReader fileReader = new XMLFileReader(file);
        Element idElement = fileReader.drilldownToElement("levelProperties", "id");

        return Integer.parseInt(idElement.getTextContent());
    }
}
