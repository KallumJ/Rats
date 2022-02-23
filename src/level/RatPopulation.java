/**
 * A class to calculate the current total population and population of male and
 * female rats
 *
 * @author Samhitha Pinisetti (2035106)
 */
package level;

import objects.GameObject;
import objects.rats.PeacefulRat;

import java.util.ArrayList;
import java.util.List;

/**
 * Constructs a RatPopulation object.
 *
 * @author Samhitha Pinisetti 2035196
 * @date 2022.02.21
 *
 */
public class RatPopulation {

    private final LevelData levelData;
    private int numberOfMaleRats;
    private int numberOfFemaleRats;
    private int totalPopulation;

    public RatPopulation(LevelData levelData) {
        this.levelData = levelData;
    }

    /**
     * A method to calculate the population for the level.
     */
    public void calculatePopulation() {
        List<GameObject> objects = levelData.getObjects();


        List<PeacefulRat> femaleList = new ArrayList<>();
        List<PeacefulRat> maleList = new ArrayList<>();

        for (GameObject object : objects) {
            if (object instanceof PeacefulRat) {
                PeacefulRat rat = (PeacefulRat) object;
                if (rat.getGender().equalsIgnoreCase("f")) {
                    femaleList.add(rat);
                } else {
                    maleList.add(rat);
                }
            }
        }

        numberOfFemaleRats = femaleList.size();
        numberOfMaleRats = maleList.size();
        totalPopulation = numberOfMaleRats + numberOfFemaleRats;
    }

    /**
     * A method to get the total population for the level.
     *
     * @return the totalPopulation for the level.
     */
    public int getTotalPopulation() {
        calculatePopulation();
        return totalPopulation;
    }

    /**
     * A method to get the population of female rats for the level.
     *
     * @return the numberOfFemaleRats for the level.
     */
    public int femalePopulation() {
        calculatePopulation();
        return numberOfFemaleRats;
    }

    /**
     * A method to get the population of male rats for the level.
     *
     * @return the numberOfMaleRats for the level.
     */
    public int malePopulation() {
        calculatePopulation();
        return numberOfMaleRats;
    }

}
