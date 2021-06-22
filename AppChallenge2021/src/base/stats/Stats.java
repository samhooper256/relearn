/**
 * 
 */
package base.stats;

import java.io.Serializable;


/**
 * @author Sam Hooper
 *
 */
public final class Stats implements Serializable, ReadOnlyStats {
	
	private static final long serialVersionUID = -4346811725723203551L;
	
	private int correct;
	private int incorrect;
	
	public Stats() {
		this(0, 0);
	}
	
	public Stats(int correct, int incorrect) {
		this.correct = correct;
		this.incorrect = incorrect;
	}
	
	@Override
	public int correct() {
		return correct;
	}
	
	@Override
	public int incorrect() {
		return incorrect;
	}
	
	@Override
	public int total() {
		return correct() + incorrect();
	}
	
	public void addCorrect() {
		correct++;
	}
	
	public void addIncorrect() {
		incorrect++;
	}
	
	public void addCorrect(int n) {
		correct += n;
	}
	
	public void addIncorrect(int n) {
		incorrect += n;
	}
	
	public void addStats(Stats s) {
		addCorrect(s.correct());
		addIncorrect(s.incorrect());
	}
	
	@Override
	public String toString() {
		return String.format("{%d, %d}", correct(), incorrect());
	}
	
}