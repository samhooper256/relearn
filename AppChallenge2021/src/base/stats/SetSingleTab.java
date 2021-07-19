/**
 * 
 */
package base.stats;

import base.sets.ProblemSet;

/**
 * @author Sam Hooper
 *
 */
public class SetSingleTab extends StatsTab {
	
	private final ProblemSet set;
	
	public SetSingleTab(ProblemSet set) {
		super(set.name());
		this.set = set;
	}
	
	ProblemSet set() {
		return set;
	}
	
}
