/**
 * 
 */
package utils;

/**
 * @author Sam Hooper
 *
 */
public interface ReadOnlyAudibleSet<E> extends ReadOnlyCollection<E> {
	
	void addAddListener(SingleListener<E> listener);
	
	void addRemoveListener(SingleListener<E> listener);
	
}
