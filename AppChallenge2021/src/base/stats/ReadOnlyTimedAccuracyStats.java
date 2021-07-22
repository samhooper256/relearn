package base.stats;

/**
 * @author Sam Hooper
 */
public interface ReadOnlyTimedAccuracyStats extends ReadOnlyAccuracyStats, ReadOnlyTimeStats {
	
	/** Equivalent to {@link #fastestCorrectTime()}.*/
	@Override
	default double fastestTime() {
		return fastestCorrectTime();
	}

	/** Returns the time of the fastest correct problem, in millis. */
	double fastestCorrectTime();
	
}
