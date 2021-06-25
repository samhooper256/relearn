/**
 * 
 */
package base.stats;

import base.sets.ProblemSet;
import javafx.scene.layout.StackPane;

/**
 * @author Sam Hooper
 *
 */
public class SetTabContent extends StackPane {
	
	private final ProblemSet set;
	
	public SetTabContent(ProblemSet set) {
		this.set = set;
	}
	
	public ProblemSet set() {
		return set;
	}
	
}
