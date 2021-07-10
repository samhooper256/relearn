/**
 * 
 */
package topics;

import java.util.ArrayList;

import base.problems.*;
import utils.RNG;

/**
 * @author Sam Hooper and Ayuj Verma
 *
 */
public class Multiplication extends AbstractTopic {

	private static final long serialVersionUID = -4007988835323001312L;
	
	public static final String NAME = "Multiplication";
	public static final TopicFactory<Multiplication> FACTORY = new TopicFactory<>(NAME, Multiplication::new);
	
	private final IntRange number;
	private final IntSetting terms;
	
	public Multiplication() {
		this(DEFAULT_COUNT);
	}
	
	public Multiplication(int count) {
		super(count);
		number = new IntRange("Term Values", 1, 12, 1, 12);
		terms = new IntSetting("Term Count", 2, 3, 2);
		createSettings(number, terms);
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
	
	public ArrayList<Integer> termCreate() {
		int numTerms = terms.value();
		ArrayList<Integer> termValues = new ArrayList<>();
		
		for(int i = 0; i < numTerms; i++)
		{
			termValues.add(RNG.intInclusive(number.low(), number.high()));
		}
		
		return termValues;
	}
	
}
