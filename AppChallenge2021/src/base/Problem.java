/**
 * 
 */
package base;

import topics.Topic;

/**
 * @author Sam Hooper
 *
 */
public interface Problem {
	
	String displayHTML();
	
	String sampleAnswer();
	
	boolean isCorrect(String answer);
	
	Topic topic();

}
