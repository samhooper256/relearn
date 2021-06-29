/**
 * 
 */
package utils;

import java.util.*;
import java.util.function.Supplier;

/**
 * @author Sam Hooper
 *
 */
class AudibleSetImpl<E> implements AudibleSet<E> {
	
	private final Set<E> set;
	private List<SingleListener<E>> addListeners, removeListeners;
	
	public AudibleSetImpl(Supplier<Set<E>> factory) {
		set = factory.get();
	}
	
	public AudibleSetImpl(Set<E> set) {
		this.set = set;
	}
	
	@Override
	public int size() {
		return set.size();
	}

	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return set.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		Iterator<E> iterator = set.iterator();
		//wrap in a new iterator so that the remove() operation is unsupported:
		return new Iterator<>() {

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public E next() {
				return iterator.next();
			}
			
		};
	}

	@Override
	public Object[] toArray() {
		return set.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}

	@Override
	public boolean add(E e) {
		boolean result = set.add(e);
		if(result)
			runAddListeners(e);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
		boolean result = set.remove(o);
		if(result)
			runRemoveListeners((E) o);
		return result;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return set.contains(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean changed = false;
		for(E item : c)
			changed |= add(item);
		return changed;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("retainAll");
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean changed = false;
		for(Object item : c)
			changed |= remove(item);
		return changed;
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("clear");
	}
	
	/** The listeners will be run after an element is added - that is, this {@link AudibleSetImpl} will
	 * contain the added element before the listeners are run.*/
	@Override
	public void addAddListener(SingleListener<E> listener) {
		if(addListeners == null)
			addListeners = new ArrayList<>();
		addListeners.add(listener);
	}
	
	private void runAddListeners(E newElement) {
		if(addListeners != null)
			for(SingleListener<E> listener : addListeners)
				listener.hear(newElement);
	}
	
	@Override
	public void addRemoveListener(SingleListener<E> listener) {
		if(removeListeners == null)
			removeListeners = new ArrayList<>();
		removeListeners.add(listener);
	}
	
	private void runRemoveListeners(E removedElement) {
		if(removeListeners != null)
			for(SingleListener<E> listener : removeListeners)
				listener.hear(removedElement);
	}
	
}
