/**
 * 
 */
package math;

import java.util.*;
import java.util.function.*;

import math.expressions.*;
import utils.*;

/**
 * @author Sam Hooper
 *
 */
public final class Evaluator {
	
	public static void main(String[] args) {
		
	}
	
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
		
		public boolean isOpenParenthesis() {
			return type() == TokenType.OPEN_PARENTHESIS;
		}
		
		public boolean isComma() {
			return type() == TokenType.COMMA;
		}
		
		public boolean isHardStart() {
			return isComma() || isOpenParenthesis();
		}
		
		public Associativity associativity() {
			if(!isOperator())
				System.out.printf("this token ('%s') is not an operator.%n", text());
			return associativity;
		}
		
		public boolean isLeftAssociative() {
			return associativity().isLeft();
		}
		
		@Override
		public String toString() {
			return String.format("%s", text());
		}
		
	}
	
	private enum TokenType {
		LITERAL, BINARY_OPERATOR, UNARY_OPERATOR, OPEN_PARENTHESIS, CLOSE_PARENTHESIS, FUNCTION, COMMA;
		
		public boolean isOperator() {
			return this == UNARY_OPERATOR || this == BINARY_OPERATOR;
		}
		
	}
	
	private enum Associativity {
		LEFT, RIGHT, BOTH;
		
		public boolean isLeft() {
			return this == LEFT || this == BOTH;
		}
		
	}
	
	private interface FunctionData {
		
		static FunctionData unary(String name, UnaryOperator<ComplexValuedExpression> function) {
			return new AbstractFunctionData(name, 1) {

				@Override
				public ComplexValuedExpression applyFunction(List<ComplexValuedExpression> arguments) {
					return applyFunction(Colls.checkSize(arguments, 1).get(0));
				}
				
				@Override
				public ComplexValuedExpression applyFunction(ComplexValuedExpression argument) {
					return function.apply(argument);
				}
				
			};
		}
		
		static FunctionData binary(String name, BinaryOperator<ComplexValuedExpression> function) {
			return new AbstractFunctionData(name, 2) {
				
				@Override
				public ComplexValuedExpression applyFunction(List<ComplexValuedExpression> arguments) {
					Colls.checkSize(arguments, 2);
					return function.apply(arguments.get(0), arguments.get(1));
				}
				
			};
		}
		
		String name();
		
		int arity();
		
		/** @throws IllegalArgumentException if {@code arguments.size() != arity()}.*/
		ComplexValuedExpression applyFunction(List<ComplexValuedExpression> arguments);
		
		/** This method is the same as {@link #applyFunction(List)}, but should only be called on
		 * {@link #isUnary() unary} functions. The default implementation simply invokes {@link #applyFunction(List)}.
		 * This method may be override to improve performance (e.g. by eliding the creation of a {@link List}).
		 * @throws IllegalStateException if the function represented by this {@link FunctionData} is not 
		 * {@link #isUnary() unary}.*/
		default ComplexValuedExpression applyFunction(ComplexValuedExpression argument) {
			if(!isUnary())
				throw new IllegalStateException("This is not a unary function");
			return applyFunction(List.of(argument));
		}
		
		default boolean isUnary() {
			return arity() == 1;
		}
		
	}
	private abstract static class AbstractFunctionData implements FunctionData {
		
		private final String name;
		private final int arity;
		
		public AbstractFunctionData(String name, int arity) {
			this.name = name;
			this.arity = arity;
		}
		
		@Override
		public String name() {
			return name;
		}
		
		@Override
		public int arity() {
			return arity;
		}
		
	}
	
	private static final String OPEN_PARENTHESIS = "(", CLOSE_PARENTHESIS = ")", COMMA = ",";
	private static final Set<String> OPERATORS_AND_SEPARATORS =
			Set.of("+", "-", "/", "*", "^", COMMA, OPEN_PARENTHESIS, CLOSE_PARENTHESIS);
	private static final Map<String, FunctionData> FUNCTION_DATA;
	
	static {
		FUNCTION_DATA = new HashMap<>();
		FUNCTION_DATA.put("sqrt", FunctionData.unary("sqrt", ComplexValuedExpression::sqrt));
		FUNCTION_DATA.put("abs", FunctionData.unary("abs", ComplexValuedExpression::abs));
		FUNCTION_DATA.put("frac", FunctionData.binary("frac", (a, b) -> a.over(b)));
	}
	
	public static ComplexValuedExpression getTree(String expression) {
		return new Evaluator(expression).getTree();
	}
	
	public static Complex evaluate(String expression) {
		return new Evaluator(expression).evaluate();
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
		if(OPERATORS_AND_SEPARATORS.contains(str))
			return start;
		for(String name : functionNames()) {
			int end = start + name.length();
			if(end > input.length())
				continue;
			if(input.substring(start, end).equals(name))
				return end - 1;
		}
		throw new IllegalArgumentException("Invalid input");
	}
	
	private static Collection<String> functionNames() {
		return FUNCTION_DATA.keySet();
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
			else if(isFunctionName(text)) {
				type = TokenType.FUNCTION;
			}
			else if(isComma(text)) {
				type = TokenType.COMMA;
			}
			else { //it must be an operator
				final Associativity associativity;
				if("-".equals(text)) {
					if(i == 0) {
						type = TokenType.UNARY_OPERATOR;
					}
					else {
						Token prev = tokens.get(i - 1);
						type = switch(prev.type()) {
							case OPEN_PARENTHESIS, BINARY_OPERATOR, UNARY_OPERATOR, COMMA -> TokenType.UNARY_OPERATOR;
							default -> TokenType.BINARY_OPERATOR;
						};
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
//			System.out.printf("t=%s, postfix=%s, operatorStack=%s%n", t, postfix, operatorStack);
			switch(t.type()) {
				case LITERAL -> postfix.add(t);
				case COMMA -> {
					Token top = operatorStack.peek();
					while(!top.isOpenParenthesis()) {
						postfix.add(operatorStack.pop());
						top = operatorStack.peek();
					}
				}
				case FUNCTION -> operatorStack.add(t);
				case OPEN_PARENTHESIS -> {
					operatorStack.push(t);
					postfix.add(t);
				}
				case CLOSE_PARENTHESIS -> {
					Token top = operatorStack.peek();
					while(!top.isOpenParenthesis() && !top.isComma()) {
						postfix.add(operatorStack.pop());
						top = operatorStack.peek();
					}
					operatorStack.pop(); //get rid of open parenthesis or comma
					if(top.isOpenParenthesis()) {
						postfix.add(t); //add the closing parenthesis 
						if(!operatorStack.isEmpty() && operatorStack.peek().type() == TokenType.FUNCTION)
							postfix.add(operatorStack.pop());
					}
				}
				case BINARY_OPERATOR, UNARY_OPERATOR -> {
					Token o1 = t, o2;
					while(!operatorStack.isEmpty() && !(o2 = operatorStack.peek()).isHardStart() &&
							(
								o2.precedence() > o1.precedence() ||
								o1.precedence() == o2.precedence() && o1.isLeftAssociative()
							)
					) {
						postfix.add(operatorStack.pop());
					}
					operatorStack.push(t);
				}
			}
		}
//		System.out.printf("finished reading tokens, postfix=%s, operatorStack=%s%n", postfix, operatorStack);
		while(!operatorStack.isEmpty()) {
			postfix.add(operatorStack.pop());
		}
		return postfix;
	}
	
	private static UnaryExpression unaryExpressionFrom(Token operator, ComplexValuedExpression operand) {
		if("-".equals(operator.text()))
			return operand.negate();
		throw new UnsupportedOperationException(String.format("Unrecognized operator: %s", operator));
	}
	
	private static BinaryExpression binaryExpressionFrom(Token operator, ComplexValuedExpression left, ComplexValuedExpression right) {
		return switch(operator.text()) {
			case "+" -> left.add(right);
			case "-" -> left.subtract(right);
			case "*" -> left.multiply(right);
			case "/" -> left.divide(right);
			case "^" -> left.pow(right);
			default -> throw new UnsupportedOperationException(String.format("Unrecognized operator: %s", operator));
		};
	}
	
	private static boolean isOpenParenthesis(String str) {
		return OPEN_PARENTHESIS.equals(str);
	}
	
	private static boolean isCloseParenthesis(String str) {
		return CLOSE_PARENTHESIS.equals(str);
	}
	
	private static boolean isComma(String str) {
		return COMMA.equals(str);
	}
	
	private static boolean isFunctionName(String str) {
		return functionNames().contains(str);
	}
	
	private static FunctionData dataFor(String functionName) {
		if(!isFunctionName(functionName))
			throw new IllegalArgumentException(String.format("%s is not a function name", functionName));
		return FUNCTION_DATA.get(functionName);
	}
	
	private static boolean isLiteralStart(char c) {
		return Parsing.isDigit(c) || c == Parsing.DECIMAL_POINT || c == Complex.IMAGINARY_UNIT_CHAR;
	}
	
	private final String expression;
	
	private Evaluator(String expression) {
		this.expression = clean(expression);
	}
	
	private ComplexValuedExpression getTree() {
		List<Token> tokens = tokenize(expression);
		List<Token> postfix = toPostfix(tokens);
		ComplexValuedExpression exp = treeFromPostfix(postfix);
		return exp;
	}
	
	private Complex evaluate() {
		return getTree().value();
	}
	
	private Complex parseLiteral(Token t) {
		return Complex.of(t.text());
	}
	
	/** Must be an instance method because it calls {@link #parseLiteral(Token)}.*/
	private ComplexValuedExpression treeFromPostfix(List<Token> postfix) {
		Stack<ComplexValuedExpression> stack = new Stack<>();
		for (int i = 0; i < postfix.size(); i++) {
			Token t = postfix.get(i);
			switch(t.type()) {
				case OPEN_PARENTHESIS -> { continue; }
				case CLOSE_PARENTHESIS -> {
					if(i != postfix.size() - 1 && postfix.get(i + 1).type() == TokenType.FUNCTION)
						continue;
					else 
						stack.push(stack.pop().parenthesized());
				}
				case LITERAL -> stack.add(ComplexValuedExpression.of(parseLiteral(t)));
				case UNARY_OPERATOR -> stack.push(unaryExpressionFrom(t, stack.pop()));
				case BINARY_OPERATOR -> {
					ComplexValuedExpression right = stack.pop();
					ComplexValuedExpression left = stack.pop();
					stack.push(binaryExpressionFrom(t, left, right));
				}
				case FUNCTION -> {
					FunctionData data = dataFor(t.text());
					if(data.isUnary()) {
						stack.push(data.applyFunction(stack.pop()));
					}
					else {
						List<ComplexValuedExpression> arguments = new ArrayList<>();
						for(int j = 0; j < data.arity(); j++)
							arguments.add(stack.pop());
						Collections.reverse(arguments);
						stack.push(data.applyFunction(arguments));
					}
				}
				default -> throw new IllegalStateException(String.format("Invalid token: %s", t));
			}
		}
		return stack.pop();
	}
	
}
