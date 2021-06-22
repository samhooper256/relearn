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
			return isValidBigDecimalWithoutSign(str.substring(1));
		else
			return isValidBigDecimalWithoutSign(str);
	}
	
	public static boolean isValidBigDecimal(String str, int startInclusive, int endExclusive) {
		return isValidBigDecimal(str.substring(startInclusive, endExclusive)); //TODO do without substring?
	}
	
	public static boolean isValidBigDecimalWithoutSign(String str) {
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
	
	public static boolean isValidBigDecimalWithoutSign(String str, int startInclusive, int endExclusive) {
		return isValidBigDecimalWithoutSign(str.substring(startInclusive, endExclusive)); //TODO do without substring?
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
	
	public static boolean equalWithinTolerancePercent(BigDecimal b1, BigDecimal b2, BigDecimal tolerancePercent) {
		BigDecimal abs1 = b1.abs(), abs2 = b2.abs();
		final BigDecimal tolerance;
		if(abs1.compareTo(abs2) >= 0)
			tolerance = abs1.multiply(tolerancePercent);
		else
			tolerance = abs2.multiply(tolerancePercent);
		return equalWithinTolerance(b1, b2, tolerance);
	}
	
}
