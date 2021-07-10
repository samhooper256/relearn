/**
 * 
 */
package base.stats;

/**
 * @author Sam Hooper
 *
 */
public class TopicTab extends StatsTab {
	
	private static final String TOPIC_TAB_CSS = "topic-tab";
	
	public TopicTab(String text) {
		super(text);
		getStyleClass().add(TOPIC_TAB_CSS);
	}
	
	public TopicTabContent content() {
		return (TopicTabContent) getContent();
	}
	
}
