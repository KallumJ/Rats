import display.Board;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import level.LevelData;
import level.LevelDataFactory;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        LevelData levelData = LevelDataFactory.constructLevelData(1);

        Board gameBoard = new Board(levelData);

        BorderPane root = new BorderPane(gameBoard.buildGUI());

        stage.setScene(new Scene(root, 1000, 1000));
        stage.show();
        gameBoard.startGame();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
