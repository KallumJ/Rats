package io;

/**
 * An enum to store all the names of the elements in our XML file system
 *
 * @author Kallum Jones 2005855
 */
public enum XMLElementNames {
    LEVEL_ROOT("level"),

    LEVEL_PROPERTIES("levelProperties"),
    LEVEL_HEIGHT("levelHeight"),
    LEVEL_WIDTH("levelWidth"),
    POPULATION_TO_LOSE("populationToLose"),
    EXPECTED_TIME("expectedTime"),
    LEVEL_ID("id"),
    ITEM_INTERVAL("itemInterval"),
    RAT_MIN_BABIES("ratMinBabies"),
    RAT_MAX_BABIES("ratMaxBabies"),
    ADULT_RAT_SPEED("adultRatSpeed"),
    BABY_RAT_SPEED("babyRatSpeed"),
    DEATH_RAT_SPEED("deathRatSpeed"),

    TILE_SET("tileSet"),
    TILE_ROW("tileRow"),
    TILE("tile"),

    PLAYERS_ROOT("players"),

    PLAYER("player"),
    PLAYER_NAME("name"),
    PLAYER_MAX_LEVEL("maxLevel"),
    HIGH_SCORE("highScore");

    private final String name;

    /**
     * Constructs an XMLElementName
     * @param nodeName the name of the node
     */
    XMLElementNames(String nodeName) {
        this.name = nodeName;
    }

    /**
     * Returns the name of the node
     * @return the name of the node
     */
    @Override
    public String toString() {
        return name;
    }
}
