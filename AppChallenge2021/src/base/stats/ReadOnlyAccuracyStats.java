/**
 * 
 */
package base.stats;

/**
 * @author Sam Hooper
 */
public interface ReadOnlyAccuracyStats extends ReadOnlyStats {
	
	int correct();
	
	int incorrect();
	
	/** Returns the percent accuracy as a {@code double} between {@code 0.0} and {@code 1.0}, inclusive.*/
	default double accuracy() {
		return (double) correct() / total();
	}
	
	default boolean hasCorrect() {
		return correct() > 0;
	}
	
}