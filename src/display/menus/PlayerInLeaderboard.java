package display.menus;

import java.util.Comparator;

/**
 * This class collect player information needed for leaderboard
 *
 * @author YIMING LI
 */
class PlayerInLeaderboard {
	private final String name;
	private final Integer level;
	private final Integer score;

	public PlayerInLeaderboard(String name, Integer level, Integer score) {
		this.name = name;
		this.level = level;
		this.score = score;
	}

	/**
	 * Get the player name.
	 *
	 * @return The player name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the level of map of this player.
	 *
	 * @return The level of map.
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * Get the score of level of this player.
	 *
	 * @return The score of level.
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * The class Sort the list by scores.
	 *
	 * @author YIMING LI
	 */
	static class SortPlayerByScore implements Comparator<PlayerInLeaderboard> {
		/**
		 * Sort in descending order.
		 */
		@Override
		public int compare(PlayerInLeaderboard a, PlayerInLeaderboard b) {
			return b.getScore() - a.getScore();
		}
	}
}
