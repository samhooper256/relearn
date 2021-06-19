/**
 * 
 */
package base;

import javafx.scene.control.TitledPane;
import javafx.scene.layout.StackPane;
import topics.Topic;

/**
 * @author Sam Hooper
 *
 */
public final class TopicPane extends TitledPane {
	
	private static final int INT_FIELD_WIDTH = 35;
	
	private final Topic topic;
	private final IntField field;
	private final StackPane content;
	
	public static TopicPane of(Topic topic) {
		return new TopicPane(topic);
	}
	
	private TopicPane(Topic topic) {
		this.topic = topic;
		field = new IntField(INT_FIELD_WIDTH);
		content = new StackPane();
		
		setContent(content);
		setText(topic.name());
		setGraphic(this.field);
	}
	
}
