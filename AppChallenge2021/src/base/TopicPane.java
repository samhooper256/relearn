/**
 * 
 */
package base;

import javafx.scene.control.TitledPane;
import topics.Topic;

/**
 * @author Sam Hooper
 *
 */
public final class TopicPane extends TitledPane {
	
	private final Topic topic;
	private final IntField field;
	
	public static TopicPane of(Topic topic) {
		return new TopicPane(topic);
	}
	
	private TopicPane(Topic topic) {
		this.topic = topic;
		this.field = new IntField();
		this.setText("TITLE TIME!");
		setGraphic(this.field);
	}
	
}
