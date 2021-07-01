package math.expressions;

/**
 * 
 * @author Sam Hooper
 *
 */
public interface SquareRootExpression extends ExponentiationExpression {

	@Override
	default Expression exponent() {
		return LiteralExpression.HALF;
	}

}
