package utils;

import java.util.*;
import java.util.function.Predicate;

/**
 * <p>A {@code FilterableSet} provides an unmodifiable, filtered view of its {@link #backingUnmodifiable() backing set}. The
 * elements shown in the view are only those elements which {@link Predicate#test(Object) satisfy} the current
 * {@link #filter() filter}.</p>
 * 
 * <p>If no filter is set - that is, if {@link #hasFilter()} returns {@code false} (or, equivalently, if
 * {@link #filter()} is {@code null}) - then all elements of the backing set are shown in the view provided by this
 * {@code FilterableSet}.</p>
 * @author Sam Hooper
 *
 * @param <T> the type of the elements of the backing set (and the filtered view this interface provides).
 */
public interface FilterableSet<T> extends Set<T> {
	
	/** <p>Returns a new {@link FilterableSet} with the given {@link #backingUnmodifiable() backing set}.
	 * The {@link #filter() filter} of the returned set is {@code null} by default.
	 * The given set is <em>not</em> defensively copied. <b>Behavior is undefined if the given {@link Set} is
	 * modified in any way after this method is called.</b></p>*/
	static <S> FilterableSet<S> of(Set<S> backing) {
		return new FilterableSetImpl<>(backing);
	}
	
	/** Sets the {@link #filter() filter} used by this {@link FilterableSet}.*/
	void setFilter(Predicate<? super T> filter);
	
	/** Returns the current filter used by this {@link FilterableSet}, or {@code null} if no filter is set.*/
	Predicate<? super T> filter();
	
	/** The call
	 * <pre><code>hasFilter()</code></pre>
	 * is equivalent to:
	 * <pre><code>(filter() != null)</code></pre>
	 * */
	default boolean hasFilter() {
		return filter() != null;
	}
	
	/** Returns an unmodifiable view of this {@link FilterableSet FilterableSet's} backing {@link Set}.*/
	Set<T> backingUnmodifiable();
	
}
