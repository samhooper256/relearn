/**
 * 
 */
package topics;

import java.math.BigDecimal;
import java.util.ArrayList;

import base.problems.*;
import math.*;
import topics.settings.*;
import utils.RNG;

/**
 * @author Ayuj Verma
 *
 */
public class Percentages extends AbstractTopic {

	private static final long serialVersionUID = 7787974191074258388L;
	
	public static final String NAME = "Percentages";
	public static final TopicFactory<Percentages> FACTORY = new TopicFactory<>(NAME, Percentages::new);
	
	private final IntRange percent;
	private final IntSetting maxNumber;
	
	public Percentages() {
		this(DEFAULT_COUNT);
	}
	
	public Percentages(int count) {
		super(count);
		percent = new IntRange("Percent", 1, 200, 1, 100);
		maxNumber = new IntSetting("Maximum Number", 1, 100, 50);
		createSettings(percent, maxNumber);
	}
	
	@Override
	public Problem generate() {
		ArrayList<Integer> vals = termCreate();
		BigDecimal actualAnswer = new BigDecimal(vals.get(0) * vals.get(1)).multiply(BigUtils.HUNDREDTH);
		String equation = String.format("What is %d%% of %d?", vals.get(0), vals.get(1));
		Complex answer = Complex.of(actualAnswer);
		MathProblem problem = MathProblem.builder()
				.set(this, Statement.ofText(equation), answer, MathAnswerMode.REAL_DECIMAL)
				.build();
		return problem;
	}
	
	@Override
	public String name() {
		return NAME;
	}
	
	public ArrayList<Integer> termCreate() {
		ArrayList<Integer> values = new ArrayList<Integer>();
		values.add(RNG.intInclusive(percent));
		values.add(RNG.intInclusive(1, maxNumber.value()));
		return values;
	}
	
}
