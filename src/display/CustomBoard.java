package display;

import display.inventory.CustomInventory;
import display.menus.CustomLevelsMenu;
import display.menus.GameMenu;
import display.menus.MainMenu;
import display.menus.editor.LevelEditorOptionsMenu;
import envrionment.TimeOfDay;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import level.LevelData;
import level.LevelProperties;
import level.LevelSaveHandler;
import level.LevelUtils;
import objects.GameObject;
import objects.GameObjectType;
import objects.rats.PeacefulRat;
import players.PlayerProfileManager;
import tile.Direction;
import tile.Tile;
import tile.TileType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A class to model a board in a level editor.
 */
public class CustomBoard extends Board {

    private static final String OPTIONS_TITLE = "Level Options";
    private final TileCanvas tileCanvas;
    private final LevelData levelData;
    private final LevelEditorOptionsMenu inputMenu;
    private final Stage levelOptionsStage = new Stage();
    private static final int BUTTON_MAX_WIDTH = 200;
    private static final int CREATE_BUTTON_X = 150;
    private static final int BACK_BUTTON_X = 250;
    private static final int BUTTON_Y = 0;


    /**
     * Constructs a custom board.
     *
     * @param levelData the LevelData for the custom level.
     */
    public CustomBoard(LevelData levelData) {
        this.levelData = levelData;
        this.tileCanvas = new TileCanvas(levelData);
        this.inputMenu = new LevelEditorOptionsMenu();
        this.initCustomBoard();
    }

