package base.stats;

import fxutils.PolarizedPane;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

abstract class StatsTabContent extends StackPane {
	
	private static final String
			STATS_TAB_CONTENT_CSS = "stats-tab-content",
			TITLE_CSS = "title",
			OVERALL_ACCURACY_CSS = "overall-accuracy";
	
	private final PolarizedPane header;
	private final Label title, overallAccuracy;
	protected final VBox vBox;
	
	protected StatsTabContent(String title) {
		this.title = new Label(title);
		overallAccuracy = new Label();
		header = new PolarizedPane(this.title, overallAccuracy);
		initHeader();
		vBox = new VBox(header);
		
		getChildren().add(vBox);
		getStyleClass().add(STATS_TAB_CONTENT_CSS);
	}
	
	private void initHeader() {
		title.getStyleClass().add(TITLE_CSS);
		overallAccuracy.getStyleClass().add(OVERALL_ACCURACY_CSS);
	}
	
	protected void updateOverallAccuracy(ReadOnlyStats stats) {
		if(stats.isEmpty())
			overallAccuracy.setText("");
		else
			overallAccuracy.setText(String.format("%.1f%%", 100d * stats.accuracy()));
	}

	abstract void updateStats();
	
}
