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
    private final int failPopulation;
    private final int timeAllowed;
    private final int itemDropInterval;
    private final int ratMinBabies;
    private final int ratMaxBabies;

    /**
     * Constructs a LevelProperties object with the provided data
     * @param id the id of the level
     * @param height the height of the level
     * @param width the width of the level
     * @param failPopulation the max population for a level before failure
     * @param timeAllowed the max time allowed for a level in seconds
     * @param itemDropInterval the time in between item drops for this level in seconds
     * @param ratMinBabies the minimum number of babies a rat can birth in a level
     * @param ratMaxBabies the maximum number of babies a rat can birth in a level
     */
    public LevelProperties(int id, int height, int width, int failPopulation, int timeAllowed, int itemDropInterval, int ratMinBabies, int ratMaxBabies) {
        this.levelId = id;
        this.levelHeight = height;
        this.levelWidth = width;
        this.failPopulation =failPopulation;
        this.timeAllowed = timeAllowed;
        this.itemDropInterval = itemDropInterval;
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
    public int getFailPopulation() {
        return failPopulation;
    }

    /**
     * A method to get the time allowed for this level
     * @return get the time allowed for this level
     */
    public int getTimeAllowed() {
        return timeAllowed;
    }

    /**
     * A method to get the item drop interval for this level
     * @return the item drop interval for this level
     */
    public int getItemDropInterval() {
        return itemDropInterval;
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
