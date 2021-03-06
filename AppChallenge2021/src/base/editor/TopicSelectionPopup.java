/**
 * 
 */
package base.editor;

import java.util.List;
import java.util.stream.*;

import base.Main;
import base.graphics.FadePopup;
import base.sets.ProblemSet;
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
		setGlassCloseAction(this::cleanupAfterHiding);
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
		setAddSelectedButtonEnabled(false);
	}
	
	private void addSelectedButtonAction() {
		List<Topic> topics = selectedTopics().collect(Collectors.toList());
		EditorPane.get().addTopics(topics); //The TopicSelectors will automatically have their buttons updated,
		//since they are listening to the their set's topics (which is a ReadOnlyAudibleSet<Topic>).
		EditorPane.get().fadeOutTopicSelectionPane();
		clearSearch();
	}

	private void initCancelButton() {
		Images.setFitSize(cancelGraphic, Main.BUTTON_ICON_SIZE);
		cancelGraphic.getStyleClass().add(BUTTON_GRAPHIC_CSS);
		cancelButton.setOnAction(e -> cancelButtonAction());
		cancelButton.getStyleClass().addAll(EditorPane.EDITOR_BUTTON_CSS, CANCEL_BUTTON_CSS);
		cancelButton.setGraphic(cancelGraphic);
	}
	
	private void cancelButtonAction() {
		EditorPane.get().fadeOutTopicSelectionPane();
		cleanupAfterHiding();
	}
	
	/** This should only be called when the {@link TopicSelectionPopup} disappears because of a reason <em>other</em>
	 * that the {@link #addSelectedButton} was pressed.*/
	private void cleanupAfterHiding() {
		clearSearch();
		selectors().forEach(ts -> {
			if(ts.isSelected())
				ts.setUnselected();
		});
	}

	private void clearSearch() {
		TopicSearchBar.get().clearSearch();
	}
	
	public void setProblemSet(ProblemSet set) {
		selectorBox = TopicSelectorBox.forSet(set);
		scroll.setContent(selectorBox);
	}
	
	/** Returns <em>all</em> of the selectors, even if they're not showing because they are filtered out by a search.*/
	public Stream<TopicSelector> selectors() {
		return selectorBox.selectors().stream();
	}
	
	public Stream<TopicSelector> selectedSelectors() {
		return selectors().filter(TopicSelector::isSelected);
	}
	
	/** This method generates a new {@link Topic} from each {@link TopicSelector#isSelected() selected}
	 * {@link TopicSelector} (using its {@link TopicSelector#factory() factory}), and returns a {@link Stream} of those
	 * topics.
	 * */
	public Stream<Topic> selectedTopics() {
		return getTopicsFrom(selectedSelectors());
	}
	
	public void setAddSelectedButtonEnabled(boolean enabled) {
		addSelectedButton.setDisable(!enabled);
	}
	
	public TopicSelectorBox selectorBox() {
		return selectorBox;
	}
	
}
