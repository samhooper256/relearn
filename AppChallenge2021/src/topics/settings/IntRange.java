package topics.settings;

public final class IntRange implements TopicSetting {
	
	private static final long serialVersionUID = 7954923562546121424L;
	
	private final String name;
	private final int max, min;
	
	private int low, high;
	
	/** By defualt, {@link #low()} and {@link #high()} are {@link #min()} and {@link #max()}, respectively.*/
	public IntRange(String name, int minValue, int maxValue) {
		this(name, minValue, maxValue, minValue, maxValue);
	}
	
	public IntRange(String name, int minValue, int maxValue, int defaultLow, int defaultHigh) {
		this.name = name;
		min = minValue;
		max = maxValue;
		low = defaultLow;
		high = defaultHigh;
	}

	@Override
	public String name() {
		return name;
	}
	
	public int low() {
		return low;
	}
	
	public int high() {
		return high;
	}
	
	public int min() {
		return min;
	}
	
	public int max() {
		return max;
	}
	
	public void setLow(int newLow) {
		if(!setLowIfPossible(newLow))
			throw new IllegalArgumentException(String.format("Invalid low value (%d) for %s%n", newLow, this));
	}
	
	public boolean setLowIfPossible(int newLow) {
		if(newLow < min() || newLow > high())
			return false;
		low = newLow;
		return true;
	}
	
	public void setHigh(int newHigh) {
		if(!setHighIfPossible(newHigh))
			throw new IllegalArgumentException(String.format("Invalid high value (%d) for %s%n", newHigh, this));
	}
	
	public boolean setHighIfPossible(int newHigh) {
		if(newHigh > max() || newHigh < low())
			return false;
		high = newHigh;
		return true;
	}
	
	/** Returns the <em>inclusive</em> range of the values between {@link #low()} and {@link #high()}. Equivalently,
	 * returns {@code (high() - low() + 1)}.*/
	public int range() {
		return high() - low() + 1;
	}
	
	/** Returns the <em>inclusive</em> range of the values between {@link #max()} and {@link #min()}. Equivalently,
	 * returns {@code (max() - min() + 1)}.*/
	public int maxRange() {
		return max() - min() + 1;
	}
	
	public boolean inRange(int value) {
		return value >= min() && value <= max();
	}
	
	@Override
	public String toString() {
		return String.format("IntRange[min=%d, low=%d, high=%d, max=%d]", min(), low(), high(), max());
	}
	
}
