/**
 * 
 */
package utils;

/**
 * <p>Utilities for working with {@link String Strings}.</p>
 * @author Sam Hooper
 *
 */
public final class Strings {
	
	public static String stripTrailing(String str, char c) {
		int i = str.length();
		while(i > 0 && str.charAt(i - 1) == c)
			i--;
		return str.substring(0, i);
	}
	
	/** Removes the longest substring of the given string that contains only characters in {@code chars} and
	 * contains ends at the end of the string.*/
	public static String stripTrailing(String str, char... chars) {
		int i = str.length();
		while(i > 0 && Arrs.contains(chars, str.charAt(i - 1)))
			i--;
		return str.substring(0, i);
	}
	
}
