package level.custom;

import level.LevelData;
import level.LevelProperties;
import tile.TileSet;
import tile.TileType;

import java.util.ArrayList;

/**
 * A class to construct custom level data objects
 *
 * @author Kallum Jones (2005855) (10/02/22)
 */
public class CustomLevelDataFactory {

    /**
     * A method to get a blank LevelData ready for editing
     * @param height the height of the level
     * @param width the width of the level
     * @return The blank LevelData
     */
    public static LevelData getBlankLevelData(int height, int width) {
        TileSet tileSet = new TileSet();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tileSet.putTile(TileType.GRASS, x, y);
            }
        }

        LevelProperties levelProperties = new LevelProperties();
        levelProperties.setLevelWidth(width);
        levelProperties.setLevelHeight(height);
        return new LevelData(levelProperties, tileSet, new ArrayList<>());
    }
}
