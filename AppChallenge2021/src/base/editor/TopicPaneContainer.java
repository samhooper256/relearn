/**
 * 
 */
package base.editor;

import java.util.stream.Stream;

import base.*;
import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * @author Sam Hooper
 *
 */
public class TopicPaneContainer extends VBox implements IndependentlyVerifiable {

	private static final String TOPIC_PANE_CONTAINER_CSS = "topic-pane-container";
	
	private final ErrorMessage noTopicError;
	private final IntegerProperty paneCountProperty;
	
	public TopicPaneContainer() {
		noTopicError = new ErrorMessage("No topics selected");
		paneCountProperty = new SimpleIntegerProperty(0);
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
		if(topicCount() <= 0) {
			showNoTopicError();
			return VerificationResult.failure();
		}
		return verifyTopicPanes();
	}
	
	private VerificationResult verifyTopicPanes() {
		VerificationResult result = VerificationResult.success();
		for(Node node : getChildren())
			if(node instanceof TopicPane p)
				result = result.and(p.verify());
		return result;
	}
	
	void addTopicPane(TopicPane tp) {
		getChildren().add(tp);
		paneCountProperty.set(paneCountProperty.get() + 1);
	}
	
	void removeTopicPane(TopicPane tp) {
		boolean removed = getChildren().remove(tp);
		assert removed;
		paneCountProperty.set(paneCountProperty.get() - 1);
	}
	
	void showTrashCans() {
		setTrashCanVisibilities(true);
	}
	
	void hideTrashCans() {
		setTrashCanVisibilities(false);
	}
	
	private void setTrashCanVisibilities(boolean visible) {
		for(Node n : getChildren())
			if(n instanceof TopicPane p)
				p.setTrashCanVisible(visible);
	}
	
	Stream<TopicPane> topicPanes() {
		return getChildren().stream().filter(n -> n instanceof TopicPane).map(n -> (TopicPane) n);
	}
	
	IntegerProperty paneCountProperty() {
		return paneCountProperty;
	}
	
}
