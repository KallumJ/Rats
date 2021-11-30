package players.scores;

import io.XMLNode;
import players.PlayerProfileManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
* The class Player
* @author Yin Man Cheung
*/
public class Player {
	private final String playerName;
	private int maxLevel;
	private final Map<Integer, Integer> highScores;
	
	/** 
	 * Player Constructor
	 */
	public Player(String name, int maxLevel){
		this.playerName = name;
		this.maxLevel = maxLevel;

		this.highScores = new HashMap<>();
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
		return highScores.get(levelId);
	}
	
	/** 
	 * Get the Player max level
	 * @return the Player max level
	 */
	public int getMaxLevel() {
		return maxLevel;
	}

	/**
	 * Returns the player, encoded as an XMLNode for file writing
	 * @return THe XMLNode for this player
	 */
	public XMLNode getAsXMLNode() {
		XMLNode playerName = new XMLNode("name", getPlayerName(), null, null);
		XMLNode maxLevel = new XMLNode("maxLevel", String.valueOf(getMaxLevel()), null, null);

		ArrayList<XMLNode> children = new ArrayList<>();
		children.add(playerName);
		children.add(maxLevel);
		highScores.forEach((levelId, score) -> {
			XMLNode highScore = new XMLNode("highScore", levelId + PlayerProfileManager.HIGH_SCORE_SEPARATOR + score, null, null);
			children.add(highScore);
		});

		return new XMLNode("player", null, null, children);
	}

	/**
	 * Adds a score to the high score map, if it is the highest for the given level
	 * @param levelId the level id
	 * @param score the score to add
	 */
	public void addScoreIfHighest(int levelId, int score) {
		// If score for this level is already recorded, check it's higher. If not, add the score
		if (highScores.containsKey(levelId)) {
			highScores.forEach((currentLevelId, currentScore) -> {
				if (currentLevelId == levelId) {
					if (score > currentScore) {
						highScores.put(levelId, score);
					}
				}
			});
		} else {
			highScores.put(levelId, score);
		}

		PlayerProfileManager.savePlayersFile();
	}

	/**
	 * A method to set the max level allowed for this player
	 * @param maxLevel the max level now allowed
	 */
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
		PlayerProfileManager.savePlayersFile();
	}

	public Map<Integer, Integer> getHighScores() {
		return highScores;
	}

	public static void main(String[] args) {
		for (Player player : PlayerProfileManager.getAllPlayers()) {
			Map<Integer, Integer> highScore = player.getHighScores();
			System.out.println(highScore);

		}
	}
}
