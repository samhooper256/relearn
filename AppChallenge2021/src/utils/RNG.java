/**
 * 
 */
package utils;

import java.util.*;

import math.MathUtils;
import topics.settings.IntRange;

/**
 * @author Sam Hooper
 *
 */
public final class RNG {
	
	private static final Random RANDOM = new Random();
	
	public static Random source() {
		return RANDOM;
	}
	
	public static int intInclusive(IntRange range) {
		return intInclusive(range.low(), range.high());
	}
	
	/** Returns a random {@code int} in the range [low, high].
	 * @throws IllegalArgumentException if {@code low > high}.*/
	public static int intInclusive(int low, int high) {
		if(low > high)
			throw new IllegalArgumentException(String.format("low > high (%d > %d)", low, high));
		return (int) (RANDOM.nextDouble() * (high - low + 1) + low);
	}
	
	/** Returns a random {@code int} in the range [low, high).
	 * @throws IllegalArgumentException if {@code low >= high}.*/
	public static int intExclusive(int low, int high) {
		if(low >= high)
			throw new IllegalArgumentException(String.format("low >= high (%d >= %d)", low, high));
		return intInclusive(low, high - 1);
	}
	
	/**
	 * Equivalent to {@code intExclusive(0, high)}.
	 */
	public static int intExclusive(int high) {
		return intExclusive(0, high);
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
	
	public static <E> E pick(E[] items) {
		return items[intExclusive(items.length)];
	}
	
	public static <E> E pick(List<? extends E> items) {
		return items.get(intExclusive(items.size()));
	}
	
	/** Returns a {@link List} containing two elements of {@code items} from different indices.
	 * @throws IllegalArgumentException if {@code (items.length < 2)}.*/
	public static <E> List<E> pick2Unique(E[] items) {
		if(items.length < 2)
			throw new IllegalArgumentException("items.length < 2");
		if(items.length == 2)
			return List.of(items[0], items[1]);
		int i1 = intExclusive(items.length), i2 =intExclusive(items.length - 1);
		Arrs.swap(items, i1, items.length - 1);
		List<E> list = List.of(items[items.length - 1], items[i2]);
		Arrs.swap(items, i1, items.length - 1);
		return list;
	}
	
}
