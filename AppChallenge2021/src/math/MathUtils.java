/**
 * 
 */
package math;

import java.math.BigInteger;
import java.util.*;

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
	
	/**
	 * Returns 10<sup>n</sup>.
	 * @throws ArithmeticException if {@code (n < 0)}.
	 */
	public static BigInteger pow10big(int n) {
		if(n < 0)
			throw new ArithmeticException("n < 0");
		return new BigInteger(String.format("1%s", "0".repeat(n)));
	}
	
	/** The returned {@link List} will contain unique elements, but they will not be in any particular order.*/
	public static List<Integer> factors(int n) {
		if(n <= 0)
			throw new IllegalArgumentException("n < 0");
		List<Integer> factors = new ArrayList<>();
		factors.add(1);
		if(n > 1) {
			factors.add(n);
			for(int i = 2; i * i <= n; i++) {
				if(n % i == 0) {
					factors.add(i);
					if(i * i != n)
						factors.add(n / i);
				}
			}
		}
		assert new HashSet<>(factors).size() == factors.size();
		return factors;
	}
	
}
