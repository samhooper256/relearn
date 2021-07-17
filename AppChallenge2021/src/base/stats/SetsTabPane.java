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
public final class SetsTabPane extends StatsTabPane {
	
	private static final SetsTabPane INSTANCE = new SetsTabPane();
	
	public static SetsTabPane get() {
		return INSTANCE;
	}
	
	private SetsTabPane() {
		for(ProblemSet set : ProblemSet.all())
			createAndAddTab(set);
		ProblemSet.all().addAddListener(this::createAndAddTab);
		ProblemSet.all().addRemoveListener(this::removeTabForSetOrThrow);
	}
	
	private void createAndAddTab(ProblemSet set) {
		getTabs().add(createTab(set));
	}
	
	private SetTab createTab(ProblemSet set) {
		SetTab tab = new SetTab(set);
		tab.setContent(new SetSingleTabContent(set));
		return tab;
	}
	
	private void removeTabForSetOrThrow(ProblemSet set) {
		getTabs().remove(tabForSetOrThrow(set));
	}
	
	SetTab tabForSetOrThrow(ProblemSet set) {
		for(Tab t : getTabs())
			if(t instanceof SetTab st && st.set() == set)
				return st;
		throw new IllegalArgumentException(String.format("The SetsTabPane does not contain the set: %s", set));
	}
	
	void updateAllStats() {
		for(Tab t : getTabs())
			if(t instanceof SetTab st)
				st.content().updateStats();
	}
	
}
