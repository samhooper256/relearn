package base.stats;

/**
 * @author Sam Hooper
 */
public interface ReadOnlyTimeStats extends ReadOnlyStats {
	
	/** in millis. */
	double totalTime();
	
	/** in millis. */
	double fastestTime();
	
	/** in millis. */
	default double averageTime() {
		return totalTime() / total();
	}
	
}
