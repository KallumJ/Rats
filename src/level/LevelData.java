package level;

import objects.Object;
import tile.TileSet;

import java.util.List;

/**
 * A class to model the data required for a Level
 *
 * @author Kallum Jones 2005855
 */
public class LevelData {
    private final LevelProperties levelProperties;
    private final TileSet tileSet;
    private final List<Object> objects;

    /**
     * Constructs a LevelData object with the provided data
     * @param levelProperties The LevelProperties of the Level
     * @param tileSet the TileSet for the level
     * @param objects the Objects present on a level, as a List of objects
     */
    public LevelData(LevelProperties levelProperties, TileSet tileSet, List<Object> objects) {
        this.levelProperties = levelProperties;
        this.tileSet = tileSet;
        this.objects = objects;
    }

    /**
     * A method to get the LevelProperties for the level
     * @return the LevelProperties for the level
     */
    public LevelProperties getLevelProperties() {
        return levelProperties;
    }

    /**
     * A method to get the TileSet for the level
     * @return the TileSet for the level
     */
    public TileSet getTileSet() {
        return tileSet;
    }

    /**
     * A method to get the List of Objects present on the level
     * @return List of Objects present on the level
     */
    public List<Object> getObjects() {
        return objects;
    }
}
