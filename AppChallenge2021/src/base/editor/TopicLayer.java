package base.editor;

import base.Verifiable;
import base.sets.ProblemSet;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import topics.Topic;

/**
 * 
 * @author Sam Hooper
 *
 */
public class TopicLayer extends HBox implements Verifiable {
	
	public enum Mode {
		NORMAL, REMOVAL;
	}
	
	private static final String
		ADD_TEXT = "+ Add Topic",
		NORMAL_MODE_REMOVE_TEXT = "- Remove Topic",
		REMOVAL_MODE_REMOVE_TEXT = "Done";
	
	private static final String
			TOPIC_LAYER_CSS = "topic-layer",
			TOPIC_MANAGEMENT_BOX_CSS = "topic-management-box",
			ADD_TOPIC_BUTTON_CSS = "add-topic-button",
			REMOVE_TOPIC_BUTTON_CSS = "remove-topic-button";
	
	private static void unsupported(Mode newMode) {
		throw new UnsupportedOperationException(String.format("Unsupported mode: %s", newMode));
	}
	
	private final VBox topicManagementBox;
	private final Button addButton, removeButton;
	private final TopicPaneContainer topicPaneContainer;
	
	private Mode mode;
	
	public TopicLayer() {
		mode = Mode.NORMAL;
		
		addButton = new Button(ADD_TEXT);
		removeButton = new Button(NORMAL_MODE_REMOVE_TEXT);
		topicManagementBox = new VBox(addButton, removeButton);
		initTopicManagementBox();
		
		topicPaneContainer = new TopicPaneContainer();
		
		HBox.setHgrow(topicPaneContainer, Priority.ALWAYS);
		getChildren().addAll(topicManagementBox, topicPaneContainer);
		getStyleClass().add(TOPIC_LAYER_CSS);
	}
	
	private void initTopicManagementBox() {
		initAddButton();
		initRemoveButton();
		topicManagementBox.getStyleClass().add(TOPIC_MANAGEMENT_BOX_CSS);
	}
	
	private void initAddButton() {
		addButton.setOnAction(e -> addAction());
		addButton.getStyleClass().addAll(EditorPane.EDITOR_BUTTON_CSS, ADD_TOPIC_BUTTON_CSS);
	}
	
	private void addAction() {
		topicPaneContainer.hideNoTopicErrorIfShowing();
		EditorPane.get().fadeInTopicSelectionPane();
	}
	
	private void initRemoveButton() {
		removeButton.setOnAction(e -> removeAction());
		removeButton.getStyleClass().addAll(EditorPane.EDITOR_BUTTON_CSS, REMOVE_TOPIC_BUTTON_CSS);
	}
	
	private void removeAction() {
		switch(mode()) {
			case NORMAL -> setMode(Mode.REMOVAL);
			case REMOVAL -> setMode(Mode.NORMAL);
			default -> unsupported(mode());
		}
	}
	
	private void addTopicPaneFor(Topic topic) {
		topicPaneContainer.getChildren().add(TopicPane.of(topic));
	}

	void addTopicPanesFor(Iterable<Topic> topics) {
		for(Topic t : topics)
			addTopicPaneFor(t);
	}
	
	void removeTopicPane(TopicPane pane) {
		topicPaneContainer.removeTopicPane(pane);
	}
	
	void loadPanesFor(ProblemSet set) {
		clearTopicPanes();
		for(Topic t : set.config().topics())
			addTopicPaneFor(t);
	}
	
	private void clearTopicPanes() {
		topicPaneContainer.getChildren().clear();
	}
	
	private Mode mode() {
		return mode;
	}
	
	private void setMode(Mode newMode) {
		if(mode() == newMode)
			return;
		switch(newMode) {
			case REMOVAL -> setToRemovalMode();
			case NORMAL -> setToNormalMode();
			default -> unsupported(newMode);
		}
	}

	private void setToRemovalMode() {
		mode = Mode.REMOVAL;
		topicPaneContainer.showTrashCans();
		removeButton.setText(REMOVAL_MODE_REMOVE_TEXT);
	}
	
	private void setToNormalMode() {
		mode = Mode.NORMAL;
		topicPaneContainer.hideTrashCans();
		removeButton.setText(NORMAL_MODE_REMOVE_TEXT);
	}

	@Override
	public VerificationResult verify() {
		return topicPaneContainer.verify();
	}
	
}
