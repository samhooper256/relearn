/**
 * 
 */
package base.stats;

import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import topics.TopicUtils;

/**
 * @author Sam Hooper
 *
 */
public final class TopicTabPane extends StatsTabPane {
	
	private static final TopicTabPane INSTANCE = new TopicTabPane();
	
	public static TopicTabPane get() {
		return INSTANCE;
	}
	
	private TopicTabPane() {
		super();
		for(String name : TopicUtils.allNames()) {
			TopicTab t = new TopicTab("");
			initTab(name, t);
			getTabs().add(t);
		}
		
	}

	private void initTab(String name, TopicTab t) {
		t.setClosable(false);
		Label l = new Label(name);
		l.setRotate(90);
		StackPane sp = new StackPane(new Group(l));
		sp.setRotate(90);
		t.setGraphic(sp);
		t.setContent(new TopicTabContent(name));
	}
	
	public void updateAllStats() {
		for(Tab t : getTabs())
			if(t instanceof TopicTab tt)
				tt.content().updateStats();
	}
}
