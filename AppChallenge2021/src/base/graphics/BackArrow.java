package base.graphics;

import base.Main;

/**
 * @author Sam Hooper
 *
 */
public class BackArrow extends ImageButton {
	
	private static final String BACK_ARROW_CSS = "back-arrow";
	
	public BackArrow() {
		super(Main.BACK_ARROW_IMAGE);
		getStyleClass().add(BACK_ARROW_CSS);
	}
	
}