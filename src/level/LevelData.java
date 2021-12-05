package level;

import objects.GameObject;
import objects.GameObjectType;
import tile.TileSet;

import java.util.List;

/**
 * A class to model the data required for a Level.
 *
 * @author Kallum Jones 2005855
 */
public class LevelData {
	private final LevelProperties levelProperties;
	private final TileSet tileSet;
	private List<GameObject> objects;
	private List<GameObjectType> inventory;

	/**
	 * Constructs a LevelData object with the provided data.
	 *
	 * @param levelProperties The LevelProperties of the Level
	 * @param tileSet         the TileSet for the level
	 * @param objects         the Objects present on a level, as a List of
	 *                        objects
	 */
	public LevelData(LevelProperties levelProperties, TileSet tileSet,
					 List<GameObject> objects) {
		this.levelProperties = levelProperties;
		this.tileSet = tileSet;
		this.objects = objects;
	}

	/**
	 * A method to get the LevelProperties for the level.
	 *
	 * @return the LevelProperties for the level
	 */
	public LevelProperties getLevelProperties() {
		return levelProperties;
	}

	/**
	 * A method to get the TileSet for the level.
	 *
	 * @return the TileSet for the level
	 */
	public TileSet getTileSet() {
		return tileSet;
	}

	/**
	 * A method to get the List of Objects present on the level.
	 *
	 * @return List of Objects present on the level
	 */
	public List<GameObject> getObjects() {
		return objects;
	}

	/**
	 * Sets the list of objects.
	 *
	 * @param objects a list of objects
	 */
	public void setObjects(List<GameObject> objects) {
		this.objects = objects;
	}

	/**
	 * A method to get the items in the inventory for this level.
	 *
	 * @return the items in the inventory for this level
	 */
	public List<GameObjectType> getInventory() {
		return inventory;
	}

	/**
	 * A method to set the items in the inventory for this level.
	 *
	 * @param inventory the items in the inventory for this level
	 */
	public void setInventory(List<GameObjectType> inventory) {
		this.inventory = inventory;
	}

	/**
	 * A method to determine whether this level has an inventory assigned.
	 *
	 * @return true if yes, false otherwise
	 */
	public boolean hasInventory() {
		return inventory != null;
	}
}
