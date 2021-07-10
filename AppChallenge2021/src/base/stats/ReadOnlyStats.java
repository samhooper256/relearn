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
	
	/** Returns the percent accuracy as a {@code double} between {@code 0.0} and {@code 1.0}, inclusive.*/
	default double accuracy() {
		return (double) correct() / total();
	}
	
}