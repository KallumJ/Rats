package display.inventory;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import level.LevelData;

/**
 * A class to model an Inventory used in actual ongoing games
 */
public class GameInventory extends Inventory {
    /**
     * Constructs a GameInventory object.
     *
     * @param levelData the LevelData of the level associated to this inventory
     */
    public GameInventory(LevelData levelData) {
        super(levelData);

        // Add an item to the inventory after every interval
        int itemInterval = levelData.getLevelProperties().getItemInterval();
        Duration intvlDur = Duration.seconds(itemInterval);
        KeyFrame keyframe = new KeyFrame(intvlDur, event -> addRandomItem());
        Timeline timeline = new Timeline(keyframe);

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
