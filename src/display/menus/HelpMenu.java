package display.menus;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
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
	 * @return the HelpMenu as a JavaFX node
	 */
    @Override
    public Parent buildMenu() {
        Pane pane = new Pane();
        pane.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        pane.setBackground(new Background(
				new BackgroundFill(Color.BLACK, null, null
				)
		));

		HelpText headerText = new HelpText("ABOUT RATS", 350, 50, false);
		HelpText subheaderText = new HelpText("\nKill all the rats before they can reproduce."
				+ " If you have too many rats in the game, YOU LOSE!!!\n", 75, 50, false);

		Text headerTextNode = headerText.getNode();
		Text subheaderTextNode = subheaderText.getNode();
		applyScrollingEffects(headerTextNode, subheaderTextNode);

		HelpText[] helpTexts = new HelpText[]{
				new HelpText("\nHOW TO PLAY", 350, 150, true),
				new HelpText("\nUSE MOUSE TO DRAG OBJECT AND PLACE IT ON THE PATH TILES.",
						150, 175, false),
				new HelpText("\nOBJECTS:", 150, 200, true),
				new HelpText("\n--> Bombs explode and kill all the rats and destroys objects nearby"
						+ "\n--> Female Sex Changers covert male rats that walk past it into females."
						+ "\n--> Male Sex Changers covert female rats that walk past it into males."
						+ "\n--> No Entry Signs block the movement of rats."
						+ "\n--> Death Rats kills other rats they come across."
						+ "\n--> Poison kills the rat that walk past." +
						"\n--> Sterilisation sterilizes rats that encounter it.",
						150, 225, false),
				new HelpText("\nNEED HELP? ", 25, 500, true),
				new HelpText("\nPlease email us on"
						+ "\nCS-230Group41-UsrGrp@SwanseaUniversity.onmicrosoft.com for further support.",
						25, 525, false)
		};

		List<Text> textNodes = new ArrayList<>();
		textNodes.add(headerTextNode);
		textNodes.add(subheaderTextNode);
		for (HelpText helpText : helpTexts) {
			textNodes.add(helpText.getNode());
		}
		pane.getChildren().addAll(textNodes);

        // Add a back button event handler
        EventHandler<Event> backHandler = event ->
                GameMenu.stage.setScene(new Scene(new MainMenu().buildMenu())
                );

        BorderPane menu = buildBlank(null, backHandler);

        menu.getChildren().add(pane);

        return menu;
    }

	/**
	 * Applys scrolling effects to the provided Text nodes
	 * @param headerTextNode the header text node
	 * @param subheaderTextNode the subheading text node
	 */
	private void applyScrollingEffects(Text headerTextNode, Text subheaderTextNode) {

		// Use Timeline, Key Value and Key Frame
		// Scrolling Text Effects for text "ABOUT RATS"
		double sceneWidth = stage.getWidth();
		double textWidth = headerTextNode.getLayoutBounds().getWidth();

		KeyValue initKeyValue =
				new KeyValue(headerTextNode.translateXProperty(), sceneWidth);
		KeyFrame initFrame = new KeyFrame(Duration.ZERO, initKeyValue);
		KeyValue endKeyValue =
				new KeyValue(headerTextNode.translateXProperty(), -3.5 * textWidth);
		KeyFrame endFrame = new KeyFrame(Duration.seconds(8), endKeyValue);

		Timeline timeline = new Timeline(initFrame, endFrame);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();

		// Scrolling Text Effects for text1
		double text1Width = subheaderTextNode.getLayoutBounds().getWidth();

		KeyValue iKV1 =
				new KeyValue(subheaderTextNode.translateXProperty(), sceneWidth);
		KeyFrame iF1 = new KeyFrame(Duration.ZERO, iKV1);
		KeyValue eKV1 =
				new KeyValue(subheaderTextNode.translateXProperty(), -1.0 * text1Width);
		KeyFrame eF1 = new KeyFrame(Duration.seconds(9), eKV1);

		Timeline t1 = new Timeline(iF1, eF1);
		t1.setCycleCount(Timeline.INDEFINITE);
		t1.play();
	}
}

/**
 * A class to model text in the HelpMenu
 */
class HelpText {
	private final String text;
	private final int x;
	private final int y;
	private final boolean underline;

	/**
	 * Constructs a HelpText
	 * @param text the text to display
	 * @param x the x position
	 * @param y the y position
	 * @param underline true if underlined, false otherwise
	 */
	HelpText(String text, int x, int y, boolean underline) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.underline = underline;
	}

	/**
	 * A method to get the HelpText as a JavaFX node
	 * @return Text node for this HelpText
	 */
	public Text getNode() {
		Text text = new Text(this.text);
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFont(Font.font(GameMenu.DEFAULT_FONT,
				FontWeight.SEMI_BOLD, 25));
		text.setFill(Color.WHITE);
		text.setX(x);
		text.setY(y);
		text.setUnderline(underline);
		return text;
	}
}
