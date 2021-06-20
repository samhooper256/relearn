/**
 * 
 */
package topics;

import java.io.Serializable;
import java.util.List;

import base.*;

/**
 * @author Sam Hooper
 *
 */
public interface Topic extends Named, Serializable {
	
	List<TopicSetting> settings();
	
	int count();
	
	void setCount(int count);
	
	Problem generate();
	
	
	/**
	 * Returns {@code true} iff {@code this} and {@code o} are both {@link Topic Topics} with the same {@link #name()}.
	 */
	@Override
	boolean equals(Object o);
	
}
