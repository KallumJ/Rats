package display;

import display.inventory.CustomInventory;
import display.menus.CustomLevelsMenu;
import display.menus.GameMenu;
import display.menus.MainMenu;
import display.menus.editor.LevelEditorOptionsMenu;
import envrionment.TimeOfDay;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import level.LevelData;
import level.LevelProperties;
import level.LevelSaveHandler;
import level.LevelUtils;
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
    private final Stage levelOptionsStage;


    /**
     * Constructs a custom board
     * @param levelData the LevelData for the custom level
     */
    public CustomBoard(LevelData levelData) {
        this.levelData = levelData;
        this.tileCanvas = new TileCanvas(levelData);
        this.inputMenu = new LevelEditorOptionsMenu();
        this.eventListeners();
        levelOptionsStage = new Stage();
        levelOptionsStage.setScene(new Scene(inputMenu.buildGUI()));
        levelOptionsStage.setX(GameMenu.getStage().getX() - LevelEditorOptionsMenu.WINDOW_OFFSET);
        levelOptionsStage.setY(GameMenu.getStage().getY());
    }

    public CustomBoard(LevelData levelData, int populationToLose, int expectedTime, int itemInterval, int ratMaxBabies, int ratMinBabies, int adultRatSpeed, int babyRatSpeed, int deathRatSpeed, boolean includeAirstrike, int costOfAirstrike, int airstrikeNumOfHits, TimeOfDay time, int timeInterval) {
        this.levelData = levelData;
        this.tileCanvas = new TileCanvas(levelData);

        this.inputMenu = new LevelEditorOptionsMenu();
        this.inputMenu.setPopulationToLose(populationToLose);
        this.inputMenu.setExpectedTime(expectedTime);
        this.inputMenu.setItemInterval(itemInterval);
        this.inputMenu.setRatMaxBabies(ratMaxBabies);
        this.inputMenu.setRatMinBabies(ratMinBabies);
        this.inputMenu.setAdultRatSpeed(adultRatSpeed);
        this.inputMenu.setBabyRatSpeed(babyRatSpeed);
        this.inputMenu.setDeathRatSpeed(deathRatSpeed);
        this.inputMenu.setIncludeAirstrike(includeAirstrike);
        this.inputMenu.setAirstrikeCost(costOfAirstrike);
        this.inputMenu.setAirstrikeNumberOfHits(airstrikeNumOfHits);
        this.inputMenu.setTimeOfDay(time);
        this.inputMenu.setTimeInterval(timeInterval);

        this.eventListeners();
        levelOptionsStage = new Stage();
        levelOptionsStage.setScene(new Scene(inputMenu.buildGUI()));
        levelOptionsStage.setX(GameMenu.getStage().getX() - LevelEditorOptionsMenu.WINDOW_OFFSET);
        levelOptionsStage.setY(GameMenu.getStage().getY());

    }
    
    public BorderPane buildGUI() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-image: url(file:resources/inventoryBg.png);");
        root.setLeft(tileCanvas.getCanvas());
        root.setBottom(createCommandsBox());
        levelOptionsStage.show();
        root.setRight(new CustomInventory(levelData).buildInventoryGUI());

        GameObject.setBoard(this);
        return root;
    }

    /**
     * Calls all event listeners in class
     */
    private void eventListeners() {
        this.tileCanvas.getCanvas().addEventFilter(javafx.scene.input.MouseEvent.MOUSE_PRESSED, event -> {
            Tile tile = levelData.getTileSet().getTile((int)event.getX()/Tile.TILE_SIZE, (int)event.getY()/Tile.TILE_SIZE);
            if (this.inputMenu.getDeleteItemsChecked()) {
                System.out.println(this.inputMenu.getDeleteItemsChecked());
                removeItemsFromTile(tile);
            } else {
                changeTile(tile);
        }});
        this.tileCanvas.getCanvas().addEventHandler(javafx.scene.input.MouseEvent.MOUSE_DRAGGED, event -> {
            Tile tile = levelData.getTileSet().getTile((int)event.getX()/Tile.TILE_SIZE, (int)event.getY()/Tile.TILE_SIZE);
            if (this.inputMenu.getDeleteItemsChecked()) {
                System.out.println(this.inputMenu.getDeleteItemsChecked());
                removeItemsFromTile(tile);
            } else {
                changeTile(tile);
        }});
    }

    private void removeItemsFromTile(Tile tile) {
        List<GameObject> objectsOnTile = LevelUtils.getObjectsOnTile(tile, levelData.getObjects());
        levelData.getObjects().removeAll(objectsOnTile);
        for (GameObject object : objectsOnTile) {
            object.standOn(null);
        }
        tileCanvas.updateBoardDisplay();
    }

    /**
     * Changes tile at mouse position with selected tile type
     * @param tile the tile to change
     */
    private void changeTile(Tile tile) {
        if (tile.getTileType() != inputMenu.getTileSelected()) {
            Tile newTile = new Tile(tile.getTileLocation(), inputMenu.getTileSelected());
            levelData.changeTile(tile, newTile);
            tileCanvas.updateBoardDisplay();
        }
    }

    public HBox createCommandsBox () {
        HBox commandsBox = new HBox();
        Button saveButton = new Button(" S A V E ");
        saveButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-border-color: darkgrey; -fx-border-width: 1px;");

        Button createButton = new Button(" C R E A T E ");
        createButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-border-color: darkgrey; -fx-border-width: 1px;");

        Button backButton = new Button(" B A C K "); 
        backButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-border-color: darkgrey; -fx-border-width: 1px;");

        saveButton.setMaxWidth(200);
        saveButton.setTranslateX(150);
        saveButton.setTranslateY(0);

        createButton.setMaxWidth(200);
        createButton.setTranslateX(150);
        createButton.setTranslateY(0);

        backButton.setMaxWidth(200);
        backButton.setTranslateX(150);
        backButton.setTranslateY(0);

        // Save level when mouse is pressed, and stop the current game
        saveButton.setOnMousePressed(event -> {
            inputMenu.setLevelProperties(levelData);
            LevelSaveHandler.saveCustomLevel(levelData, PlayerProfileManager.getCurrentlyLoggedInPlayer());
            levelOptionsStage.close();
            GameObject.setBoard(null);
            GameMenu.getStage().setScene(new Scene(new MainMenu().buildMenu()));
        });

        createButton.setOnMousePressed(event -> {
            // TODO behaviour of save button
        });

        backButton.setOnMousePressed(event -> {
            levelOptionsStage.close();
            GameObject.setBoard(null);
            GameMenu.getStage().setScene(new Scene(new CustomLevelsMenu().buildMenu()));
        });

        commandsBox.getChildren().add(saveButton);
        commandsBox.getChildren().add(createButton);
        commandsBox.getChildren().add(backButton);
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
