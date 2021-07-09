package math;

import utils.Assertions;

public class EvaluatorTester {
	
	public static void main(String[] args) {
		Assertions.require();
		assert Evaluator.evaluate("sqrt(sqrt(16))").equals(Complex.of(2));
		assert Evaluator.evaluate("sqrt(sqrt(16))*1").equals(Complex.of(2));
		assert Evaluator.evaluate("sqrt(sqrt(16))*2*2").equals(Complex.of(8));
		assert Evaluator.evaluate("sqrt(sqrt(16))*2^2").equals(Complex.of(8));
		assert Evaluator.evaluate("sqrt(sqrt(16))*2^2^2").equals(Complex.of(32));
		assert Evaluator.evaluate("-sqrt(abs(-16))").equals(Complex.of(-4));
		assert Evaluator.evaluate("abs(-10--8)").equals(Complex.of(2));
		System.out.println("Success!");
	}
	
}
