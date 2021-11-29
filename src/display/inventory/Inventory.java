package display.inventory;

import display.Board;
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

/**
 *
 * A class to display and control the inventory for the game
 *
 * @author YIMING LI
 */
public class Inventory {
    public static final int INVENTORY_WIDTH = 300; // in pixels
    private final ArrayList<GameObjectType> itemsInInventory;
    private final LevelData levelData;
    private VBox inventoryNode;
    private final Set<ItemRow> itemRows;

    /**
     * Constructs an inventory object
     * @param levelData The LevelData of the level associated with this inventory
     */
    public Inventory(LevelData levelData) {
        this.itemsInInventory = new ArrayList<>();
        this.itemRows = new HashSet<>();

        this.levelData = levelData;

        // Add an item to the inventory after every interval
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(levelData.getLevelProperties().getItemInterval()), event -> addItem()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * A method to return a random GameObjectType
     * @return a randomly selected GameObjectType
     */
    private static GameObjectType makeRandomObjectSelection() {
        Random random = new Random();
        int randomItemSelection = random.nextInt(GameObjectType.values().length);

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
            default:
                throw new IllegalArgumentException("An invalid random item selection was made. Selection " + randomItemSelection + " is not assigned an item");
        }
    }

    /**
     * A method to get the item rows in the inventory
     * @return the item rows in the inventory
     */
    public Set<ItemRow> getItemRows() {
        return itemRows;
    }

    /**
     * A method to add a random item to the inventory
     */
    public void addItem() {
        if (isSlotOpen()) {
            GameObjectType selectedObject = makeRandomObjectSelection();

            itemsInInventory.add(selectedObject);

            // If a row for this item already exists, find it
            ItemRow itemRow = null;
            for (ItemRow row : itemRows) {
                if (row.getObjectType() == selectedObject) {
                    itemRow = row;
                }
            }

            // If a row doesn't already exist, create one
            if (itemRow == null) {
                itemRow = new ItemRow(selectedObject, this);
                inventoryNode.getChildren().add(itemRow.gethBox());
                itemRows.add(itemRow);
            }

            // Increment the object count on this row
            itemRow.incrementObjectCount();
        }
    }

    /**
     * A method to return whether there is still an available slot in the inventory
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

    public VBox buildInventoryGUI() {
        VBox inventoryContainer = new VBox();
        inventoryContainer.setPadding(new Insets(10, 10, 10, 10));
        inventoryContainer.setStyle("-fx-background-image: url(file:resources/inventorySkin.png);");
        inventoryContainer.setMinWidth(INVENTORY_WIDTH);

        inventoryNode = inventoryContainer;

        // Start game Inventory with 1 item in it
        addItem();

        return inventoryNode;
    }

    /**
     * A method to get the level data associated with this inventory
     * @return the level data associated with this inventory
     */
    public LevelData getLevelData() {
        return levelData;
    }

    /**
     * A method to get the items currently stored in the inventory
     * @return the items currently stored in the inventory
     */
    public ArrayList<GameObjectType> getItemsInInventory() {
        return itemsInInventory;
    }

    /**
     * A method to get the JavaFX node representing the inventory
     * @return the JavaFX node representing the inventory
     */
    public VBox getInventoryNode() {
        return inventoryNode;
    }
}
    

