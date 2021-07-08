package utils;

import java.util.*;

/**
 * <p>Utilities for working with {@link Collection Collections}.</p>
 * @author Sam Hooper
 *
 */
public final class Colls {

	private Colls() {
		
	}
	
	/** <p>Sets the element at the given {@code index} in the given {@link List} to {@code element}. If the index
	 * is greater than the list's {@link List#size() size}, {@code null} elements are added until the index is reached.
	 * </p>*/
	public static <E> void set(List<? super E> list, int index, E item) {
		if(index < list.size()) {
			list.set(index, item);
		}
		else {
			while(index > list.size())
				list.add(null);
			list.add(item);
		}
	}
	
	/**
	 * <p>Returns the element of the given {@link List} at the given {@code index}, or {@code null} if that index is
	 * out of range (including if it is negative).</p>
	 */
	public static <E> E get(List<? extends E> list, int index) {
		return index >= 0 && index < list.size() ? list.get(index) : null;
	}
	
}
