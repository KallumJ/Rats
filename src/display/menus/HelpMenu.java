package display.menus;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * The class Help menu
 *
 * @author Yin Man Cheung
 */
public class HelpMenu extends GameMenu {
	private static final int WINDOW_WIDTH = 860;
	private static final int WINDOW_HEIGHT = 600;

	/**
	 * Builds the HelpMenu
	 *
	 * @return the HelpMenu as a JavaFX node
	 */
	@Override
	public Parent buildMenu() {
		Pane pane = new Pane();
		pane.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		pane.setBackground(new Background(new BackgroundFill(Color.BLACK, null
				, null)));

		HelpText headingsText =
				new HelpText("ABOUT RATS" + "\nKill all the " + "rats before " + "they can reproduce." + " If you have too many " + "rats in the game, YOU LOSE!!!\n", 75, 50, false);

		Text headingsTextNode = headingsText.getNode();
		applyScrollingEffects(headingsTextNode);

		HelpText[] helpTexts = new HelpText[]{
				new HelpText("\nHOW TO PLAY", 200, 150, true),
				new HelpText("\nUSE MOUSE TO DRAG OBJECT AND PLACE IT ON THE " + "PATH TILES.", 35, 175, false),
				new HelpText("\nOBJECTS:", 35, 200, true),
				new HelpText("\nBombs explode and kill all the rats and " +
						"destroys objects nearby" + "\nFemale Sex Changers " + "covert male rats that walk past it into females." + "\nMale Sex Changers covert female rats that walk past" + " it into males." + "\nNo Entry Signs block the " + "movement of rats." + "\nDeath Rats kills other rats " + "they come across." + "\nPoison kills the rat that " + "walk past." + "\nSterilisation sterilizes rats that " + "encounter it.", 35, 225, false),
				new HelpText("\nNEED HELP? ", 25, 500, true),
				new HelpText("\nPlease email us on" + "\nCS-230Group41-UsrGrp" + "@SwanseaUniversity.onmicrosoft.com for further " + "support.", 25, 525, false)
		};

		List<Text> textNodes = new ArrayList<>();
		textNodes.add(headingsTextNode);
		for (HelpText helpText : helpTexts) {
			textNodes.add(helpText.getNode());
		}
		pane.getChildren().addAll(textNodes);

		// Add a back button event handler
		EventHandler<Event> backHandler = event -> GameMenu.getStage()
				.setScene(new Scene(new MainMenu().buildMenu()));

		BorderPane menu = buildBlank(null, backHandler);

		menu.getChildren().add(pane);

		return menu;
	}

	/**
	 * Apply scrolling effects to the provided Text nodes
	 *
	 * @param headerTextNode    the header text node
	 * @param subheaderTextNode the subheading text node
	 */
	private void applyScrollingEffects(Text headingsTextNode) {

		// Use Timeline, Key Value and Key Frame
		// Scrolling Text Effects for text "ABOUT RATS"
		double sceneWidth = getStage().getWidth();
		double textWidth = headingsTextNode.getLayoutBounds().getWidth();

		KeyValue initKeyValue =
				new KeyValue(headingsTextNode.translateXProperty(),
						sceneWidth);
		KeyFrame initFrame = new KeyFrame(Duration.ZERO, initKeyValue);
		KeyValue endKeyValue =
				new KeyValue(headingsTextNode.translateXProperty(),
						-3.5 * textWidth);
		KeyFrame endFrame = new KeyFrame(Duration.seconds(15), endKeyValue);

		Timeline timeline = new Timeline(initFrame, endFrame);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
}

