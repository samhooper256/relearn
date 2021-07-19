package base.settings;

import base.Main;
import base.graphics.BackArrow;
import javafx.beans.property.BooleanProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.*;

public final class SettingsPane extends StackPane {
	
	private static final SettingsPane INSTANCE = new SettingsPane();
	
	public static SettingsPane get() {
		return INSTANCE;
	}
	
	private static CheckBox createCheckBox(String name, BooleanProperty backingProperty) {
		CheckBox box = new CheckBox(name);
		box.setSelected(backingProperty.get());
		box.selectedProperty().bindBidirectional(backingProperty);
		return box;
	}
	
	private final BackArrow backArrow;
	private final HBox header;
	private final VBox vBox;
	
	private SettingsPane() {
		backArrow = new BackArrow();
		header = new HBox(backArrow);
		vBox = new VBox(header);
		initVBox();
		getChildren().add(vBox);
	}
	
	private void initBackArrow() {
		backArrow.setOnAction(this::backArrowAction);
	}
	
	private void initVBox() {
		initBackArrow();
		initSettings();
	}
	
	private void initSettings() {
		vBox.getChildren().addAll(
				createCheckBox("Don't show erase warning when editing a set", Settings.get().doNotShowEditWarning())
		);
	}
	
	private void backArrowAction() {
		Main.scene().showMainMenu();
	}
	
}
