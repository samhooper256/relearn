package base.graphics;

import base.Main;
import javafx.animation.*;

/**
 * 
 * @author Sam Hooper
 *
 */
final class SetsMainMenuButton extends MainMenuButton {
	
	public static final String TEXT = "Sets";
	
	private final ScaleTransition scale;
	
	SetsMainMenuButton() {
		super(Main.SETS_ICON, TEXT);
		scale = new ScaleTransition(SLIDE_DURATION, iconView());
		initScale();
	}
	
	private void initScale() {
		scale.setInterpolator(Interpolator.EASE_BOTH);
		scale.setFromX(1);
		scale.setFromY(1);
		scale.setToX(1.2);
		scale.setToY(1.2);
		hoverAnimations.add(scale);
	}
}
