package utils;

import java.util.*;
import java.util.function.Predicate;

/**
 * 
 * @author Sam Hooper
 *
 */
class FilterableSetImpl<T> implements FilterableSet<T> {

	private final Set<T> backing;
	private final Set<T> view;
	
	private Predicate<? super T> filter;
	
	public FilterableSetImpl(Set<T> backing) {
		this.backing = backing;
		view = new HashSet<>(backing);
		filter = null;
	}
	
	@Override
	public int size() {
		return view.size();
	}

	@Override
	public boolean isEmpty() {
		return view.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return view.contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		return Iterators.unmodifiableIterator(view.iterator());
	}

	@Override
	public Object[] toArray() {
		return view.toArray();
	}

	@Override
	public <S> S[] toArray(S[] a) {
		return view.toArray(a);
	}

	@Override
	public boolean add(T e) {
		throw new UnsupportedOperationException("add");
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException("remove");
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return view.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		throw new UnsupportedOperationException("addAll");
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("retainAll");
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("removeAll");
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("clear");
	}

	@Override
	public void setFilter(Predicate<? super T> newFilter) {
		if(filter == newFilter)
			return;
		filter = newFilter;
		view.clear();
		if(filter == null) {
			view.addAll(backing);
		}
		else {
			for(T obj : backing)
				if(filter.test(obj))
					view.add(obj);
		}
	}

	@Override
	public Predicate<? super T> filter() {
		return filter;
	}

	@Override
	public Set<T> backingUnmodifiable() {
		return Collections.unmodifiableSet(backing);
	}

}
