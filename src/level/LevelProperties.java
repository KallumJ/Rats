package level;

/**
 * A class to model the properties of a level
 *
 * @author Kallum Jones 2005855
 */
public class LevelProperties {
    private final int levelId;
    private final int levelHeight;
    private final int levelWidth;
    private final int populationToLose;
    private final int expectedTime;
    private final int itemInterval;
    private final int ratMinBabies;
    private final int ratMaxBabies;

    /**
     * Constructs a LevelProperties object with the provided data
     * @param id the id of the level
     * @param height the height of the level
     * @param width the width of the level
     * @param populationToLose the max population for a level before failure
     * @param expectedTime the expected time to complete the level in seconds
     * @param itemInterval the time in between item drops for this level in seconds
     * @param ratMinBabies the minimum number of babies a rat can birth in a level
     * @param ratMaxBabies the maximum number of babies a rat can birth in a level
     */
    public LevelProperties(int id, int height, int width, int populationToLose, int expectedTime, int itemInterval, int ratMinBabies, int ratMaxBabies) {
        this.levelId = id;
        this.levelHeight = height;
        this.levelWidth = width;
        this.populationToLose = populationToLose;
        this.expectedTime = expectedTime;
        this.itemInterval = itemInterval;
        this.ratMaxBabies = ratMaxBabies;
        this.ratMinBabies = ratMinBabies;
    }

    /**
     * A method to get the level id for this level
     * @return the level id for this level
     */
    public int getLevelId() {
        return levelId;
    }

    /**
     * A method to get the height for this level
     * @return height for this level
     */
    public int getLevelHeight() {
        return levelHeight;
    }

    /**
     * A method to get the width of the level
     * @return the width of the level
     */
    public int getLevelWidth() {
        return levelWidth;
    }

    /**
     * A method to get the fail population for the level
     * @return the fail population for the level
     */
    public int getPopulationToLose() {
        return populationToLose;
    }

    /**
     * A method to get the time allowed for this level
     * @return get the time allowed for this level
     */
    public int getExpectedTime() {
        return expectedTime;
    }

    /**
     * A method to get the item drop interval for this level
     * @return the item drop interval for this level
     */
    public int getItemInterval() {
        return itemInterval;
    }

    /**
     * A method to get the minimum number of babies a rat can birth in this level
     * @return the minimum number of babies a rat can birth in this level
     */
    public int getRatMinBabies() {
        return ratMinBabies;
    }

    /**
     * A method to get the minimum number of babies a rat can birth in this level
     * @return the minimum number of babies a rat can birth in this level
     */
    public int getRatMaxBabies() {
        return ratMaxBabies;
    }
}
