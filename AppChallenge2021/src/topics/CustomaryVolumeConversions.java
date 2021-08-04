package topics;

import base.problems.Problem;
import math.units.CustomaryVolume;

/**
 * @author Ayuj Verma
 *
 */
public class CustomaryVolumeConversions extends AbstractTopic {

	private static final long serialVersionUID = 6875051615506878151L;
	
	public static final String NAME = "Customary Volume Conversions";
	public static final TopicFactory<CustomaryVolumeConversions> FACTORY = new TopicFactory<>(NAME, CustomaryVolumeConversions::new);
	
	public CustomaryVolumeConversions() {
		this(DEFAULT_COUNT);
	} 
	
	public CustomaryVolumeConversions(int count) {
		super(count);
	}
	
	@Override
	public Problem generate() { 
		return TopicUtils.generateConversionProblem(this, CustomaryVolume.values());
	}
	
	@Override
	public String name() {
		return NAME;
	} 
}