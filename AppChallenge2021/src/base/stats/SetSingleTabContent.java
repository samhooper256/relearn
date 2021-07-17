/**
 * 
 */
package base.stats;

import base.sets.ProblemSet;
import javafx.scene.layout.*;

/**
 * @author Sam Hooper
 *
 */
public class SetSingleTabContent extends StatsTabContent {
	
	private final ProblemSet set;
	private final TopicAccuracyDistribution dist;
	
	public SetSingleTabContent(ProblemSet set) {
		super(set.name());

		dist = new TopicAccuracyDistribution(set);
		
		vBox.getChildren().add(dist);
		VBox.setVgrow(dist, Priority.ALWAYS);
		this.set = set;
	}
	
	public ProblemSet set() {
		return set;
	}

	@Override
	void updateStats() {
		dist.update(Data.mapForSet(set));
	}
	
}
