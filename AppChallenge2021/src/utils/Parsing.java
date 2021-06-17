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
	
}
