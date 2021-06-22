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
	
	String displayText();
	
	String sampleAnswer();
	
	boolean isCorrect(String answer);
	
	Topic topic();
	
}
