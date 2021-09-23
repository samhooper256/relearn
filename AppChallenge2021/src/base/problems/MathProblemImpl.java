package base.problems;

import java.math.BigDecimal;
import java.util.EnumSet;

import math.Complex;
import topics.Topic;

final class MathProblemImpl implements MathProblem {
	
	private final Topic topic;
	private final Statement statement;
	private final Complex answer;
	private final boolean isTolerant;
	private final BigDecimal tolerance;
	private final EnumSet<MathAnswerMode> answerModes;
	private final MathAnswerMode canonicalMode;
	
	MathProblemImpl(Topic topic, Statement statement, Complex answer, boolean isTolerant, BigDecimal tolerance,
			EnumSet<MathAnswerMode> answerModes, MathAnswerMode canonicalMode) {
		this.topic = topic;
		this.statement = statement;
		this.answer = answer;
		this.isTolerant = isTolerant;
		this.tolerance = tolerance;
		this.answerModes = answerModes;
		this.canonicalMode = canonicalMode;
	}

	@Override
	public Statement statement() {
		return statement;
	}

	@Override
	public Topic topic() {
		return topic;
	}

	@Override
	public Complex answer() {
		return answer;
	}

	@Override
	public boolean isTolerant() {
		return isTolerant;
	}

	@Override
	public EnumSet<MathAnswerMode> answerModes() {
		return answerModes;
	}

	@Override
	public MathAnswerMode canonicalMode() {
		return canonicalMode;
	}
	
	@Override
	public BigDecimal tolerance() {
		return tolerance;
	}
	
}