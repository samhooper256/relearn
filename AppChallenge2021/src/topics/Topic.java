/**
 * 
 */
package topics;

import java.util.List;

import base.*;

/**
 * @author Sam Hooper
 *
 */
public interface Topic extends Named {
	
	List<TopicSetting> settings();
	
	int count();
	
	Problem generate();
	
	
	/**
	 * Returns {@code true} iff {@code this} and {@code o} are both {@link Topic Topics} with the same {@link #name()}.
	 */
	@Override
	boolean equals(Object o);
	
}
