package base.stats;

/**
 * @author Sam Hooper
 */
final class TimeStats implements ReadOnlyTimeStats {

	private static final long serialVersionUID = -4501904052827629417L;
	
	private double totalTime, fastestTime;
	private int total;
	
	TimeStats() {
		total = 0;
		totalTime = 0;
		fastestTime = Double.POSITIVE_INFINITY;
	}
	
	void addTime(double timeInMillis) {
		assert timeInMillis >= 0;
		totalTime += timeInMillis;
		fastestTime = Math.min(fastestTime, timeInMillis);
	}

	@Override
	public int total() {
		return total;
	}

	@Override
	public double totalTime() {
		return totalTime;
	}

	@Override
	public double fastestTime() {
		return fastestTime;
	}
	
}
