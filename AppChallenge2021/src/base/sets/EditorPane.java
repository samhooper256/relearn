/**
 * 
 */
package base.sets;

import java.util.*;

import base.*;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import topics.*;

/**
 * @author Sam Hooper
 *
 */
public class EditorPane extends StackPane implements Verifiable {
	
	private static final String TITLE = "Set Editor";
	private static final String 
			EDITOR_PANE_CSS = "editor-pane",
			NAME_LAYER_CSS = "name-layer",
			PORTION_LAYER_CSS = "portion-layer";
	private static final EditorPane INSTANCE = new EditorPane();
	private static final double PORTION_BAR_HEIGHT = 200;
	
	private ProblemSet set;
	private String nameOnOpening;
	private final VBox primaryZLayer, topicPaneContainer, nameLayer;
	private final HBox topLayer, nameRow, topicLayer, portionLayer;
	private final BackArrow backArrow;
	private final Button addTopicButton;
	private final NameInputField nameField;
	private final Label nameLabel;
	private final ErrorMessage noTopicError, nameError;
	public static EditorPane get() {
		return INSTANCE;
	}
	
	private EditorPane() {
		set = null;
		
		//top layer:
		backArrow = new BackArrow();
		topicPaneContainer = new VBox();
		Label headerLabel = new Label(TITLE);
		headerLabel.setFont(Font.font(24));
		topLayer = new HBox(backArrow, headerLabel);
		
		//name layer:
		nameLabel = new Label("Name:");
		nameField = new NameInputField();
		nameRow = new HBox(nameLabel, nameField);
		nameError = new ErrorMessage("");
		nameLayer = new VBox(nameRow, nameError);
		
		//topic layer
		noTopicError = new ErrorMessage("No topics selected.");
		addTopicButton = new Button("+ Add Topic");
		topicLayer = new HBox(addTopicButton, topicPaneContainer);
		
		//portion layer
		portionLayer = new HBox(TopicPortionBar.get());
		primaryZLayer = new VBox(topLayer, nameLayer, topicLayer, portionLayer);
		initPrimaryZLayer();
		
		getChildren().add(primaryZLayer);
		getStyleClass().add(EDITOR_PANE_CSS);
	}
	
	private void initPrimaryZLayer() {
		initBackArrow();
		initNameLayer();
		initTopicLayer();
		initPortionLayer();
		VBox.setVgrow(topicLayer, Priority.ALWAYS);
	}
	
	private void initBackArrow() {
		backArrow.setOnAction(this::backArrowAction);
	}

	private void backArrowAction() {
		if(verify().isSuccess()) {
			set.setName(nameField.getText().strip());
			if(!set.isRegistered())
				set.register();
			Main.scene().showSets();
			hideNameError();
		}
	}
	
	private void initTopicLayer() {
		topicLayer.setSpacing(20);
		topicLayer.setPadding(new Insets(25));
		HBox.setHgrow(topicPaneContainer, Priority.ALWAYS);
		initAddTopicButton();
	}
	
	private void initAddTopicButton() {
		addTopicButton.setOnAction(e -> addTopicAction());
	}
	
	private void addTopicAction() {
		hideNoTopicsErrorMessage();
		showTopicSelectionPane();
	}
	
	private void showTopicSelectionPane() {
		TopicSelectionPane.get().fadeOnto(this);
	}
	
	private void initNameLayer() {
		initNameRow();
		nameError.setVisible(false);
		nameLayer.getStyleClass().add(NAME_LAYER_CSS);
	}
	
	private void initNameRow() {
		nameRow.setSpacing(20);
		nameRow.setAlignment(Pos.CENTER);
		nameField.setOnChange(this::hideNameError);
	}
	
	private void initPortionLayer() {
		portionLayer.setPrefHeight(PORTION_BAR_HEIGHT);
		HBox.setHgrow(TopicPortionBar.get(), Priority.ALWAYS);
		portionLayer.getStyleClass().add(PORTION_LAYER_CSS);
	}
	
	public void edit(ProblemSet set) {
		this.set = set;
		nameOnOpening = set.name();
		nameField.setText(nameOnOpening);
		topicPaneContainer.getChildren().clear();
		for(Topic t : set.config().topics())
			addTopicPaneFor(t);
	}
	
	public void createFreshSet() {
		edit(new ProblemSet());
	}
	
	public void addTopics(Collection<Topic> topics) {
		set.addTopics(topics);
		addTopicPanesFor(topics);
		updatePortions();
	}

	private void updatePortions() {
		TopicPortionBar.get().update(set.config().topics());
	}
	
	private void addTopicPanesFor(Collection<Topic> topics) {
		for(Topic t : topics)
			addTopicPaneFor(t);
	}
	
	private void addTopicPaneFor(Topic topic) {
		topicPaneContainer.getChildren().add(TopicPane.of(topic));
	}
	
	public void hideTopicSelectionPane() {
		TopicSelectionPane.get().fadeOutFrom(this);
	}
	
	@Override
	public VerificationResult verify() {
		boolean hasTopic = set.config().topics().size() > 0;
		if(!hasTopic)
			notifyNoTopic();
		VerificationResult nameResult = nameField.verify();
		if(nameResult.isFailure())
			showNameError(nameResult.errorMessage());
		return VerificationResult.of(hasTopic && nameResult.isSuccess());
	}
	
	private void notifyNoTopic() {
		showNoTopicsErrorMessage();
	}
	
	private void showNoTopicsErrorMessage() {
		assert topicPaneContainer.getChildren().isEmpty();
		topicPaneContainer.getChildren().add(noTopicError);
	}
	
	/** Hides {@link #noTopicError} iff it is present; otherwise, does nothing.*/
	private void hideNoTopicsErrorMessage() {
		ObservableList<Node> children = topicPaneContainer.getChildren();
		if(children.contains(noTopicError)) {
			assert children.size() == 1;
			children.clear();
		}
	}
	
	private void showNameError(String message) {
		nameError.setText(message);
		nameError.setVisible(true);
	}
	
	private void hideNameError() {
		nameError.setVisible(false);
	}
	
	public ProblemSet currentSet() {
		return set;
	}
	
}
