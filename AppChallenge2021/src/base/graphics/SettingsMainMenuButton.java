/**
 * 
 */
package base.graphics;

import base.Main;
import javafx.animation.*;

/**
 * @author Sam Hooper
 *
 */
final class SettingsMainMenuButton extends MainMenuButton {

	private static final String TEXT = "Settings";
	public static final double TO_ANGLE = 20;
	
	private final RotateTransition spin;
	
	SettingsMainMenuButton() {
		super(Main.SETTINGS_ICON, TEXT);
		spin = new RotateTransition(SLIDE_DURATION, iconView());
		initSpin();
	}
	
	private void initSpin() {
		spin.setInterpolator(Interpolator.EASE_BOTH);
		spin.setFromAngle(0);
		spin.setToAngle(TO_ANGLE);
		hoverAnimations.add(spin);
	}
	
}
