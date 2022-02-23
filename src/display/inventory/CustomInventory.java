package display.inventory;

import level.LevelData;
import objects.GameObjectType;

/**
 * A class to an inventory for creating custom levels.
 */
public class CustomInventory extends Inventory {
    private static final int INVENTORY_WIDTH = 75;

    /**
     * Constructs a CustomInventory object.
     *
     * @param levelData the LevelData of the level associated to this inventory.
     */
    public CustomInventory(LevelData levelData) {
        super(levelData);

        // Add one of each object to the inventory
        for (GameObjectType object : GameObjectType.values()) {
            addItem(object);
        }
    }

    /**
     * Returns whether there is still an available slot in the inventory.
     *
     * @return true if there is a slot open, false otherwise.
     */
    @Override
    protected boolean isSlotOpen() {
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

    /**
     * Get the width of the inventory.
     *
     * @return the width of the inventory, in pixels.
     */
    @Override
    public double getInventoryWidth() {
        return INVENTORY_WIDTH;
    }
}
