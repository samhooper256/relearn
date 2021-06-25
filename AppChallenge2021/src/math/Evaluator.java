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
		private Associativity associativity;
		
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
		
		public void setType(TokenType newType) {
			if(type != null)
				throw new IllegalStateException("TokenType already set");
			this.type = newType;
		}
		
		public void setAssociativity(Associativity newAssociativity) {
			if(associativity != null)
				throw new IllegalStateException("Associativity already set");
			this.associativity = newAssociativity;
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
		
		public boolean isUnaryOperator() {
			return type() == TokenType.UNARY_OPERATOR;
		}
		
		public boolean isBinaryOperator() {
			return type() == TokenType.BINARY_OPERATOR;
		}
		
		public boolean isLiteral() {
			return type() == TokenType.LITERAL;
		}
		
		public boolean isOpenParenthesis() {
			return type() == TokenType.OPEN_PARENTHESIS;
		}
		
		public boolean isCloseParenthesis() {
			return type() == TokenType.CLOSE_PARENTHESIS;
		}
		
		public Associativity associativity() {
			if(!isOperator())
				System.out.printf("this token ('%s') is not an operator.%n", text());
			return associativity;
		}
		
		public boolean isLeftAssociative() {
			return associativity().isLeft();
		}
		
		public boolean isRightAssociative() {
			return associativity().isLeft();
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
	
	private enum Associativity {
		LEFT, RIGHT, BOTH;
		
		public boolean isLeft() {
			return this == LEFT || this == BOTH;
		}
		
		public boolean isRight() {
			return this == RIGHT || this == BOTH;
		}
		
	}
	
	private static final String OPEN_PARENTHESIS = "(";
	private static final String CLOSE_PARENTHESIS = ")";
	private static final Set<String> NON_LITERAL_TOKENS =
			Set.of("+", "-", "/", "*", "^", OPEN_PARENTHESIS, CLOSE_PARENTHESIS);
	
	private Evaluator() {
		
	}
	
	public static ConstantExpression getTree(String expression) {
		expression = clean(expression);
		System.out.printf("expression=%s%n", expression);
		List<Token> tokens = tokenize(expression);
		System.out.printf("\ttokens=%s%n", tokens);
		List<Token> postfix = toPostfix(tokens);
		System.out.printf("\tpostfix=%s%n", postfix);
		ConstantExpression exp = treeFromPostfix(postfix);
		return exp;
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
		findTypesAndAssociativites(tokens);
		return tokens;
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
	
	private static void findTypesAndAssociativites(List<Token> tokens) {
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
			else { //it must be an operator
				final Associativity associativity;
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
				associativity = findAssociativity(text, type);
				t.setAssociativity(associativity);
			}
			t.setType(type);
		}
	}
	
	private static Associativity findAssociativity(String op, TokenType type) {
		return switch(op) {
			case "-" -> type == TokenType.UNARY_OPERATOR ? Associativity.RIGHT : Associativity.LEFT;
			case "/" -> Associativity.LEFT;
			case "^" -> Associativity.RIGHT;
			case "+", "*" -> Associativity.BOTH;
			default -> throw new UnsupportedOperationException("Unrecognized operator: " + op);
		};
	}
	
	private static List<Token> toPostfix(List<Token> infix) {
		List<Token> postfix = new ArrayList<>();
		Stack<Token> operatorStack = new Stack<>();
		for(Token t : infix) {
			if(t.isLiteral()) {
				postfix.add(t);
			}
			else if(t.isOpenParenthesis()) {
				operatorStack.push(t);
			}
			else if(t.isCloseParenthesis()) {
				Token top = operatorStack.peek();
				while(!top.isOpenParenthesis()) {
					postfix.add(operatorStack.pop());
					top = operatorStack.peek();
				}
				operatorStack.pop(); //get rid of open parenthesis.
			}
			else if(t.isOperator()) {
				Token o1 = t;
				Token o2;
				while(!operatorStack.isEmpty() && !(o2 = operatorStack.peek()).isOpenParenthesis() &&
					(
						(o1.isLeftAssociative() && o1.precedence() <= o2.precedence()) ||
						(o1.isRightAssociative() && o1.precedence() < o2.precedence())
					)
				) {
					postfix.add(operatorStack.pop());
				}
				operatorStack.push(o1);
			}
		}
		while(!operatorStack.isEmpty())
			postfix.add(operatorStack.pop());
		return postfix;
	}
	
	private static ConstantExpression treeFromPostfix(List<Token> postfix) {
		Stack<ConstantExpression> stack = new Stack<>();
		for(Token t : postfix) {
			if(t.isLiteral()) {
				stack.add(new LiteralExpression(t.text()));
			}
			else {
				if(t.isUnaryOperator()) {
					stack.push(unaryExpressionFrom(t, stack.pop()));
				}
				else { //t must be binary operator
					ConstantExpression right = stack.pop();
					ConstantExpression left = stack.pop();
					stack.push(binaryExpressionFrom(t, left, right));
				}
			}
		}
		return stack.pop();
	}
	
	private static UnaryExpression unaryExpressionFrom(Token operator, ConstantExpression operand) {
		if("-".equals(operator.text())) {
			return new NegatedExpression(operand);
		}
		throw new UnsupportedOperationException(String.format("Unrecognized operator: %s", operator));
	}
	
	private static BinaryExpression binaryExpressionFrom
			(Token operator, ConstantExpression left, ConstantExpression right) {
		return switch(operator.text()) {
			case "+" -> new AdditionExpression(left, right);
			case "-" -> new SubtractionExpression(left, right);
			case "*" -> new MultiplicationExpression(left, right);
			case "/" -> new DivisionExpression(left, right);
			case "^" -> new ExponentiationExpression(left, right);
			default -> throw new UnsupportedOperationException(String.format("Unrecognized operator: %s", operator));
		};
	}
	
	private static boolean isOpenParenthesis(String str) {
		return OPEN_PARENTHESIS.equals(str);
	}
	
	private static boolean isCloseParenthesis(String str) {
		return CLOSE_PARENTHESIS.equals(str);
	}
	
	private static boolean isLiteralStart(char c) {
		return Parsing.isDigit(c) || c == Parsing.DECIMAL_POINT || c == Complex.IMAGINARY_UNIT_CHAR;
	}
}
