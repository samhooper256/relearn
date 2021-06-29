/**
 * 
 */
package base.sets;

import java.util.*;

import base.*;
import base.graphics.BackArrow;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import topics.*;

/**
 * @author Sam Hooper
 *
 */
public class EditorPane extends StackPane implements Verifiable {
	
	public enum Mode {
		NORMAL, REMOVAL;
	}
	
	private static final String
			TITLE = "Set Editor",
			ADD_TEXT = "+ Add Topic",
			NORMAL_MODE_REMOVE_TEXT = "- Remove",
			REMOVAL_MODE_REMOVE_TEXT = "Done";
	private static final String 
			EDITOR_PANE_CSS = "editor-pane",
			NAME_LAYER_CSS = "name-layer",
			PORTION_LAYER_CSS = "portion-layer";
	private static final EditorPane INSTANCE = new EditorPane();
	private static final double PORTION_BAR_HEIGHT = 200;
	
	public static EditorPane get() {
		return INSTANCE;
	}
	
	private final VBox primaryZLayer, nameLayer, topicManagementBox;
	private final TopicPaneContainer topicPaneContainer;
	private final HBox topLayer, nameRow, topicLayer, portionLayer;
	private final BackArrow backArrow;
	private final Button addTopicButton, removeTopicButton;
	private final NameInputField nameField;
	private final Label nameLabel;
	private final ErrorMessage nameError;
	
	private ProblemSet set;
	private String nameOnOpening;
	private Mode mode;
	
	private EditorPane() {
		set = null;
		mode = Mode.NORMAL;
		
		//top layer:
		backArrow = new BackArrow();
		topicPaneContainer = new TopicPaneContainer();
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
		addTopicButton = new Button(ADD_TEXT);
		removeTopicButton = new Button(NORMAL_MODE_REMOVE_TEXT);
		topicManagementBox = new VBox(addTopicButton, removeTopicButton);
		topicLayer = new HBox(topicManagementBox, topicPaneContainer);
		
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
		initTopicManagementBox();
	}
	
	private void initTopicManagementBox() {
		initAddTopicButton();
		initRemoveTopicButton();
	}
	
	private void initAddTopicButton() {
		addTopicButton.setOnAction(e -> addTopicAction());
	}
	
	private void addTopicAction() {
		topicPaneContainer.hideNoTopicErrorIfShowing();
		showTopicSelectionPane();
	}
	
	private void initRemoveTopicButton() {
		removeTopicButton.setOnAction(e -> removeTopicAction());
	}
	
	private void removeTopicAction() {
		switch(mode()) {
			case NORMAL -> setToRemovalMode();
			case REMOVAL -> setToNormalMode();
			default -> unsupported(mode);
		}
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
	
	private void showTopicSelectionPane() {
		TopicSelectionPopup.get().fadeOnto(this);
	}
	
	public void edit(ProblemSet set) {
		this.set = set;
		nameOnOpening = set.name();
		nameField.setText(nameOnOpening);
		topicPaneContainer.getChildren().clear();
		TopicSelectionPopup.get().setProblemSet(set);
		updatePortions();
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
	
	public void removeTopic(Topic topic) {
		TopicPane tp = TopicPane.of(topic);
		assert currentSet().config().topics().contains(topic);
		topicPaneContainer.removeTopicPane(tp);
		currentSet().config().removeTopic(topic);
		updatePortions();
	}
	
	public void hideTopicSelectionPane() {
		TopicSelectionPopup.get().fadeOutFrom(this);
	}
	
	@Override
	public VerificationResult verify() {
		boolean hasTopic = topicPaneContainer.verify().isSuccess();
		VerificationResult nameResult = nameField.verify();
		if(nameResult.isFailure())
			showNameError(nameResult.errorMessage());
		return VerificationResult.of(hasTopic && nameResult.isSuccess());
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
	
	public Mode mode() {
		return mode;
	}
	
	public void setMode(Mode newMode) {
		switch(newMode) {
			case REMOVAL -> setToRemovalMode();
			case NORMAL -> setToNormalMode();
			default -> unsupported(newMode);
		}
	}

	private void unsupported(Mode newMode) {
		throw new UnsupportedOperationException(String.format("Unsupported mode: %s", newMode));
	}
	
	private void setToRemovalMode() {
		if(mode() == Mode.REMOVAL)
			return;
		mode = Mode.REMOVAL;
		topicPaneContainer.showTrashCans();
		removeTopicButton.setText(REMOVAL_MODE_REMOVE_TEXT);
	}
	
	private void setToNormalMode() {
		if(mode() == Mode.NORMAL)
			return;
		mode = Mode.NORMAL;
		topicPaneContainer.hideTrashCans();
		removeTopicButton.setText(NORMAL_MODE_REMOVE_TEXT);
	}
	
}
