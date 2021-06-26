/**
 * 
 */
package base.sets;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Sam Hooper
 *
 */
public record ProblemSetData(Set<ProblemSet> sets, int nextUID) implements Serializable {
	
}
