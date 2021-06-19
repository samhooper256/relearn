/**
 * 
 */
package topics;

import java.util.List;

import base.*;
import utils.RNG;

/**
 * @author Sam Hooper
 *
 */
public class Addition extends AbstractTopic {
	
	private final IntSetting maxDigits;
	private final IntSetting terms;
	private final List<TopicSetting> settings;
	
	public Addition(int count) {
		super(count);
		maxDigits = new IntSetting("Digits", 1, 4, 1);
		terms = new IntSetting("Terms", 2, 4, 2);
		settings = List.of(maxDigits, terms);
	}
	
	@Override
	public Problem generate() {
		int left = RNG.intMaxDigits(maxDigits.value());
		int right = RNG.intMaxDigits(maxDigits.value());
		return MathProblem.fromExpression(String.format("%d+%d", left, right));
	}

	@Override
	public String name() {
		return "Addition";
	}

	@Override
	public List<TopicSetting> settings() {
		return settings;
	}
	
}
