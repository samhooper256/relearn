/**
 * 
 */
package topics;

import java.util.ArrayList;

import base.*;
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
	private final IntSetting terms;
	
	public PEMDAS() {
		this(DEFAULT_COUNT);	}
	
	public PEMDAS(int count) {
		super(count);
		maxDigits = new IntSetting("Digits", 1, 2, 1);
		terms = new IntSetting("Terms", 2, 4, 2);
		createSettings(maxDigits, terms);
	}
	
	@Override
	public Problem generate() {
		ArrayList<Integer> termList = termCreate();
		
		if(termList.size() == 2)
			return MathProblem.fromExpression(this, String.format("%d" + randSign() + "%d", termList.get(0), termList.get(1)));
		if(termList.size() == 3)
			return MathProblem.fromExpression(this, String.format("%d" + randSign() + "%d" + randSign() + "%d", termList.get(0), termList.get(1), termList.get(2)));
		if(termList.size() == 4)
			return MathProblem.fromExpression(this, String.format("%d" + randSign() + "%d" + randSign() + "%d" + randSign() + "%d", termList.get(0), termList.get(1), termList.get(2), termList.get(3)));
		throw new IllegalStateException();
	}
	
	@Override
	public String name() {
		return NAME;
	}
	
	public ArrayList<Integer> termCreate() {
		int numTerms = terms.value();
		ArrayList<Integer> termValues = new ArrayList<Integer>();
		
		for(int i = 0; i < numTerms; i++)
		{
			termValues.add(RNG.intMaxDigits(maxDigits.value()));
		}
		
		return termValues;
	}
	
	public String randSign()
	{
		ArrayList<String> signList = new ArrayList<String>();
		signList.add("+");
		signList.add("-");
		signList.add("*");
		signList.add("/");
		String randSign = signList.get(RNG.intInclusive(0, 3));
		return randSign;
	}
	
}
