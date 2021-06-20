/**
 * 
 */
package topics;

import java.util.function.Supplier;

import base.Named;

/**
 * <p>A factory that creates {@link Topic Topics}. The {@link #name() name} of a {@code TopicFactory} is the
 * {@link Topic#name() name} of the {@link Topic} it creates.</p>
 * @author Sam Hooper
 *
 */
public record TopicFactory<T extends Topic>(String name, Supplier<T> generator) implements Named {
	
	public T create() {
		return generator.get();
	}
	
}
