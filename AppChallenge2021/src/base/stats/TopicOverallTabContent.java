package base.stats;

import javafx.scene.layout.*;

final class TopicOverallTabContent extends TopicTabContent {
	
	private static final String
			TOPIC_OVERALL_TAB_CONTENT_CSS = "topic-overall-tab-content",
			GRID_PANE_CSS = "grid-pane";
	
	private final TopicOverallPie topicPie;
	private final StackPane topicPieHolder;
	private final GridPane gridPane;
	private final AccuracyDisplay accuracy;
	
	TopicOverallTabContent() {
		super("Overall");
		topicPie = new TopicOverallPie();
		topicPieHolder = new StackPane(topicPie);
		accuracy = new AccuracyDisplay();
		gridPane = new GridPane();
		initGridPane();
		vBox.getChildren().add(gridPane);
		VBox.setVgrow(gridPane, Priority.ALWAYS);
		getStyleClass().add(TOPIC_OVERALL_TAB_CONTENT_CSS);
	}
	

	private void initGridPane() {
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(100);
		
		ColumnConstraints c1 = new ColumnConstraints(), c2 = new ColumnConstraints();
		c1.setPercentWidth(50);
		c2.setPercentWidth(50);
		
		gridPane.getRowConstraints().add(r);
		gridPane.getColumnConstraints().addAll(c1, c2);
		gridPane.getStyleClass().add(GRID_PANE_CSS);
		
		gridPane.add(topicPieHolder, 0, 0);
		gridPane.add(accuracy, 1, 0);
	}

	@Override
	void updateStats() {
		topicPie.update(Data.mapByTopics());
		updateOverallAccuracy(Data.overall());
	}

	@Override
	protected void updateOverallAccuracy(ReadOnlyStats stats) {
		super.updateOverallAccuracy(stats);
		accuracy.setAccuracy(Data.overall());
	}
	
}
