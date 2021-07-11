/**
 * 
 */
package topics;

import java.util.ArrayList;
import java.util.Collections;

import base.problems.*;
import math.BigUtils;
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
		this(DEFAULT_COUNT);	} 
	
	public PEMDAS(int count) {
		super(count);
		maxDigits = new IntSetting("Digits", 1, 2, 1);
		createSettings(maxDigits);
	}
	
	@Override
	public Problem generate() {
		String expression = expressionCreate();
		return MathProblem.builder()
				.set(this, expression, BigUtils.HUNDREDTH, MathAnswerMode.REAL_DECIMAL, MathAnswerMode.REAL_FRACTION)
				.build();
		//The tolerance allows for something like "1÷3" to be typed exactly, as "1/3"
	}
	
	@Override
	public String name() {
		return NAME;
	}
	
	public String randOperator()
	{
		ArrayList<String> signList = new ArrayList<String>();
		signList.add("+");
		signList.add("-");
		signList.add("*");
		signList.add("/");
		String randSign = signList.get(RNG.intInclusive(0, 3));
		return randSign;
	}
	
	
	public String expressionCreate()
	{
		String equation = "";
		int difficulty = RNG.intInclusive(1, 5);
		ArrayList<String> equationTerms = new ArrayList<String>(Collections.nCopies(difficulty * 2 + 1, ""));
		
		for(int i = 1; i < equationTerms.size(); i = i + 2)
		{
			equationTerms.set(i, randOperator());
		}
		
		for(int i = 0; i < equationTerms.size(); i = i + 2)
		{
			final int val;
			if(i > 0 && equationTerms.get(i - 1).equals("/"))
				val = generateNonZero();
			else
				val = RNG.intMaxDigits(maxDigits.value());
			equationTerms.set(i, String.valueOf(val));
		}
		
		if(difficulty > 1)
		{
			int first = RNG.intInclusive(0, equationTerms.size() - 3);
			while(first % 2 != 0)
			{
				first = RNG.intInclusive(0, equationTerms.size() - 3);
			}
			
			int second = RNG.intInclusive(first + 2, equationTerms.size());
			while(second % 2 == 0)
			{
				second = RNG.intInclusive(first + 2, equationTerms.size());
			}
			
			equationTerms.add(second, ")");
			equationTerms.add(first, "(");
		}
		
		for(String str: equationTerms)
		{
			equation+= str;
		}
		 
		return equation;
	}
	
	private int generateNonZero() {
		int val;
		do {
			val = RNG.intMaxDigits(maxDigits.value());
		}
		while(val == 0);
		return val;
	}
}
