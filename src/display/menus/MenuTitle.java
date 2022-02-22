package display.menus;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import util.TextUtils;

/**
 * Models the title of a menu.
 *
 * @author Samhitha Pinisetti 2035196
 * @date 2022/02/18
 *
 */
public class MenuTitle extends StackPane {

	private static final int TITLE_WIDTH = 280;
	private static final int TITLE_HEIGHT = 60;
	private static final int BORDER_SIZE = 2;
	private static final int TITLE_FONT_SIZE = 50;
	private static final Font TITLE_FONT = TextUtils.getFont(TITLE_FONT_SIZE);

	/**
	 * Constructor for a MenuTitle.
	 *
	 * @param name the title text
	 */
	public MenuTitle(final String name) {
		Rectangle bg = new Rectangle(TITLE_WIDTH, TITLE_HEIGHT);
		bg.setStroke(Color.WHITE);
		bg.setStrokeWidth(BORDER_SIZE);
		bg.setFill(null);

		Text text = new Text(name);
		text.setFill(Color.WHITE);
		text.setFont(TITLE_FONT);

		setAlignment(Pos.CENTER);
		getChildren().addAll(bg, text);
	}

}
