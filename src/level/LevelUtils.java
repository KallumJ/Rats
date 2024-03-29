package level;

import display.CustomBoard;
import io.XMLElementNames;
import io.XMLFileReader;
import objects.GameObject;
import objects.Gas;
import objects.GasEffect;
import objects.NoEntrySignCounter;
import org.w3c.dom.Element;
import players.scores.Player;
import tile.Direction;
import tile.Tile;
import tile.TileSet;
import tile.TileType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A utility file to help with general functions around finding levels from
 * file.
 *
 * @author Kallum Jones 2005855
 * @author Aser (minor updates)
 * @date 2022.02.21
 *
 */
public final class LevelUtils {

    private static final String LEVELS_PATH = "levels";
    public static final String SAVED_LEVELS_DIR_PATH = LEVELS_PATH 
            + "/savedLevels/";
    private static final String SAVED_LEVELS_FILE_PATH =
            SAVED_LEVELS_DIR_PATH + "/%s-%s.xml";
    private static final String CUSTOM_LEVELS_DIR_PATH =
            LEVELS_PATH + "/customLevels/";
    private static final String CUSTOM_LEVELS_FILE_PATH =
            CUSTOM_LEVELS_DIR_PATH + "/%s-%s.xml";
    private static final String SAVED_CUSTOM_LEVELS_DIR_PATH = CUSTOM_LEVELS_DIR_PATH 
            + "/savedLevels/";
    private static final String SAVED_CUSTOM_LEVELS_FILE_PATH = SAVED_CUSTOM_LEVELS_DIR_PATH + "/%s-%s.xml";
    private static final String INVALID_TILE_TYPE =
            "%s is an invalid tile " + "type";
    private static final String INVALID_DIRECTION_ERROR =
            "%s is an unknown " + "direction";
    private static final String NO_LEVEL_FOUND = "No level was found matching "
            + "the provided level id";
    private static final String NO_SAVED_LEVELS =
            "The player has no saved " + "levels";

    /**
     * Empty private constructor method, preventing LevelUtils from being
     * instantiated as an object.
     */
    private LevelUtils() {
    }

    /**
     * A method to return an array of File objects of all the files in the
     * level directory.
     *
     * @return the array of files in the directory.
     */
    public static File[] getFilesInLevelDirectory() {
        return getFilesInDirectory(LEVELS_PATH);
    }

    /**
     * A method to return an array of File objects of all the files in the
     * saved level directory for custom levels.
     *
     * @return the array of files in the directory.
     */
    public static File[] getSavedCustomLevelFiles() {
        return getFilesInDirectory(SAVED_CUSTOM_LEVELS_DIR_PATH);
    }

    /**
     * A method to return an array of File objects of all the files in the
     * saved level's directory.
     *
     * @return the array of files in the directory.
     */
    public static File[] getSavedLevelsFiles() {
        return getFilesInDirectory(SAVED_LEVELS_DIR_PATH);
    }

    /**
     * A method to get the Level file by the provided id.
     *
     * @param id the level id.
     * @return the level file
     */
    public static File getLevelFileById(String id) {

        // Find a file with a matching id to what was passed to the method
        File selectedLevel = null;
        for (File file : getFilesInLevelDirectory()) {
            String fileId = getFilesLevelId(file);

            if (fileId.equals(id)) {
                selectedLevel = file;
            }
        }

        if (selectedLevel == null) {
            throw new IllegalArgumentException(NO_LEVEL_FOUND);
        }

        return selectedLevel;
    }

    /**
     * A utility method to get the level id for a provided file.
     *
     * @param file the file to get the id from.
     * @return the found id.
     */
    public static String getFilesLevelId(File file) {
        XMLFileReader fileReader = new XMLFileReader(file);
        Element idElement =
                fileReader.drilldownToElement(XMLElementNames.LEVEL_PROPERTIES,
                        XMLElementNames.LEVEL_ID);

        return idElement.getTextContent();
    }

