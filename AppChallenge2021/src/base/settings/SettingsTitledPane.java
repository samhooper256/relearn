package base.settings;

import javafx.scene.control.TitledPane;

class SettingsTitledPane extends TitledPane {

	private static final String SETTINGS_TITLED_PANE_CSS = "settings-titled-pane";
	
	SettingsTitledPane(String text, SettingsTitledPaneContent content) {
		setText(text);
		getStyleClass().add(SETTINGS_TITLED_PANE_CSS);
		setContent(content);
	}
	
}
