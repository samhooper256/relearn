/**
 * 
 */
package utils;

import java.util.*;

/**
 * @author Sam Hooper
 *
 */
public interface ReadOnlyCollection<E> extends Iterable<E> {

	boolean contains(Object o);
	
	boolean containsAll(Collection<?> c);
	
	int size();
	
	default boolean isEmpty() {
		return size() == 0;
	}
	
}
