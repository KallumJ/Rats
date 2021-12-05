package display.menus;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * A class to model a Lose Menu.
 *
 * @author Samhitha Pinisetti (2035106)
 */
public class LoseMenu extends GameMenu {
	private static final String HEADER = " Y O U  L O S E ! ";
	private static final String CONTENT = "The rats spread out of control!";
	private static final int X_POS = 110;
	private static final int Y_POS = 300;
	private static final int LABEL_FONT_SIZE = 24;

	/**
	 * A method to create the popup window that displays the 'YOU Lose'
	 * message.
	 *
	 * @return the node of the created menu layout
	 */
	@Override
	public Parent buildMenu() {
		EventHandler<Event> backHandler = event -> GameMenu.getStage()
				.setScene(new Scene(new MainMenu().buildMenu()));

		BorderPane menu = buildBlank(new MenuTitle(HEADER), backHandler);

		Label label = new Label();
		label.setText(CONTENT);

		label.setTextFill(Color.WHITE);
		label.setFont(Font.font(DEFAULT_FONT, LABEL_FONT_SIZE));
		label.setTranslateX(X_POS);
		label.setTranslateY(Y_POS);
		getCenter().getChildren().add(label);

		return menu;
	}

}
