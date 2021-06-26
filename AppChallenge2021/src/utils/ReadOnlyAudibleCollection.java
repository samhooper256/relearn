/**
 * 
 */
package utils;

/**
 * @author Sam Hooper
 *
 */
public interface ReadOnlyAudibleCollection<E> extends ReadOnlyCollection<E> {
	
	void addAddListener(SingleListener<E> listener);
	
	void addRemoveListener(SingleListener<E> listener);
	
}
