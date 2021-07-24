package base.settings;

import base.graphics.FadePopup;
import javafx.scene.layout.StackPane;

public class ConversionChart extends FadePopup {

	private static final String CONVERSION_CHART_CSS = "conversion-chart";
	
	ConversionChart(StackPane over) {
		super(over);
		setGlassCloseable(true);
		setMaxSize(DEFAULT_WIDTH * 2, DEFAULT_HEIGHT * 2);
		getStyleClass().add(CONVERSION_CHART_CSS);
	}
	
}
