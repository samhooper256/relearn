package base.stats;

import javafx.scene.layout.*;

final class TopicSingleTabContent extends StatsTabContent {
	
	private final AccuracyDisplay display;
	private final String name;
	
	TopicSingleTabContent(String topicName) {
		super(topicName);
		name = topicName;
		display = new AccuracyDisplay();
		VBox.setVgrow(display, Priority.ALWAYS);
		vBox.getChildren().add(display);
	}
	
	@Override
	void updateStats() {
		ReadOnlyAccuracyStats stats = Data.accuracyStatsForTopic(name);
		updateOverallAccuracy(stats);
		display.setAccuracy(stats);
	}
	
}
