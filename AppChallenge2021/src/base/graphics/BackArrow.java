package base.graphics;

import base.Main;

/**
 * @author Sam Hooper
 *
 */
public class BackArrow extends ImageButton {
	
	private static final String BACK_ARROW_CSS = "back-arrow";
	
	public BackArrow() {
		super(Main.BACK_ARROW);
		setOnMouseEntered(e -> setImage(Main.BACK_ARROW_HOVER));
		setOnMouseExited(e -> setImage(Main.BACK_ARROW));
		getStyleClass().add(BACK_ARROW_CSS);
	}
	
}