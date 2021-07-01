package utils;

import java.util.*;

/**
 * 
 * @author Sam Hooper
 *
 */
public final class Iterators {
	
	private Iterators() {
		
	}
	
	public static <T> Iterator<T> unmodifiableIterator(Iterator<? extends T> iterator) {
		return new Iterator<>() {

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public T next() {
				return iterator.next();
			}
			
		};
	}
	
	public static <T> ListIterator<T> unmodifiableListIterator(ListIterator<? extends T> iterator) {
		return new ListIterator<>() {

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public T next() {
				return iterator.next();
			}

			@Override
			public boolean hasPrevious() {
				return iterator.hasPrevious();
			}

			@Override
			public T previous() {
				return iterator.previous();
			}

			@Override
			public int nextIndex() {
				return iterator.nextIndex();
			}

			@Override
			public int previousIndex() {
				return iterator.previousIndex();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("remove");
			}

			@Override
			public void set(T e) {
				throw new UnsupportedOperationException("set");
			}

			@Override
			public void add(T e) {
				throw new UnsupportedOperationException("add");
			}
			
		};
	}
}
