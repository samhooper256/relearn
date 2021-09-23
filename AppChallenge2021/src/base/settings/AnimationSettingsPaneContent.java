package base.settings;

import static base.settings.Utils.createCheckBox;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

/**
 * 
 * @author Sam Hooper
 *
 */
public class AnimationSettingsPaneContent extends SettingsTitledPaneContent {

	private final Button selectAll, deselectAll;
	private final HBox buttonBox;
	
	AnimationSettingsPaneContent() {
		selectAll = new Button("Select all");
		deselectAll = new Button("Deselect all");
		buttonBox = new HBox(selectAll, deselectAll);
		initButtonBox();
		vBox.getChildren().addAll(
				buttonBox,
				createCheckBox("Popup animations", AnimationSettings.get().popups()),
				createCheckBox("Timer animations (in practice pane)", AnimationSettings.get().timeInPracticePane()),
				createCheckBox("Accuracy bar animations (in practice pane)", AnimationSettings.get().accuracyBar()),
				createCheckBox("Progress bar animations (in practice pane)", AnimationSettings.get().progressBar())
		);
	}

	private void initButtonBox() {
		initSelectAllButton();
		initDeselectAllButton();
		buttonBox.getStyleClass().add(BUTTON_BOX_CSS);
	}
	
	private void initSelectAllButton() {
		selectAll.setOnAction(e -> selectAllAction());
	}
	
	private void selectAllAction() {
		setCheckBoxesSelected(true);
	}

	private void initDeselectAllButton() {
		deselectAll.setOnAction(e -> deselectAllAction());
	}
	
	private void deselectAllAction() {
		setCheckBoxesSelected(false);
	}
	
	private void setCheckBoxesSelected(boolean selected) {
		for(Node n : vBox.getChildren())
			if(n instanceof CheckBox)
				((CheckBox) n).setSelected(selected);
	}
	
}