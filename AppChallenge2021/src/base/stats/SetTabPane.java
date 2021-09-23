/**
 * 
 */
package base.stats;

import base.sets.ProblemSet;
import javafx.scene.control.Tab;

/**
 * @author Sam Hooper
 *
 */
public final class SetTabPane extends StatsTabPane {
	
	private static final SetTabPane INSTANCE = new SetTabPane();
	
	public static SetTabPane get() {
		return INSTANCE;
	}
	
	private final SetOverallTab overallTab;
	
	private SetTabPane() {
		overallTab = new SetOverallTab();
		getTabs().add(overallTab);
		for(ProblemSet set : ProblemSet.all())
			createAndAddTab(set);
		ProblemSet.all().addAddListener(this::setAdded);
		ProblemSet.all().addRemoveListener(this::setRemoved);
	}
	
	private void createAndAddTab(ProblemSet set) {
		getTabs().add(createTab(set));
	}
	
	private SetSingleTab createTab(ProblemSet set) {
		SetSingleTab tab = new SetSingleTab(set);
		tab.setContent(new SetSingleTabContent(set));
		return tab;
	}
	
	private void removeTabForSetOrThrow(ProblemSet set) {
		getTabs().remove(tabForSetOrThrow(set));
	}
	
	SetSingleTab tabForSetOrThrow(ProblemSet set) {
		for(Tab t : getTabs())
			if(t instanceof SetSingleTab && ((SetSingleTab) t).set() == set)
				return ((SetSingleTab) t);
		throw new IllegalArgumentException(String.format("The SetsTabPane does not contain the set: %s", set));
	}

	void updateAllStats() {
		for(Tab t : getTabs())
			if(t instanceof StatsTab)
				((StatsTab) t).content().updateStats();
	}

	private void setAdded(ProblemSet set) {
		createAndAddTab(set);
		overallTab.content().setAdded(set);
		
	}
	
	private void setRemoved(ProblemSet set) {
		removeTabForSetOrThrow(set);
		overallTab.content().setRemoved(set);
	}
}
