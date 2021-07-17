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
	
	public SetTab(ProblemSet set) {
		super(set.name());
	}
	
	public SetSingleTabContent content() {
		return (SetSingleTabContent) getContent();
	}
}
