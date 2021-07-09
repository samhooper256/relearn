package math;

import math.expressions.ComplexValuedExpression;

public class Tester {
	
	public static void main(String[] args) {
		ComplexValuedExpression tree = Evaluator.getTree("2^2^3");
		System.out.printf("tree=%s%n", tree);
		System.out.printf("value=%s%n", tree.value());
	}
	
}
