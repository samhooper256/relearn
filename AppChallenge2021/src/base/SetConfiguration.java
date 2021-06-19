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
	
	private final Set<Topic> topics;
	private int total;
	
	public SetConfiguration() {
		this.topics = new LinkedHashSet<>();
		total = 0;
	}
	
	public Set<Topic> topics() {
		return Collections.unmodifiableSet(topics);
	}
	
	public boolean removeTopic(Topic topic) {
		return topics.remove(topic);
	}
	
	public void addTopic(Topic topic) {
		if(topics.contains(topic))
			throw new IllegalStateException(String.format("SetConfiguration already has topic: %s", topic));
		topics.add(topic);
		
	}
	
	public Deck createDeck() {
		List<Problem> list = new ArrayList<>();
		for(Topic topic : topics) {
			int count = topic.count();
			for(int i = 0; i < count; i++)
				list.add(topic.generate());
		}
		Collections.shuffle(list, RNG.source());
		return Deck.of(list);
	}
}
