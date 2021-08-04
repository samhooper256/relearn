package topics;

import base.problems.Problem;
import math.units.CustomaryLength;

/**
 * @author Ayuj Verma
 *
 */
public class CustomaryLengthConversions extends AbstractTopic {

	private static final long serialVersionUID = -5309265310903242555L;
	
	public static final String NAME = "Customary Length Conversions";
	public static final TopicFactory<CustomaryLengthConversions> FACTORY = new TopicFactory<>(NAME, CustomaryLengthConversions::new);
	
	public CustomaryLengthConversions() {
		this(DEFAULT_COUNT);
	} 
	
	public CustomaryLengthConversions(int count) {
		super(count);
	}
	
	@Override
	public Problem generate() { 
		return TopicUtils.generateConversionProblem(this, CustomaryLength.values());
	}
	
	@Override
	public String name() {
		return NAME;
	} 
}