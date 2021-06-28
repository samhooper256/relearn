package base;

import base.sets.ImageButton;

/**
 * @author Sam Hooper
 *
 */
public class BackArrow extends ImageButton {
	
	public BackArrow() {
		super(Main.backArrowImage());
	}
	
	public BackArrow(Runnable action) {
		super(Main.backArrowImage(), action);
	}
	
}