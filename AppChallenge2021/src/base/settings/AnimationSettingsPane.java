package base.settings;

import javafx.scene.control.TitledPane;

/**
 * 
 * @author Sam Hooper
 *
 */
final class AnimationSettingsPane extends TitledPane {
	
	
	AnimationSettingsPane() {
		setContent(new AnimationSettingsPaneContent());
	}
	
}
