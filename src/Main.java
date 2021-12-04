import display.menus.GameMenu;
import display.menus.LoginMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Main class to start the application
 */ 
public class Main extends Application {
    private static final String APP_HEADER = "Rats CS-230 Group 41";


    /**
     * Starts the application
     * @param stage the stage
     */
    @Override
    public void start(Stage stage) {
        LoginMenu loginMenu = new LoginMenu();
        stage.setScene(new Scene(loginMenu.buildMenu()));
        stage.setTitle(APP_HEADER);
        stage.setResizable(false);
        stage.show();
        GameMenu.setStage(stage);
    }

	/** 
	* Method for java to launch the application
	* @param args the command line args
	*/
    public static void main(String[] args) {
        launch(args);
    }
}
