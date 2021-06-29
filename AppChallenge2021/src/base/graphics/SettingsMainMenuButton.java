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
class SettingsMainMenuButton extends MainMenuButton {

	private static final String TEXT = "Settings";

	private final RotateTransition spin;
	public SettingsMainMenuButton() {
		super(Main.settingsIcon(), TEXT);
		spin = new RotateTransition(SLIDE_DURATION.multiply(2), iconView());
		initSpin();
	}
	
	private void initSpin() {
		spin.setInterpolator(Interpolator.EASE_OUT);
		spin.setFromAngle(0);
		spin.setToAngle(30);
		hoverAnimations.add(spin);
	}
	
}
