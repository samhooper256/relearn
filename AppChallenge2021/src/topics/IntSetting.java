/**
 * 
 */
package topics;

/**
 * @author Sam Hooper
 *
 */
public final class IntSetting implements TopicSetting {
	
	private static final long serialVersionUID = -8350093090440983667L;
	
	private final String name;
	private final int max, min;
	private int value;
	
	public IntSetting(String name, int minValue, int maxValue, int defaultValue) {
		this.name = name;
		this.min = minValue;
		this.max = maxValue;
		this.value = defaultValue;
	}
	
	@Override
	public String name() {
		return name;
	}
	
	public int value() {
		return value;
	}
	
	public void set(int newValue) {
		this.value = newValue;
	}
	
	public int max() {
		return max;
	}
	
	public int min() {
		return min;
	}
	
}
