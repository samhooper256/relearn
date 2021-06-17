/**
 * 
 */
package math;

import java.math.BigInteger;

/**
 * <p>Math-related utilities.</p>
 * @author Sam Hooper
 *
 */
public final class MathUtils {
	
	private static final int[] POWERS_OF_10 =
		{1, 10, 100, 1_000, 10_000, 100_000, 1_000_000, 10_000_000, 100_000_000, 1_000_000_000};
	
	/** gcd(0, 0) returns 0.*/
	public static BigInteger gcd(BigInteger a, BigInteger b) {
		b = b.abs();
		if(BigUtils.isZero(a))
			return b;
		a = a.abs();
		if(BigUtils.isZero(b))
			return a;
		while(!BigUtils.isZero(b)) {
			BigInteger temp = b;
			b = a.mod(b);
			a = temp;
		}
		return a;
	}
	
	/**
	 * Returns 10<sup>n</sup>.
	 * @throws ArithmeticException if 10<sup>n</sup> is not representable in an {@code int}. 
	 */
	public static int pow10int(int n) {
		if(n > 9 || n < 0)
			throw new ArithmeticException(String.format("10^%d is not representable in an int", n));
		return POWERS_OF_10[n];
	}
}
