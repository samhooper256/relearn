package base.settings;

import static base.settings.Utils.createCheckBox;

/**
 * 
 * @author Sam Hooper
 *
 */
public class AnimationSettingsPaneContent extends SettingsTitledPaneContent {

	AnimationSettingsPaneContent() {
		vBox.getChildren().addAll(
				createCheckBox("Time animations in practice pane", AnimationSettings.get().timeInPracticePane())
		);
	}
	
}
