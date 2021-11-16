package level;

import tile.TileSet;

public class LevelData {
    private final LevelProperties levelProperties;
    private final TileSet tileSet;

    public LevelData(LevelProperties levelProperties, TileSet tileSet) {
        this.levelProperties = levelProperties;
        this.tileSet = tileSet;
    }

    public LevelProperties getLevelProperties() {
        return levelProperties;
    }

    public TileSet getTileSet() {
        return tileSet;
    }
}
