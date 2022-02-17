package display;

import display.inventory.CustomInventory;
import display.menus.GameMenu;
import display.menus.MainMenu;
import display.menus.editor.LevelEditorOptionsMenu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import level.LevelData;
import level.LevelProperties;
import level.LevelSaveHandler;
import objects.GameObject;
import players.PlayerProfileManager;
import tile.Tile;

import java.util.List;

/**
 * A class to model a board in a level editor
 */
public class CustomBoard extends Board {
    private final TileCanvas tileCanvas;
    private final LevelData levelData;
    private final LevelEditorOptionsMenu inputMenu;


    /**
     * Constructs a custom board
     * @param levelData the LevelData for the custom level
     */
    public CustomBoard(LevelData levelData) {
        this.levelData = levelData;
        this.tileCanvas = new TileCanvas(levelData);
        this.inputMenu = new LevelEditorOptionsMenu();
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
        root.setRight(new CustomInventory(levelData).buildInventoryGUI());
        root.setBottom(createCommandsBox());
        Stage levelOptionsStage = new Stage();
        levelOptionsStage.setScene(new Scene(inputMenu.buildGUI()));
        levelOptionsStage.setX(GameMenu.getStage().getX() - LevelEditorOptionsMenu.WINDOW_OFFSET);
        levelOptionsStage.setY(GameMenu.getStage().getY());
        levelOptionsStage.show();

        GameObject.setBoard(this);
        return root;
    }

    public HBox createCommandsBox () {
        HBox commandsBox = new HBox();
        Button saveButton = new Button("Save for later!");
        Button createButton = new Button("Create the level!");

        saveButton.setMaxWidth(200);
        saveButton.setAlignment(Pos.CENTER_LEFT);

        createButton.setMaxWidth(200);
        createButton.setAlignment(Pos.CENTER_LEFT);

        // Save level when mouse is pressed, and stop the current game
        saveButton.setOnMousePressed(event -> {
            // TODO behaviour of save button
        });

        createButton.setOnMousePressed(event -> {
            // TODO behaviour of save button
        });

        commandsBox.getChildren().add(saveButton);
        commandsBox.getChildren().add(createButton);
        return commandsBox;

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
