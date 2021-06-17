/**
 * 
 */
package base;

import java.util.Objects;

import math.*;

/**
 * @author Sam Hooper
 *
 */
public class MathProblem implements Problem {

	private final String text;
	private final Complex answer;
	
	public static MathProblem fromExpression(String expression) {
		return new MathProblem(expression, Evaluator.evaluate(expression));
	}
	
	public MathProblem(String text, Complex answer) {
		this.text = text;
		this.answer = Objects.requireNonNull(answer);
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
	
}
