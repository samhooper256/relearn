/**
 * 
 */
package topics;

import java.util.ArrayList;

import base.problems.*;
import topics.settings.*;
import utils.RNG;

/**
 * @author Ayuj Verma
 *
 */
public class MultiplyingIntegers extends AbstractTopic {

	private static final long serialVersionUID = -4007988835323001312L;
	
	public static final String NAME = "Multiplying Integers";
	public static final TopicFactory<MultiplyingIntegers> FACTORY = new TopicFactory<>(NAME, MultiplyingIntegers::new);
	
	private final IntRange number;
	private final IntSetting terms;
	
	public MultiplyingIntegers() {
		this(DEFAULT_COUNT);
	}
	
	public MultiplyingIntegers(int count) {
		super(count);
		number = new IntRange("Term Values", 0, 999, 1, 12);
		terms = new IntSetting("Term Count", 2, 3, 2);
		createSettings(number, terms);
	}
	
	@Override
	public Problem generate() {
		ArrayList<Integer> termList = termCreate();
		String expression = switch(termList.size()) {
			case 2 -> String.format("%d*%d", termList.get(0), termList.get(1));
			case 3 -> String.format("%d*%d*%d", termList.get(0), termList.get(1), termList.get(2));
			default -> throw new IllegalStateException();
		};
		return MathProblem.builder().set(this, expression, MathAnswerMode.REAL_DECIMAL).build();
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
