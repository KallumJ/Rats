package level;

public class LevelData {
    private final LevelProperties levelProperties;
    private final String[][] tileSet;

    public LevelData(LevelProperties levelProperties, String[][] tileSet) {
        this.levelProperties = levelProperties;
        this.tileSet = tileSet;
    }

    public LevelProperties getLevelProperties() {
        return levelProperties;
    }

    public String[][] getTileSet() {
        return tileSet;
    }
}
