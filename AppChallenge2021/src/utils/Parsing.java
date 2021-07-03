/**
 * 
 */
package utils;

import java.util.Objects;

/**
 * @author Sam Hooper
 *
 */
public final class Parsing {
	
	public static final char DECIMAL_POINT = '.';
	
	/** Returns {@code true} iff the given {@code String} could be passed to {@link Integer#parseInt(String)} without
	 * throwing a {@link NumberFormatException}.
	 * @throws NullPointerException if {@code str} is {@code null}.*/
	public static boolean isint(String str) {
		Objects.requireNonNull(str);
		//TODO do properly
		try {
			Integer.parseInt(str);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	/** Returns {@code true} iff the given {@code String} could be passed to {@link Long#parseLong(String)} without
	 * throwing a {@link NumberFormatException}.
	 * @throws NullPointerException if {@code str} is {@code null}.*/
	public static boolean islong(String str) {
		Objects.requireNonNull(str);
		//TODO do properly
		try {
			Long.parseLong(str);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public static boolean containsOnlyDigits(String str) {
		return containsOnlyDigits(str, 0);
	}
	
	public static boolean containsOnlyDigits(String str, int startInclusive) {
		return containsOnlyDigits(str, startInclusive, str.length());
	}
	
	/**
	 * @throws IndexOutOfBoundsException if the indices are invalid.
	 * */
	public static boolean containsOnlyDigits(String str, int startInclusive, int endExclusive) {
		for(int i = startInclusive; i < endExclusive; i++)
			if(!isDigit(str.charAt(i)))
				return false;
		return true;
	}
	
	public static boolean containsAnyDigits(String str) {
		for(int i = 0; i < str.length(); i++)
			if(isDigit(str.charAt(i)))
				return true;
		return false;
	}
	
	
	public static boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}
	
	public static boolean isNonDigit(char c) {
		return !isDigit(c);
	}
	
	public static boolean isExponentIndicator(char c) {
		return c == 'e' || c == 'E';
	}
	
	public static boolean isSign(char c) {
		return c == '+' || c == '-';
	}
	
	public static boolean isDigitOrSign(char c) {
		return isDigit(c) || isSign(c);
	}
	
	public static boolean isDecimalPoint(char c) {
		return c == DECIMAL_POINT;
	}
	
	/** <p>Returns {@code true} if the given {@code String} is an unsigned real number in decimal form -
	 * that is, a string of one or more digits, optionally followed by a decimal point and another string of
	 * one or more digits.</p>
	 * <p>This method does <b>not</b> accept a number with no digits after the decimal point.</p>
	 * */
	public static boolean isRealInDecimalFormWithoutSign(String str) {
		int decimalIndex = str.indexOf(DECIMAL_POINT);
		if(decimalIndex == str.length() - 1)
			return false;
		return containsOnlyDigits(str, 0, decimalIndex) && containsOnlyDigits(str, decimalIndex + 1);
	}
	
	public static boolean isRealInDecimalFormWithoutSign(String str, int startInclusive) {
		return isRealInDecimalFormWithoutSign(str, startInclusive, str.length()); //TODO do without substring?
	}
	
	public static boolean isRealInDecimalFormWithoutSign(String str, int startInclusive, int endExclusive) {
		return isRealInDecimalFormWithoutSign(str.substring(startInclusive, endExclusive)); //TODO do without substring?
	}
	
	public static boolean containsDigit(String str) {
		return Strings.contains(str, Parsing::isDigit);
	}
	
	public static String withoutWhitespace(String str) {
		return Regex.WHITESPACE.matcher(str).replaceAll("");
	}
	
}
