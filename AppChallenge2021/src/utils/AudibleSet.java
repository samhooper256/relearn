/**
 * 
 */
package utils;

import java.util.Set;
import java.util.function.Supplier;

/**
 * @author Sam Hooper
 *
 */
public interface AudibleSet<E> extends Set<E>, ReadOnlyAudibleSet<E> {
	
	static <T> AudibleSet<T> create(Supplier<Set<T>> factory) {
		return new AudibleSetImpl<>(factory);
	}
	
	/** <p>Creates a new {@link AudibleSet} that is backed by the given set. Changes made through this
	 * {@code AudibleSet} will be heard and will cause any listeners to be fired. However, <em>any changes made to the
	 * backing set after this method is called will not be heard and will not cause any listeners to fire</em>.</p>
	 * <p>The returned {@code AudibleSet} stores a strong reference to the given backing set, and does <em>not</em>
	 * defensively copy its elements. Changes made through the returned {@code AudibleSet} will be visible through
	 * the backing set.</p>*/
	static <T> AudibleSet<T> from(Set<T> backing) {
		return new AudibleSetImpl<>(backing);
	}
}
