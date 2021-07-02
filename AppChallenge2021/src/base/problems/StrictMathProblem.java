package base.problems;

import math.Complex;
import topics.Topic;

record StrictMathProblem(Topic topic, Statement statement, Complex answer) implements MathProblem {

	@Override
	public boolean isTolerant() {
		return false;
	}
	
}