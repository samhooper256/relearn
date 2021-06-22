/**
 * 
 */
package base;

import java.math.BigDecimal;
import java.util.Objects;

import math.*;
import topics.Topic;

/**
 * @author Sam Hooper
 *
 */
public class MathProblem implements Problem {
	
	private static final BigDecimal TOLERANCE_PERCENT = new BigDecimal("0.01");
	
	private final String text;
	private final Complex answer;
	private final Topic topic;
	private final boolean isTolerant;
	
	public static MathProblem fromExpression(Topic topic, String expression) {
		return new MathProblem(topic, expression, Evaluator.evaluate(expression), false);
	}
	
	public MathProblem(Topic topic, String text, Complex answer, boolean tolerant) {
		this.text = text;
		this.answer = Objects.requireNonNull(answer);
		this.topic = topic;
		this.isTolerant = tolerant;
	}
	
	@Override
	public String displayText() {
		return text;
	}

	@Override
	public boolean isCorrect(String guess) {
		guess = guess.strip();
		if(Complex.isValid(guess)) {
			Complex c = Complex.of(guess);
			if(answer.equals(c))
				return true;
			if(isTolerant && answer.isReal() && c.isReal()) {
				return BigUtils.equalWithinTolerancePercent(answer.real(), c.real(), TOLERANCE_PERCENT);
			}
		}
		else if(Fraction.isValid(guess)) {
			Fraction f = Fraction.of(guess);
			if(answer.equals(f))
				return true;
			if(isTolerant && answer.isReal())
				return BigUtils.equalWithinTolerancePercent(answer.real(), f.real(), TOLERANCE_PERCENT);
		}
		return false;
	}
	
	@Override
	public Topic topic() {
		return topic;
	}

	@Override
	public String sampleAnswer() {
		return answer.toString();
	}
	
}
