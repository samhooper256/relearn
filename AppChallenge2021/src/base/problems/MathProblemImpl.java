package base.problems;

import java.math.BigDecimal;
import java.util.EnumSet;

import math.Complex;
import topics.Topic;

record MathProblemImpl
		(Topic topic, Statement statement, Complex answer, boolean isTolerant, BigDecimal tolerance,
		EnumSet<MathAnswerMode> answerModes) implements MathProblem {
	
	
}