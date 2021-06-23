/**
 * 
 */
package utils;

import java.util.Random;

import math.MathUtils;

/**
 * @author Sam Hooper
 *
 */
public final class RNG {
	
	private static final Random RANDOM = new Random();
	
	public static Random source() {
		return RANDOM;
	}
	
	/** Returns a random {@code int} in the range [low, high].
	 * @throws IllegalArgumentException if {@code low > high}.*/
	public static int intInclusive(int low, int high) {
		if(low > high)
			throw new IllegalArgumentException(String.format("low > high (%d > %d)", low, high));
		return (int) (RANDOM.nextDouble() * (high - low) + low);
	}
	
	/** Returns a random {@code int} in the range [low, high).
	 * @throws IllegalArgumentException if {@code low >= high}.*/
	public static int intExclusive(int low, int high) {
		if(low >= high)
			throw new IllegalArgumentException(String.format("low >= high (%d >= %d)", low, high));
		return intInclusive(low, high - 1);
	}
	
	/** 
	 * Returns a random positive {@code int} with between 1 and {@code maxDigits} digits, inclusive.
	 * @throws IllegalArgumentException if {@code maxDigits <= 0}.
	 * */
	public static int intMaxDigits(int maxDigits) {
		if(maxDigits <= 0)
			throw new IllegalArgumentException(String.format("maxDigits <= 0 (was %d)", maxDigits));
		return intExclusive(0, MathUtils.pow10int(maxDigits));
	}
	
	public static double next() {
		return source().nextDouble();
	}
	
	public static double high() {
		return 0.5 + low();
	}
	
	public static double low() {
		return next() / 2;
	}
	
}
