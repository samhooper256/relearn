/**
 * 
 */
package base.editor;

import base.*;
import base.graphics.BackArrow;
import base.sets.ProblemSet;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import topics.*;

/**
 * @author Sam Hooper
 *
 */
public class EditorPane extends StackPane implements IndependentlyVerifiable {
	
	public static final String EDITOR_BUTTON_CSS = "editor-button";
	
	private static final String 
			EDITOR_PANE_CSS = "editor-pane",
			PORTION_LAYER_CSS = "portion-layer";
	private static final EditorPane INSTANCE = new EditorPane();
	private static final double PORTION_BAR_HEIGHT = 200;
	
	public static EditorPane get() {
		return INSTANCE;
	}
	
	private final VBox primaryZLayer;
	private final HBox portionLayer;
	private final NameLayer nameLayer;
	private final TopicLayer topicLayer;
	private final Header header;
	
	private ProblemSet set;
	private String nameOnOpening;
	
	private EditorPane() {
		set = null;
		
		header = new Header();
		
		nameLayer = new NameLayer();
		
		topicLayer = new TopicLayer();
		
		//portion layer:
		portionLayer = new HBox(TopicPortionBar.get());
		primaryZLayer = new VBox(header, nameLayer, topicLayer, portionLayer);
		initPrimaryZLayer();
		
		getChildren().add(primaryZLayer);
		getStyleClass().add(EDITOR_PANE_CSS);
	}
	
	private void initPrimaryZLayer() {
		initPortionLayer();
		VBox.setVgrow(topicLayer, Priority.ALWAYS);
	}
	
	void backArrowAction() {
		if(verify().isSuccess())
			saveAndGoBack();
	}

	private void saveAndGoBack() {
		set.setName(nameLayer.name());
		if(!set.isRegistered())
			set.register();
		goBack();
	}

	private void goBack() {
		Main.scene().showSets();
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
		boolean hasValidName = nameLayer.verify(nameOnOpening).isSuccess();
		return VerificationResult.of(hasTopic && hasValidName);
	}
	
	public ProblemSet currentSet() {
		return set;
	}

	private boolean hasAnyTopics() {
		return topicLayer.topicCount() > 0;
	}
	
	void deleteSetButonAction() {
		if(!hasAnyTopics())
			goBack();
	}
	
}
