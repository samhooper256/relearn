/**
 * 
 */
package topics;

import java.util.Comparator;
import java.util.function.Supplier;

import base.Named;

/**
 * <p>A factory that creates {@link Topic Topics}. The {@link #name() name} of a {@code TopicFactory} is the
 * {@link Topic#name() name} of the topic it creates.</p>
 * @author Sam Hooper
 *
 */
public final class TopicFactory<T extends Topic> implements Named, Comparable<TopicFactory<?>> {
	
	private final String name;
	private final Supplier<T> generator;
	
	public TopicFactory(String name, Supplier<T> generator) {
		this.name = name;
		this.generator = generator;
	}
	
	private static final Comparator<TopicFactory<?>> COMPARATOR = Comparator.comparing(TopicFactory::name);
	
	public T create() {
		return generator.get();
	}

	@Override
	public int compareTo(TopicFactory<?> o) {
		return COMPARATOR.compare(this, o);
	}
	
	@Override
	public String name() {
		return name;
	}
	
	public Supplier<T> generator() {
		return generator;
	}
	
}
