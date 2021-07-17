/**
 * 
 */
package base.stats;

import base.sets.ProblemSet;

/**
 * @author Sam Hooper
 *
 */
public class SetTab extends StatsTab {
	
	private final ProblemSet set;
	
	public SetTab(ProblemSet set) {
		super(set.name());
		this.set = set;
	}
	
	public SetSingleTabContent content() {
		return (SetSingleTabContent) getContent();
	}
	
	ProblemSet set() {
		return set;
	}
	
}
