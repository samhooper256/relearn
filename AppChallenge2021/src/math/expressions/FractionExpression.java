package math.expressions;

/**
 * 
 * @author Sam Hooper
 *
 */
public interface FractionExpression extends BinaryExpression {
	
	Expression numerator();
	
	Expression denominator();
	
	@Override
	default Expression first() {
		return numerator();
	}
	
	@Override
	default Expression second() {
		return denominator();
	}
	
}
