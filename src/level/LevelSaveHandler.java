package level;

import io.XMLElementNames;
import io.XMLFileWriter;
import io.XMLNode;
import objects.GameObject;
import objects.GameObjectType;
import objects.ObjectUtils;
import players.scores.Player;
import tile.Tile;
import tile.TileSet;

import java.io.File;
import java.util.*;

/**
 * A class to handle the saving of level files
 *
 * @author Kallum Jones 2005855
 */
public class LevelSaveHandler {


    /**
     * A method to save the provided level data for the provided player
     *
     * @param levelData The level data currently in play
     * @param player    The player playing the level
     */
    public static void saveLevel(LevelData levelData, Player player) {

        // Construct a file name
        int levelId = levelData.getLevelProperties().getLevelId();
        String path = LevelUtils.constructSavedLevelFileName(player, levelId);

        // Create a file writer for the generated file path
        File levelFile = new File(path);
        XMLFileWriter xmlFileWriter = new XMLFileWriter(
                levelFile, XMLElementNames.LEVEL_ROOT.toString()
        );

        // Save the level data
        saveLevelProperties(xmlFileWriter, levelData);
        saveTileSet(xmlFileWriter, levelData);

        if (levelData.hasInventory()) {
            saveInventory(xmlFileWriter, levelData);
        }

        // Save the file
        xmlFileWriter.saveAndClose();
    }

    /**
     * A method to save the inventory in this level data to the file
     * @param xmlFileWriter the file writer to write with
     * @param levelData the level data for this level
     */
    private static void saveInventory(XMLFileWriter xmlFileWriter, LevelData levelData) {

        List<GameObjectType> itemsInInventory = levelData.getInventory();
        ArrayList<XMLNode> children = new ArrayList<>();
        for (GameObjectType gameObjectType : itemsInInventory) {
            XMLNode itemNode = new XMLNode(
                    XMLElementNames.ITEM.toString(),
                    ObjectUtils.getStringForItem(gameObjectType),
                    null,
                    null
            );
            children.add(itemNode);
        }

        XMLNode inventoryNode = new XMLNode(
                XMLElementNames.INVENTORY.toString(),
                null, null, children
        );

        xmlFileWriter.writeNode(inventoryNode);
    }

    /**
     * A method to save the provided tile set
     *
     * @param xmlFileWriter the file writer to write with
     * @param levelData     the level data containing the tile set
     */
    private static void saveTileSet(XMLFileWriter xmlFileWriter, LevelData levelData) {
        TileSet tileSet = levelData.getTileSet();
        List<GameObject> objects = levelData.getObjects();

        // For every row in the tile set
        List<XMLNode> tileRows = new ArrayList<>();
        for (ArrayList<Tile> tileRow : tileSet.getTileSetRows()) {
            // Get the list of tiles as XMLNodes
            List<XMLNode> tileNodes = getRowAsXMLNodes(tileRow, objects);

            // Create a node for this row, and add it to the list of rows
            XMLNode tileRowNode = new XMLNode(XMLElementNames.TILE_ROW.toString(),
                    null, null, tileNodes
            );
            tileRows.add(tileRowNode);
        }

        // Write the generated tile set
        XMLNode tileSetNode = new XMLNode(XMLElementNames.TILE_SET.toString(),
                null, null, tileRows
        );
        xmlFileWriter.writeNode(tileSetNode);
    }

    /**
     * A method to return the provided tile row as a list of XMLNodes representing each tile
     *
     * @param tileRow the list of Tiles in a row
     * @param objects the list of objects on the board
     * @return the list of XMLNodes representing each tile on a row
     */
    private static List<XMLNode> getRowAsXMLNodes(ArrayList<Tile> tileRow, List<GameObject> objects) {
        List<XMLNode> tileNodes = new ArrayList<>();

        for (Tile tile : tileRow) {
            // Get the objects on this tile
            List<GameObject> objectsOnTile =
                    LevelUtils.getObjectsOnTile(tile, objects);

            // Convert each object to an attribute string
            Map<String, String> attributes = new HashMap<>();
            if (!objectsOnTile.isEmpty()) {
                for (GameObject objectOnTile : objectsOnTile) {
                    ObjectAttributeGenerator.addAttributeForObject(objectOnTile, attributes);
                }
            }

            if (attributes.isEmpty()) {
                attributes = null;
            }

            // Create a node for each tile and add it to the list
            XMLNode tileNode = new XMLNode(XMLElementNames.TILE.toString(),
                    LevelUtils.getTileTypeString(tile.getTileType()), attributes, null
            );
            tileNodes.add(tileNode);
        }

        return tileNodes;
    }

    /**
     * A method to write the level properties to the file
     *
     * @param xmlFileWriter the file writer to use
     * @param levelData     the level data to obtain the properties from
     */
    private static void saveLevelProperties(XMLFileWriter xmlFileWriter, LevelData levelData) {
        LevelProperties levelProperties = levelData.getLevelProperties();

        String levelHeight = String.valueOf(levelProperties.getLevelHeight());
        String levelWidth = String.valueOf(levelProperties.getLevelWidth());
        String populationToLose = String.valueOf(levelProperties.getPopulationToLose());
        String expectedTime = String.valueOf(levelProperties.getExpectedTime());
        String id = String.valueOf(levelProperties.getLevelId());
        String itemInterval = String.valueOf(levelProperties.getItemInterval());
        String ratMinBabies = String.valueOf(levelProperties.getRatMinBabies());
        String ratMaxBabies = String.valueOf(levelProperties.getRatMaxBabies());
        String adultRatSpeed = String.valueOf(levelProperties.getAdultRatSpeed());
        String babyRatSpeed = String.valueOf(levelProperties.getBabyRatSpeed());
        String deathRatSpeed = String.valueOf(levelProperties.getDeathRatSpeed());
        String elapsedTime = String.valueOf(levelProperties.getTimeElapsed());

        XMLNode[] children = new XMLNode[]{
                new XMLNode(XMLElementNames.LEVEL_HEIGHT.toString(),
                        levelHeight, null, null),
                new XMLNode(XMLElementNames.LEVEL_WIDTH.toString(),
                        levelWidth, null, null),
                new XMLNode(XMLElementNames.POPULATION_TO_LOSE.toString(),
                        populationToLose, null, null),
                new XMLNode(XMLElementNames.EXPECTED_TIME.toString(),
                        expectedTime, null, null),
                new XMLNode(XMLElementNames.LEVEL_ID.toString(),
                        id, null, null),
                new XMLNode(XMLElementNames.ITEM_INTERVAL.toString(),
                        itemInterval, null, null),
                new XMLNode(XMLElementNames.RAT_MIN_BABIES.toString(),
                        ratMinBabies, null, null),
                new XMLNode(XMLElementNames.RAT_MAX_BABIES.toString(),
                        ratMaxBabies, null, null),
                new XMLNode(XMLElementNames.ADULT_RAT_SPEED.toString(),
                        adultRatSpeed, null, null),
                new XMLNode(XMLElementNames.BABY_RAT_SPEED.toString(),
                        babyRatSpeed, null, null),
                new XMLNode(XMLElementNames.DEATH_RAT_SPEED.toString(),
                        deathRatSpeed, null, null),
                new XMLNode(XMLElementNames.TIME_ELAPSED.toString(),
                        elapsedTime, null, null)
        };

        List<XMLNode> childrenList = Arrays.asList(children);

        XMLNode levelPropertiesNode = new XMLNode(XMLElementNames.LEVEL_PROPERTIES.toString(),
                null, null, childrenList);

        xmlFileWriter.writeNode(levelPropertiesNode);
    }
}
