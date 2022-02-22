package display.menus;

import java.util.Comparator;

/**
 * Collects player information needed for leaderboard.
 *
 * @author YIMING LI
 * @date 2022/02/16
 *
 */
class PlayerInLeaderboard {
	private final String name;
	private final String level;
	private final Integer score;

	/**
	 * Constructs a {@code PlayerInLeaderboard} object.
	 *  @param name    the name of the player
	 * @param level the level the score was achieved on
     * @param score the score reached
     */
	public PlayerInLeaderboard(String name, String level, Integer score) {
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
	public String getLevel() {
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
