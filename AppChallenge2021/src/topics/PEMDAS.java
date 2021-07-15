/**
 * 
 */
package topics;

import java.util.*;

import base.problems.*;
import math.*;
import math.expressions.ComplexValuedExpression;
import topics.settings.IntSetting;
import utils.RNG;

/**
 * @author Sam Hooper and Ayuj Verma
 *
 */
public class PEMDAS extends AbstractTopic {

	private static final long serialVersionUID = 4273852660100258380L;
 	
	public static final String NAME = "PEMDAS";
	public static final TopicFactory<PEMDAS> FACTORY = new TopicFactory<>(NAME, PEMDAS::new);
	
	private final IntSetting maxDigits;
	
	public PEMDAS() {
		this(DEFAULT_COUNT);
	} 
	
	public PEMDAS(int count) {
		super(count);
		maxDigits = new IntSetting("Digits", 1, 2, 1);
		createSettings(maxDigits);
	}
	
	@Override
	public Problem generate() { 
		ComplexValuedExpression tree;
		Complex answer;
		//Despite our best efforts, there is always the rare possibility of generating an expression such as "1÷(3-3)"
		//that involves dividing by zero. In that case, ComplexValuedExpression.value() throws an ArithmeticException.
		while(true) {
			try {
				tree = ComplexValuedExpression.of(createExpression());
				answer = tree.value();
			} catch (ArithmeticException e) {
				continue;
			}
			break;
		}
		return MathProblem.builder()
				.set(this, Statement.fromExpression(tree), answer,
						BigUtils.HUNDREDTH, MathAnswerMode.REAL_DECIMAL, MathAnswerMode.REAL_FRACTION)
				.build();
		//The tolerance allows for something like "1÷3" to be typed exactly, as "1/3"
	}
	
	@Override
	public String name() {
		return NAME;
	}
	
	public String randOperator() {
		List<String> opList = List.of("+", "-", "*", "/");
		String randOperator = opList.get(RNG.intInclusive(0, 3));
		return randOperator;
	}
	
	public String createExpression() {
		String equation = "";
		int difficulty = RNG.intInclusive(1, 5);
		ArrayList<String> equationTerms = new ArrayList<>(Collections.nCopies(difficulty * 2 + 1, ""));
		
		for(int i = 1; i < equationTerms.size(); i = i + 2)
			equationTerms.set(i, randOperator());
		
		for(int i = 0; i < equationTerms.size(); i = i + 2) {
			final int val;
			if(i > 0 && equationTerms.get(i - 1).equals("/")) //simple check to avoid dividing by zero. Not foolproof.
				val = generateNonZero();
			else
				val = RNG.intMaxDigits(maxDigits.value());
			equationTerms.set(i, String.valueOf(val));
		}
		
		if(difficulty > 1) {
			int first = RNG.intInclusive(0, equationTerms.size() - 3);
			while(first % 2 != 0)
				first = RNG.intInclusive(0, equationTerms.size() - 3);
			
			int second = RNG.intInclusive(first + 2, equationTerms.size());
			while(second % 2 == 0)
				second = RNG.intInclusive(first + 2, equationTerms.size());
			
			equationTerms.add(second, ")");
			equationTerms.add(first, "(");
		}
		
		equation = String.join("", equationTerms);
		
		return equation;
	}
	
	private int generateNonZero() {
		int val;
		do val = RNG.intMaxDigits(maxDigits.value());
		while(val == 0);
		return val;
	}
}
