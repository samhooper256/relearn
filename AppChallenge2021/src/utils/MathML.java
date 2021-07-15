package utils;

import java.math.*;

import math.Fraction;

/**
 * 
 * @author Sam Hooper
 *
 */
public final class MathML {

	private MathML() {
		
	}
	
	public static String integer(BigInteger integer) {
		return String.format("<mn>%d</mn>", integer);
	}
	
	public static String decimal(BigDecimal integer) {
		return String.format("<mn>%d</mn>", integer);
	}
	
	public static String fraction(Fraction f) {
		return 	String.format("<mfrac><mrow>%s</mrow><mrow>%s</mrow></mfrac>",
				integer(f.numerator()), integer(f.denominator()));
	}
	
}
