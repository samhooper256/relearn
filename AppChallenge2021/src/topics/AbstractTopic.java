/**
 * 
 */
package topics;

import java.util.Objects;

/**
 * @author Sam Hooper
 *
 */
abstract class AbstractTopic implements Topic {
	
	private static final long serialVersionUID = -3884983155589210552L;
	
	protected static final int DEFAULT_COUNT = 10;

	private int count;
	
	/**
	 * @throws IllegalArgumentException if {@code (count <= 0)}. 
	 */
	public AbstractTopic(int count) {
		if(count <= 0)
			throw new IllegalArgumentException(String.format("count must be positive (was: %d)", count));
		this.count = count;
	}

	@Override
	public int count() {
		return count;
	}
	
	@Override
	public void setCount(int count) {
		this.count = count;
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof Topic t && Objects.equals(name(), t.name());
	}
	
}
