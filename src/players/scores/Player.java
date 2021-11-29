package players.scores;

/**
* The class Player
* @author Yin Man Cheung
*/
public class Player {
	private String playerName;
	//private int playerCompletedTime;
	private int pointsOnEachRat = 10;
	private int playerScore;
	private int playerMaxLevel;
	
	/** 
	 * Player Constructor
	 * @param name  the player's name
	 * @param numberOfKills  the number of kills
	 * @param level  the level
	 */
	public Player(String name, int numberOfKills, int level){
		this.playerName = name;
		this.playerScore = numberOfKills * pointsOnEachRat;
		this.playerMaxLevel = level;
	}
	
	/** 
	 * Gets the Player name
	 * @return the Player name
	 */
	public String getPlayerName() {
		return playerName;
	}
	
	/** 
	 * Gets the Player score
	 * @return the Player score
	 */
	public int getPlayerScore() {
		return playerScore;
	}
	
	/** 
	 * Get the Player max level
	 * @return the Player max level
	 */
	public int getPlayerMaxLevel() {
		return playerMaxLevel;
	}	
}
