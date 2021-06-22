/**
 * 
 */
package base.stats;

import javafx.scene.control.Tab;

/**
 * @author Sam Hooper
 *
 */
public class TopicTab extends Tab {
	
	public TopicTab() {
		super();
	}
	
	public TopicTab(String text) {
		super(text);
	}
	
	public TopicTabContent content() {
		return (TopicTabContent) getContent();
	}
	
}
