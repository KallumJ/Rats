package level;

import io.XMLFileWriter;
import io.XMLNode;
import objects.GameObject;
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
    private static final String SAVED_LEVELS_FILE_PATH = LevelUtils.SAVED_LEVELS_DIR_PATH + "/%s%d.xml";

    /**
     * A method to save the provided level data for the provided player
     * @param levelData The level data currently in play
     * @param player The player playing the level
     */
    public static void saveLevel(LevelData levelData, Player player) {

        // Construct a file name, of playerName + levelId
        String playerName = player.getPlayerName();
        int levelId = levelData.getLevelProperties().getLevelId();
        String path = String.format(SAVED_LEVELS_FILE_PATH, playerName, levelId);

        // Create a file writer for the generated file path
        File levelFile = new File(path);
        XMLFileWriter xmlFileWriter = new XMLFileWriter(levelFile, "level");

        // Save the level data
        saveLevelProperties(xmlFileWriter, levelData);
        saveTileSet(xmlFileWriter, levelData);

        // Save the file
        xmlFileWriter.saveAndClose();
    }

    /**
     * A method to save the provided tile set
     * @param xmlFileWriter the file writer to write with
     * @param levelData the level data containing the tile set
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
            XMLNode tileRowNode = new XMLNode("tileRow", null, null, tileNodes);
            tileRows.add(tileRowNode);
        }

        // Write the generated tile set
        XMLNode tileSetNode = new XMLNode("tileSet", null, null, tileRows);
        xmlFileWriter.writeNode(tileSetNode);
    }

    /**
     * A method to return the provided tile row as a list of XMLNodes representing each tile
     * @param tileRow the list of Tiles in a row
     * @param objects the list of objects on the board
     * @return the list of XMLNodes representing each tile on a row
     */
    private static List<XMLNode> getRowAsXMLNodes(ArrayList<Tile> tileRow, List<GameObject> objects) {
        List<XMLNode> tileNodes = new ArrayList<>();

        for (Tile tile : tileRow) {
            // Get the objects on this tile
            List<GameObject> objectsOnTile = getObjectsOnTile(tile, objects);

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
            XMLNode tileNode = new XMLNode("tile", LevelUtils.getTileTypeString(tile.getTileType()), attributes, null);
            tileNodes.add(tileNode);
        }

        return tileNodes;
    }

    /**
     * A method to get all the objects on the given tile
     * @param tile the tile the objects are standing on
     * @param objects the list of objects on the board
     * @return the list of game objects on the given tile
     */
    private static List<GameObject> getObjectsOnTile(Tile tile, List<GameObject> objects) {
        List<GameObject> objectsOnTile = new ArrayList<>();

        // For every object, if the object is standing on the provided tile, add it to the list
        for (GameObject object : objects) {
            if (object.getStandingOn().equals(tile)) {
                objectsOnTile.add(object);
            }
        }

        return objectsOnTile;
    }

    /**
     * A method to write the level properties to the file
     * @param xmlFileWriter the file writer to use
     * @param levelData the level data to obtain the properties from
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

        XMLNode levelHeightNode = new XMLNode("levelHeight", levelHeight, null, null);
        XMLNode levelWidthNode = new XMLNode("levelWidth", levelWidth, null, null);
        XMLNode populationToLoseNode = new XMLNode("populationToLose", populationToLose, null, null);
        XMLNode expectedTimeNode = new XMLNode("expectedTime", expectedTime, null, null);
        XMLNode idNode = new XMLNode("id", id, null, null);
        XMLNode itemIntervalNode = new XMLNode("itemInterval", itemInterval, null, null);
        XMLNode ratMinBabiesNode = new XMLNode("ratMinBabies", ratMinBabies, null, null);
        XMLNode ratMaxBabiesNode = new XMLNode("ratMaxBabies", ratMaxBabies, null, null);
        XMLNode adultRatSpeedNode = new XMLNode("adultRatSpeed", adultRatSpeed, null, null);
        XMLNode babyRatSpeedNode = new XMLNode("babyRatSpeed", babyRatSpeed, null, null);
        XMLNode deathRatSpeedNode = new XMLNode("deathRatSpeed", deathRatSpeed, null, null);

        ArrayList<XMLNode> children = new ArrayList<>();
        children.add(levelHeightNode);
        children.add(levelWidthNode);
        children.add(populationToLoseNode);
        children.add(expectedTimeNode);
        children.add(idNode);
        children.add(itemIntervalNode);
        children.add(ratMinBabiesNode);
        children.add(ratMaxBabiesNode);
        children.add(adultRatSpeedNode);
        children.add(babyRatSpeedNode);
        children.add(deathRatSpeedNode);

        XMLNode levelPropertiesNode = new XMLNode("levelProperties", null, null, children);

        xmlFileWriter.writeNode(levelPropertiesNode);
    }
}
