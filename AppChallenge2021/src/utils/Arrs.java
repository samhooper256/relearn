/**
 * 
 */
package utils;

/**
 * <p>Utilities for working with arrays.</p>
 * @author Sam Hooper
 *
 */
public final class Arrs {
	
	public static int indexOf(char[] arr, char val) {
		for(int i = 0; i < arr.length; i++)
			if(arr[i] == val)
				return i;
		return -1;
	}
	
	public static boolean contains(char[] arr, char val) {
		return indexOf(arr, val) >= 0;
	}
	
}
