/**
 * A class to calculate the current total population and population of male and female rats
 * 
 * @author Samhitha Pinisetti (2035106)
 *
 */
package level;

import java.util.ArrayList;
import java.util.List;
import objects.GameObject;
import objects.rats.PeacefulRat;

/**
 * Constructs a RatPopulation object
 * @param numberOfMaleRats The total number of male rats in the level 
 * @param numberOfFemaleRats The total number of female rats in the level
 * @param totalPopulation The total population of all rats in the level 
 */
public class RatPopulation {
	
	private LevelData levelData;
	int numberOfMaleRats;
	int numberOfFemaleRats;
	int totalPopulation;
	
	public RatPopulation(LevelData levelData) {
		this.levelData = levelData;
	}
	
	/**
     * A method to calculate the population for the level
     *
     */
	public void calculatePopulation() {
		List<GameObject> objects = levelData.getObjects();
		
		  for (int i = 0; i < objects.size(); i++) {
			  if (objects instanceof PeacefulRat) {
				  	PeacefulRat rat = (PeacefulRat) objects;
				  	if (rat.getGender().equalsIgnoreCase("f")) {
				  		List<PeacefulRat> femaleList = new ArrayList<PeacefulRat>();
				  		femaleList.add(rat);
				  		numberOfFemaleRats = femaleList.size();
				  	} else {
				  		List<PeacefulRat> maleList = new ArrayList<PeacefulRat>();
				  		maleList.add(rat);
				  		numberOfMaleRats = maleList.size();
				  	}
				  	
				  	int totalPopulation = numberOfMaleRats + numberOfFemaleRats;
			  }
		}
	}
	
	
	/**
     * A method to get the total population for the level
     * @return the totalPopulation for the level
     */
	public int getTotalPopulation() {
		return totalPopulation;
	}
	
	/**
     * A method to get the population of female rats for the level
     * @return the numberOfFemaleRats for the level
     */	
	public int femalePopulation() {
		return numberOfFemaleRats;
	}
	
	/**
     * A method to get the population of male rats for the level
     * @return the numberOfMaleRats for the level
     */	
	public int malePopulation() {
		return numberOfMaleRats;
	}
	
}




















