/**
 * 
 */
package base.stats;

/**
 * @author Sam Hooper
 *
 */
public interface ReadOnlyStats {
	
	int correct();
	
	int incorrect();
	
	int total();
	
	default boolean isEmpty() {
		return total() == 0;
	}
	
}