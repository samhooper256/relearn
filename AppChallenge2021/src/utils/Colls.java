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
	
	/** Returns the given {@link List}.
	 * @throws IndexOutOfBoundsException if the given index is out of bounds for a call to {@link List#get(int)}.
	 * @throws NullPointerException if {@code list} is {@code null}.*/
	public static <E> List<E> checkBounds(List<E> list, int index) {
		if(index < 0 || index > list.size())
			throw new IndexOutOfBoundsException(String.format("index %d is out of bounds for list of size %d", index,
					list.size()));
		return list;
	}
	
	/**
	 * Returns the given {@link List}.
	 * @throws IllegalArgumentException if the {@link List#size() size} of the given {@link List} is not {@code size}.
	 * @throws NullPointerException if {@code list} is {@code null}.
	 */
	public static <E> List<E> checkSize(List<E> list, int size) {
		if(list.size() != size)
			throw new IllegalArgumentException(String.format("The given list is not of size %d", size));
		return list;
	}
	
	/** Returns the number of objects in {@code items} that are contained in the given {@link Collection}.
	 * Assumes all elements in {@code items} are unique.*/
	public static int countContains(Collection<?> collection, Object... items) {
		int count = 0;
		for(Object item : items)
			if(collection.contains(item))
				count++;
		return count;
	}
	
}
