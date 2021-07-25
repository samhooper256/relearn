package base.settings;

import static base.settings.Utils.createCheckBox;

import javafx.scene.layout.VBox;

/**
 * 
 * @author Sam Hooper
 *
 */
final class SettingsBox extends VBox {

	private static final String SETTINGS_BOX_CSS = "settings-box";
	
	SettingsBox() {
		getStyleClass().add(SETTINGS_BOX_CSS);
		getChildren().addAll(
				createCheckBox("Don't show erase warning when editing a set", Settings.get().doNotShowEditWarning()),
				new AnimationSettingsPane()
		);
	}
	
}
