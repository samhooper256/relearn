package base.stats;

import javafx.scene.layout.*;

final class TopicOverallTabContent extends TopicTabContent {
	
	private final TopicOverallPie pie;
	private final StackPane pieHolder;
	
	TopicOverallTabContent() {
		super("Overall");
		pie = new TopicOverallPie();
		pieHolder = new StackPane(pie);
		VBox.setVgrow(pieHolder, Priority.ALWAYS);
		getChildren().add(pieHolder);
	}

	@Override
	void updateStats() {
		pie.update(Data.mapByTopics());
	}
	
}
