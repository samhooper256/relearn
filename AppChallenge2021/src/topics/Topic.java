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
	
	Problem generate();
	
}
