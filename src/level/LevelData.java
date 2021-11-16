package level;

import objects.Object;
import tile.TileSet;

import java.util.ArrayList;

public class LevelData {
    private final LevelProperties levelProperties;
    private final TileSet tileSet;
    private final ArrayList<Object> objects;

    public LevelData(LevelProperties levelProperties, TileSet tileSet, ArrayList<Object> objects) {
        this.levelProperties = levelProperties;
        this.tileSet = tileSet;
        this.objects = objects;
    }

    public LevelProperties getLevelProperties() {
        return levelProperties;
    }

    public TileSet getTileSet() {
        return tileSet;
    }

    public ArrayList<Object> getObjects() {
        return objects;
    }
}
