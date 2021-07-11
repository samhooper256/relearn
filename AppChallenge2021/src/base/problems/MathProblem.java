package base.problems;

import java.math.BigDecimal;
import java.util.EnumSet;

import math.*;
import math.expressions.*;
import topics.Topic;

/**
 * @implNote implementations are only required to supported {@link #tolerance() tolerant answers} when the answer is
 * a {@link Complex #isReal() real} number.
 * @author Sam Hooper
 *
 */
public interface MathProblem extends Problem {
	
	/** If {@link MathProblem#isTolerant()} is not {@link #setTolerant(boolean) set}, it will be {@code false} by
	 * default. If the problem being built is not tolerant, then its {@link MathProblem#tolerance() tolerance} does not
	 * need to be {@link #setTolerance(BigDecimal) set}. If the problem is tolerant and the tolerance is not set,
	 * it will default to {@link MathProblem#DEFAULT_TOLERANCE}. All other parts must be set.*/
	class Builder {
		
		private Topic topic;
		private Statement statement;
		private Complex answer;
		private boolean isTolerant;
		private BigDecimal tolerance;
		private EnumSet<MathAnswerMode> modes;
		
		/** Sets {@link MathProblem#isTolerant()} to {@code false}.*/
		public Builder set
				(Topic topic, Statement statement, Complex answer, MathAnswerMode first, MathAnswerMode... rest) {
			setTopic(topic);
			setStatement(statement);
			setAnswer(answer);
			setModes(first, rest);
			setTolerant(false);
			return this;
		}
		
		/** Sets {@link MathProblem#isTolerant()} to {@code true}.*/
		public Builder set(Topic topic, Statement statement, Complex answer, BigDecimal tolerance,
				MathAnswerMode first, MathAnswerMode... rest) {
			setTopic(topic);
			setStatement(statement);
			setAnswer(answer);
			setModes(first, rest);
			setTolerant(true);
			setTolerance(tolerance);
			return this;
		}
		
		/** Sets {@link MathProblem#isTolerant()} to {@code false}.*/
		public Builder set(Topic topic, String expression, MathAnswerMode first, MathAnswerMode... rest) {
			setTopic(topic);
			setExpression(expression);
			setModes(first, rest);
			setTolerant(false);
			return this;
		}
		
		/** Sets {@link MathProblem#isTolerant()} to {@code false}.*/
		public Builder set
				(Topic topic, String expression, BigDecimal tolerance, MathAnswerMode first, MathAnswerMode... rest) {
			setTopic(topic);
			setExpression(expression);
			setModes(first, rest);
			setTolerant(true);
			setTolerance(tolerance);
			return this;
		}
		
		public Builder setTopic(Topic topic) {
			this.topic = topic;
			return this;
		}
		
		public Builder setStatement(Statement statement) {
			this.statement = statement;
			return this;
		}
		
		public Builder setAnswer(Complex answer) {
			this.answer = answer;
			return this;
		}
		
		public Builder setTolerant(boolean isTolerant) {
			this.isTolerant = isTolerant;
			return this;
		}
		
		public Builder setTolerance(BigDecimal tolerance) {
			this.tolerance = tolerance;
			return this;
		}
		
		public Builder setModes(MathAnswerMode first, MathAnswerMode... rest) {
			this.modes = EnumSet.of(first, rest);
			return this;
		}
		
		/** This is a convenience method for:
		 * <pre><code>
		 * 	ComplexValuedExpression tree = ComplexValuedExpression.of(expression);
		 *	setStatement(Statement.fromExpression(tree));
		 *	setAnswer(tree.value());
		 * </code></pre>*/
		public Builder setExpression(String expression) {
			ComplexValuedExpression tree = ComplexValuedExpression.of(expression);
			setStatement(Statement.fromExpression(tree));
			setAnswer(tree.value());
			return this;
		}
		
		private void ensureFullyConfigured() {
			if(	topic == null || statement == null || answer == null && !modes.isEmpty())
				throw new IllegalStateException("This MathProblem.Builder is not fully configured");
			if(modes.stream().anyMatch(MathAnswerMode::allowsComplex) && isTolerant)
				throw new IllegalStateException("A MathProblem allowing complex answers cannot be tolerant");
		}
		
		public MathProblem build() {
			ensureFullyConfigured();
			if(tolerance == null)
				tolerance = DEFAULT_TOLERANCE;
			return new MathProblemImpl(topic, statement, answer, isTolerant, tolerance, modes);
		}
		
	}
	
	/** The default {@link #tolerance() tolerance} of {@link #isTolerant() tolerant} {@link MathProblem MathProblems},
	 * equal to 5% (0.05).*/
	BigDecimal DEFAULT_TOLERANCE = new BigDecimal("0.05");
	
	static Builder builder() {
		return new Builder();
	}
	
	Complex answer();
	
	/** Returns {@code true} iff this {@link MathProblem} is tolerant.*/
	boolean isTolerant();

	/** The returned {@link EnumSet} may or may not me modifiable. However, the caller should never modify the returned
	 * set, and the returned set will never be modified by this {@link MathProblem}.*/
	EnumSet<MathAnswerMode> answerModes();
	
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
	default boolean isCorrect(String guess) { //TODO redo aware of answerModes();
		for(MathAnswerMode mode : answerModes())
			if(isCorrect(mode, guess))
				return true;
		return false;
	}
	
	private boolean isCorrect(MathAnswerMode mode, String guess) {
		if(!mode.isValid(guess))
			return false;
		Complex value = mode.parse(guess);
		if(answer().isReal() && value.isReal() && isTolerant())
			return BigUtils.equalWithinTolerancePercent(answer().real(), value.real(), tolerance());
		return answer().equals(value);
	}
	
}
