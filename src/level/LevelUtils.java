package level;

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
 * file
 *
 * @author Kallum Jones 2005855
 */
public class LevelUtils {

	private static final String LEVELS_PATH = "levels";
	public static final String SAVED_LEVELS_DIR_PATH = LEVELS_PATH +
			"/savedLevels/";
	private static final String SAVED_LEVELS_FILE_PATH =
			SAVED_LEVELS_DIR_PATH + "/%s-%d.xml";
	private static final String INVALID_TILE_TYPE =
			"%s is an invalid tile " + "type";
	private static final String INVALID_DIRECTION_ERROR =
			"%s is an unknown " + "direction";
	private static final String INVALID_DIRECTORY =
			"The provided directory " + "is" + " empty";
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
	 * level
	 * directory
	 *
	 * @return the array of files in the directory
	 */
	public static File[] getFilesInLevelDirectory() {
		return getFilesInDirectory(LEVELS_PATH);
	}

	/**
	 * A method to return an array of File objects of all the files in the
	 * saved
	 * levels directory
	 *
	 * @return the array of files in the directory
	 */
	public static File[] getSavedLevelsFiles() {
		return getFilesInDirectory(SAVED_LEVELS_DIR_PATH);
	}

	/**
	 * A method to get the LevelFile by the provided id
	 *
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
			throw new IllegalArgumentException(NO_LEVEL_FOUND);
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
		Element idElement =
				fileReader.drilldownToElement(XMLElementNames.LEVEL_PROPERTIES
						, XMLElementNames.LEVEL_ID);

		return Integer.parseInt(idElement.getTextContent());
	}

	/**
	 * A method to convert tile types to a string
	 *
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
	 *
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

	/**
	 * A method to get the player name associated with a saved level
	 *
	 * @param savedLevel the saved level
	 * @return the player name associated with this saved level
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
	 * player and level id
	 *
	 * @param player  the player
	 * @param levelId the level id
	 * @return the file name for a saved level for a given player and level id
	 */
	public static String constructSavedLevelFileName(Player player,
													 int levelId) {
		// If the directory doesnt exist, create it
		File file = new File(SAVED_LEVELS_DIR_PATH);
		if (!file.exists()) {
			file.mkdir();
		}

		return String.format(SAVED_LEVELS_FILE_PATH, player.getPlayerName(),
				levelId);
	}

	/**
	 * A method to get all the objects on the given tile
	 *
	 * @param tile    the tile the objects are standing on
	 * @param objects the list of objects on the board
	 * @return the list of game objects on the given tile
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
	 * A method to check whether a tile is blocked by other objects
	 *
	 * @param tile    the tile to check
	 * @param objects the list of objects on the board
	 * @return true if this tile is blocked by another object, and can not have
	 * an item placed, false otherwise
	 */
	public static boolean isTileBlocked(Tile tile, List<GameObject> objects) {
		List<GameObject> objectsOnTile = getObjectsOnTile(tile, objects);

		// return true if all of the items on the tile are of an allowed type


		return !(objectsOnTile.stream()
				.allMatch(object -> object instanceof NoEntrySignCounter || object instanceof GasEffect || object instanceof Gas));
	}

	/**
	 * A method to get the list of saved levels for the provided player
	 *
	 * @param player the player to get the levels for
	 * @return the List of level files for the provided player
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
	 * Returns the most recent level for a player
	 *
	 * @param player the player
	 * @return the most recently level file for the player
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
	 * A method to get the files in the provided directory
	 *
	 * @param path the path to check
	 * @return the list of files in the provided directory
	 */
	private static File[] getFilesInDirectory(String path) {
		File levelsDirectory = new File(path);
		File[] filesInLevelDirectory = levelsDirectory.listFiles();

		if (filesInLevelDirectory == null) {
			throw new RuntimeException(INVALID_DIRECTORY);
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
	 * Check whether the provided level file is for the provided player
	 *
	 * @param file   the level file
	 * @param player the player
	 * @return true if yes, false otherwise
	 */
	private static boolean isSavedLevelForPlayer(File file, Player player) {
		String playerName = player.getPlayerName();
		String filePlayer = getPlayerNameFromSavedLevel(file);
		return filePlayer.equals(playerName);
	}

}
