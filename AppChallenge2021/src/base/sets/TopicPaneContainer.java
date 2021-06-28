/**
 * 
 */
package base.sets;

import base.*;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * @author Sam Hooper
 *
 */
public class TopicPaneContainer extends VBox implements Verifiable {

	private static final String TOPIC_PANE_CONTAINER_CSS = "topic-pane-container";
	
	private final ErrorMessage noTopicError;
	
	public TopicPaneContainer() {
		noTopicError = new ErrorMessage("No topics selected");
		getStyleClass().add(TOPIC_PANE_CONTAINER_CSS);
	}
	
	public void showNoTopicError() {
		assert getChildren().isEmpty();
		getChildren().add(noTopicError);
	}
	
	public void hideNoTopicErrorIfShowing() {
		if(isShowingNoTopicError())
			hideNoTopicError();
	}
	
	public void hideNoTopicError() {
		assert getChildren().size() == 1 && getChildren().get(0) == noTopicError;
		getChildren().clear();
	}
	
	public boolean isShowingNoTopicError() {
		return getChildren().size() == 1 && getChildren().get(0) == noTopicError;
	}
	
	public int topicCount() {
		if(isShowingNoTopicError())
			return 0;
		return getChildren().size();
	}
	
	@Override
	public VerificationResult verify() {
		if(topicCount() > 0)
			return VerificationResult.success();
		showNoTopicError();
		return VerificationResult.failure();
	}
	
	public void removeTopicPane(TopicPane tp) {
		boolean removed = getChildren().remove(tp);
		assert removed;
	}
	
	public void showTrashCans() {
		setTrashCanVisibilities(true);
	}
	
	public void hideTrashCans() {
		setTrashCanVisibilities(false);
	}
	
	private void setTrashCanVisibilities(boolean visible) {
		for(Node n : getChildren())
			if(n instanceof TopicPane p)
				p.setTrashCanVisible(visible);
	}
	
}
