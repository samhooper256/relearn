/**
 * 
 */
package base.stats;

import base.Named;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.*;

/**
 * @author Sam Hooper
 *
 */
public class TopicTabContent extends StatsTabContent implements Named {
	
	private static final String TOPIC_TAB_CONTENT_CSS = "topic-tab-content";
			
	private final String topicName;
	private final StackPane pieHolder;
	
	public TopicTabContent(String topicName) {
		super(topicName);
		this.topicName = topicName;
		if(Data.hasDoneProblemFromTopic(topicName))
			pieHolder = new StackPane(new AccuracyPie(Data.statsForTopic(topicName)));
		else
			pieHolder = new StackPane(new DeadPie());
		vBox.getChildren().add(pieHolder);
		VBox.setVgrow(pieHolder, Priority.ALWAYS);
		getStyleClass().add(TOPIC_TAB_CONTENT_CSS);
	}
	
	public void updateStats() {
		ReadOnlyStats stats = Data.statsForTopic(topicName);
		updateOverallAccuracy(stats);
		if(stats.isEmpty())
			setPie(new DeadPie());
		else
			setPie(new AccuracyPie(stats));
	}
	
	private void setPie(PieChart newPie) {
		pieHolder.getChildren().set(0, newPie);
	}

	@Override
	public String name() {
		return topicName;
	}
	
}
