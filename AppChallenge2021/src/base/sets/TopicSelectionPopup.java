/**
 * 
 */
package base.sets;

import java.util.List;
import java.util.stream.Stream;

import base.Main;
import base.graphics.FadePopup;
import fxutils.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
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
			ADD_SELECTED_BUTTON_CSS = "add-selected-button",
			CANCEL_BUTTON_CSS = "cancel-button",
			BUTTON_GRAPHIC_CSS = "graphic";
	
	private static final TopicSelectionPopup INSTANCE = new TopicSelectionPopup();
	
	public static final TopicSelectionPopup get() {
		return INSTANCE;
	}
	
	private static Stream<Topic> getTopicsFrom(Stream<TopicSelector> selectors) {
		return selectors.map(t -> t.factory().create());
	}
	
	private final ScrollPane scroll;
	private final VBox vBox;
	private final Button addSelectedButton, cancelButton;
	private final ImageView cancelGraphic;
	private final PolarizedPane buttonLayer;
	private TopicSelectorBox selectorBox;
	
	private TopicSelectionPopup() {
		super(EditorPane.get());
		scroll = new ScrollPane();
		
		addSelectedButton = new Button("Add Selected");
		cancelButton = new Button("Cancel");
		cancelGraphic = new ImageView();
		
		buttonLayer = new PolarizedPane(addSelectedButton, cancelButton);
		
		vBox = new VBox(TopicSearchBar.get(), scroll, buttonLayer);
		initVBox();
		
		setMaxSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		getChildren().add(vBox);
		getStyleClass().add(TOPIC_SELECTION_POPUP_CSS);
		setGlassCloseAction(this::cancelButtonAction);
	}

	private void initVBox() {
		initScroll();
		initButtonLayer();
		VBox.setVgrow(scroll, Priority.ALWAYS);
		vBox.getStyleClass().add(VBOX_CSS);
	}

	private void initScroll() {
		scroll.getStyleClass().add(SCROLL_CSS);
	}
	
	private void initButtonLayer() {
		initAddSelectedButton();
		initCancelButton();
	}
	
	private void initAddSelectedButton() {
		addSelectedButton.setOnAction(e -> addSelectedButtonAction());
		addSelectedButton.getStyleClass().addAll(EditorPane.EDITOR_BUTTON_CSS, ADD_SELECTED_BUTTON_CSS);
		disableAddSelectedButton();
	}
	
	private void addSelectedButtonAction() {
		List<TopicSelector> selectors = selectedSelectors().toList();
		List<Topic> topics = selectedTopics().toList();
		EditorPane.get().addTopics(topics);
		EditorPane.get().hideTopicSelectionPane();
		for(TopicSelector ts : selectors)
			ts.setAdded();
		cleanupAfterHiding();
	}

	private void initCancelButton() {
		Images.setFitSize(cancelGraphic, Main.BUTTON_ICON_SIZE);
		cancelGraphic.getStyleClass().add(BUTTON_GRAPHIC_CSS);
		cancelButton.setOnAction(e -> cancelButtonAction());
		cancelButton.getStyleClass().addAll(EditorPane.EDITOR_BUTTON_CSS, CANCEL_BUTTON_CSS);
		cancelButton.setGraphic(cancelGraphic);
	}
	
	private void cancelButtonAction() {
		EditorPane.get().hideTopicSelectionPane();
		cleanupAfterHiding();
	}
	
	private void cleanupAfterHiding() {
		selectors().forEach(ts -> {
			if(ts.isSelected())
				ts.setUnselected();
		});
		TopicSearchBar.get().clearSearch();
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
	
	public TopicSelectorBox selectorBox() {
		return selectorBox;
	}
	
}
