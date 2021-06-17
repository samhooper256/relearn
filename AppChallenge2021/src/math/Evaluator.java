/**
 * 
 */
package math;

import java.util.*;

import utils.Parsing;

/**
 * @author Sam Hooper
 *
 */
public final class Evaluator {
	
	public static void main(String[] args) {
		evaluate("23i*i+(23+7i)^(99i)");
	}
	
	private static final class Token {
		
		private final String text;
		
		public Token(String text) {
			this.text = text;
		}
		
		public String text() {
			return text;
		}

		@Override
		public String toString() {
			return String.format("%s", text());
		}
		
	}
	
	private static final Set<String> NON_LITERAL_TOKENS = Set.of("+", "-", "/", "*", "^", "(", ")");
	
	private Evaluator() {
		
	}
	
	public static Complex evaluate(String expression) {
		expression = clean(expression);
		List<Token> tokens = tokenize(expression);
//		System.out.printf("tokenized %s to: %s%n", expression, tokens);
		System.err.println(String.format("Evaluator is unfinished. Expression: %s", expression));
		return Complex.of(7);
		//TODO
	}
	
	private static String clean(String expression) {
		return expression.strip();
	}
	
	private static List<Token> tokenize(String input) {
		List<Token> tokens = new ArrayList<>();
		for(int i = 0; i < input.length(); ) {
//			System.out.printf("(enter loop) i=%d%n", i);
			char at = input.charAt(i);
			int end;
			if(isLiteralStart(at))
				end = findEndOfLiteral(input, i);
			else
				end = findEndOfNonLiteral(input, i);
//			System.out.printf("i=%d, end=%d%n", i, end);
			Token t = new Token(input.substring(i, end + 1));
			tokens.add(t);
			i = end + 1;
		}
		return tokens;
	}
	
	private static boolean isLiteralStart(char c) {
		return Parsing.isDigit(c) || c == Parsing.DECIMAL_POINT || c == Complex.IMAGINARY_UNIT_CHAR;
	}
	
	/** returns the index of the last character in the literal starting at {@code start}.*/
	private static int findEndOfLiteral(String input, int start) {
		if(!isLiteralStart(input.charAt(start)))
			throw new IllegalArgumentException("A literal does not start at the start index.");
		boolean decimalFound = Parsing.isDecimalPoint(input.charAt(start));
		boolean imaginaryFound = Complex.isImaginaryUnit(input.charAt(start));
		int i = start + 1;
		for(; i < input.length(); i++) {
			char at = input.charAt(i);
			if(Parsing.isDecimalPoint(at)) {
				if(decimalFound)
					throw new IllegalArgumentException("Invalid input");
				decimalFound = true;
			}
			else if(Complex.isImaginaryUnit(at)) {
				if(imaginaryFound)
					throw new IllegalArgumentException("Invalid input");
				imaginaryFound = true;
			}
			else if(Parsing.isDigit(at)) {
				continue;
			}
			else {
				return i - 1;
			}
		}
		return i - 1;
	}
	
	private static int findEndOfNonLiteral(String input, int start) {
		String str = input.substring(start, start  + 1);
		if(!NON_LITERAL_TOKENS.contains(str))
			throw new IllegalArgumentException("Invalid input");
		return start;
	}
	
}
