package math.units;

public enum Time implements Unit {
	MILLISECOND(1d/1000), SECOND(1), MINUTE(60), HOUR(60 * MINUTE.factor()), DAY(24 * HOUR.factor());
	
	private final double factor;
	
	Time(double factor) {
		this.factor = factor;
	}
	
	@Override
	public double factor() {
		return factor;
	}
	
	/** Example of use:
	 * <pre><code>Time.MINUTES.convert(30, Time.HOUR)</code></pre>
	 * would convert 30 minutes to one hour and return {@code 0.5}.
	 * @param timeInThisUnit a time in the unit denoted by {@code this} object.
	 * */ 
	public double convert(double timeInThisUnit, Time destUnit) {
		return timeInThisUnit * factor() / destUnit.factor();
	}
	
}
