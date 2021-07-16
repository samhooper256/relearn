/**
 * 
 */
package topics;

import java.util.ArrayList;

import base.problems.*;
import topics.settings.IntSetting;
import utils.RNG;

/**
 * @author Sam Hooper and Ayuj Verma
 *
 */
public class SubtractingIntegers extends AbstractTopic {

	private static final long serialVersionUID = 5803139330572684391L;
	
	public static final String NAME = "Subtracting Integers";
	public static final TopicFactory<SubtractingIntegers> FACTORY = new TopicFactory<>(NAME, SubtractingIntegers::new);
	
	private final IntSetting maxDigits;
	private final IntSetting terms;
	
	public SubtractingIntegers() {
		this(DEFAULT_COUNT);	}
	
	public SubtractingIntegers(int count) {
		super(count);
		maxDigits = new IntSetting("Digits", 1, 4, 1);
		terms = new IntSetting("Terms", 2, 4, 2);
		createSettings(maxDigits, terms);
	}
	
	@Override
	public Problem generate() {
		ArrayList<Integer> termList = termCreate();
		String expression = switch(termList.size()) {
			case 2 -> String.format("%d-%d", termList.get(0), termList.get(1));
			case 3 -> String.format("%d-%d-%d", termList.get(0), termList.get(1), termList.get(2));
			case 4 -> String.format("%d-%d-%d-%d", termList.get(0), termList.get(1), termList.get(2), termList.get(3));
			default -> throw new IllegalStateException();
		};
		return 	MathProblem.builder()
				.set(this, expression, MathAnswerMode.REAL_DECIMAL)
				.build();
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
	
}
