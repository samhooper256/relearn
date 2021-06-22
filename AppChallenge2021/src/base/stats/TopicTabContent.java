/**
 * 
 */
package base.stats;

import base.Named;
import javafx.scene.layout.StackPane;

/**
 * @author Sam Hooper
 *
 */
public class TopicTabContent extends StackPane implements Named {
	
	private final String topicName;
	
	public TopicTabContent(String topicName) {
		this.topicName = topicName;
		if(Data.hasDoneProblemFromTopic(topicName)) {
			ReadOnlyStats stats = Data.statsForTopic(topicName);
			AccuracyPie pie = new AccuracyPie(stats);
			getChildren().add(pie);
		}
	}

	@Override
	public String name() {
		return topicName;
	}
	
}
