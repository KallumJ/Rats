package display.inventory;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import level.LevelData;
import level.LevelUtils;
import objects.GameObject;
import objects.GameObjectType;
import objects.ObjectUtils;
import sfx.SFXManager;
import tile.Tile;
import tile.TileType;

/**
 * A class to model a row of items in the inventory.
 *
 * @author Kallum Jones 2005855
 */
public class ItemRow {

	public static final int MAX_ITEMS_IN_ROW = 4;
	private final GameObjectType objectType;
	private final HBox hBox;
	private final Inventory inventory;
	private int objectCount;
	private int originalX = -1;
	private int originalY = -1;

	/**
	 * Constructs an item row for the provided object type.
	 *
	 * @param objectType The type of items in this row
	 * @param inventory  the inventory this row is being stored in
	 */
	public ItemRow(final GameObjectType objectType,
                   final Inventory inventory) {
		this.objectType = objectType;
		this.hBox = new HBox();
		this.objectCount = 0;
		this.inventory = inventory;
	}

	/**
	 * Gets the type of object being stored in this row.
	 *
	 * @return the type of object being stored in this row
	 */
	public GameObjectType getObjectType() {
		return objectType;
	}

	/**
	 * Gets the HBox node representing this row.
	 *
	 * @return the HBox node representing this row
	 */
	public HBox gethBox() {
		return hBox;
	}

	/**
	 * Increments the number of objects being stored in this row.
	 */
	public void incrementObjectCount() {
		// If this row isn't at its maximum items
		if (objectCount < MAX_ITEMS_IN_ROW) {
			objectCount++;

			// Grab an image of the item
			String objectImgUrl = ObjectUtils.getObjectImageUrl(objectType);
			ImageView imageOfItem = new ImageView(objectImgUrl);

			// Add drag and release event handlers
			imageOfItem.setOnMouseDragged(event -> onDrag(event, imageOfItem));
			imageOfItem.setOnMouseReleased(event -> onRelease(imageOfItem,
                    objectType));

			// Add the image to the row
			hBox.getChildren().add(imageOfItem);
		} else { // If this row is at its max items, try adding a different
            // item
			inventory.addRandomItem();
		}
	}

	/**
	 * An event handler for dragging item images.
	 *
	 * @param event the MouseEvent that triggered the handler
	 * @param image the image being dragged
	 */
	public void onDrag(final MouseEvent event, final ImageView image) {
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
	 * An event handler for releasing an item image.
	 *
	 * @param image  the image being released
	 * @param object the type of the object being released
	 */
	public void onRelease(final ImageView image, final GameObjectType object) {
		double canvasWidth = GameObject.getBoard().getCanvas().getWidth();

		// Find the dropped items position on the x axis,
		// by finding how far it has moved in pixels
		// Offsetting the width of the canvas, and how far into
		// the inventory it was
		int gridX = (int) (image.getTranslateX() + canvasWidth) + originalX;

		// Find the dropped items position on the y axis, by finding how far
		// it has moved in pixels
		// Offsetting the position of this row on the screen
		int gridY = (int) (image.getTranslateY() + hBox.getLayoutY());

		// Find the tiles position in the tile set
		int x = gridX / Tile.TILE_SIZE;
		int y = gridY / Tile.TILE_SIZE;

		LevelData levelData = inventory.getLevelData();
		Tile tile = levelData.getTileSet().getTile(x, y);
		boolean isTileBlocked = LevelUtils.isTileBlocked(tile,
                levelData.getObjects());

        /*
            If this tile is invalid, or the user placed the item where there
                is no tile, or the tile is blocked by another object:
                remove this object, and then add a new one,
                as the item was not successfully used.
        */
		if (tile == null || !(tile.getTileType()
				.equals(TileType.PATH)) || isTileBlocked) {
			decrementCount(image);
			incrementObjectCount();
		} else {
			// If player has dropped the item on a valid tile, add item to
            // board
			// and remove it from row
			SFXManager.bePlaced();
			GameObject objForPlacing = ObjectUtils.getObjectFromType(tile,
                    object, levelData);
			GameObject.getBoard().addObject(objForPlacing);
			decrementCount(image);
		}

		// If there are no objects in row, remove it from row list, and
        // display.
		if (objectCount == 0) {
			inventory.getItemRows().remove(this);
			inventory.getInventoryNode().getChildren().remove(gethBox());
		}

		originalX = -1;
		originalY = -1;
	}

	/**
	 * Gets the count of objects in this row.
	 *
	 * @return the count of objects in this row
	 */
	public int getObjectCount() {
		return objectCount;
	}

	/**
	 * Decrements the object count.
	 *
	 * @param imageOfItemToRemove the image to remove
	 */
	private void decrementCount(final ImageView imageOfItemToRemove) {
		objectCount--;
		inventory.getItemsInInventory().remove(objectType);
		this.hBox.getChildren().remove(imageOfItemToRemove);
	}
}
