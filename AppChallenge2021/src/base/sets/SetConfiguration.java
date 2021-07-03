/**
 * 
 */
package base.sets;

import java.io.*;
import java.util.*;

import base.problems.Problem;
import topics.Topic;
import utils.*;

/**
 * <p>A configuration of a {@link ProblemSet}.</p>
 * @author Sam Hooper
 *
 */
public final class SetConfiguration implements Serializable {
	
	private static final long serialVersionUID = -8580472330700911391L;
	
	/** Should not be assigned to except in a constructor or in {@link #readObject(ObjectInputStream)}.*/
	private transient AudibleSet<Topic> topics; //TODO if this set does not need to be audible, make it non-audible.
	
	public SetConfiguration(Topic... topics) {
		this.topics = AudibleSet.create(LinkedHashSet::new);
		for(Topic t : topics)
			addTopic(t);
	}
	
	public Set<Topic> topics() {
		return topics;
	}
	
	/** @throws IllegalArgumentException if the given {@link Topic} is not in this {@link SetConfiguration}.*/
	public void removeTopic(Topic topic) {
		if(!removeTopicIfPresent(topic))
			throw new IllegalArgumentException(String.format("%s is not in this SetConfiguration", topic));
	}
	
	public boolean removeTopicIfPresent(Topic topic) {
		return topics.remove(topic);
	}
	
	public void addTopic(Topic topic) {
		if(topics.contains(topic))
			throw new IllegalStateException(String.format("SetConfiguration already has topic: %s", topic));
		topics.add(topic);
	}

	public void addTopics(Iterable<Topic> topics) {
		for(Topic t : topics)
			addTopic(t);
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
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
	    oos.defaultWriteObject();
	    oos.writeObject(new LinkedHashSet<>(topics));
	}

	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
	    ois.defaultReadObject();
	    @SuppressWarnings("unchecked")
		Set<Topic> topicSet = (Set<Topic>) ois.readObject();
	    topics = AudibleSet.from(topicSet);
	}
	
}
