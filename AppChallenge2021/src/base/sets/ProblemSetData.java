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
public final class ProblemSetData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7493411446352327480L;
	
	private final Set<ProblemSet> sets;
	private final int nextUID;
	
	public ProblemSetData(Set<ProblemSet> sets, int nextUID) {
		this.sets = sets;
		this.nextUID = nextUID;
	}
	
	public Set<ProblemSet> sets() {
		return sets;
	}
	
	public int nextUID() {
		return nextUID;
	}
	
}
