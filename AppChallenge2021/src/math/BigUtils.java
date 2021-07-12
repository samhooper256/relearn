/**
 * 
 */
package math;

import static utils.Parsing.containsOnlyDigits;
import static utils.Parsing.isRealInDecimalFormWithoutSign;
import static utils.Parsing.isSign;

import java.math.*;

import utils.*;

/**
 * <p>Utilities for working with {@link BigDecimal BigDecimals}. All methods throw NPE if an argument is {@code null}.
 * </p>
 * @author Sam Hooper
 *
 */
public final class BigUtils {
		
	private BigUtils() {
		
	}
	
	public static final BigDecimal HUNDRED = new BigDecimal("100");
	public static final BigDecimal HUNDREDTH = new BigDecimal("0.01");
	
	public static boolean equals(BigInteger b1, BigInteger b2) {
		return b1.compareTo(b2) == 0;
	}
	
	public static boolean equals(BigDecimal b1, BigDecimal b2) {
		return b1.compareTo(b2) == 0;
	}
	
	public static boolean isZero(BigDecimal bd) {
		return bd.compareTo(BigDecimal.ZERO) == 0;
	}
	
	public static boolean isPositive(BigDecimal bd) {
		return bd.compareTo(BigDecimal.ZERO) > 0;
	}
	
	public static boolean isNonNegative(BigDecimal bd) {
		return bd.compareTo(BigDecimal.ZERO) >= 0;
	}
	
	public static boolean isNegative(BigDecimal bd) {
		return bd.compareTo(BigDecimal.ZERO) < 0;
	}
	
	public static boolean isNonPositive(BigDecimal bd) {
		return bd.compareTo(BigDecimal.ZERO) <= 0;
	}
	
	public static boolean isZero(BigInteger bi) {
		return bi.compareTo(BigInteger.ZERO) == 0;
	}
	
	public static boolean isPositive(BigInteger bi) {
		return bi.compareTo(BigInteger.ZERO) > 0;
	}
	
	public static boolean isNonNegative(BigInteger bi) {
		return bi.compareTo(BigInteger.ZERO) >= 0;
	}
	
	public static boolean isNegative(BigInteger bi) {
		return bi.compareTo(BigInteger.ZERO) < 0;
	}
	
	public static boolean isNonPositive(BigInteger bi) {
		return bi.compareTo(BigInteger.ZERO) <= 0;
	}
	
	public static boolean isValidBigDecimal(String str) {
		if(str.isBlank())
			return false;
		if(isSign(str.charAt(0)))
			return isValidUnsignedBigDecimal(str.substring(1));
		else
			return isValidUnsignedBigDecimal(str);
	}
	
	public static boolean isValidBigDecimal(String str, int startInclusive, int endExclusive) {
		return isValidBigDecimal(str.substring(startInclusive, endExclusive)); //TODO do without substring?
	}
	
	public static boolean isValidUnsignedBigDecimal(String str) {
		if(str.isBlank())
			return false;
		int exponentIndex = Strings.indexOf(str, Parsing::isExponentIndicator);
		if(exponentIndex >= 0) {
			return 	exponentIndex < str.length() - 1 &&
					isRealInDecimalFormWithoutSign(str, 0, exponentIndex) &&
					isValidBigInteger(str, exponentIndex + 1);
		}
		else {
			return isRealInDecimalFormWithoutSign(str);
		}
	}
	
	public static boolean isValidUnsignedBigDecimal(String str, int startInclusive, int endExclusive) {
		return isValidUnsignedBigDecimal(str.substring(startInclusive, endExclusive)); //TODO do without substring?
	}
	
	
	public static boolean isValidBigInteger(String str) {
		return isValidBigInteger(str, 0, str.length());
	}
	
	public static boolean isValidBigInteger(String str, int startInclusive) {
		return isValidBigInteger(str, startInclusive, str.length());
	}
	
	public static boolean isValidBigInteger(String str, int startInclusive, int endExclusive) {
		if(isSign(str.charAt(startInclusive)))
			startInclusive++;
		return containsOnlyDigits(str, startInclusive, endExclusive);
	}
	
	public static String toPrettyString(BigDecimal bd) {
		String str = bd.toPlainString();
		if(Strings.contains(str, Parsing.DECIMAL_POINT)) {
			str = Strings.stripTrailing(str, '0');
			str = Strings.stripTrailing(str, '.');
		}
		return str;
	}
	
	public static boolean equalWithinTolerance(BigDecimal b1, BigDecimal b2, BigDecimal tolerance) {
		return b1.subtract(b2).abs().compareTo(tolerance) <= 0;
	}
	
	/** Returns {@code true} iff {@code estimate} is within the closed interval:
	 * <pre>[trueValue - A*tolerancePercent, trueValue + A*tolerancePercent]</pre>,
	 * where {@code A} is the {@link BigDecimal#abs() absolute value} of {@code trueValue}.
	 * @throws IllegalArgumentException if {@code tolerancePercent} is negative.*/
	public static boolean equalWithinTolerancePercent
			(BigDecimal trueValue, BigDecimal estimate, BigDecimal tolerancePercent) {
		if(isNegative(tolerancePercent))
			throw new IllegalArgumentException(
					String.format("tolerancePercent must not be negative, was: %f", tolerancePercent));
		final BigDecimal tolerance = trueValue.abs().multiply(tolerancePercent);
		return equalWithinTolerance(trueValue, estimate, tolerance);
	}
	
}
