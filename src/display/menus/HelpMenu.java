package display.menus;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
* The class Help menu
* @author Yin Man Cheung
*/ 
public class HelpMenu {
	/**
	 * Start
	 * @param stage  the stage
	 */
	public void start(Stage stage) {
		try {
			stage.setTitle("Rats CS-230 Group 41 Help Menu");
			Pane pane = new Pane();
			pane.setPrefSize(860, 600);
			pane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
			
			Text text = new Text("ABOUT RATS");
			text.setX(350);
			text.setY(50);
			text.setTextAlignment(TextAlignment.CENTER);
			text.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 45));
			text.setFill(Color.WHITE);
			Effect glow = new Glow(1.0);
	        text.setEffect(glow);
	        
	        Text text1 = new Text("\nKill all the rats before they can reproduce."
					+ " If you have too many rats in the game, YOU LOSE!!!\n" 
					);
			text1.setX(75);
			text1.setY(50);
			text1.setTextAlignment(TextAlignment.CENTER);
			text1.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 25));
			text1.setFill(Color.WHITE);
			
			Text text2 = new Text("\nHOW TO PLAY");
			text2.setX(350);
			text2.setY(150);
			text2.setTextAlignment(TextAlignment.CENTER);
			text2.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 25));
			text2.setFill(Color.WHITE);
			text2.setUnderline(true);
			Effect glow2 = new Glow(1.0);
	        text2.setEffect(glow);
	        
	        Text text3 = new Text("\nUSE MOUSE TO DRAG OBJECT AND PLACE IT ON THE PATH TILES.");
	        text3.setX(150); 
		    text3.setY(175);
			text3.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 25));
			text3.setFill(Color.WHITE);
	        
			Text text4 = new Text("\nOBJECTS:");
			text4.setX(150); 
		    text4.setY(200);
			text4.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 25));
			text4.setFill(Color.WHITE);
			text4.setUnderline(true);
			Effect glow3 = new Glow(1.0);
	        text4.setEffect(glow);
			
			Text text5 = new Text("\n--> Bombs explode and kill all the rats in path"
					+ "\n--> Female Sex Exchnage covert male rats that walk pass it into females." 
					+ "\n--> Male Sex Exchnage covert female rats that walk pass it into males." 
					+ "\n--> No Entry Sign block the movement of rats."
					+ "\n--> Death Rats kills other rats they come across."				
					+ "\n--> Poison kill the rat that walk past." +
					"\n--> Sterilisation sterilizes rats that encounter it.");
			text5.setX(150); 
		    text5.setY(225);
			text5.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 25));
			text5.setFill(Color.WHITE);
			
			Text text6 = new Text("\nNEED HELP? ");
			text6.setX(25); 
		    text6.setY(500);
		    text6.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 25));
			text6.setFill(Color.WHITE);
			text6.setUnderline(true);
			Effect glow4 = new Glow(1.0);
	        text6.setEffect(glow);
					
			Text text7 = new Text("\nPlease email us on" 
					+ "\nCS-230Group41-UsrGrp@SwanseaUniversity.onmicrosoft.com for further support.");
			text7.setX(25); 
		    text7.setY(525);
		    text7.setFont(Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 25));
			text7.setFill(Color.WHITE);
			
			pane.getChildren().add(text);
			pane.getChildren().add(text1);
			pane.getChildren().add(text2);
			pane.getChildren().add(text3);
			pane.getChildren().add(text4);
			pane.getChildren().add(text5);
			pane.getChildren().add(text6);
			pane.getChildren().add(text7);
	        
			Scene scene = new Scene(pane);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
			
			// Use Timeline, Key Value and Key Frame 
			//Scrolling Text Effects for text "ABOUT RATS"
			double sceneWidth = scene.getWidth();
			double textWidth = text.getLayoutBounds().getWidth();
			
			KeyValue initKeyValue = 
					new KeyValue(text.translateXProperty(), sceneWidth);
			KeyFrame initFrame = new KeyFrame(Duration.ZERO, initKeyValue);
			KeyValue endKeyValue = 
					new KeyValue(text.translateXProperty(), -3.5 * textWidth);
			KeyFrame endFrame = new KeyFrame(Duration.seconds(8), endKeyValue);

			Timeline timeline = new Timeline(initFrame, endFrame);
			timeline.setCycleCount(Timeline.INDEFINITE);
			timeline.play();
			
			// Scrolling Text Effects for text1
			double text1Width = text1.getLayoutBounds().getWidth();
			
			KeyValue iKV1 = 
					new KeyValue(text1.translateXProperty(), sceneWidth);
			KeyFrame iF1 = new KeyFrame(Duration.ZERO, iKV1);
			KeyValue eKV1 = 
					new KeyValue(text1.translateXProperty(), -1.0 * text1Width);
			KeyFrame eF1 = new KeyFrame(Duration.seconds(9), eKV1);

			Timeline t1 = new Timeline(iF1, eF1);
			t1.setCycleCount(Timeline.INDEFINITE);
			t1.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
