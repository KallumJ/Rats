import display.GameMenu;
import display.MainMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The class Main extends application
 */ 
public class Main extends Application {

    @Override
	/** 
	* Start
	* @param stage  the stage
	*/
    public void start(Stage stage) {
        GameMenu.stage = stage;
        MainMenu mainMenu = new MainMenu();
        stage.setScene(new Scene(mainMenu.buildMenu()));
        stage.setTitle("Rats CS-230 Group 41");
        stage.show();
    }
	/** 
	* Main
	* @param args  the args
	*/
    public static void main(String[] args) {
        launch(args);
    }
}