    public CustomBoard(LevelData levelData, int populationToLose, int expectedTime, int itemInterval,
                       int ratMaxBabies, int ratMinBabies, int adultRatSpeed, int babyRatSpeed,
                       int deathRatSpeed, boolean includeAirstrike, int costOfAirstrike,
                       int airstrikeNumOfHits, TimeOfDay time, int timeInterval,
                       HashSet<GameObjectType> allowedItems, String name) {
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
        if (allowedItems != null) {
            this.inputMenu.setSelectedObjects(allowedItems);
        }
        if (name != null) {
            this.inputMenu.setLevelNameTextField(name);
        }

        this.initCustomBoard();


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
     * Calls all event listeners in classTest.
     */
    private void initCustomBoard() {
        levelOptionsStage.setTitle(OPTIONS_TITLE);
        levelOptionsStage.setScene(new Scene(inputMenu.buildGUI()));
        levelOptionsStage.setX(GameMenu.getStage().getX() - LevelEditorOptionsMenu.WINDOW_OFFSET);
        levelOptionsStage.setY(GameMenu.getStage().getY());
        levelOptionsStage.setResizable(false);

        // Prevent dismissing of the options menu
        levelOptionsStage.setOnCloseRequest(Event::consume);

        EventHandler<MouseEvent> eventHandler = event -> {
            // makes sure that borders are always grass
            int x = (int) event.getX() / Tile.TILE_SIZE;
            int y = (int) event.getY() / Tile.TILE_SIZE;
            int canvasWidth = (int) getCanvas().getWidth() / Tile.TILE_SIZE;
            int canvasHeight = (int) getCanvas().getHeight() / Tile.TILE_SIZE;

            // makes sure that borders are always grass
            if (x >= 1 && y >= 1 && x < canvasWidth - 1 && y < canvasHeight - 1) {
                Tile tile = levelData.getTileSet().getTile(x, y);
                if (inputMenu.getDeleteItemsChecked()) {
                    removeItemsFromTile(tile);
                } else {
                    changeTile(tile);
                    if (!(inputMenu.getTileSelected().equals(TileType.PATH))) {
                        removeItemsFromTile(tile);
                    }

                }
            }
        };
        this.tileCanvas.getCanvas().addEventFilter(javafx.scene.input.MouseEvent.MOUSE_PRESSED, eventHandler);
        this.tileCanvas.getCanvas().addEventFilter(javafx.scene.input.MouseEvent.MOUSE_DRAGGED, eventHandler);
    }

    /**
     * removes all items/objects from tile.
     *
     * @param tile to remove items from.
     */
    private void removeItemsFromTile(Tile tile) {
        List<GameObject> objectsOnTile = LevelUtils.getObjectsOnTile(tile, levelData.getObjects());
        levelData.getObjects().removeAll(objectsOnTile);
        for (GameObject object : objectsOnTile) {
            object.standOn(null);
        }
        tileCanvas.updateBoardDisplay();
    }

    /**
     * Changes tile at mouse position with selected tile type.
     *
     * @param tile the tile to change.
     */
    private void changeTile(Tile tile) {
        if (tile.getTileType() != inputMenu.getTileSelected()) {
            Tile newTile = new Tile(tile.getTileLocation(), inputMenu.getTileSelected());
            levelData.changeTile(tile, newTile);
            tileCanvas.updateBoardDisplay();
        }
    }

    public HBox createCommandsBox() {
        HBox commandsBox = new HBox();
        Button createButton = new Button(" C R E A T E ");
        createButton.setStyle("-fx-background-color: black; -fx-text-fill: white;"
                + "-fx-border-color: darkgrey; -fx-border-width: 1px;");

        Button backButton = new Button(" B A C K ");
        backButton.setStyle("-fx-background-color: black; -fx-text-fill: white;"
                + "-fx-border-color: darkgrey; -fx-border-width: 1px;");

        createButton.setMaxWidth(BUTTON_MAX_WIDTH);
        createButton.setTranslateX(CREATE_BUTTON_X);
        createButton.setTranslateY(BUTTON_Y);

        backButton.setMaxWidth(BUTTON_MAX_WIDTH);
        backButton.setTranslateX(BACK_BUTTON_X);
        backButton.setTranslateY(BUTTON_Y);

        createButton.setOnMousePressed(event -> {
            // checks if board has atleast minimum number of path tiles and all paths are connected
            if (minTileCheck() && allPathsConnected() && minRatsCheck()) {
                try {
                    inputMenu.setLevelProperties(levelData);
                    // Rats should be loaded as babies
                    levelData.makeRatsBabies();
                    LevelSaveHandler.saveCustomLevel(levelData, PlayerProfileManager.getCurrentlyLoggedInPlayer());
                    levelOptionsStage.close();
                    GameObject.setBoard(null);
                    GameMenu.getStage().setScene(new Scene(new MainMenu().buildMenu()));
                } catch (IllegalArgumentException ignored) {}
            } else {
                if (!minTileCheck()) {
                    Alert alert = new Alert(AlertType.ERROR,
                            "Minimum number of paths/tunnels not met!");
                    alert.showAndWait();
                }
                if (!allPathsConnected()) {
                    Alert alert = new Alert(AlertType.ERROR,
                            "All paths/tunnels need to be connected!");
                    alert.showAndWait();
                }
                if (!minRatsCheck()) {
                    Alert alert = new Alert(AlertType.ERROR,
                            "The level must contain at least 2 rats,"
                                    + " 1 male and 1 female");
                    alert.showAndWait();
                }
            }
        });

        backButton.setOnMousePressed(event -> {
            levelOptionsStage.close();
            GameObject.setBoard(null);
            GameMenu.getStage().setScene(new Scene(new CustomLevelsMenu().buildMenu()));
        });

        commandsBox.getChildren().add(createButton);
        commandsBox.getChildren().add(backButton);
        return commandsBox;

    }

    /**
     * A method to check the level meets the minimum number of rats
     *
     * @return true if the map has at least 2 rats of differing genders, false otherwise
     */
    private boolean minRatsCheck() {
        boolean male = false;
        boolean female = false;
        for (GameObject object : levelData.getObjects()) {
            if (object instanceof PeacefulRat) {
                PeacefulRat rat = (PeacefulRat) object;
                if (rat.getGender().equals("m")) {
                    male = true;
                } else {
                    female = true;
                }
            }
        }

        return male && female;
    }

    /**
     * method to check if the custom board has the required number of path tiles.
     *
     * @return true if board has at least minimum number of path tiles else false.
     */
    private boolean minTileCheck() {
        int numOfPathTiles = 0;
        List<Tile> tiles = levelData.getTileSet().getAllTiles();
        for (Tile tile : tiles) {
            if (tile.getTileType() == TileType.PATH || tile.getTileType() == TileType.TUNNEL) {
                numOfPathTiles++;
            }
        }
        int boardArea = levelData.getTileSet().getHeight() * levelData.getTileSet().getWidth();
        int minTiles = (int) Math.sqrt(boardArea);

        return numOfPathTiles >= minTiles;
    }

    /**
     * finds first available path/tunnel and calls connectedTiles
     * using the path/tunnel as a parameter and an empty array list.
     *
     * @return true if all paths/tunnels are connected.
     */
    private boolean allPathsConnected() {
        List<Tile> tiles = this.levelData.getTileSet().getAllTiles();
        Tile startTile = null;
        for (Tile tile : tiles) {
            if (tile.getTileType() == TileType.PATH || tile.getTileType() == TileType.TUNNEL) {
                startTile = tile;
                break;
            }
        }
        if (startTile == null) {
            return false;
        }

        List<Tile> visitedTiles = connectedTiles(startTile, new ArrayList<>());
        int numOfPaths = 0;
        for (Tile tile : tiles) {
            if (tile.getTileType() == TileType.PATH || tile.getTileType() == TileType.TUNNEL) {
                numOfPaths++;
            }
        }
        return visitedTiles.size() == numOfPaths;
    }

    /**
     * recursively calls itself until tile parameter has no more adjacent tiles of type PATH/TUNNEL.
     *
     * @param tile         current tile used to get new tile from its adjacent tiles.
     * @param visitedTiles all tiles that have been visited/tile parameter.
     * @return List<Tile> of visited tiles.
     */
    private List<Tile> connectedTiles(Tile tile, List<Tile> visitedTiles) {
        visitedTiles.add(tile);
        Direction[] directions = new Direction[]{Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT};
        for (Direction direction : directions) {
            TileType tileType = tile.getAdjacentTile(direction).getTileType();
            // if adjacent tiles tile type is grass or tunnel and adjacent tile hasn't been visited before
            if ((tileType == TileType.PATH || tileType == TileType.TUNNEL)
                    && !visitedTiles.contains(tile.getAdjacentTile(direction))) {
                visitedTiles = connectedTiles(tile.getAdjacentTile(direction), visitedTiles);
            }
        }
        return visitedTiles;
    }

    /**
     * Returns the TileCanvas as a JavaFX Canvas.
     *
     * @return a JavaFX Canvas.
     */
    @Override
    public Canvas getCanvas() {
        return tileCanvas.getCanvas();
    }

    /**
     * Get the level properties for the level in play.
     *
     * @return the LevelProperties for this level.
     */
    @Override
    public LevelProperties getLevelProperties() {
        return levelData.getLevelProperties();
    }

    /**
     * Get the level data for this board.
     *
     * @return the level data for this board.
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
