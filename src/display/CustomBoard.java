package display;

import display.menus.editor.LevelPropertiesInputMenu;
import javafx.scene.layout.BorderPane;
import level.LevelData;

/**
 * A class to model a board in a level editor
 */
public class CustomBoard {
    private final TileCanvas tileCanvas;

    /**
     * Constructs a custom board
     * @param levelData the LevelData for the custom level
     */
    public CustomBoard(LevelData levelData) {
        this.tileCanvas = new TileCanvas(levelData);
    }

    /**
     * A method to build the board's GUI
     * @return the GUI as a BorderPane
     */
    public BorderPane buildGUI() {
        BorderPane root = new BorderPane();
        root.setLeft(tileCanvas.getCanvas());
        root.setBottom(new LevelPropertiesInputMenu().buildGUI());

        return root;
    }
}
