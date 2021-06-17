/**
 * 
 */
package base;

import java.util.*;

/**
 * @author Sam Hooper
 *
 */
public final class Deck {
	
	private final List<Problem> problems;
	
	public static Deck of(List<Problem> problems) {
		return new Deck(new ArrayList<>(problems));
	}
	
	public static Deck of(Problem... problems) {
		List<Problem> list = new ArrayList<>(problems.length);
		Collections.addAll(list, problems);
		return new Deck(list);
	}
	
	private Deck(List<Problem> problems) {
		this.problems = problems;
	}
	
	public List<Problem> problems() {
		return Collections.unmodifiableList(problems);
	}
	
	public Problem at(int index) {
		return problems.get(index);
	}
}
