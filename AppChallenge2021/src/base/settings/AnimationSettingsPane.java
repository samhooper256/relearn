package base.settings;

import javafx.scene.control.TitledPane;

/**
 * 
 * @author Sam Hooper
 *
 */
final class AnimationSettingsPane extends TitledPane {
	
	
	AnimationSettingsPane() {
		setText("Animations");
		getStyleClass().add(SettingsPane.TITLED_PANE_CSS);
		setContent(new AnimationSettingsPaneContent());
	}
	
}
