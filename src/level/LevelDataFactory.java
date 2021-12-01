package level;

import io.XMLFileReader;
import objects.GameObject;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import players.scores.Player;
import tile.*;

import java.io.File;
import java.util.ArrayList;

/**
 * A class to load LevelData objects from disk
 *
 * @author Kallum Jones 2005855
 */
public class LevelDataFactory {

    /**
     * A method to construct the LevelData object for a given level id
     *
     * @param id The id of the level that needs constructing
     * @return The complete LevelData object
     */
    public static LevelData constructLevelData(int id) {
        File selectedLevel = LevelUtils.getLevelFileById(id);
        return constructLevelDataFromFile(selectedLevel);
    }

    /**
     * A method to read the objects that are stored on the tiles
     * @param tileSet An instance of TileSet with all the tiles to read from
     * @return an ArrayList of Objects with the required information
     */
    private static ArrayList<GameObject> readObjects(TileSet tileSet, LevelProperties levelProperties) {
        ArrayList<GameObject> objects = new ArrayList<>();

        for (Tile tile : tileSet.getAllTiles()) {
            if (tile.hasInitalAttributes()) {
                NamedNodeMap attributes = tile.getInitialAttributes();

                // For every attribute on the tile TODO: Does this need to be a loop?
                for (int i = 0; i < attributes.getLength(); i++) {
                    Node attribute = attributes.item(i);
                    String attributeName = attribute.getNodeName();
                    String attributeValue = attribute.getNodeValue();

                    // Read the object from the provided attribute
                    objects.add(TileAttributeReader.getObjectFromAttribute(attributeName, attributeValue, tile, levelProperties));
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
     * A method to give every tile in a tile set their adjacent tile
     * @param tileSet the tile set to run through
     */
    private static void setAdjacentTiles(TileSet tileSet) {
        for (int y = 0; y < tileSet.getHeight(); y++) {
            for (int x = 0; x < tileSet.getWidth(); x++) {
                Tile tile = tileSet.getTile(x, y);


                tile.setAdjacentTileIfPresent(Direction.UP, tileSet.getTile(x, y - 1));
                tile.setAdjacentTileIfPresent(Direction.DOWN, tileSet.getTile(x, y + 1));
                tile.setAdjacentTileIfPresent(Direction.LEFT, tileSet.getTile(x - 1, y));
                tile.setAdjacentTileIfPresent(Direction.RIGHT, tileSet.getTile(x + 1, y));
            }
        }

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
        int populationToLose = getPropertyInt(levelProperties, "populationToLose");
        int expectedTime = getPropertyInt(levelProperties, "expectedTime");
        int id = getPropertyInt(levelProperties, "id");
        int itemInterval = getPropertyInt(levelProperties, "itemInterval");
        int ratMinBabies = getPropertyInt(levelProperties, "ratMinBabies");
        int ratMaxBabies = getPropertyInt(levelProperties, "ratMaxBabies");
        int adultRatSpeed = getPropertyInt(levelProperties, "adultRatSpeed");
        int babyRatSpeed = getPropertyInt(levelProperties, "babyRatSpeed");
        int deathRatSpeed = getPropertyInt(levelProperties, "deathRatSpeed");

        return new LevelProperties(id, height, width, populationToLose, expectedTime, itemInterval, ratMinBabies, ratMaxBabies, adultRatSpeed, babyRatSpeed, deathRatSpeed);
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
     * A method to construct the saved level data for the provided player and level
     * @param currentlyLoggedInPlayer the player
     * @param id the level
     * @return the complete LevelData object
     */
    public static LevelData constructSavedLevelData(Player currentlyLoggedInPlayer, String id) {
        File file = new File(
                LevelUtils.constructSavedLevelFileName(currentlyLoggedInPlayer, Integer.parseInt(id))
        );
        return constructLevelDataFromFile(file);
    }

    /**
     * A method to construct the LevelData object for a given level file
     *
     * @param file The level file that needs constructing
     * @return The complete LevelData object
     */
    private static LevelData constructLevelDataFromFile(File file) {
        XMLFileReader xmlFileReader = new XMLFileReader(file);

        Element levelPropertiesElement = xmlFileReader.drilldownToElement("levelProperties");
        Element tileSetElement = xmlFileReader.drilldownToElement("tileSet");

        LevelProperties levelProperties = readLevelProperties(levelPropertiesElement);
        TileSet tileSet = readTileSet(tileSetElement);
        setAdjacentTiles(tileSet);
        ArrayList<GameObject> objects = readObjects(tileSet, levelProperties);

        return new LevelData(levelProperties, tileSet, objects);

    }
}
