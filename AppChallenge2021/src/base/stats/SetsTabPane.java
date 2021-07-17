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
			getTabs().add(createTab(set));
	}
	
	private SetTab createTab(ProblemSet set) {
		SetTab tab = new SetTab(set);
		tab.setContent(new SetSingleTabContent(set));
		return tab;
	}
	
	void updateAllStats() {
		for(Tab t : getTabs())
			if(t instanceof SetTab st)
				st.content().updateStats();
	}
	
}
