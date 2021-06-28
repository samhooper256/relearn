/**
 * 
 */
package base.sets;

import javafx.scene.layout.VBox;

/**
 * @author Sam Hooper
 *
 */
public class TopicPaneContainer extends VBox {

	private static final String TOPIC_PANE_CONTAINER_CSS = "topic-pane-container";
	
	public TopicPaneContainer() {
		getStyleClass().add(TOPIC_PANE_CONTAINER_CSS);
	}
	
}
