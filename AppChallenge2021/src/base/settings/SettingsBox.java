package base.settings;

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
				new GeneralSettingsPane(),
				new AnimationSettingsPane()
		);
	}
	
}
