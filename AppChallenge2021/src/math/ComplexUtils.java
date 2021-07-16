package math;

import java.math.*;

final class ComplexUtils {

	private ComplexUtils() {
		
	}
	
	private static final MathContext CONTEXT = MathContext.DECIMAL128;
	
	public static Complex add(Complex a, Complex b) {
		if(a instanceof FractionConvertible ac && b instanceof FractionConvertible bc)
			return ac.toFraction().addFraction(bc.toFraction());
		return Complex.of(
			a.real().add(b.real(), CONTEXT),
			a.imaginary().add(b.imaginary(), CONTEXT)
		);
	}
	
	public static Complex subtract(Complex a, Complex b) {
		return add(a, b.negate());
	}
	
	public static Complex multiply(Complex a, Complex b) {
		if(a instanceof FractionConvertible ac && b instanceof FractionConvertible bc)
			return ac.toFraction().multiplyFraction(bc.toFraction());
		return multiplyRectangular(a, b);
	}

	private static Complex multiplyRectangular(Complex first, Complex second) {
		if(first.isReal() && second.isReal())
			return Complex.of(first.real().multiply(second.real(), CONTEXT));
		BigDecimal a = first.real(), b = first.imaginary();
		BigDecimal c = second.real(), d = second.imaginary();
		BigDecimal s1 = a.multiply(c, CONTEXT);
		BigDecimal s2 = b.multiply(d, CONTEXT);
		BigDecimal s3 = a.add(b, CONTEXT).multiply(c.add(d, CONTEXT));
		return Complex.of(
			s1.subtract(s2, CONTEXT), s3.subtract(s1, CONTEXT).subtract(s2, CONTEXT)
		);
	}
	
	public static Complex divide(Complex a, Complex b) {
		if(a instanceof FractionConvertible ac && b instanceof FractionConvertible bc)
			return ac.toFraction().divideFraction(bc.toFraction());
		return divideRectangular(a, b);
	}
	
	private static Complex divideRectangular(Complex a, Complex b) {
		if(a.isReal() && b.isReal())
			return Complex.of(a.real().divide(b.real(), CONTEXT));
		Complex conj = b.conjugate();
		BigDecimal div = b.abs2();
		Complex num = a.multiply(conj);
		return Complex.of(num.real().divide(div, CONTEXT), num.imaginary().divide(div, CONTEXT));
	}
}
