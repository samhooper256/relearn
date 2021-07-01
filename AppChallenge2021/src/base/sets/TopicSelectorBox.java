package base.sets;

import java.util.*;

import javafx.scene.layout.VBox;
import topics.*;

/**
 * 
 * @author Sam Hooper
 *
 */
public final class TopicSelectorBox extends VBox {
	
	private static final Map<ProblemSet, TopicSelectorBox> CACHE = new IdentityHashMap<>();
	private static final String TOPIC_SELECTOR_BOX_CSS = "topic-selector-box";
	
	public static TopicSelectorBox forSet(ProblemSet set) {
		if(CACHE.containsKey(set))
			return CACHE.get(set);
		TopicSelectorBox box = new TopicSelectorBox(set);
		CACHE.put(set, box);
		return box;
	}
	
	private final ProblemSet set;
	/** Maps topicName -> TopicSelector*/
	private final Map<String, TopicSelector> selectorMap;
	
	private int numSelected;
	
	private TopicSelectorBox(ProblemSet set) {
		this.set = set;
		numSelected = 0;
		selectorMap = new HashMap<>();
		for(TopicFactory<?> factory : TopicUtils.allFactories())
			addSelector(factory);
		getStyleClass().add(TOPIC_SELECTOR_BOX_CSS);
	}

	private void addSelector(TopicFactory<?> factory) {
		TopicSelector selector = new TopicSelector(factory);
		getChildren().add(selector);
		selectorMap.put(factory.name(), selector);
	}
	
	public TopicSelector selectorFor(Topic topic) {
		return selectorFor(topic.name());
	}
	
	public TopicSelector selectorFor(String topicName) {
		return selectorMap.get(topicName);
	}
	
	public ProblemSet set() {
		return set;
	}

	public void notifySelected() {
		numSelected++;
		TopicSelectionPopup.get().enableAddSelectedButton();
	}
	
	public void notifyUnselectedOrAdded() {
		numSelected--;
		if(numSelected == 0)
			TopicSelectionPopup.get().disableAddSelectedButton();
	}
	
	public void setVisibleTopics(Collection<String> topics) {
		getChildren().clear();
		for(String topicName : topics)
			getChildren().add(selectorMap.get(topicName));
	}
	
}
