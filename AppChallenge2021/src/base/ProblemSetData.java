/**
 * 
 */
package base;

import java.io.Serializable;

/**
 * @author Sam Hooper
 *
 */
public record ProblemSetData(String name, SetConfiguration config) implements Serializable {
	
	public ProblemSet toProblemSet() {
		return new ProblemSet(name, config);
	}
	
}
