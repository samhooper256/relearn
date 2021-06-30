/**
 * 
 */
package base.sets;

import java.util.List;
import java.util.stream.Stream;

import base.graphics.FadePopup;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import topics.*;

/**
 * @author Sam Hooper
 *
 */
public class TopicSelectionPopup extends FadePopup {
	
	private static final String
			TOPIC_SELECTION_POPUP_CSS = "topic-selection-popup",
			VBOX_CSS = "vbox",
			SCROLL_CSS = "scroll",
			ADD_SELECTED_BUTTON_CSS = "add-selected-button";
	
	private static final TopicSelectionPopup INSTANCE = new TopicSelectionPopup();
	
	public static final TopicSelectionPopup get() {
		return INSTANCE;
	}
	
	private static Stream<Topic> getTopicsFrom(Stream<TopicSelector> selectors) {
		return selectors.map(t -> t.factory().create());
	}
	
	private final ScrollPane scroll;
	private final VBox vBox;
	private final Button addSelectedButton;
	
	private TopicSelectorBox selectorBox;
	
	private TopicSelectionPopup() {
		scroll = new ScrollPane();
		
		addSelectedButton = new Button("Add Selected");
		
		vBox = new VBox(scroll, addSelectedButton);
		initVBox();
		
		setMaxSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		getChildren().add(vBox);
		getStyleClass().add(TOPIC_SELECTION_POPUP_CSS);
	}

	private void initVBox() {
		initScroll();
		initAddSelectedButton();
		VBox.setVgrow(scroll, Priority.ALWAYS);
		vBox.getStyleClass().add(VBOX_CSS);
	}

	private void initScroll() {
		scroll.getStyleClass().add(SCROLL_CSS);
	}
	
	private void initAddSelectedButton() {
		addSelectedButton.setOnAction(e -> addSelectedButtonAction());
		addSelectedButton.getStyleClass().add(ADD_SELECTED_BUTTON_CSS);
		disableAddSelectedButton();
	}
	
	private void addSelectedButtonAction() {
		List<TopicSelector> selectors = selectedSelectors().toList();
		List<Topic> topics = selectedTopics().toList();
		EditorPane.get().addTopics(topics);
		EditorPane.get().hideTopicSelectionPane();
		for(TopicSelector ts : selectors)
			ts.setAdded();
		disableAddSelectedButton();
	}
	
	public void setProblemSet(ProblemSet set) {
		selectorBox = TopicSelectorBox.forSet(set);
		scroll.setContent(selectorBox);
	}
	
	public Stream<TopicSelector> selectors() {
		return selectorBox.getChildren().stream().map(n -> (TopicSelector) n);
	}
	
	public Stream<TopicSelector> selectedSelectors() {
		return selectors().filter(s -> s.isSelected());
	}
	
	/** This method generates a new {@link Topic} from each {@link TopicSelector#isSelected() selected}
	 * {@link TopicSelector} (using its {@link TopicSelector#factory() factory}), and returns a {@link Stream} of those
	 * topics.
	 * */
	public Stream<Topic> selectedTopics() {
		return getTopicsFrom(selectedSelectors());
	}
	
	public void disableAddSelectedButton() {
		addSelectedButton.setDisable(true);
	}
	
	public void enableAddSelectedButton() {
		addSelectedButton.setDisable(false);
	}
	
}
