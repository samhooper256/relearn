/**
 * 
 */
package math;

/**
 * @author Sam Hooper
 *
 */
public class MathTester {
	
	public static void main(String[] args) {
		isValid("23");
		isValid(" -23");
		isValid("1/2");
		isValid("-1/2 ");
		isValid("+9999999999999999999999999999999999999/2 ");
		isValid("+34 /2 ");
		isValid("+34/ 2 ");
		isValid("3 4/2 ");
	}
	
	private static void isValid(String str) {
		boolean res = Fraction.isValid(str);
		System.out.printf("\"%s\" : %b%n", str, res);
		if(res) {
			System.out.printf("\t%s%n", Fraction.of(str));
		}
	}
}
