/**
 * 
 */
package base;

import java.util.*;

import topics.Topic;
import utils.RNG;

/**
 * <p>A configuration of a {@link ProblemSet}.</p>
 * @author Sam Hooper
 *
 */
public final class SetConfiguration {
	
	private final Map<Topic, Integer> topics;
	private int count;
	
	public SetConfiguration() {
		this.topics = new HashMap<>();
	}
	
	public void putTopic(Topic topic, Integer count) {
		if(count <= 0)
			throw new IllegalArgumentException("count < 0");
		if(topics.containsKey(topic)) {
			int oldValue = topics.get(topic);
			this.count -= oldValue;
		}
		topics.put(topic, count);
		this.count += count;
	}
	
	public boolean removeTopic(Topic topic) {
		return topics.remove(topic) != null;
	}
	
	public Deck createDeck() {
		List<Problem> list = new ArrayList<>();
		for(Map.Entry<Topic, Integer> e : topics.entrySet()) {
			Topic topic = e.getKey();
			int value = e.getValue();
			for(int i = 0; i < value; i++)
				list.add(topic.generate());
		}
		Collections.shuffle(list, RNG.source());
		return Deck.of(list);
	}
}
