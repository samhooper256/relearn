package utils;

import java.math.*;

import math.*;

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
	
	/** Displays the given {@link Complex} number. If the number is an integer, it will be displayed as a single
	 * integer without a denominator, even if the given object is a {@link Fraction}. Otherwise, if the given object
	 * is a {@code Fraction}, it will be displayed as if by a call to {@link #fraction(Fraction)}.*/
	public static String complex(Complex c) {
		if(c instanceof Fraction f) {
			if(f.isInteger())
				return integer(f.signedNumerator());
			else
				return fraction(f);
		}
		else if(c instanceof MixedNumber mn)
			return mixedNumber(mn);
		else if(c.isReal())
			return decimal(c.real());
		else if(c.isImaginary())
			return 	String.format("%s<mo>&#x2062;<!--INVISIBLE TIMES--></mo><mi>i</mi>",
					decimal(c.real()));
		String real = decimal(c.real());
		if(BigUtils.isNegative(c.imaginary()))
			real += "<mo>-</mo>";
		else
			real += "<mo>+</mo>";
		return real + complex(Complex.of(BigDecimal.ZERO, c.imaginary().abs()));
	}
	
	/** Displays the given fraction as a numerator over a denominator, <em>even if the fraction is an
	 * {@link Fraction#isInteger() integer}.</em>*/
	public static String fraction(Fraction f) {
		return 	String.format("<mfrac><mrow>%s</mrow><mrow>%s</mrow></mfrac>",
				integer(f.numerator()), integer(f.denominator()));
	}
	
	public static String mixedNumber(MixedNumber mn) {
		return integer(mn.integer()) + fraction(mn.fraction());
	}
}
