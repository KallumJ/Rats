package level.custom;

import level.LevelData;
import level.LevelDataFactory;
import level.LevelProperties;
import level.LevelUtils;
import players.scores.Player;
import tile.TileSet;
import tile.TileType;

import java.io.File;
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
        LevelUtils.setAdjacentTiles(tileSet);

        LevelProperties levelProperties = new LevelProperties();
        levelProperties.setLevelWidth(width);
        levelProperties.setLevelHeight(height);
        return new LevelData(levelProperties, tileSet, new ArrayList<>());
    }

    /**
     * Get the Custom LevelData for the provided player and ID
     * @param currentlyLoggedInPlayer the current player
     * @param id the id of the level they selected
     * @return the constructed LevelData
     */
    public static LevelData constructCustomLevelData(Player currentlyLoggedInPlayer, String id) {
        File file = new File(LevelUtils.constructCustomLevelFileName(currentlyLoggedInPlayer, Integer.parseInt(id)));
        return LevelDataFactory.constructLevelDataFromFile(file);
    }
}
