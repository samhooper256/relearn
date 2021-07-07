package base.editor;

import java.util.*;

import base.sets.ProblemSet;
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
	
	private TopicSelectorBox(ProblemSet set) {
		this.set = set;
		selectorMap = new HashMap<>();
		for(TopicFactory<?> factory : TopicUtils.allFactories())
			addSelector(factory);
		set.topics().addAddListener(t -> selectorFor(t).setAdded());
		set.topics().addRemoveListener(t -> selectorFor(t).setUnselected());
		for(Topic t : set.topics())
			selectorFor(t).setAdded();
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

	public void notifySelectorChanged() {
		TopicSelectionPopup.get().setAddSelectedButtonEnabled(selectors().stream().anyMatch(TopicSelector::isSelected));
	}
	
	public void setVisibleTopics(Collection<String> topics) {
		getChildren().clear();
		for(String topicName : topics)
			getChildren().add(selectorMap.get(topicName));
	}
	
	public Collection<TopicSelector> selectors() {
		return selectorMap.values();
	}
	
}
