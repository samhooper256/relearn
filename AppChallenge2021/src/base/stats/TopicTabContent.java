/**
 * 
 */
package base.stats;

import base.Named;

/**
 * @author Sam Hooper
 *
 */
abstract class TopicTabContent extends StatsTabContent implements Named {
	
	private static final String TOPIC_TAB_CONTENT_CSS = "topic-tab-content";
			
	private final String displayText;
	
	TopicTabContent(String displayText) {
		super(displayText);
		this.displayText = displayText;
		getStyleClass().add(TOPIC_TAB_CONTENT_CSS);
	}
	
	@Override
	public String name() {
		return displayText;
	}
	
}
