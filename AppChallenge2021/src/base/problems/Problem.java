/**
 * 
 */
package base.problems;

import topics.Topic;

/**
 * @author Sam Hooper
 *
 */
public interface Problem {
	
	Statement statement();
	
	Topic topic();
	
	boolean isCorrect(String answer);
	
	String sampleAnswer();
	
}
