package level;

import envrionment.TimeOfDay;
import objects.GameObjectType;

import java.util.HashSet;

/**
 * A class to model the properties of a level.
 *
 * @author Kallum Jones 2005855
 * @date 2022.02.12
 *
 */
public class LevelProperties {
	private String levelId;
	private int levelHeight;
	private int levelWidth;
	private int populationToLose;
	private int expectedTime;
	private int itemInterval;
	private int ratMinBabies;
	private int ratMaxBabies;
	private int adultRatSpeed;
	private int babyRatSpeed;
	private int deathRatSpeed;
	private int score;
	private int timeElapsed;
	private TimeOfDay timeOfDay;
	private int timeInterval;
	private boolean airstrikeEnabled;
	private int costOfAirstrike;
	private int numOfAirstrikeHits;
	private HashSet<GameObjectType> allowedItems;

	/**
	 * Constructs a LevelProperties object with the provided data.
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
	 *                         in a level
	 * @param ratMaxBabies     the maximum number of babies a rat can birth
	 *                         in a level
	 * @param adultRatSpeed    the speed of an adult rat, in time between
	 *                         movement, in milliseconds
	 * @param babyRatSpeed     the speed of a baby rat, in time between
	 *                         movement, in milliseconds
	 * @param deathRatSpeed    the speed of a death rat, in time between
	 *                         movement, in milliseconds
	 * @param timeOfDay		   the time of day allowed in this level
	 * @param timeInterval	   the time between changes in time of day,
	 *                         in seconds
	 * @param airstrikeEnabled whether or not airstrikes are enabled
	 * @param costOfAirstrike  the price of calling an airstrike, in score
	 * @param numOfAirstrikeHits the number of target tiles an airstrike
	 *                           will hit
	 * @param allowedItems 		a list of allowed items in the inventory for this level
	 * @param score 			the current score of the level
	 * @param timeElapsed  		the amount of time that has elapsed so far in this level in seconds
	 */
	public LevelProperties(String id, int height, int width, int populationToLose
			, int expectedTime, int itemInterval, int ratMinBabies,
						   int ratMaxBabies, int adultRatSpeed,
						   int babyRatSpeed, int deathRatSpeed,
						   int timeElapsed, int score,
						   TimeOfDay timeOfDay, int timeInterval,
						   boolean airstrikeEnabled, int costOfAirstrike,
						   int numOfAirstrikeHits, HashSet<GameObjectType> allowedItems) {
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
		this.timeOfDay = timeOfDay;
		this.timeInterval = timeInterval;
		this.airstrikeEnabled = airstrikeEnabled;
		this.costOfAirstrike = costOfAirstrike;
		this.numOfAirstrikeHits = numOfAirstrikeHits;
		this.allowedItems = allowedItems;
	}

	/**
	 * Constructs a blank LevelProperties object
	 */
	public LevelProperties() {
		this.allowedItems = new HashSet<>();
	}

	/**
	 * Sets the level height
	 * @param levelHeight the height of the level
	 */
	public void setLevelHeight(int levelHeight) {
		this.levelHeight = levelHeight;
	}

	/**
	 * Sets the level width
	 * @param levelWidth the width of the level
	 */
	public void setLevelWidth(int levelWidth) {
		this.levelWidth = levelWidth;
	}

	/**
	 * A method to get the level id for this level.
	 *
	 * @return the level id for this level
	 */
	public String getLevelId() {
		return levelId;
	}

	/**
	 * A method to get the height for this level.
	 *
	 * @return height for this level
	 */
	public int getLevelHeight() {
		return levelHeight;
	}

	/**
	 * A method to get the width of the level.
	 *
	 * @return the width of the level
	 */
	public int getLevelWidth() {
		return levelWidth;
	}

	/**
	 * A method to get the fail population for the level.
	 *
	 * @return the fail population for the level
	 */
	public int getPopulationToLose() {
		return populationToLose;
	}

	/**
	 * A method to get the time allowed for this level.
	 *
	 * @return get the time allowed for this level
	 */
	public int getExpectedTime() {
		return expectedTime;
	}

	/**
	 * A method to get the item drop interval for this level.
	 *
	 * @return the item drop interval for this level
	 */
	public int getItemInterval() {
		return itemInterval;
	}

	/**
	 * A method to get the minimum number of babies a rat can birth in this
	 * level.
	 *
	 * @return the minimum number of babies a rat can birth in this level
	 */
	public int getRatMinBabies() {
		return ratMinBabies;
	}

	/**
	 * A method to get the minimum number of babies a rat can birth in this
	 * level.
	 *
	 * @return the minimum number of babies a rat can birth in this level
	 */
	public int getRatMaxBabies() {
		return ratMaxBabies;
	}

	/**
	 * A method to get the speed of adult rats in this level.
	 *
	 * @return the speed of adult rats in this level in milliseconds between
	 * movements
	 */
	public int getAdultRatSpeed() {
		return adultRatSpeed;
	}

	/**
	 * A method to get the speed of baby rats in this level.
	 *
	 * @return the speed of baby rats in this level in milliseconds between
	 * movements
	 */
	public int getBabyRatSpeed() {
		return babyRatSpeed;
	}

	/**
	 * A method to get the speed of death rats in this level.
	 *
	 * @return the speed of death rats in this level in milliseconds between
	 * movements
	 */
	public int getDeathRatSpeed() {
		return deathRatSpeed;
	}

