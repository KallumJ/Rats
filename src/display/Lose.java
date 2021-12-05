/**
 * @author Samhitha Pinisetti (2035106)
 *
 */
package display;

import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import level.LevelData;
import level.LevelProperties;
import objects.GameObject;
import objects.rats.PeacefulRat;
import players.scores.Player;

/**
 *Constructs a Lose class
 *@param label javafx feature 'label' for the pop up window displaying 'Lose Status'
 *@param levelProperties
 */
public class Lose extends Application {
	
	Label label;
	private static LevelProperties levelProperties;
	
	public static void main(String[] args) {
		int decider = levelProperties.getPopulationToLose();
		int populationLeft = 0; //TODO: This cannot be zero. must be casted to some type. 
		
		if (populationLeft == decider) {
		launch(args);
		}
	}
	
	/**
     * A method to create the pop up window that displays the 'YOU LOSE' message. 
     *
     */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Lose Window");
		label = new Label();
		label.setText("YOU LOSE");
		
		StackPane layout = new StackPane();
		layout.getChildren().add(label);
		
		Scene scene = new Scene(layout, 300, 250);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
