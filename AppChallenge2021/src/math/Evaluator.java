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
	
	private static final class Token {
		
		private final String text;
		private TokenType type;
		
		public Token(String text) {
			this.text = text;
			type = null;
		}
		
		public String text() {
			return text;
		}
		
		public TokenType type() {
			return type;
		}
		
		public void setType(TokenType type) {
			if(type == null)
				throw new IllegalStateException("TokenType already set");
			this.type = type;
		}
		
		public int precedence() {
			if(!isOperator())
				throw new IllegalStateException(String.format("This token ('%s') is not an operator", text()));
			return switch(text) {
				case "+" -> 1;
				case "-" -> type() == TokenType.UNARY_OPERATOR ? 3 : 1;
				case "*", "/" -> 2;
				case "^" -> 4;
				default -> throw new IllegalStateException(String.format("Unrecognized operator: %s", text()));
			};
		}
		
		public boolean isOperator() {
			return type().isOperator();
		}
		
		@Override
		public String toString() {
			return String.format("%s", text());
		}
		
	}
	
	private enum TokenType {
		LITERAL, BINARY_OPERATOR, UNARY_OPERATOR, OPEN_PARENTHESIS, CLOSE_PARENTHESIS;
		
		public boolean isOperator() {
			return this == UNARY_OPERATOR || this == BINARY_OPERATOR;
		}
	}
	
	private static final String OPEN_PARENTHESIS = "(";
	private static final String CLOSE_PARENTHESIS = ")";
	private static final Set<String> NON_LITERAL_TOKENS =
			Set.of("+", "-", "/", "*", "^", OPEN_PARENTHESIS, OPEN_PARENTHESIS);
	
	private Evaluator() {
		
	}
	
	public static ConstantExpression getTree(String expression) {
		expression = clean(expression);
		List<Token> tokens = tokenize(expression);
		ConstantExpression tree = TreeGenerator.generate(tokens);
		return tree;
	}
	
	public static Complex evaluate(String expression) {
		return getTree(expression).value();
	}
	
	private static String clean(String expression) {
		return expression.strip();
	}
	
	private static List<Token> tokenize(String input) {
		List<Token> tokens = new ArrayList<>();
		for(int i = 0; i < input.length(); ) {
			char at = input.charAt(i);
			int end;
			if(isLiteralStart(at))
				end = findEndOfLiteral(input, i);
			else
				end = findEndOfNonLiteral(input, i);
			Token t = new Token(input.substring(i, end + 1));
			tokens.add(t);
			i = end + 1;
		}
		findTypes(tokens);
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
	
	private static void findTypes(List<Token> tokens) {
		for(int i = 0; i < tokens.size(); i++) {
			Token t = tokens.get(i);
			String text = t.text();
			final TokenType type;
			if(text.contains("i") || Parsing.containsDigit(text)) {
				type = TokenType.LITERAL;
			}
			else if(isOpenParenthesis(text)) {
				type = TokenType.OPEN_PARENTHESIS;
			}
			else if(isCloseParenthesis(text)) {
				type = TokenType.CLOSE_PARENTHESIS;
			}
			else {
				if("-".equals(text)) {
					if(i == 0) {
						type = TokenType.UNARY_OPERATOR;
					}
					else {
						Token prev = tokens.get(i - 1);
						TokenType prevType = prev.type();
						if(	prevType == TokenType.OPEN_PARENTHESIS ||
							prevType == TokenType.BINARY_OPERATOR ||
							prevType == TokenType.UNARY_OPERATOR)
							type = TokenType.UNARY_OPERATOR;
						else
							type = TokenType.BINARY_OPERATOR;
					}
				}
				else {
					type = TokenType.BINARY_OPERATOR;
				}
			}
			t.setType(type);
		}
	}
	
	private static boolean isOpenParenthesis(String str) {
		return OPEN_PARENTHESIS.equals(str);
	}
	
	private static boolean isCloseParenthesis(String str) {
		return CLOSE_PARENTHESIS.equals(str);
	}
	
	private static final class TreeGenerator {
		
		private final List<Token> tokens;
		private int index;
		
		public static ConstantExpression generate(List<Token> tokens) {
			return new TreeGenerator(tokens).generate();
		}
		
		public TreeGenerator(List<Token> tokens) {
			this.tokens = tokens;
		}
		
		public ConstantExpression generate() {
			index = 0;
			ConstantExpression exp = parse(0);
			while(index < tokens.size()) {
				Token t = tokens.get(index);
				index++;
				exp = combineTerms(t, exp, parse(t.precedence()));
			}
			return exp;
		}
		
		/** Leaves {@link #index} on a binary operator or the end of the input.*/
		public ConstantExpression parse(int precedence) {
			System.out.printf("[enter] parse(precedence=%d), index=%d%n", precedence, index);
			Token t = tokens.get(index);
			System.out.printf("\tt=%s%n", t);
			String text = t.text();
			TokenType ty = t.type();
			if(ty == TokenType.LITERAL) {
				LiteralExpression lit = new LiteralExpression(t.text());
				if(index == tokens.size() - 1) {
					index++;
					return lit;
				}
				Token next = tokens.get(index + 1);
				if(next.type() == TokenType.BINARY_OPERATOR && next.precedence() > precedence) {
					index += 2;
					ConstantExpression nextExp = parse(next.precedence());
					return combineTerms(next, lit, nextExp);
				}
				else {
					index++;
					return lit;
				}
			}
			if(ty == TokenType.UNARY_OPERATOR) {
				if(text.equals("-")) {
					index++;
					return new NegatedExpression(parse(t.precedence()));
				}
				else {
					throw new UnsupportedOperationException(String.format("Unrecognized operator: %s", text));
				}
			}
			if(ty == TokenType.OPEN_PARENTHESIS) {
				index++; //get past opening parenthesis
				ConstantExpression exp = parse(0);
				index++; //get past closing parenthesis
				return exp;
			}
			throw new UnsupportedOperationException("oof");
		}
	}
	
	private static ConstantExpression combineTerms
			(Token binaryOperator, ConstantExpression left, ConstantExpression right) {
		String text = binaryOperator.text();
		return switch(text) {
			case "+" -> new AdditionExpression(left, right);
			case "-" -> new SubtractionExpression(left, right);
			case "*" -> new MultiplicationExpression(left, right);
			case "/" -> new DivisionExpression(left, right);
			case "^" -> new ExponentiationExpression(left, right);
			default -> throw new UnsupportedOperationException(
					String.format("Unrecognized operator: %s", text));
		};
	}
}
