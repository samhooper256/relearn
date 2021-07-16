/**
 * 
 */
package math;

import math.expressions.ComplexValuedExpression;

/**
 * @author Sam Hooper
 *
 */
public class MathTester {
	
	public static void main(String[] args) {
		ComplexValuedExpression tree = ComplexValuedExpression.of("frac(1,2)+frac(3,4)");
		System.out.printf("tree=%s%n", tree);
		
	}
}
