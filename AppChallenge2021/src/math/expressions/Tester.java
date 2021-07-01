package math.expressions;

import java.math.BigDecimal;

import math.Complex;

public class Tester {
	
	public static void main(String[] args) {
		Expression real = Expression.of(Complex.of(BigDecimal.ONE));
		Expression realNeg = Expression.of(Complex.of(BigDecimal.ONE.negate()));
		Expression im = Expression.of(Complex.of(BigDecimal.ZERO, BigDecimal.ONE));
		Expression imNeg = Expression.of(Complex.of(BigDecimal.ZERO, BigDecimal.ONE.negate()));
		Expression complex = Expression.of(Complex.of(BigDecimal.ONE.negate(), BigDecimal.TEN.negate()));
		Expression complex2 = Expression.of(Complex.of(BigDecimal.ONE.negate(), BigDecimal.TEN));
		Expression complex3 = Expression.of(Complex.of(BigDecimal.ONE, BigDecimal.TEN));
		Expression complex4 = Expression.of(Complex.of(BigDecimal.ONE, BigDecimal.TEN));
		System.out.println(real);
		System.out.println(realNeg);
		System.out.println(im);
		System.out.println(imNeg);
		System.out.println(complex);
		System.out.println(complex2);
		System.out.println(complex3);
		System.out.println(complex4);
	}
	
}
