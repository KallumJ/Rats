package scores;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
* The class Leader board
* @author Yin Man Cheung
*/
public class LeaderBoard {
	private ArrayList<Player> playerScore;
	private static final String FILE_LVL_1 = "C:/Users/chrys/OneDrive/Semster 1/CS-270/Github A2/Rats/src/scores/LeaderBoard/LeaderBoard.txt";
	
	ObjectOutputStream outo = null;
	ObjectInputStream ino = null;
	
	// contains the ArrayList of high scores
	public LeaderBoard() {
		playerScore = new ArrayList<Player>();
	}
	
	/** 
	 * Gets the player score, then sort into Top 10.
	 * @return the player score
	 */
	public ArrayList<Player> getPlayerScore() {
		loadPlayerScore();
		sortScores();
		return playerScore;
	}
		
	/** 
	 * Load player score
	 */
	public void loadPlayerScore() {
		try {
			String fileToRead = FILE_LVL_1;
			ino = new ObjectInputStream(new FileInputStream(fileToRead));
			playerScore = (ArrayList<Player>) ino.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (outo != null) {
					outo.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/** 
	 * Update player score
	 */
	public void updatePlayerScore() {
		try {
			String fileToRead = FILE_LVL_1;
			outo = new ObjectOutputStream(new FileOutputStream(fileToRead));
			outo.writeObject(playerScore);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (outo != null) {
					outo.close();
				}
			}  catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
		
	/** 
	 * Sort player scores using the comparator
	 */ 
	public void sortScores() {
		SortScore comparator = new SortScore();
		Collections.sort(playerScore, comparator);
	}
	
	/** 
	 * Add player score to list
	 * @param name  the name
	 * @param score  the score
	 * @param maxlvl  the maxlvl
	 */
	public void addPlayerScore(String name, int score, int maxlvl) {
		loadPlayerScore();
		playerScore.add(new Player(name, score, maxlvl));
		updatePlayerScore();
	}
	
	/** 
	 * Gets the high score string
	 * @return the high score string
	 */
	public String getTopTenString() {
        String topTenString = "";
        int topTen = 10;

        ArrayList<Player> playerScore;
        playerScore = getPlayerScore();

        int i = 0;
        int x = playerScore.size();
        if (x > topTen) {
            x = topTen;
        }
        while (i < x) {
            topTenString += (i + 1) + ".\t" + playerScore.get(i).getPlayerName() 
            	+ "\t\t" + playerScore.get(i).getPlayerScore() + "\t\t" + playerScore.get(i).getPlayerMaxLevel() +"\n";
            i++;
        }
        return topTenString;
	}
	
}