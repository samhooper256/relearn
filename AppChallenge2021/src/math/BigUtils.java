/**
 * 
 */
package math;

import java.math.*;

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
	
}
