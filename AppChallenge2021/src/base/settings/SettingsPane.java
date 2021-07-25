package base.settings;

import base.Main;
import base.graphics.BackArrow;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * 
 * @author Sam Hooper
 *
 */
public final class SettingsPane extends StackPane {
	
	private static final String
			SETTINGS_PANE_CSS = "settings-pane",
			HEADER_CSS = "header",
			TITLE_CSS = "title",
			VBOX_CSS = "vbox";
	
	private static final SettingsPane INSTANCE = new SettingsPane();
	
	public static SettingsPane get() {
		return INSTANCE;
	}
	
	private final BackArrow backArrow;
	private final Button conversionChartButton;
	private final ConversionChart conversionChart;
	private final HBox header;
	private final Label title;
	private final VBox vBox, helpBox;
	private final SettingsBox settingsBox;
	
	private SettingsPane() {
		backArrow = new BackArrow();
		title = new Label("Settings");
		header = new HBox(backArrow, title);
		settingsBox = new SettingsBox();
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
		initHelpBox();
	}

	private void initHeader() {
		header.getStyleClass().add(HEADER_CSS);
		title.getStyleClass().add(TITLE_CSS);
		initBackArrow();
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
