package players.scores;

import io.XMLElementNames;
import io.XMLNode;
import level.LevelUtils;
import players.PlayerProfileManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The class Player.
 *
 * @author Yin Man Cheung
 * @date 2022/02/15
 *
 */
public class Player {
	private final String playerName;
	private final Map<Integer, Integer> highScores;
	private int maxLevel;

	/**
	 * Player Constructor
	 *
	 * @param name     the name of the player
	 * @param maxLevel the max level unlocked by the player
	 */
	public Player(String name, int maxLevel) {
		this.playerName = name;
		this.maxLevel = maxLevel;

		this.highScores = new HashMap<>();
	}

	/**
	 * Gets the Player name
	 *
	 * @return the Player name
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Gets the Player score
	 *
	 * @return the Player score
	 */
	public int getPlayerHighScore(int levelId) {
		return highScores.get(levelId);
	}

	/**
	 * Get the Player max level
	 *
	 * @return the Player max level
	 */
	public int getMaxLevel() {
		return maxLevel;
	}

	/**
	 * A method to set the max level allowed for this player
	 *
	 * @param maxLevel the max level now allowed
	 */
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
		PlayerProfileManager.savePlayersFile();
	}

	/**
	 * Returns the player, encoded as an XMLNode for file writing
	 *
	 * @return THe XMLNode for this player
	 */
	public XMLNode getAsXMLNode() {
		XMLNode playerName =
				new XMLNode(XMLElementNames.PLAYER_NAME.toString(),
						getPlayerName(), null, null);
		XMLNode maxLevel =
				new XMLNode(XMLElementNames.PLAYER_MAX_LEVEL.toString(),
						String.valueOf(getMaxLevel()), null, null);

		ArrayList<XMLNode> children = new ArrayList<>();
		children.add(playerName);
		children.add(maxLevel);
		highScores.forEach((levelId, score) -> {
			XMLNode highScore =
					new XMLNode(XMLElementNames.HIGH_SCORE.toString(),
							levelId + PlayerProfileManager.HIGH_SCORE_SEPARATOR + score, null, null);
			children.add(highScore);
		});

		return new XMLNode(XMLElementNames.PLAYER.toString(), null, null,
				children);
	}

	/**
	 * Adds their score to the file, and increments the player's max level
	 *
	 * @param levelId the level id
	 * @param score   the score to add
	 */
	public void winGame(int levelId, int score) {
		// If score for this level is already recorded, check it's higher.
		// If not, add the score
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

		if (!LevelUtils.isIdForCustomLeveL(levelId)) {
			maxLevel += 1;
		}

		PlayerProfileManager.savePlayersFile();
	}

	public Map<Integer, Integer> getHighScores() {
		return highScores;
	}

}
