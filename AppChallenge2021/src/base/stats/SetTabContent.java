/**
 * 
 */
package base.stats;

import base.sets.ProblemSet;

/**
 * @author Sam Hooper
 *
 */
public class SetTabContent extends StatsTabContent {
	
	private final ProblemSet set;
	
	public SetTabContent(ProblemSet set) {
		this.set = set;
	}
	
	public ProblemSet set() {
		return set;
	}
	
}
