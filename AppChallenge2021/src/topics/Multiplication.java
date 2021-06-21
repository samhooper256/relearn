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
public class Multiplication extends AbstractTopic {

	private static final long serialVersionUID = -4007988835323001312L;
	
	public static final String NAME = "Multiplication";
	public static final TopicFactory<Multiplication> FACTORY = new TopicFactory<>(NAME, Multiplication::new);
	
	private final IntSetting minNumber;
	private final IntSetting maxNumber;
	private final IntSetting terms;
	private final List<TopicSetting> settings;
	
	public Multiplication() {
		this(DEFAULT_COUNT);	}
	
	public Multiplication(int count) {
		super(count);
		minNumber = new IntSetting("Minimum Number", 0, 12, 1);
		maxNumber = new IntSetting("Maximum Number", 0, 12, 1);
		terms = new IntSetting("Terms", 2, 3, 2);
		settings = List.of(minNumber, maxNumber, terms);
	}
	
	@Override
	public Problem generate() {
		ArrayList<Integer> termList = termCreate();
		if(termList.size() == 2)
			return MathProblem.fromExpression(this, String.format("%d*%d", termList.get(0), termList.get(1)));
		if(termList.size() == 3)
			return MathProblem.fromExpression(this, String.format("%d*%d*%d", termList.get(0), termList.get(1), termList.get(2)));
		
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
			termValues.add(RNG.intInclusive(minNumber.value(), maxNumber.value()));
		}
		
		return termValues;
	}
	
}
