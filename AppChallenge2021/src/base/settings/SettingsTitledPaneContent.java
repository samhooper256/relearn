package base.settings;

import javafx.scene.layout.*;

abstract class SettingsTitledPaneContent extends StackPane {
	
	protected static final String BUTTON_BOX_CSS = "button-box";
	
	private static final String VBOX_CSS = "vbox";
	
	protected final VBox vBox;
	
	SettingsTitledPaneContent() {
		vBox = new VBox();
		vBox.getStyleClass().add(VBOX_CSS);
		getChildren().add(vBox);
	}
	
}
