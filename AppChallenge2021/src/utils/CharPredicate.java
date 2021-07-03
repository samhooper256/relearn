/**
 * 
 */
package utils;

/**
 * @author Sam Hooper
 *
 */
@FunctionalInterface
public interface CharPredicate {
	
	static CharPredicate of(char c) {
		return k -> k == c;
	}
	
	static CharPredicate any(char... chars) {
		return k -> Arrs.contains(chars, k);
	}
	
	boolean test(char c);
	
	default CharPredicate negate() {
		return c -> !test(c);
	}
	
}
