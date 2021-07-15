package topics;

import base.problems.*;
import utils.RNG;

/**
 * 
 * @author Sam Hooper
 *
 */
public class SquareRoots extends AbstractTopic {

	private static final long serialVersionUID = -3068271291688727391L;
	
	public static final String NAME = "Square Roots";
	public static final TopicFactory<SquareRoots> FACTORY = new TopicFactory<>(NAME, SquareRoots::new);
	
	private final IntRange root;
	
	public SquareRoots() {
		this(DEFAULT_COUNT);
	}
	
	public SquareRoots(int count) {
		super(count);
		createSettings(root = new IntRange("Root Value", 1, 12));
	}

	@Override
	public Problem generate() {
		int rootValue = RNG.intInclusive(root);
		return 	MathProblem.builder()
				.set(this, String.format("sqrt(%s)", rootValue * rootValue), MathAnswerMode.REAL_DECIMAL)
				.build();
	}
	
	@Override
	public String name() {
		return NAME;
	}
	
}
