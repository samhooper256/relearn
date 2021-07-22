/**
 * 
 */
package base.stats;

/**
 * @author Sam Hooper
 */
class AccuracyStats implements ReadOnlyAccuracyStats {
	
	private static final long serialVersionUID = -4346811725723203551L;
	
	private int correct, incorrect;
	
	AccuracyStats() {
		this(0, 0);
	}
	
	AccuracyStats(int correct, int incorrect) {
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
	
	void addCorrect() {
		correct++;
	}
	
	void addIncorrect() {
		incorrect++;
	}
	
	void addCorrect(int n) {
		correct += n;
	}
	
	void addIncorrect(int n) {
		incorrect += n;
	}
	
	void addStats(AccuracyStats s) {
		addCorrect(s.correct());
		addIncorrect(s.incorrect());
	}
	
	void removeStats(AccuracyStats s) {
		removeCorrect(s.correct());
		removeIncorrect(s.incorrect());
	}
	
	public void removeCorrect(int n) {
		if(correct() - n < 0)
			throw new IllegalArgumentException(
					String.format("Cannot remove %d correct because count would be negative", n));
		correct -= n;
	}
	
	public void removeIncorrect(int n) {
		if(incorrect() - n < 0)
			throw new IllegalArgumentException(
					String.format("Cannot remove %d incorrect because count would be negative", n));
		incorrect -= n;
	}
	
	
	@Override
	public String toString() {
		return String.format("{%d, %d}", correct(), incorrect());
	}
	
}