    /**
     * A method to convert tile types to a string.
     *
     * @param tileType the tile type.
     * @return the converted string.
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
     * A method to get the string from a direction.
     *
     * @param directionOfMovement the direction.
     * @return the string for this direction.
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

    /**
     * A method to get the player name associated with a saved level.
     *
     * @param savedLevel the saved level.
     * @return the player name associated with this saved level.
     */
    public static String getPlayerNameFromSavedLevel(File savedLevel) {
        String[] filePath = savedLevel.getName().split("/");
        String fileName = filePath[filePath.length - 1];

        String fileNameNoExtension = fileName.replace(".xml", "");
        String[] fileNameSplit = fileNameNoExtension.split("-");

        return fileNameSplit[0];
    }

    /**
     * Added a method to construct the file name for a saved level for a given
     * player and level id.
     *
     * @param player  the player.
     * @param levelId the level id.
     * @return the file name for a saved level for a given player and level id.
     */
    public static String constructSavedLevelFileName(Player player,
                                                     String levelId) {
        // If the directory doesn't exist, create it
        File file = new File(SAVED_LEVELS_DIR_PATH);
        if (!file.exists()) {
            file.mkdir();
        }

        return String.format(SAVED_LEVELS_FILE_PATH, player.getPlayerName(),
                levelId);
    }

    /**
     * Added a method to construct the file name for a saved custom level for a given
     * player and level id.
     *
     * @param player  the player.
     * @param levelId the level id.
     * @return the file name for a saved custom level for a given player and level id.
     */
    public static String constructCustomLevelFileName(Player player, String levelId) {
        // If the directory doesn't exist, create it
        File file = new File(CUSTOM_LEVELS_DIR_PATH);
        if (!file.exists()) {
            file.mkdir();
        }

        return String.format(CUSTOM_LEVELS_FILE_PATH, player.getPlayerName(),
                levelId);
    }

    /**
     * A method to get all the objects on the given tile.
     *
     * @param tile    the tile the objects are standing on.
     * @param objects the list of objects on the board.
     * @return the list of game objects on the given tile.
     */
    public static List<GameObject> getObjectsOnTile(Tile tile,
                                                    List<GameObject> objects) {
        List<GameObject> objectsOnTile = new ArrayList<>();

        // For every object, if the object is standing on the provided tile,
        // add it to the list
        for (GameObject object : objects) {
            if (object.getStandingOn().equals(tile)) {
                objectsOnTile.add(object);
            }
        }

        return objectsOnTile;
    }

    /**
     * A method to check whether a tile is blocked by other objects.
     *
     * @param tile    the tile to check.
     * @param objects the list of objects on the board.
     * @return true if this tile is blocked by another object, and can not have
     * an item placed, false otherwise.
     */
    public static boolean isTileBlocked(Tile tile, List<GameObject> objects) {
        List<GameObject> objectsOnTile = getObjectsOnTile(tile, objects);

        // return true if all the items on the tile are of an allowed type


        return !(objectsOnTile.stream()
                .allMatch(object -> object instanceof NoEntrySignCounter
                        || object instanceof GasEffect || object instanceof Gas));
    }

    /**
     * A method to get the list of saved levels for the provided player.
     *
     * @param player the player to get the levels for.
     * @return the List of level files for the provided player.
     */
    public static List<File> getSavedLevelsForPlayer(Player player) {
        // Get a stream of saved level files
        Stream<File> savedLevelFilesStream =
                Arrays.stream(getSavedLevelsFiles());

        // Filter the stream based on whether the current file is
        // for the provided player
        savedLevelFilesStream =
                savedLevelFilesStream.filter(file -> isSavedLevelForPlayer(file, player));

        // Return the stream as a list
        return savedLevelFilesStream.collect(Collectors.toList());
    }

