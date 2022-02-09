package display.menus;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import util.TextUtils;

/**
 * A class to model text in the HelpMenu.
 *
 * @author Kallum Jones 2005855
 */
class HelpText {
	private final String text;
	private final int x;
	private final int y;
	private final boolean underline;
	private static final Font TITLE_FONT = Font.font(GameMenu.DEFAULT_FONT,
		FontWeight.SEMI_BOLD, 25);

	/**
	 * Constructs a HelpText.
	 *
	 * @param text      the text to display
	 * @param x         the x position
	 * @param y         the y position
	 * @param underline true if underlined, false otherwise
	 */
	HelpText(String text, int x, int y, boolean underline) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.underline = underline;
	}

	/**
	 * A method to get the HelpText as a JavaFX node.
	 *
	 * @return Text node for this HelpText
	 */
	public Text getNode() {
		Text text = new Text(this.text);
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFont(TextUtils.getFont(25));
		text.setFill(Color.WHITE);
		text.setX(x);
		text.setY(y);
		text.setUnderline(underline);
		return text;
	}
}
