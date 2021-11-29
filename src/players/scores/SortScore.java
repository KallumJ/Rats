package players.scores;


import java.util.Comparator;

/**
 * The class Sort score implements comparator <Player>
 * @author Yin Man Cheung
 */ 
public class SortScore implements Comparator<Player> {

	/** 
	 * Compare players.scores
	 * @param score1  the score1
	 * @param score2  the score2
	 * @return int
	 */
	public int compare (Player score1, Player score2) {
		int s1 = score1.getPlayerScore();
		int s2 = score2.getPlayerScore();
		if (s1 < s2) { 
        	return +1;
        } else if (s1 > s2) {
            return -1;
        } else {
		return 0;
        }
	}
}
