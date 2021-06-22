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
			Tab t = new Tab("");
			t.setClosable(false);
			Label l = new Label(name);
			l.setRotate(90);
			StackPane sp = new StackPane(new Group(l));
			sp.setRotate(90);
			t.setGraphic(sp);
			t.setContent(new TopicTabContent(name));
			getTabs().add(t);
		}
		
	}
}
