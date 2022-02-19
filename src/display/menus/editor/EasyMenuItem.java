package display.menus.editor;

import display.EasyCustomBoard;
import display.menus.GameMenu;
import display.menus.MenuItem;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import level.LevelData;
import level.custom.CustomLevelDataFactory;

public class EasyMenuItem extends MenuItem{
    private static final int HEIGHT_LIMIT = 20;
    private static final int WIDTH_LIMIT = 20;
    private static final String INVALID_SIZE =
            "Please choose a size with a height no greater than " + HEIGHT_LIMIT +
                    "and a width no greater than " + WIDTH_LIMIT;

    public EasyMenuItem() {
        super(" E A S Y ");

        setOnMousePressed(event -> {
            int height = 12;
            int width = 12;

            if (height > HEIGHT_LIMIT || width > WIDTH_LIMIT) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(INVALID_SIZE);
                alert.showAndWait();
            } else {
                LevelData customLevel = CustomLevelDataFactory.getBlankLevelData(height, width);

                EasyCustomBoard board = new EasyCustomBoard(customLevel);
                Scene scene = new Scene(board.buildGUI());              

                GameMenu.getStage().setScene(scene);
            }
        });
    }

}