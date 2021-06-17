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
public class Addition implements Topic {
	
	private final IntSetting maxDigits;
	private final List<TopicSetting> settings;
	
	public Addition() {
		this.maxDigits = new IntSetting("Digits", 1, 4, 1);
		this.settings = List.of(maxDigits);
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
