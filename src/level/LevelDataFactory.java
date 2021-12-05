package level;

import io.XMLElementNames;
import io.XMLFileReader;
import objects.GameObject;
import objects.GameObjectType;
import objects.ObjectUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import players.scores.Player;
import tile.Direction;
import tile.Tile;
import tile.TileSet;
import tile.TileType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to load LevelData objects from disk
 *
 * @author Kallum Jones 2005855
 */
public class LevelDataFactory {
    private static final String INVALID_TILE_TYPE = "An invalid tile type was read from file";

    /**
     * Empty private constructor method, preventing LevelDataFactory from being
     * instantiated as an object.
     */
    private LevelDataFactory() {
    }

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
     * A method to construct the saved level data for the provided player and level
     *
     * @param currentlyLoggedInPlayer the player
     * @param id                      the level
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
    public static LevelData constructLevelDataFromFile(File file) {
        XMLFileReader xmlFileReader = new XMLFileReader(file);

        Element levelPropertiesElement = xmlFileReader.drilldownToElement(XMLElementNames.LEVEL_PROPERTIES);
        Element tileSetElement = xmlFileReader.drilldownToElement(XMLElementNames.TILE_SET);
        Element inventoryElement = xmlFileReader.drilldownToElement(XMLElementNames.INVENTORY);

        LevelProperties levelProperties = readLevelProperties(levelPropertiesElement);
        TileSet tileSet = readTileSet(tileSetElement);


        setAdjacentTiles(tileSet);
        ArrayList<GameObject> objects = readObjects(tileSet, levelProperties);

        LevelData levelData = new LevelData(levelProperties, tileSet, objects);

        // If this level has an inventory, then add it to the level data
        if (inventoryElement != null) {
            List<GameObjectType> inventory = readInventory(inventoryElement);
            levelData.setInventory(inventory);
        }

        return levelData;
    }

    /**
     * A method to read the objects that are stored on the tiles
     *
     * @param tileSet An instance of TileSet with all the tiles to read from
     * @return an ArrayList of Objects with the required information
     */
    private static ArrayList<GameObject> readObjects(TileSet tileSet, LevelProperties levelProperties) {
        ArrayList<GameObject> objects = new ArrayList<>();

        for (Tile tile : tileSet.getAllTiles()) {
            if (tile.hasInitalAttributes()) {
                NamedNodeMap attributes = tile.getInitialAttributes();

                // For every attribute on the tile
                for (int i = 0; i < attributes.getLength(); i++) {
                    Node attribute = attributes.item(i);
                    String attributeName = attribute.getNodeName();
                    String attributeValue = attribute.getNodeValue();

                    // Read the object from the provided attribute
                    objects.add(
                            TileAttributeReader.getObjectFromAttribute(
                                    attributeName, attributeValue, tile, levelProperties
                            )
                    );
                }
            }
        }

        return objects;
    }

    /**
     * A method to create a TileSet for the provided tileSet element
     *
     * @param tileSetElement the tileSet element
     * @return a TileSet representing the tiles in the tile set element
     */
    private static TileSet readTileSet(Element tileSetElement) {
        TileSet tileSet = new TileSet();
        NodeList tileRows = tileSetElement.getElementsByTagName(XMLElementNames.TILE_ROW.toString());

        // For every row in the tileSet element in the level file
        for (int i = 0; i < tileRows.getLength(); i++) {
            Element tileRow = (Element) tileRows.item(i);

            NodeList tiles = tileRow.getElementsByTagName(XMLElementNames.TILE.toString());

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
                        throw new IllegalArgumentException(INVALID_TILE_TYPE);
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
     *
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
     *
     * @param levelProperties the levelProperties elememt
     * @return a LevelProperties object containing the data read from the element
     */
    private static LevelProperties readLevelProperties(Element levelProperties) {
        // Load properties into variables
        int height = getPropertyInt(levelProperties, XMLElementNames.LEVEL_HEIGHT);
        int width = getPropertyInt(levelProperties, XMLElementNames.LEVEL_WIDTH);
        int populationToLose = getPropertyInt(levelProperties, XMLElementNames.POPULATION_TO_LOSE);
        int expectedTime = getPropertyInt(levelProperties, XMLElementNames.EXPECTED_TIME);
        int id = getPropertyInt(levelProperties, XMLElementNames.LEVEL_ID);
        int itemInterval = getPropertyInt(levelProperties, XMLElementNames.ITEM_INTERVAL);
        int ratMinBabies = getPropertyInt(levelProperties, XMLElementNames.RAT_MIN_BABIES);
        int ratMaxBabies = getPropertyInt(levelProperties,  XMLElementNames.RAT_MAX_BABIES);
        int adultRatSpeed = getPropertyInt(levelProperties, XMLElementNames.ADULT_RAT_SPEED);
        int babyRatSpeed = getPropertyInt(levelProperties, XMLElementNames.BABY_RAT_SPEED);
        int deathRatSpeed = getPropertyInt(levelProperties, XMLElementNames.DEATH_RAT_SPEED);
        int timeElapsed = getPropertyInt(levelProperties, XMLElementNames.TIME_ELAPSED);
        int score = getPropertyInt(levelProperties, XMLElementNames.LEVEL_SCORE);

        return new LevelProperties(
                id, height, width, populationToLose, expectedTime, itemInterval, ratMinBabies,
                ratMaxBabies, adultRatSpeed, babyRatSpeed, deathRatSpeed, timeElapsed, score
        );
    }

    /**
     * A utility method to get the property of type integer from the properties element provided, with the provided property name
     *
     * @param propertiesElement the levelProperties element to read from
     * @param propertyName      The property to read
     * @return The read property, of type int
     */
    private static int getPropertyInt(Element propertiesElement, XMLElementNames propertyName) {
        String propertyStr = propertyName.toString();
        Node propertyElement = propertiesElement.getElementsByTagName(propertyStr).item(0);
        return Integer.parseInt(propertyElement.getTextContent());
    }

    /**
     * A method to get the items in the inventory for this level
     * @param inventoryElement the inventory element read from file
     * @return the list of game object types represented in the file
     */
    private static List<GameObjectType> readInventory(Element inventoryElement) {
        NodeList itemElements = inventoryElement.getElementsByTagName(XMLElementNames.ITEM.toString());

        ArrayList<GameObjectType> itemsInInventory = new ArrayList<>();

        for (int i = 0; i < itemElements.getLength(); i++) {
            Node item = itemElements.item(i);

            String itemString = item.getTextContent();

            GameObjectType itemObj = ObjectUtils.getTypeFromString(itemString);

            itemsInInventory.add(itemObj);
        }

        return itemsInInventory;
    }
}
