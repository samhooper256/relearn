/**
 * 
 */
package math;

/**
 * @author Sam Hooper
 *
 */
public interface UnaryExpression extends ConstantExpression {
	
	ConstantExpression operand();
	
}
