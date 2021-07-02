/**
 * 
 */
package base;

import java.math.BigDecimal;
import java.util.Objects;

import math.*;
import math.expressions.Expression;
import topics.Topic;
import utils.HTML;

/**
 * @author Sam Hooper
 *
 */
public class MathProblem implements Problem {
	
	private static final BigDecimal TOLERANCE_PERCENT = new BigDecimal("0.01");
	
	private final String html;
	private final Complex answer;
	private final Topic topic;
	private final boolean isTolerant;
	
	public static MathProblem fromExpression(Topic topic, String expression) {
		Expression tree = Expression.of(expression);
		return new MathProblem(topic, HTML.ensureHTMLTags(HTML.ensureMathTags(tree.toMathML())), tree.value(), false);
	}
	
	public MathProblem(Topic topic, String html, Complex answer, boolean tolerant) {
		this.html = html;
		this.answer = Objects.requireNonNull(answer);
		this.topic = topic;
		this.isTolerant = tolerant;
	}
	
	@Override
	public String displayHTML() {
		return html;
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
