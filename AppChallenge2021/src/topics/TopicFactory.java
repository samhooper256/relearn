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
public record TopicFactory<T extends Topic>(String name, Supplier<T> generator)
		implements Named, Comparable<TopicFactory<?>> {
	
	private static final Comparator<TopicFactory<?>> COMPARATOR = Comparator.comparing(TopicFactory::name);
	
	public T create() {
		return generator.get();
	}

	@Override
	public int compareTo(TopicFactory<?> o) {
		return COMPARATOR.compare(this, o);
	}
	
}
