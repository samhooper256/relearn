package base.settings;

import base.Main;
import base.graphics.BackArrow;
import javafx.beans.property.BooleanProperty;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public final class SettingsPane extends StackPane {
	
	private static final String
			SETTINGS_PANE_CSS = "settings-pane",
			HEADER_CSS = "header",
			TITLE_CSS = "title",
			VBOX_CSS = "vbox",
			SETTINGS_BOX_CSS = "settings-box";
	
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
	private final Button conversionChartButton;
	private final ConversionChart conversionChart;
	private final HBox header;
	private final Label title;
	private final VBox vBox, settingsBox, helpBox;
	
	private SettingsPane() {
		backArrow = new BackArrow();
		title = new Label("Settings");
		header = new HBox(backArrow, title);
		settingsBox = new VBox();
		conversionChartButton = new Button("Show Conversion Chart");
		conversionChart = new ConversionChart(this);
		helpBox = new VBox(conversionChartButton);
		vBox = new VBox(header, settingsBox, helpBox);
		initVBox();
		getStyleClass().add(SETTINGS_PANE_CSS);
		getChildren().add(vBox);
	}
	
	private void initBackArrow() {
		backArrow.setOnAction(this::backArrowAction);
	}
	
	private void backArrowAction() {
		Main.scene().showMainMenu();
	}
	
	private void initVBox() {
		vBox.getStyleClass().add(VBOX_CSS);
		initHeader();
		initSettingsBox();
		initHelpBox();
	}

	private void initHeader() {
		header.getStyleClass().add(HEADER_CSS);
		title.getStyleClass().add(TITLE_CSS);
		initBackArrow();
	}
	
	private void initSettingsBox() {
		settingsBox.getStyleClass().add(SETTINGS_BOX_CSS);
		settingsBox.getChildren().addAll(
				createCheckBox("Don't show erase warning when editing a set", Settings.get().doNotShowEditWarning())
		);
	}
	
	private void initHelpBox() {
		initConversionChartButton();
	}
	
	private void initConversionChartButton() {
		conversionChartButton.setOnAction(e -> conversionChartAction());
	}
	
	private void conversionChartAction() {
		conversionChart.fadeIn();
	}
}
