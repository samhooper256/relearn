/**
 * 
 */
package topics;

import java.util.ArrayList;
import java.util.List;

import base.*;
import utils.RNG;

/**
 * @author Sam Hooper and Ayuj Verma
 *
 */
public class Subtraction extends AbstractTopic {

	private static final long serialVersionUID = 5803139330572684391L;
	
	public static final String NAME = "Subtraction";
	public static final TopicFactory<Subtraction> FACTORY = new TopicFactory<>(NAME, Subtraction::new);
	
	private final IntSetting maxDigits;
	private final IntSetting terms;
	private final List<TopicSetting> settings;
	
	public Subtraction() {
		this(DEFAULT_COUNT);	}
	
	public Subtraction(int count) {
		super(count);
		maxDigits = new IntSetting("Digits", 1, 4, 1);
		terms = new IntSetting("Terms", 2, 4, 2);
		settings = List.of(maxDigits, terms);
	}
	
	@Override
	public Problem generate() {
		ArrayList<Integer> termList = termCreate();
		if(termList.size() == 2)
			return MathProblem.fromExpression(String.format("%d-%d", termList.get(0), termList.get(1)));
		if(termList.size() == 3)
			return MathProblem.fromExpression(String.format("%d-%d-%d", termList.get(0), termList.get(1), termList.get(2)));
		if(termList.size() == 4)
			return MathProblem.fromExpression(String.format("%d-%d-%d-%d", termList.get(0), termList.get(1), termList.get(2), termList.get(3)));
		throw new IllegalStateException();
	}
	
	@Override
	public String name() {
		return NAME;
	}

	@Override
	public List<TopicSetting> settings() {
		return settings;
	}
	
	public ArrayList<Integer> termCreate()
	{
		int numTerms = terms.value();
		ArrayList<Integer> termValues = new ArrayList<Integer>();
		
		for(int i = 0; i < numTerms; i++)
		{
			termValues.add(RNG.intMaxDigits(maxDigits.value()));
		}
		
		return termValues;
	}
	
}
