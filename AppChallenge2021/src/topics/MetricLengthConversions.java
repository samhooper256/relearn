package topics;

import base.problems.Problem;
import math.units.MetricLength;

/**
 * @author Ayuj Verma
 *
 */
public class MetricLengthConversions extends AbstractTopic {
	
	private static final long serialVersionUID = -4560008811071000539L;
	
	public static final String NAME = "Metric Length Conversions";
	public static final TopicFactory<MetricLengthConversions> FACTORY = new TopicFactory<>(NAME, MetricLengthConversions::new);
	
	public MetricLengthConversions() {
		this(DEFAULT_COUNT);
	} 
	
	public MetricLengthConversions(int count) {
		super(count);
	}
	
	@Override
	public Problem generate() { 
		return TopicUtils.generateConversionProblem(this, MetricLength.values());
	}
	
	@Override
	public String name() {
		return NAME;
	} 
}