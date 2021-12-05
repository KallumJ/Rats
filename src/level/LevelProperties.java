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
	private final int adultRatSpeed;
	private final int babyRatSpeed;
	private final int deathRatSpeed;
	private int score;
	private int timeElapsed;

	/**
	 * Constructs a LevelProperties object with the provided data
	 *
	 * @param id               the id of the level
	 * @param height           the height of the level
	 * @param width            the width of the level
	 * @param populationToLose the max population for a level before failure
	 * @param expectedTime     the expected time to complete the level in
	 *                         seconds
	 * @param itemInterval     the time in between item drops for this level in
	 *                         seconds
	 * @param ratMinBabies     the minimum number of babies a rat can birth
	 *                            in a
	 *                         level
	 * @param ratMaxBabies     the maximum number of babies a rat can birth
	 *                            in a
	 *                         level
	 * @param adultRatSpeed    the speed of an adult rat, in time between
	 *                         movement, in milliseconds
	 * @param babyRatSpeed     the speed of a baby rat, in time between
	 *                         movement, in milliseconds
	 * @param deathRatSpeed    the speed of a death rat, in time between
	 *                         movement, in milliseconds
	 */
	public LevelProperties(int id, int height, int width, int populationToLose
			, int expectedTime, int itemInterval, int ratMinBabies,
						   int ratMaxBabies, int adultRatSpeed,
						   int babyRatSpeed, int deathRatSpeed,
						   int timeElapsed, int score) {
		this.levelId = id;
		this.levelHeight = height;
		this.levelWidth = width;
		this.populationToLose = populationToLose;
		this.expectedTime = expectedTime;
		this.itemInterval = itemInterval;
		this.ratMaxBabies = ratMaxBabies;
		this.ratMinBabies = ratMinBabies;
		this.adultRatSpeed = adultRatSpeed;
		this.babyRatSpeed = babyRatSpeed;
		this.deathRatSpeed = deathRatSpeed;
		this.timeElapsed = timeElapsed;
		this.score = score;
	}

	/**
	 * A method to get the level id for this level
	 *
	 * @return the level id for this level
	 */
	public int getLevelId() {
		return levelId;
	}

	/**
	 * A method to get the height for this level
	 *
	 * @return height for this level
	 */
	public int getLevelHeight() {
		return levelHeight;
	}

	/**
	 * A method to get the width of the level
	 *
	 * @return the width of the level
	 */
	public int getLevelWidth() {
		return levelWidth;
	}

	/**
	 * A method to get the fail population for the level
	 *
	 * @return the fail population for the level
	 */
	public int getPopulationToLose() {
		return populationToLose;
	}

	/**
	 * A method to get the time allowed for this level
	 *
	 * @return get the time allowed for this level
	 */
	public int getExpectedTime() {
		return expectedTime;
	}

	/**
	 * A method to get the item drop interval for this level
	 *
	 * @return the item drop interval for this level
	 */
	public int getItemInterval() {
		return itemInterval;
	}

	/**
	 * A method to get the minimum number of babies a rat can birth in this
	 * level
	 *
	 * @return the minimum number of babies a rat can birth in this level
	 */
	public int getRatMinBabies() {
		return ratMinBabies;
	}

	/**
	 * A method to get the minimum number of babies a rat can birth in this
	 * level
	 *
	 * @return the minimum number of babies a rat can birth in this level
	 */
	public int getRatMaxBabies() {
		return ratMaxBabies;
	}

	/**
	 * A method to get the speed of adult rats in this level
	 *
	 * @return the speed of adult rats in this level in milliseconds between
	 * movements
	 */
	public int getAdultRatSpeed() {
		return adultRatSpeed;
	}

	/**
	 * A method to get the speed of baby rats in this level
	 *
	 * @return the speed of baby rats in this level in milliseconds between
	 * movements
	 */
	public int getBabyRatSpeed() {
		return babyRatSpeed;
	}

	/**
	 * A method to get the speed of death rats in this level
	 *
	 * @return the speed of death rats in this level in milliseconds between
	 * movements
	 */
	public int getDeathRatSpeed() {
		return deathRatSpeed;
	}

	/**
	 * A method to get the time elapsed for this level
	 *
	 * @return the time elapsed for this level, in seconds
	 */
	public int getTimeElapsed() {
		return timeElapsed;
	}

	/**
	 * A method to set the time elapsed for this level
	 *
	 * @param timeElapsed the time elapsed for this level, in seconds
	 */
	public void setTimeElapsed(int timeElapsed) {
		this.timeElapsed = timeElapsed;
	}

	/**
	 * Gets the current score of the level
	 *
	 * @return the score of the level
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Sets the score for the level
	 *
	 * @param score the score for the level
	 */
	public void setScore(int score) {
		this.score = score;
	}
}
