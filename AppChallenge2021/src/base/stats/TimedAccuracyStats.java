package base.stats;

/**
 * @author Sam Hooper
 */
public final class TimedAccuracyStats implements ReadOnlyTimedAccuracyStats {

	private static final long serialVersionUID = -451808215194235229L;

	private int correct, incorrect;
	private double totalTime, fastestCorrectTime;
	
	public TimedAccuracyStats() {
		reset();
	}
	
	public void addCorrect(double timeInMillis) {
		correct++;
		addTime(timeInMillis);
		fastestCorrectTime = Math.min(fastestCorrectTime, timeInMillis);
	}
	
	public void addIncorrect(double timeInMillis) {
		incorrect++;
		addTime(timeInMillis);
	}
	
	private void addTime(double timeInMillis) {
		totalTime += timeInMillis;
	}

	public void reset() {
		totalTime = correct = incorrect = 0;
		fastestCorrectTime = Double.POSITIVE_INFINITY;
	}
	
	@Override
	public int correct() {
		return correct;
	}
	
	@Override
	public int incorrect() {
		return incorrect;
	}
	
	@Override
	public int total() {
		return correct() + incorrect();
	}
	
	@Override
	public double totalTime() {
		return totalTime;
	}
	
	@Override
	public double fastestCorrectTime() {
		if(fastestCorrectTime == Double.POSITIVE_INFINITY)
			throw new IllegalStateException("Cannot get the fastest time; no times have been recorded yet.");
		return fastestCorrectTime;
	}
	
	@Override
	public String toString() {
		return String.format("TimedStats[%d/%d, totalTime=%.3f, fastestCorrectTime=%.3f]", correct(), incorrect(),
				totalTime(), fastestTime());
	}
	
}
