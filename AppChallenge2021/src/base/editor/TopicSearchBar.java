package base.editor;

import java.util.function.Predicate;

import base.Main;
import fxutils.Images;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import topics.TopicUtils;
import utils.*;

/**
 * 
 * @author Sam Hooper
 *
 */
public class TopicSearchBar extends HBox {
	
	private static final String
			TOPIC_SEARCH_BAR_CSS = "topic-search-bar",
			MAGNIFYING_GLASS_CSS = "magnifying-glass";
	private static final TopicSearchBar INSTANCE = new TopicSearchBar();
	
	public static final TopicSearchBar get() {
		return INSTANCE;
	}
	
	private static TopicSelectorBox selectorBox() {
		return TopicSelectionPopup.get().selectorBox();
	}
	
	private final ImageView magnifyingGlass;
	private final TextField searchBox;
	private final FilterableSet<String> visibleTopicNames;
	
	private TopicSearchBar() {
		magnifyingGlass = new ImageView();
		initMagnifyingGlass();
		
		searchBox = new TextField();
		initSearchBox();
		
		visibleTopicNames = FilterableSet.of(TopicUtils.allNames());
		
		getChildren().addAll(magnifyingGlass, searchBox);
		getStyleClass().add(TOPIC_SEARCH_BAR_CSS);
	}
	
	private void initMagnifyingGlass() {
		Images.setFitSize(magnifyingGlass, Main.BUTTON_ICON_SIZE);
		magnifyingGlass.getStyleClass().add(MAGNIFYING_GLASS_CSS);
	}
	
	private void initSearchBox() {
		searchBox.textProperty().addListener((x, oldValue, newValue) -> searchChanged(oldValue, newValue));
	}
	
	private void searchChanged(String oldValue, String newValue) {
		if(oldValue.equals(newValue))
			return;
		visibleTopicNames.setFilter(filterFor(newValue));
		selectorBox().setVisibleTopics(visibleTopicNames);
	}
	
	public void clearSearch() {
		searchBox.setText("");
	}
	
	private Predicate<String> filterFor(String search) {
		return s -> Strings.startsWithIgnoreCase(s, search);
	}
}
