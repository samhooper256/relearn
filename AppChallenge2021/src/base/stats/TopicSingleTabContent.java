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
		getChildren().add(display);
	}
	
	@Override
	void updateStats() {
		ReadOnlyStats stats = Data.statsForTopic(name);
		updateOverallAccuracy(stats);
		display.setAccuracy(stats);
	}
	
}
