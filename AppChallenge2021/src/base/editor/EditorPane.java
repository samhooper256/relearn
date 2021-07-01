/**
 * 
 */
package base.editor;

import base.*;
import base.graphics.BackArrow;
import base.sets.ProblemSet;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import topics.*;

/**
 * @author Sam Hooper
 *
 */
public class EditorPane extends StackPane implements Verifiable {
	
	public static final String EDITOR_BUTTON_CSS = "editor-button";
	
	private static final String TITLE = "Set Editor";
	private static final String 
			EDITOR_PANE_CSS = "editor-pane",
			PORTION_LAYER_CSS = "portion-layer",
			TITLE_CSS = "title";
	private static final EditorPane INSTANCE = new EditorPane();
	private static final double PORTION_BAR_HEIGHT = 200;
	
	public static EditorPane get() {
		return INSTANCE;
	}
	
	private final VBox primaryZLayer;
	private final HBox topLayer, portionLayer;
	private final BackArrow backArrow;
	private final NameLayer nameLayer;
	private final TopicLayer topicLayer;
	private final Label title;
	
	private ProblemSet set;
	private String nameOnOpening;
	
	private EditorPane() {
		set = null;
		
		//top layer:
		backArrow = new BackArrow();
		title = new Label(TITLE);
		topLayer = new HBox(backArrow, title);
		
		nameLayer = new NameLayer();
		
		topicLayer = new TopicLayer();
		
		//portion layer:
		portionLayer = new HBox(TopicPortionBar.get());
		primaryZLayer = new VBox(topLayer, nameLayer, topicLayer, portionLayer);
		initPrimaryZLayer();
		
		getChildren().add(primaryZLayer);
		getStyleClass().add(EDITOR_PANE_CSS);
	}
	
	private void initPrimaryZLayer() {
		initTopLayer();
		initPortionLayer();
		VBox.setVgrow(topicLayer, Priority.ALWAYS);
	}
	
	private void initTopLayer() {
		backArrow.setOnAction(this::backArrowAction);
		title.getStyleClass().add(TITLE_CSS);
	}

	private void backArrowAction() {
		if(verify().isSuccess()) {
			set.setName(nameLayer.name());
			if(!set.isRegistered())
				set.register();
			Main.scene().showSets();
			nameLayer.hideError();
		}
	}
	
	private void initPortionLayer() {
		HBox.setHgrow(TopicPortionBar.get(), Priority.ALWAYS);
		portionLayer.setPrefHeight(PORTION_BAR_HEIGHT);
		portionLayer.getStyleClass().add(PORTION_LAYER_CSS);
	}
	
	void fadeInTopicSelectionPane() {
		TopicSelectionPopup.get().fadeIn();
	}
	
	public void edit(ProblemSet set) {
		this.set = set;
		nameOnOpening = set.name();
		nameLayer.setName(nameOnOpening);
		TopicSelectionPopup.get().setProblemSet(set);
		topicLayer.loadPanesFor(set);
		updatePortions();
	}
	
	public void createFreshSet() {
		edit(new ProblemSet());
	}
	
	public void addTopics(Iterable<Topic> topics) {
		set.addTopics(topics);
		topicLayer.addTopicPanesFor(topics);
		updatePortions();
	}

	private void updatePortions() {
		TopicPortionBar.get().update(set.config().topics());
	}
	
	public void removeTopicPane(TopicPane pane) {
		Topic topic = pane.topic();
		assert currentSet().config().topics().contains(topic);
		topicLayer.removeTopicPane(pane);
		currentSet().config().removeTopic(topic);
		updatePortions();
	}
	
	public void hideTopicSelectionPane() {
		TopicSelectionPopup.get().fadeOut();
	}
	
	@Override
	public VerificationResult verify() {
		boolean hasTopic = topicLayer.verify().isSuccess();
		boolean hasValidName = nameLayer.verify().isSuccess();
		return VerificationResult.of(hasTopic && hasValidName);
	}
	
	public ProblemSet currentSet() {
		return set;
	}
	
}
