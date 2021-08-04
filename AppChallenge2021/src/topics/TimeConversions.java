package topics;

import base.problems.Problem;
import math.units.Time;

/**
 * @author Ayuj Verma
 *
 */
public class TimeConversions extends AbstractTopic {

	private static final long serialVersionUID = -2260574303036042246L;
	
	public static final String NAME = "Time Conversions";
	public static final TopicFactory<TimeConversions> FACTORY = new TopicFactory<>(NAME, TimeConversions::new);
	
	public TimeConversions() {
		this(DEFAULT_COUNT);
	} 
	
	public TimeConversions(int count) {
		super(count);
	}
	
	@Override
	public Problem generate() { 
		return TopicUtils.generateConversionProblem(this, Time.values());
	}
	
	@Override
	public String name() {
		return NAME;
	} 
}