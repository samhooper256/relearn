/**
 * 
 */
package base.editor;

import base.*;
import base.editor.TopicLayer.Mode;
import base.sets.ProblemSet;
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
	private static final double PORTION_LAYER_HEIGHT = 200, PORTION_LAYER_MIN_HEIGHT = 80;
	
	public static EditorPane get() {
		return INSTANCE;
	}
	
	private final VBox primaryZLayer;
	private final HBox portionLayer;
	private final NameLayer nameLayer;
	private final TopicLayer topicLayer;
	private final Header header;
	private final DeletePopup deletePopup;
	
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
		
		deletePopup = new DeletePopup(this, this::afterDeletionAction);
		initDeletePopup();
		
		getChildren().add(primaryZLayer);
		getStyleClass().add(EDITOR_PANE_CSS);
	}
	
	private void initPrimaryZLayer() {
		initPortionLayer();
		VBox.setVgrow(topicLayer, Priority.ALWAYS);
	}
	
	private void initPortionLayer() {
		HBox.setHgrow(TopicPortionBar.get(), Priority.ALWAYS);
		portionLayer.setPrefHeight(PORTION_LAYER_HEIGHT);
		portionLayer.setMinHeight(PORTION_LAYER_MIN_HEIGHT);
		portionLayer.getStyleClass().add(PORTION_LAYER_CSS);
	}
	
	private void initDeletePopup() {
		deletePopup.setGlassCloseable(true);
	}
	
	void backArrowAction() {
		if(!hasName() && !hasAnyTopics())
			goBack();
		else if(verify().isSuccess())
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
	
	void fadeInTopicSelectionPane() {
		assert TopicSelectionPopup.get().selectors().noneMatch(TopicSelector::isSelected);
		TopicSelectionPopup.get().fadeIn();
	}
	
	public void edit(ProblemSet set) {
		this.set = set;
		nameOnOpening = set.name();
		nameLayer.setName(nameOnOpening);
		TopicSelectionPopup.get().setProblemSet(set);
		topicLayer.loadPanesFor(set);
		topicLayer.setMode(Mode.NORMAL);
		updatePortions();
	}
	
	public void createFreshSet() {
		edit(new ProblemSet());
	}
	
	void addTopics(Iterable<Topic> topics) {
		set.addTopics(topics);
		topicLayer.addTopicPanesFor(topics);
		updatePortions();
	}

	private void updatePortions() {
		TopicPortionBar.get().update(set.config().topics());
	}
	
	void removeTopicPane(TopicPane pane) {
		Topic topic = pane.topic();
		assert currentSet().config().topics().contains(topic);
		topicLayer.removeTopicPane(pane);
		currentSet().config().removeTopic(topic);
		if(topicLayer.topicCount() == 0)
			topicLayer.setMode(Mode.NORMAL);
		updatePortions();
	}
	
	void fadeOutTopicSelectionPane() {
		TopicSelectionPopup.get().fadeOut();
	}
	
	@Override
	public VerificationResult verify() {
		boolean hasTopic = topicLayer.verify().isSuccess();
		boolean hasValidName = nameLayer.verify(nameOnOpening).isSuccess();
		return VerificationResult.of(hasTopic && hasValidName);
	}
	
	ProblemSet currentSet() {
		return set;
	}

	private boolean hasAnyTopics() {
		return topicLayer.topicCount() > 0;
	}
	
	private boolean hasName() {
		return !nameLayer.name().isEmpty();
	}
	
	void deleteSetButonAction() {
		if(isSafelyDeletable())
			goBack();
		else
			fadeInDeletePopup();
	}

	/** {@code true} iff the current set in the editor can be deleted without needing the
	 * {@link DeletePopup}.*/
	private boolean isSafelyDeletable() {
		return !hasAnyTopics();
	}
	
	void afterDeletionAction() {
		goBack();
		deletePopup.hidePopup();
		set = null;
	}
	
	private void fadeInDeletePopup() {
		deletePopup.fadeIn(currentSet());
	}
	
}
