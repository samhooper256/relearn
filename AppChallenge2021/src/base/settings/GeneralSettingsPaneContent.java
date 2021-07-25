package base.settings;

import static base.settings.Utils.createCheckBox;

/**
 * 
 * @author Sam Hooper
 *
 */
final class GeneralSettingsPaneContent extends SettingsTitledPaneContent {
	
	GeneralSettingsPaneContent() {
		vBox.getChildren().addAll(
				createCheckBox("Don't show erase warning when editing a set",
						GeneralSettings.get().doNotShowEditWarning())
		);
	}
	
}