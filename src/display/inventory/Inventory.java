package display.inventory;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import level.LevelData;
import objects.GameObjectType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.List;

/**
 *
 * A class to display and control the inventory for the game
 *
 * @author YIMING LI
 */
public class Inventory {

    public static final int INVENTORY_WIDTH = 300; // in pixels
    private static final String INVALID_RANDOM_ITEM =
            "An invalid random item selection was made. " +
                    "Selection %d is not assigned an item";
    private final List<GameObjectType> itemsInInventory;
    private final LevelData levelData;
    private final Set<ItemRow> itemRows;
    private VBox inventoryNode;

    /**
     * Constructs an inventory object
     *
     * @param levelData The LevelData of the level associated with this
     * inventory
     */
    public Inventory(LevelData levelData) {
        // Set up the JavaFX node for the inventory
        this.inventoryNode = new VBox();
        inventoryNode.setPadding(
                new Insets(10, 10, 10, 10)
        );
        inventoryNode.setStyle(
                "-fx-background-image: url(file:resources/inventorySkin.png);"
        );
        inventoryNode.setMinWidth(INVENTORY_WIDTH);

        // Create a set of rows
        this.itemRows = new HashSet<>();

        // Create an item list
        this.itemsInInventory = new ArrayList<>();

        // if the inventory is already preexisting, load it
        if (levelData.hasInventory()) {
            for (GameObjectType gameObjectType : levelData.getInventory()) {
                addItem(gameObjectType);
            }
        }

        this.levelData = levelData;

        // Add an item to the inventory after every interval
        int itemInterval = levelData.getLevelProperties().getItemInterval();
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(itemInterval),
                        event -> addRandomItem()
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * A method to get the item rows in the inventory
     *
     * @return the item rows in the inventory
     */
    public Set<ItemRow> getItemRows() {
        return itemRows;
    }

    /**
     * A method to add a random item to the inventory
     */
    public void addRandomItem() {
        GameObjectType selectedObject = makeRandomObjectSelection();
        addItem(selectedObject);
    }

    /**
     * A method to get the level data associated with this inventory
     *
     * @return the level data associated with this inventory
     */
    public LevelData getLevelData() {
        return levelData;
    }

    public VBox buildInventoryGUI() {
        // Start game Inventory with 1 item in it
        if (itemsInInventory.isEmpty()) {
            addRandomItem();
        }
        return inventoryNode;
    }

    /**
     * A method to get the items currently stored in the inventory
     *
     * @return the items currently stored in the inventory
     */
    public List<GameObjectType> getItemsInInventory() {
        return itemsInInventory;
    }

    /**
     * A method to get the JavaFX node representing the inventory
     *
     * @return the JavaFX node representing the inventory
     */
    public VBox getInventoryNode() {
        return inventoryNode;
    }

    /**
     * A method to return a random GameObjectType
     *
     * @return a randomly selected GameObjectType
     */
    private static GameObjectType makeRandomObjectSelection() {
        Random random = new Random();
        int randomItemSelection =
                random.nextInt(GameObjectType.values().length);

        switch (randomItemSelection) {
            case 0:
                return GameObjectType.BOMB;
            case 1:
                return GameObjectType.FEMALE_SEX_CHANGER;
            case 2:
                return GameObjectType.MALE_SEX_CHANGER;
            case 3:
                return GameObjectType.NO_ENTRY_SIGN;
            case 4:
                return GameObjectType.POISON;
            case 5:
                return GameObjectType.STERILISATION;
            case 6:
                return GameObjectType.DEATH_RAT;
            case 7:
                return GameObjectType.GAS;
            default:
                throw new IllegalArgumentException(
                        String.format(INVALID_RANDOM_ITEM, randomItemSelection)
                );
        }
    }

    /**
     * A method to add a item to the inventory
     * @param object the object type to add to the inventory
     */
    private void addItem(GameObjectType object) {
        if (isSlotOpen()) {
            itemsInInventory.add(object);

            // If a row for this item already exists, find it
            ItemRow itemRow = null;
            for (ItemRow row : itemRows) {
                if (row.getObjectType() == object) {
                    itemRow = row;
                }
            }

            // If a row doesn't already exist, create one
            if (itemRow == null) {
                itemRow = new ItemRow(object, this);
                inventoryNode.getChildren().add(itemRow.gethBox());
                itemRows.add(itemRow);
            }

            // Increment the object count on this row
            itemRow.incrementObjectCount();
        }
    }

    /**
     * A method to return whether there is still an available slot in the
     * inventory
     *
     * @return true if there is a slot open, false otherwise
     */
    private boolean isSlotOpen() {
        // Return true if there is a row currently with an empty slot
        for (ItemRow itemRow : itemRows) {
            if (itemRow.getObjectCount() < ItemRow.MAX_ITEMS_IN_ROW) {
                return true;
            }
        }

        // Return true if the number of rows is less than the number of objects
        // Else return false
        return itemRows.size() < GameObjectType.values().length;
    }
}
