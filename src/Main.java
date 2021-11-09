import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        root.setCenter(new Label("Hello World!"));
        stage.setScene(new Scene(root, 250, 250));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
