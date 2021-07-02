package base.problems;

import java.math.BigDecimal;

import math.Complex;
import topics.Topic;

record TolerantMathProblem(Topic topic, Statement statement, Complex answer, BigDecimal tolerance)
		implements MathProblem {
	
	TolerantMathProblem(Topic topic, Statement statement, Complex answer) {
		this(topic, statement, answer, MathProblem.DEFAULT_TOLERANCE);
	}

	@Override
	public boolean isTolerant() {
		return true;
	}
	
}
