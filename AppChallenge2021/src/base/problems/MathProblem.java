package base.problems;

import java.math.BigDecimal;

import math.*;
import math.expressions.ComplexValuedExpression;
import topics.Topic;

/**
 * @implNote implementations are only required to supported {@link #tolerance() tolerant answers} when the answer is
 * a {@link Complex #isReal() real} number.
 * @author Sam Hooper
 *
 */
public interface MathProblem extends Problem {
	
	/** The default {@link #tolerance() tolerance} of {@link #isTolerant() tolerant} {@link MathProblem MathProblems},
	 * equal to 5% (0.05).*/
	BigDecimal DEFAULT_TOLERANCE = new BigDecimal("0.05");
	
	/** The returned {@link MathProblem} is not {@link #isTolerant() tolerant}.*/
	static MathProblem of(Topic topic, Statement statement, Complex answer) {
		return new StrictMathProblem(topic, statement, answer);
	}
	
	static MathProblem ofTolerant(Topic topic, Statement statement, Complex answer) {
		return new TolerantMathProblem(topic, statement, answer);
	}
	
	static MathProblem ofTolerant(Topic topic, Statement statement, Complex answer, BigDecimal tolerance) {
		return new TolerantMathProblem(topic, statement, answer, tolerance);
	}
	
	/** The returned {@link MathProblem} is not {@link #isTolerant() tolerant}.*/
	static MathProblem fromExpression(Topic topic, String expression) {
		return fromExpression(topic, ComplexValuedExpression.of(expression));
	}

	/** The returned {@link MathProblem} is not {@link #isTolerant() tolerant}.*/
	static MathProblem fromExpression(Topic topic, ComplexValuedExpression expression) {
		return of(topic, Statement.fromExpression(expression), expression.value());
	}
	
	static MathProblem fromExpressionTolerant(Topic topic, String expression) {
		return fromExpressionTolerant(topic, ComplexValuedExpression.of(expression));
	}

	static MathProblem fromExpressionTolerant(Topic topic, String expression, BigDecimal tolerance) {
		return fromExpressionTolerant(topic, ComplexValuedExpression.of(expression), tolerance);
	}
	
	static MathProblem fromExpressionTolerant(Topic topic, ComplexValuedExpression expression) {
		return ofTolerant(topic, Statement.fromExpression(expression), expression.value());
	}
	
	static MathProblem fromExpressionTolerant(Topic topic, ComplexValuedExpression expression, BigDecimal tolerance) {
		return ofTolerant(topic, Statement.fromExpression(expression), expression.value(), tolerance);
	}
	
	Complex answer();
	
	/** Returns {@code true} iff this {@link MathProblem} is tolerant.*/
	boolean isTolerant();

	/** A {@link BigDecimal} representing the
	 * {@link BigUtils#equalWithinTolerancePercent(BigDecimal, BigDecimal, BigDecimal) tolerance} of this
	 * {@link MathProblem}. For example, if the tolerance is {@code 0.05}, then any guess that is within 5% of the
	 * value of the true {@link #answer() answer} is considered {@link #isCorrect(String) correct}.
	 * @throws IllegalStateException if this {@link MathProblem} is not {@link #isTolerant() tolerant}.*/
	default BigDecimal tolerance() {
		if(isTolerant())
			throw new UnsupportedOperationException("Should be overridden.");
		else
			throw new IllegalStateException("Cannot get tolerance because this is not a tolerant MathProblem.");
	}
	
	@Override
	default String sampleAnswer() {
		return answer().toString();
	}

	@Override
	default boolean isCorrect(String guess) {
		if(!Complex.isValid(guess))
			return false;
		Complex g = Complex.of(guess);
		if(isTolerant())
			return 	g.isReal() &&
					BigUtils.equalWithinTolerancePercent(answer().real(), g.real(), tolerance());
		else
			return g.equals(answer());
	}
	
}
