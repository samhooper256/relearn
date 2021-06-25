/**
 * 
 */
package base.stats;

/**
 * @author Sam Hooper
 *
 */
public class TopicTab extends StatsTab {
	
	public TopicTab(String text) {
		super(text);
	}
	
	public TopicTabContent content() {
		return (TopicTabContent) getContent();
	}
	
}
