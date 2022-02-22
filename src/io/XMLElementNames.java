package io;

/**
 * An enum to store all the names of the elements in our XML file system
 *
 * @author Kallum Jones 2005855
 * @date 2022.02.12
 *
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
	LEVEL_SCORE("score"),
	TIME_OF_DAY("timeOfDay"),
	TIME_INTERVAL("timeInterval"),
	AIRSTRIKE_ENABLED("airstrikeEnabled"),
	COST_OF_AIRSTRIKE("costOfAirstrike"),
	NUM_OF_AIRSTRIKE_HITS("numOfAirstrikeHits"),
	ALLOWED_ITEMS("allowedItems"),


	TILE_SET("tileSet"),
	TILE_ROW("tileRow"),
	TILE("tile"),

	PLAYERS_ROOT("players"),

	PLAYER("player"),
	PLAYER_NAME("name"),
	PLAYER_MAX_LEVEL("maxLevel"),
	HIGH_SCORE("highScore"),
	TIME_ELAPSED("timeElapsed"),

	INVENTORY("inventory"),
	ITEM("item");

	private final String name;

	/**
	 * Constructs an XMLElementName
	 *
	 * @param nodeName the name of the node
	 */
	XMLElementNames(String nodeName) {
		this.name = nodeName;
	}

	/**
	 * Returns the name of the node
	 *
	 * @return the name of the node
	 */
	@Override
	public String toString() {
		return name;
	}
}
