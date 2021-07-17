/**
 * 
 */
package base.stats;

import base.sets.ProblemSet;

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
