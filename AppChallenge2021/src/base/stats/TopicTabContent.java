/**
 * 
 */
package base.stats;

import base.Named;
import javafx.scene.chart.PieChart;

/**
 * @author Sam Hooper
 *
 */
public class TopicTabContent extends StatsTabContent implements Named {
	
	private final String topicName;
	private PieChart pie;
	
	public TopicTabContent(String topicName) {
		this.topicName = topicName;
		if(Data.hasDoneProblemFromTopic(topicName)) {
			ReadOnlyStats stats = Data.statsForTopic(topicName);
			AccuracyPie pie = new AccuracyPie(stats);
			
			getChildren().add(pie);
		}
	}
	
	public void updateStats() {
		ReadOnlyStats stats = Data.statsForTopic(topicName);
		if(stats.isEmpty())
			setPie(new DeadPie());
		else
			setPie(new AccuracyPie(stats));
	}
	
	private void setPie(PieChart newPie) {
		getChildren().remove(pie());
		pie = newPie;
		getChildren().add(newPie);
	}
	
	private PieChart pie() {
		return pie;
	}

	@Override
	public String name() {
		return topicName;
	}
	
}
