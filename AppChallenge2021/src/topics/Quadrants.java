/**
 * 
 */
package topics;

import base.problems.*;
import math.Complex;
import utils.RNG;

/**
 * @author Sam Hooper and Ayuj Verma
 *
 */
public class Quadrants extends AbstractTopic {

	private static final long serialVersionUID = 7365832072957189055L;
	
	public static final String NAME = "Quadrants";
	public static final TopicFactory<Quadrants> FACTORY = new TopicFactory<>(NAME, Quadrants::new);
	
	private final IntRange coords;
	
	public Quadrants() {
		this(DEFAULT_COUNT);
	} 
	
	public Quadrants(int count) {
		super(count);
		coords = new IntRange("Coordinate Values", -12, 12);
		createSettings(coords);
	}
	
	@Override
	public Problem generate() 
	{
		int x = RNG.intInclusive(coords);
		int y = RNG.intInclusive(coords);
		while(x == 0)
		{
			x = RNG.intInclusive(coords);
		}
		while(y == 0)
		{
			y = RNG.intInclusive(coords);
		}
		
		String equation = String.format("What quadrant is (%d,%d) in?", x, y);
		int answer = 0;
		
		if(x > 0 && y > 0)
		{
			answer = 1;
		}
		else if(x < 0 && y > 0)
		{
			answer = 2;
		}
		else if(x < 0 && y < 0)
		{
			answer = 3;
		}
		else if(x > 0 && y < 0)
		{
			answer = 4;
		}
		MathProblem problem = MathProblem.ofTolerant(this, Statement.ofText(equation), Complex.of(answer));
		return problem;
		
	}
	
	@Override
	public String name() {
		return NAME;
	}
}
