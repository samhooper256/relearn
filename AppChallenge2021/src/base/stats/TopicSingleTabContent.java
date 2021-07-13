package base.stats;

import javafx.scene.layout.*;

final class TopicSingleTabContent extends TopicTabContent {
	
	private final AccuracyDisplay display;
	
	TopicSingleTabContent(String topicName) {
		super(topicName);
		display = new AccuracyDisplay();
		VBox.setVgrow(display, Priority.ALWAYS);
		getChildren().add(display);
	}
	
	@Override
	void updateStats() {
		ReadOnlyStats stats = Data.statsForTopic(name());
		updateOverallAccuracy(stats);
		display.setAccuracy(stats);
	}
	
}
