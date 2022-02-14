package display;

import display.inventory.CustomInventory;
import display.menus.editor.LevelPropertiesInputMenu;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import level.LevelData;
import level.LevelProperties;
import objects.GameObject;
import tile.Tile;

import java.util.List;

/**
 * A class to model a board in a level editor
 */
public class CustomBoard extends Board {
    private final TileCanvas tileCanvas;
    private final LevelData levelData;
    private final LevelPropertiesInputMenu inputMenu;

    /**
     * Constructs a custom board
     * @param levelData the LevelData for the custom level
     */
    public CustomBoard(LevelData levelData) {
        this.levelData = levelData;
        this.tileCanvas = new TileCanvas(levelData);
        this.inputMenu = new LevelPropertiesInputMenu();
        this.eventListeners();
    }

    /**
     * Calls all event listeners in class
     */
    private void eventListeners() {
        this.tileCanvas.getCanvas().addEventFilter(javafx.scene.input.MouseEvent.MOUSE_PRESSED, event -> {changeTile(event);});
        this.tileCanvas.getCanvas().addEventHandler(javafx.scene.input.MouseEvent.MOUSE_DRAGGED, event -> {changeTile(event);});
    }

    /**
     * Changes tile at mouse position with selected tile type
     * @param event MouseEvent used to get mouse X Y
     */
    private void changeTile(MouseEvent event) {
        Tile tile = levelData.getTileSet().getTile(((int)event.getX())/Tile.TILE_SIZE, ((int)event.getY())/Tile.TILE_SIZE);
        // System.out.println(String.format("X:%s Y:%s", event.getX(), event.getY()));
        if (tile == null) {
            System.out.println("Null!");
        } else if (tile.getTileType() != inputMenu.getTileSelected()) {
            Tile newTile = new Tile(tile.getTileLocation(), inputMenu.getTileSelected());
            levelData.changeTile(tile, newTile);
            System.out.println("Tile changed");
            tileCanvas.updateBoardDisplay();
        }
    }

    /**
     * A method to build the board's GUI
     * @return the GUI as a BorderPane
     */
    public BorderPane buildGUI() {
        BorderPane root = new BorderPane();
        root.setLeft(tileCanvas.getCanvas());
        root.setBottom(inputMenu.buildGUI());
        root.setRight(new CustomInventory(levelData).buildInventoryGUI());

        GameObject.setBoard(this);
        return root;
    }

    /**
     * Returns the TileCanvas as a JavaFX Canvas
     * @return a JavaFX Canvas
     */
    @Override
    public Canvas getCanvas() {
        return tileCanvas.getCanvas();
    }

    /**
     * Get the level properties for the level in play.
     * @return the LevelProperties for this level.
     */
    @Override
    public LevelProperties getLevelProperties() {
        return levelData.getLevelProperties();
    }

    /**
     * Get the level data for this board
     * @return the level data for this board
     */
    @Override
    public LevelData getLevelData() {
        return levelData;
    }

    /**
     * Add the provided GameObject to the board.
     *
     * @param objectAdded the GameObject to add.
     */
    @Override
    public void addObject(GameObject objectAdded) {
        List<GameObject> objects = levelData.getObjects();

        objects.add(objectAdded);
        tileCanvas.updateBoardDisplay();
    }
}
