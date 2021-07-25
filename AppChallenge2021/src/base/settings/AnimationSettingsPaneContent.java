package base.settings;

import static base.settings.Utils.createCheckBox;

import javafx.scene.layout.*;

/**
 * 
 * @author Sam Hooper
 *
 */
public class AnimationSettingsPaneContent extends StackPane {

	private final VBox vBox;
	
	AnimationSettingsPaneContent() {
		vBox = new VBox(
				createCheckBox("Time animations in practice pane", AnimationSettings.get().timeInPracticePane())
		);
		getChildren().add(vBox);
	}
	
}
