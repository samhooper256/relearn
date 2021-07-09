package math.expressions;

/**
 * 
 * @author Sam Hooper
 *
 */
public interface BinaryExpression extends ComplexValuedExpression {

	ComplexValuedExpression first();
	
	ComplexValuedExpression second();
	
}
