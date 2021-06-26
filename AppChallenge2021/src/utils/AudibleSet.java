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
public interface AudibleSet<E> extends Set<E>, ReadOnlyAudibleCollection<E> {
	
	static <T> AudibleSet<T> create(Supplier<Set<T>> factory) {
		return new AudibleSetImpl<>(factory);
	}
	
}
