package utils;

import java.util.List;

/**
 * <p>A {@link List} that can be filtered based on a given {@code String} {@link #filter() filter}. The
 * {@code FilterableStringList} will only contain the elements of its {@link #backingUnmodifiable() backing list} that
 * {@link String#startsWith(String) start with} the current filter.</p>
 * 
 * @author Sam Hooper
 * 
 * */
public interface FilterableStringList extends List<String> {
	
	/** <p>Returns a new {@link FilterableStringList} with the given {@link #backingUnmodifiable() backing list}.
	 * The {@link #filter() filter} of the returned list is, by default, the empty string.
	 * The given list is <em>not</em> defensively copied. <b>Behavior is undefined if the given {@link List} is
	 * modified in any way after this method is called.</b></p>*/
	static FilterableStringList of(List<String> backing) {
		return new FilterableStringListImpl(backing);
	}
	
	/** Returns the current filter that this {@link FilterableStringList} is using. The returned value will never be
	 * {@code null}.*/
	String filter();
	
	/** Sets this {@link FilterableStringList FilterableStringList's} {@link #filter() filter} to the empty
	 * string. This is equivalent to {@code setFilter("")}.*/
	void clearFilter();
	
	/** Sets this {@link FilterableStringList FilterableStringList's} {@link #filter() filter} to the given
	 * {@code String}.*/
	void setFilter(String filter);
	
	/** Appends the given suffix to this {@link FilterableStringList FilterableStringList's}
	 * {@link #filter() filter}. The call:
	 * <pre><code>
	 * 	addToFilter(suffix)
	 * </code></pre>
	 * is equivalent to, but likely much more efficient than:
	 * <pre><code>
	 * 	setFilter(filter() + suffix)
	 * </code></pre>*/
	void addToFilter(String suffix);
	
	/** Removes the given suffix from this {@link FilterableStringList FilterableStringList's} {@link #filter() filter}.
	 * The call:
	 * <pre><code>
	 * 	removeFromFilter(suffix)
	 * </code></pre>
	 * is equivalent to:
	 * <pre><code>
	 * 	if(!filter().endsWith(suffix))
	 * 		throw new IllegalArgumentException("Invalid suffix: " + suffix);
	 * 	setFilter(filter().substring(0, filter().lastIndexOf(suffix)));
	 * </code></pre>
	 * */
	default void removeFromFilter(String suffix) {
		if(!filter().endsWith(suffix))
			 throw new IllegalArgumentException("Invalid suffix: " + suffix);
		setFilter(filter().substring(0, filter().lastIndexOf(suffix)));
	}
	
	/** Returns an unmodifiable view of this {@link FilterableStringList FilterableStringList's} backing {@link List}.*/
	List<String> backingUnmodifiable();
	
}
