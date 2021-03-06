package base.stats;

import javafx.scene.Node;
import javafx.scene.layout.*;

final class TopicOverallTabContent extends StatsTabContent {
	
	private static final String
			TOPIC_OVERALL_TAB_CONTENT_CSS = "topic-overall-tab-content",
			GRID_PANE_CSS = "grid-pane";
	
	private final TopicOverallPie topicPie;
	private final StackPane topicPieHolder;
	private final GridPane gridPane;
	private final AccuracyDisplay accuracy;
	private final HBox hBox;
	private final TopicOverallPieLegend legend;
	
	private AccuracyDisplay deadPie;
	
	TopicOverallTabContent() {
		super("Overall");
		topicPie = new TopicOverallPie();
		topicPieHolder = new StackPane(topicPie);
		accuracy = new AccuracyDisplay();
		gridPane = new GridPane();
		legend = new TopicOverallPieLegend();
		hBox = new HBox(legend, gridPane);
		initHBox();
		vBox.getChildren().add(hBox);
		VBox.setVgrow(hBox, Priority.ALWAYS);
		getStyleClass().add(TOPIC_OVERALL_TAB_CONTENT_CSS);
	}


	private void initHBox() {
		HBox.setHgrow(gridPane, Priority.ALWAYS);
		initGridPane();
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

	private AccuracyDisplay deadPie() {
		if(deadPie == null)
			deadPie = new AccuracyDisplay();
		return deadPie;
	}
	
	@Override
	void updateStats() {
		legend.update();
		updateTopicPie();
		updateOverallAccuracy(Data.overall());
	}


	private void updateTopicPie() {
		if(Data.overall().isEmpty())
			setLeft(deadPie());
		else
			setLeft(topicPie);
		topicPie.update(Data.mapByTopics());
	}

	private void setLeft(Node left) {
		topicPieHolder.getChildren().set(0, left);
	}
	
	@Override
	protected void updateOverallAccuracy(ReadOnlyAccuracyStats stats) {
		super.updateOverallAccuracy(stats);
		accuracy.setAccuracy(Data.overall());
	}
	
}
