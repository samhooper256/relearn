package math;

import utils.Assertions;

public class EvaluatorTester {
	
	public static void main(String[] args) {
		Assertions.require();
		assert Evaluator.evaluate("2+3*5").equals(Complex.of(17));
		assert Evaluator.evaluate("(2+3)*5").equals(Complex.of(25));
		assert Evaluator.evaluate("2^3^2").equals(Complex.of(512));
		assert Evaluator.evaluate("(2^3)^2").equals(Complex.of(64));
		assert Evaluator.evaluate("sqrt(sqrt(16))").equals(Complex.of(2));
		assert Evaluator.evaluate("sqrt(sqrt(16))*1").equals(Complex.of(2));
		assert Evaluator.evaluate("sqrt(sqrt(16))*2*2").equals(Complex.of(8));
		assert Evaluator.evaluate("sqrt(sqrt(16))*2^2").equals(Complex.of(8));
		assert Evaluator.evaluate("sqrt(sqrt(16))*2^2^2").equals(Complex.of(32));
		assert Evaluator.evaluate("-sqrt(abs(-16))").equals(Complex.of(-4));
		assert Evaluator.evaluate("abs(-10--8)").equals(Complex.of(2));
		assert Evaluator.evaluate("--frac(-4,-1)").equals(Complex.of(4));
		assert Evaluator.evaluate("--frac(-4,2^2)").equals(Complex.of(-1));
		assert Evaluator.evaluate("--frac(abs(12)+7^2,sqrt(4)-frac(0,77))").equals(Complex.of("30.5"));
		assert Evaluator.evaluate("--frac(abs(12)+7^2,sqrt(4)-frac(0,77))*1+0").equals(Complex.of("30.5"));
		System.out.println("Success!");
	}
	
}
