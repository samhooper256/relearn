package base.settings;

import javafx.scene.layout.*;

abstract class SettingsTitledPaneContent extends StackPane {
	
	protected final VBox vBox;
	
	SettingsTitledPaneContent() {
		vBox = new VBox();
		getChildren().add(vBox);
	}
	
}
