package display.inventory;

import display.Board;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import level.LevelData;
import objects.GameObject;
import objects.GameObjectType;
import objects.ObjectUtils;
import sfx.SFXManager;
import tile.Tile;
import tile.TileType;

/**
 * A class to model a row of items in the inventory
 */
public class ItemRow {

    public static final int MAX_ITEMS_IN_ROW = 5;
    private final GameObjectType objectType;
    private final HBox hBox;
    private int objectCount;
    private final Inventory inventory;
    private int originalX = -1;
    private int originalY = -1;

    /**
     * Constructs an item row for the provided object type
     *
     * @param objectType The type of items in this row
     * @param inventory the inventory this row is being stored in
     */
    public ItemRow(GameObjectType objectType, Inventory inventory) {
        this.objectType = objectType;
        this.hBox = new HBox();
        this.objectCount = 0;
        this.inventory = inventory;
    }

    /**
     * A method to get the type of object being stored in this row
     *
     * @return the type of object being stored in this row
     */
    public GameObjectType getObjectType() {
        return objectType;
    }

    /**
     * A method to get the HBox node representing this row
     *
     * @return the HBox node representing this row
     */
    public HBox gethBox() {
        return hBox;
    }

    /**
     * Increment the number of objects being stored in this row
     */
    public void incrementObjectCount() {
        // If this row isn't at its maximum items
        if (objectCount < MAX_ITEMS_IN_ROW) {
            objectCount++;

            // Create an image of the item, and add drag and release event handlers
            ImageView imageOfItem =
                    new ImageView(ObjectUtils.getObjectImageUrl(objectType));

            imageOfItem.setOnMouseDragged(event ->
                    onDrag(event, imageOfItem));
            imageOfItem.setOnMouseReleased(event ->
                    onRelease(imageOfItem, objectType));

            // Add the image to the row
            hBox.getChildren().add(imageOfItem);
        } else { // If this row is at its max items, try adding a different item
            inventory.addItem();
        }
    }

    /**
     * An event handler for dragging item images
     *
     * @param event the MouseEvent that triggered the handler
     * @param image THe image being dragged
     */
    public void onDrag(MouseEvent event, ImageView image) {
        if (originalX == -1) {
            originalX = (int) image.getLayoutX();
        }

        if (originalY == -1) {
            originalY = (int) image.getLayoutY();
        }

        // Move the image wherever the mouse is
        image.setTranslateX(image.getTranslateX() + event.getX());
        image.setTranslateY(image.getTranslateY() + event.getY());
    }

    /**
     * An event handler for releasing an item image
     *
     * @param image the image being released
     * @param object the type of the object being released
     */
    public void onRelease(ImageView image, GameObjectType object) {
        double canvasWidth =
                GameObject.getBoard().getCanvas().getWidth();

        // Find the dropped items position on the x axis, by finding how far it has moved in pixels
        // Offsetting the width of the canvas, and how far into the inventory it was
        int gridX = (int) (image.getTranslateX() + canvasWidth) + originalX;

        // Find the dropped items position on the y axis, by finding how far it has moved in pixels
        // Offsetting the position of this row on the screen
        int gridY = (int) (image.getTranslateY() + hBox.getLayoutY());

        // Find the tiles position in the tile set
        int x = gridX / Tile.TILE_SIZE;
        int y = gridY / Tile.TILE_SIZE;

        LevelData levelData = inventory.getLevelData();
        Tile tile = levelData.getTileSet().getTile(x, y);

        // If this tile is invalid, or the user placed the item where there is no tile
        // remove this object, and then add a new one, as the item was not successfully used
        if (tile == null || !(tile.getTileType().equals(TileType.PATH))) {
            decrementCount(image);
            incrementObjectCount();
        } else { // If player has dropped the item on a valid tile, add item to board, and remove from row
            SFXManager.bePlaced();
            GameObject.getBoard().addObject(ObjectUtils.getObjectFromType(tile, object, levelData));
            decrementCount(image);
        }

        // If there are no objects in this row, remove it from row list, and display.
        if (objectCount == 0) {
            inventory.getItemRows().remove(this);
            inventory.getInventoryNode().getChildren().remove(gethBox());
        }

        originalX = -1;
        originalY = -1;
    }

    /**
     * A method to decrement the object count
     *
     * @param imageOfItemToRemove the image to remove
     */
    private void decrementCount(ImageView imageOfItemToRemove) {
        objectCount--;
        inventory.getItemsInInventory().remove(objectType);
        this.hBox.getChildren().remove(imageOfItemToRemove);
    }

    /**
     * A method to get the count of objects in this row
     *
     * @return the count of objects in this row
     */
    public int getObjectCount() {
        return objectCount;
    }
}
