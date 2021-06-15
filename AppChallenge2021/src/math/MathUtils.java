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
	
}
