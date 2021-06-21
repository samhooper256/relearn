/**
 * 
 */
package base;

import java.util.Objects;

import math.*;
import topics.Topic;

/**
 * @author Sam Hooper
 *
 */
public class MathProblem implements Problem {

	private final String text;
	private final Complex answer;
	private final Topic topic;
	
	public static MathProblem fromExpression(Topic topic, String expression) {
		return new MathProblem(topic, expression, Evaluator.evaluate(expression));
	}
	
	public MathProblem(Topic topic, String text, Complex answer) {
		this.text = text;
		this.answer = Objects.requireNonNull(answer);
		this.topic = topic;
	}
	
	@Override
	public String displayText() {
		return text;
	}

	@Override
	public boolean isCorrect(String answer) {
		answer = answer.strip();
		return Complex.isValid(answer) && this.answer.equals(Complex.of(answer));
	}
	
	@Override
	public Topic topic() {
		return topic;
	}
	
}
