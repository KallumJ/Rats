package players.scores;

/**
* The class Player
* @author Yin Man Cheung
*/
public class Player {
	private final String playerName;
	
	/** 
	 * Player Constructor
	 * @param name  the player's name
	 */
	public Player(String name){
		this.playerName = name;
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
	public int getPlayerHighScore(int levelId) {
		return 0;
	}
	
	/** 
	 * Get the Player max level
	 * @return the Player max level
	 */
	public int getPlayerMaxLevel() {
		return 0;
	}	
}
