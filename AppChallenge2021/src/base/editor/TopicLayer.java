package base.editor;

import base.IndependentlyVerifiable;
import base.sets.ProblemSet;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import topics.Topic;

/**
 * 
 * @author Sam Hooper
 *
 */
public class TopicLayer extends HBox implements IndependentlyVerifiable {
	
	public enum Mode {
		NORMAL, REMOVAL;
	}
	
	private static final String
		ADD_TEXT = "+ Add Topic",
		NORMAL_MODE_REMOVE_TEXT = "- Remove Topic",
		REMOVAL_MODE_REMOVE_TEXT = "Done",
		COLLAPSE_ALL_TEXT = "Collapse all",
		EXPAND_ALL_TEXT = "Expand all";
	private static final String
			TOPIC_LAYER_CSS = "topic-layer",
			TOPIC_MANAGEMENT_BOX_CSS = "topic-management-box",
			ADD_TOPIC_BUTTON_CSS = "add-topic-button",
			REMOVE_TOPIC_BUTTON_CSS = "remove-topic-button",
			EXPANSION_BUTTON_CSS = "expansion-button",
			SCROLL_CSS = "scroll";
	
	private static final double TOPIC_MANAGEMENT_BOX_WIDTH = 120;
	
	private static void unsupported(Mode newMode) {
		throw new UnsupportedOperationException(String.format("Unsupported mode: %s", newMode));
	}
	
	private final VBox topicManagementBox;
	private final Button addButton, removeButton, collapseAllButton, expandAllButton;
	private final ScrollPane scroll;
	private final TopicPaneContainer topicPaneContainer;
	
	private Mode mode;
	
	public TopicLayer() {
		mode = Mode.NORMAL;
		
		addButton = new Button(ADD_TEXT);
		removeButton = new Button(NORMAL_MODE_REMOVE_TEXT);
		collapseAllButton = new Button(COLLAPSE_ALL_TEXT);
		expandAllButton = new Button(EXPAND_ALL_TEXT);
		topicManagementBox = new VBox(addButton, removeButton, collapseAllButton, expandAllButton);
		initTopicManagementBox();
		
		topicPaneContainer = new TopicPaneContainer();
		scroll = new ScrollPane(topicPaneContainer);
		initScroll();
		
		HBox.setHgrow(scroll, Priority.ALWAYS);
		getChildren().addAll(topicManagementBox, scroll);
		getStyleClass().add(TOPIC_LAYER_CSS);
	}
	
	private void initTopicManagementBox() {
		initAddButton();
		initRemoveButton();
		initCollapseAllButton();
		initExpandAllButton();
		topicManagementBox.getStyleClass().add(TOPIC_MANAGEMENT_BOX_CSS);
		topicManagementBox.setPrefWidth(TOPIC_MANAGEMENT_BOX_WIDTH);
	}
	
	private void initAddButton() {
		addButton.setOnAction(e -> addAction());
		addButton.getStyleClass().addAll(EditorPane.EDITOR_BUTTON_CSS, ADD_TOPIC_BUTTON_CSS);
	}
	
	private void addAction() {
		setMode(Mode.NORMAL);
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
	
	private void initCollapseAllButton() {
		collapseAllButton.getStyleClass().addAll(EditorPane.EDITOR_BUTTON_CSS, EXPANSION_BUTTON_CSS);
		collapseAllButton.setOnAction(e -> collapseAllAction());
	}
	
	private void collapseAllAction() {
		topicPaneContainer.topicPanes().forEachOrdered(tp -> tp.setExpanded(false));
	}
	
	private void initExpandAllButton() {
		expandAllButton.getStyleClass().addAll(EditorPane.EDITOR_BUTTON_CSS, EXPANSION_BUTTON_CSS);
		expandAllButton.setOnAction(e -> expandAllAction());
	}
	
	private void expandAllAction() {
		topicPaneContainer.topicPanes().forEachOrdered(tp -> tp.setExpanded(true));
	}
	
	private void initScroll() {
		scroll.getStyleClass().add(SCROLL_CSS);
	}
	
	private void addTopicPaneFor(Topic topic) {
		topicPaneContainer.addTopicPane(TopicPane.of(topic));
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
	
	void clearTopicPanes() {
		topicPaneContainer.getChildren().clear();
	}
	
	Mode mode() {
		return mode;
	}
	
	void setMode(Mode newMode) {
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

	public int topicCount() {
		return topicPaneContainer.topicCount();
	}
	
	@Override
	public VerificationResult verify() {
		return topicPaneContainer.verify();
	}
	
}
