package level;

public class LevelProperties {
    private final int levelId;
    private final int levelHeight;
    private final int levelWidth;
    private final int failPopulation;
    private final int timeAllowed;
    private final int itemDropInterval;

    public LevelProperties(int id, int height, int width, int failPopulation, int timeAllowed, int itemDropInterval) {
        this.levelId = id;
        this.levelHeight = height;
        this.levelWidth = width;
        this.failPopulation =failPopulation;
        this.timeAllowed = timeAllowed;
        this.itemDropInterval = itemDropInterval;
    }

    public int getLevelId() {
        return levelId;
    }

    public int getLevelHeight() {
        return levelHeight;
    }

    public int getLevelWidth() {
        return levelWidth;
    }

    public int getFailPopulation() {
        return failPopulation;
    }

    public int getTimeAllowed() {
        return timeAllowed;
    }

    public int getItemDropInterval() {
        return itemDropInterval;
    }
}