	/**
	 * A method to get the time elapsed for this level.
	 *
	 * @return the time elapsed for this level, in seconds
	 */
	public int getTimeElapsed() {
		return timeElapsed;
	}

	/**
	 * A method to set the time elapsed for this level.
	 *
	 * @param timeElapsed the time elapsed for this level, in seconds
	 */
	public void setTimeElapsed(int timeElapsed) {
		this.timeElapsed = timeElapsed;
	}

	/**
	 * Gets the current score of the level.
	 *
	 * @return the score of the level
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Sets the score for the level.
	 *
	 * @param score the score for the level
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Gets the time of day allowed in this level
	 * @return the time of day allowed in this level
	 */
	public TimeOfDay getTimeOfDay() {
		return timeOfDay;
	}

	/**
	 * Gets the time between changes in times of day
	 * @return the time between changes in times of day in seconds
	 */
	public int getTimeInterval() {
		return timeInterval;
	}

	/**
	 * Gets whether airstrikes are enabled in this level
	 * @return whether airstrikes are enabled in this level
	 * 		   true if they are, false otherwise
	 */
	public boolean isAirstrikeEnabled() {
		return airstrikeEnabled;
	}

	/**
	 * Gets the cost of a calling an airstrike
	 * @return the cost of a calling an airstrike in points
	 */
	public int getCostOfAirstrike() {
		return costOfAirstrike;
	}

	/**
	 * Gets the number of tiles an airstrike will hit
	 * @return the number of tiles an airstrike will hit
	 */
	public int getNumOfAirstrikeHits() {
		return numOfAirstrikeHits;
	}

	/**
	 * Sets the level id for this level
	 * @param levelId Sets the level id for this level
	 */
	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	/**
	 * Sets the population to lose
	 * @param populationToLose Sets the population to lose in total rats
	 */
	public void setPopulationToLose(int populationToLose) {
		this.populationToLose = populationToLose;
	}

	/**
	 * Sets the expected time to complete a level
	 * @param expectedTime the expected time to complete a level in seconds
	 */
	public void setExpectedTime(int expectedTime) {
		this.expectedTime = expectedTime;
	}

	/**
	 * Sets the time between item drops, in seconds
	 * @param itemInterval Sets the time between item drops, in seconds
	 */
	public void setItemInterval(int itemInterval) {
		this.itemInterval = itemInterval;
	}

	/**
	 * Sets the minimum number of rats a pregnant rat will birth
	 * @param ratMinBabies the minimum number of rats a pregnant rat will birth, in number of rats
	 */
	public void setRatMinBabies(int ratMinBabies) {
		this.ratMinBabies = ratMinBabies;
	}

	/**
	 * Sets the maximum number of rats a pregnant rat will birth
	 * @param ratMaxBabies the maximum number of rats a pregnant rat will birth, in number of rats
	 */
	public void setRatMaxBabies(int ratMaxBabies) {
		this.ratMaxBabies = ratMaxBabies;
	}

	/**
	 * Sets the speed of adult rats
	 * @param adultRatSpeed the speed of adult rats in milliseconds between movements
	 */
	public void setAdultRatSpeed(int adultRatSpeed) {
		this.adultRatSpeed = adultRatSpeed;
	}

	/**
	 * Sets the speed of baby rats
	 * @param babyRatSpeed the speed of baby rats in milliseconds between movements
	 */
	public void setBabyRatSpeed(int babyRatSpeed) {
		this.babyRatSpeed = babyRatSpeed;
	}

	/**
	 * Sets the speed of death rats
	 * @param deathRatSpeed the speed of death rats in milliseconds between movements
	 */
	public void setDeathRatSpeed(int deathRatSpeed) {
		this.deathRatSpeed = deathRatSpeed;
	}

	/**
	 * Sets the time of day in this level
	 * @param timeOfDay the time of day in this level
	 */
	public void setTimeOfDay(TimeOfDay timeOfDay) {
		this.timeOfDay = timeOfDay;
	}

	/**
	 * Sets the time between changes in time
	 * @param timeInterval the time between changes in time, in seconds
	 */
	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}

	/**
	 * Sets whether airstrikes are enabled
	 * @param airstrikeEnabled whether airstrikes are enabled
	 */
	public void setAirstrikeEnabled(boolean airstrikeEnabled) {
		this.airstrikeEnabled = airstrikeEnabled;
	}

	/**
	 * Sets the cost of an airstrike
	 * @param costOfAirstrike the cost of an airstrike, in total points
	 */
	public void setCostOfAirstrike(int costOfAirstrike) {
		this.costOfAirstrike = costOfAirstrike;
	}

	/**
	 * Sets the number of airstrike hits
	 * @param numOfAirstrikeHits the number of airstrike hits in total tiles
	 */
	public void setNumOfAirstrikeHits(int numOfAirstrikeHits) {
		this.numOfAirstrikeHits = numOfAirstrikeHits;
	}

	/**
	 * Sets the set of allowed items
	 * @param allowedItems the set of allowed items
	 */
	public void setAllowedItems(HashSet<GameObjectType> allowedItems) {
		this.allowedItems = allowedItems;
	}

	/**
	 * Gets the set of allowed items
	 * @return the set of allowed items
	 */
	public HashSet<GameObjectType> getAllowedItems() {
		return allowedItems;
	}
}
