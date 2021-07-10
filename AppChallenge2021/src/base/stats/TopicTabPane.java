/**
 * 
 */
package base.stats;

import javafx.scene.control.*;
import topics.TopicUtils;

/**
 * @author Sam Hooper
 *
 */
public final class TopicTabPane extends StatsTabPane {
	
	private static final String TOPIC_TAB_PANE_CSS = "topic-tab-pane";
	private static final TopicTabPane INSTANCE = new TopicTabPane();
	
	public static TopicTabPane get() {
		return INSTANCE;
	}
	
	private TopicTabPane() {
		for(String name : TopicUtils.allNames())
			getTabs().add(createTab(name));
		getStyleClass().add(TOPIC_TAB_PANE_CSS);
	}

	private TopicTab createTab(String name) {
		TopicTab t = new TopicTab(name);
		t.setContent(new TopicTabContent(name));
		return t;
	}
	
	public void updateAllStats() {
		for(Tab t : getTabs())
			if(t instanceof TopicTab tt)
				tt.content().updateStats();
	}
}
