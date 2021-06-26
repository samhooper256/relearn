/**
 * 
 */
package utils;

import java.util.*;

/**
 * <p>The {@link Iterator} returned by {@link #iterator()} must <em>not</em> support the
 * {@link Iterator#remove()} operation.</p>
 * @author Sam Hooper
 *
 */
public interface ReadOnlyCollection<E> extends Iterable<E> {

	boolean contains(Object o);
	
	int size();
	
	default boolean containsAll(Collection<?> objects) {
		for(Object o : objects)
			if(!contains(o))
				return false;
		return true;
	}
	
	default boolean containsAll(Object... objects) {
		for(Object o : objects)
			if(!contains(o))
				return false;
		return true;
	}
	
	default boolean isEmpty() {
		return size() == 0;
	}
	
}
