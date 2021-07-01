package utils;

import java.util.*;

/**
 * 
 * @author Sam Hooper
 *
 */
class FilterableStringListImpl implements FilterableStringList {
	
	private final List<String> backing;
	
	private List<String> view, viewTemp;
	private String filter;
	
	public FilterableStringListImpl(final List<String> backing) {
		this.backing = backing;
		filter = "";
		view = new ArrayList<>(backing);
		viewTemp = null; //will be initialized later if needed.
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

	/**
	 * {@inheritDoc}
	 * The returned iterator does not support the {@link Iterator#remove()} operation.
	 */
	@Override
	public Iterator<String> iterator() {
		Iterator<String> iterator = view.iterator();
		return new Iterator<>() {
			
			@Override
			public String next() {
				return iterator.next();
			}
			
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}
			
		};
	}

	@Override
	public Object[] toArray() {
		return view.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return view.toArray(a);
	}

	/** Unsupported. */
	@Override
	public boolean add(String e) {
		throw new UnsupportedOperationException("add");
	}

	/** Unsupported. */
	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException("remove");
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return view.containsAll(c);
	}

	/** Unsupported. */
	@Override
	public boolean addAll(Collection<? extends String> c) {
		throw new UnsupportedOperationException("addAll");
	}

	/** Unsupported. */
	@Override
	public boolean addAll(int index, Collection<? extends String> c) {
		throw new UnsupportedOperationException("addAll");
	}

	/** Unsupported. */
	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("removeAll");
	}

	/** Unsupported. */
	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("retainAll");
	}

	/** Unsupported. */
	@Override
	public void clear() {
		throw new UnsupportedOperationException("clear");
	}

	@Override
	public String get(int index) {
		return view.get(index);
	}

	/** Unsupported. */
	@Override
	public String set(int index, String element) {
		throw new UnsupportedOperationException("set");
	}

	/** Unsupported. */
	@Override
	public void add(int index, String element) {
		throw new UnsupportedOperationException("add");
	}

	/** Unsupported. */
	@Override
	public String remove(int index) {
		throw new UnsupportedOperationException("remove");
	}

	@Override
	public int indexOf(Object o) {
		return view.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return view.lastIndexOf(o);
	}

	@Override
	public ListIterator<String> listIterator() {
		return listIterator(0);
	}

	@Override
	public ListIterator<String> listIterator(int index) {
		return Iterators.unmodifiableListIterator(view.listIterator(index));
	}

	@Override
	public List<String> subList(int fromIndex, int toIndex) {
		return view.subList(fromIndex, toIndex);
	}

	@Override
	public String filter() {
		return filter;
	}

	@Override
	public void clearFilter() {
		filter = "";
		view.clear();
		view.addAll(backing);
	}

	@Override
	public void setFilter(String filter) {
		this.filter = filter;
		view.clear();
		for(String s : backing)
			if(s.startsWith(filter))
				view.add(s);
	}

	@Override
	public void addToFilter(String suffix) {
		initViewTemp();
		assert viewTemp.isEmpty();
		filter = filter + suffix;
		viewTemp.addAll(view);
		view.clear();
		for(String s : viewTemp)
			if(s.startsWith(suffix))
				view.add(s);
		viewTemp.clear();
	}

	private void initViewTemp() {
		if(viewTemp != null)
			viewTemp = new ArrayList<>(backing.size());
	}
	
	@Override
	public List<String> backingUnmodifiable() {
		return Collections.unmodifiableList(backing);
	}
	
	
}