    /**
     * Returns the most recent level for a player.
     *
     * @param player the player.
     * @return the most recently level file for the player.
     * @throws FileNotFoundException when file is not found
     */
    public static File getMostRecentLevel(Player player) throws FileNotFoundException {
        List<File> savedLevels = LevelUtils.getSavedLevelsForPlayer(player);

        File mostRecentLevel = null;
        for (File level : savedLevels) {
            if (mostRecentLevel == null || level.lastModified() > mostRecentLevel.lastModified()) {
                mostRecentLevel = level;
            }
        }

        if (mostRecentLevel == null) {
            throw new FileNotFoundException(NO_SAVED_LEVELS);
        }
        return mostRecentLevel;
    }

    /**
     * A method to get the files in the provided directory.
     *
     * @param path the path to check.
     * @return the list of files in the provided directory.
     */
    private static File[] getFilesInDirectory(String path) {
        File levelsDirectory = new File(path);
        File[] filesInLevelDirectory = levelsDirectory.listFiles();

        if (!levelsDirectory.exists()) {
            levelsDirectory.mkdir();
        }

        ArrayList<File> nonDirectoryFiles = new ArrayList<>();
        if (filesInLevelDirectory != null) {
            for (File file : filesInLevelDirectory) {
                if (!file.isDirectory()) {
                    nonDirectoryFiles.add(file);
                }
            }
        }

        return nonDirectoryFiles.toArray(new File[0]);
    }

    /**
     * Check whether the provided level file is for the provided player.
     *
     * @param file   the level file.
     * @param player the player.
     * @return true if yes, false otherwise.
     */
    private static boolean isSavedLevelForPlayer(File file, Player player) {
        String playerName = player.getPlayerName();
        String filePlayer = getPlayerNameFromSavedLevel(file);
        return filePlayer.equals(playerName);
    }

    /**
     * A method to give every tile in a tile set their adjacent tile.
     *
     * @param tileSet the tile set to run through.
     */
    public static void setAdjacentTiles(TileSet tileSet) {
        for (int y = 0; y < tileSet.getHeight(); y++) {
            for (int x = 0; x < tileSet.getWidth(); x++) {
                Tile tile = tileSet.getTile(x, y);

                tile.setAdjacentTileIfPresent(Direction.UP, tileSet.getTile(x,
                        y - 1));
                tile.setAdjacentTileIfPresent(Direction.DOWN,
                        tileSet.getTile(x, y + 1));
                tile.setAdjacentTileIfPresent(Direction.LEFT,
                        tileSet.getTile(x - 1, y));
                tile.setAdjacentTileIfPresent(Direction.RIGHT,
                        tileSet.getTile(x + 1, y));
            }
        }

    }

    /**
     * A method to return whether the current board is custom or not.
     *
     * @return true if board is custom, false otherwise.
     */
    public static boolean isCurrentBoardCustom() {
        return (GameObject.getBoard() instanceof CustomBoard);
    }

    /**
     * A method to get an array of all the custom level files.
     *
     * @return a File[] of all the custom level files.
     */
    public static File[] getCustomLevels() {
        return getFilesInDirectory(CUSTOM_LEVELS_DIR_PATH);
    }

    /**
     * A method to return whether the level id is for a custom level.
     *
     * @param levelId the level id to check.
     * @return true if the id is custom, false otherwise.
     */
    public static boolean isIdNotForCustomLevel(String levelId) {
        try {
            Integer.parseInt(levelId);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * A method to the file path for a saved custom level.
     *
     * @param player  the player saving.
     * @param levelId the level id of the level.
     * @return The level's file path.
     */
    public static String constructCustomSavedLevelFileName(Player player, String levelId) {
        // If the directory doesn't exist, create it
        File file = new File(SAVED_CUSTOM_LEVELS_DIR_PATH);
        if (!file.exists()) {
            file.mkdir();
        }

        return String.format(SAVED_CUSTOM_LEVELS_FILE_PATH, player.getPlayerName(),
                levelId);
    }
}
