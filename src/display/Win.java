/**
 * @author Samhitha Pinisetti (2035196)
 */
package display;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import level.LevelProperties;
import level.RatPopulation;

/**
 *Constructs a Win class
 *@param label javafx feature 'label' for the pop up window displaying 'Win Status'
 *@param ratPopulation 
 */
public class Win extends Application {
	
	Label label;
	private static RatPopulation ratPopulation;
	
	//if total population == 0, then the player wins!
	
	public static void main(String[] args) {
		int decider = ratPopulation.getTotalPopulation();
		
		if (decider == 0) {
			launch(args);
		}
	}
	
	/**
     * A method to create the pop up window that displays the 'YOU WIN' message. 
     *
     */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Win Window");
		label = new Label();
		label.setText("YOU WIN");
		
		StackPane layout = new StackPane();
		layout.getChildren().add(label);
		
		Scene scene = new Scene(layout, 300, 250);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
