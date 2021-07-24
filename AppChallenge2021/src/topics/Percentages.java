/**
 * 
 */
package topics;

import java.util.List;

import base.problems.*;
import math.*;
import topics.settings.*;
import utils.RNG;

/**
 * @author Sam Hooper
 *
 */
public class Percentages extends AbstractTopic {

	private static final long serialVersionUID = 4709652847759981097L;
	private static final int NUMBER_CAP = 1000;
	
	public static final String NAME = "Percentages";
	public static final TopicFactory<Percentages> FACTORY = new TopicFactory<>(NAME, Percentages::new);
	
	private static int generateMultipleOf100(int n) {
		return RNG.intInclusive(0, n) * 100;
	}
	
	private final IntRange percent;
	
	public Percentages() {
		this(DEFAULT_COUNT);
	}
	
	public Percentages(int count) {
		super(count);
		percent = new IntRange("Percent", 1, 200, 1, 100);
		createSettings(percent);
	}
	
	@Override
	public Problem generate() {
		int mul100 = generateMultipleOf100(percent.high());
		List<Integer> factors = mul100 == 0 ? List.of(0) : MathUtils.factors(mul100);
		//without the if statement below, mul100 might be 0 for removeIf(...) call, which means factors would be
		//unmodifiable.
		if(mul100 > 0)
			factors.removeIf(i -> i > NUMBER_CAP);
		int n1 = factors.get(RNG.intExclusive(0, factors.size()));
		int n2 = mul100 / n1;
		int percent = Math.min(n1, n2);
		int num = Math.max(n1, n2);
		int answer = num * percent / 100;
		return MathProblem.builder()
				.set(this,
					Statement.builder()
					.addText(String.format("What is %d%% of %d?", percent, num))
					.build(),
					Complex.of(answer),
					MathAnswerMode.REAL_DECIMAL)
				.build();
	}
	
	@Override
	public String name() {
		return NAME;
	}
	
}
