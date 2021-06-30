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
	
	private TopicSelectorBox(ProblemSet set) {
		this.set = set;
		selectorMap = new HashMap<>();
		for(TopicFactory<?> factory : TopicUtils.allFactories())
			addSelector(factory);
		initListeners();
		getStyleClass().add(TOPIC_SELECTOR_BOX_CSS);
	}

	private void addSelector(TopicFactory<?> factory) {
		TopicSelector selector = new TopicSelector(factory);
		getChildren().add(selector);
		selectorMap.put(factory.name(), selector);
	}
	
	private void initListeners() {
		set.topics().addAddListener(t -> selectorFor(t).setAdded());
		set.topics().addRemoveListener(t -> selectorFor(t).setUnselected());
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
	
}
