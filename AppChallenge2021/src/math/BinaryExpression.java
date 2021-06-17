/**
 * 
 */
package math;

/**
 * @author Sam Hooper
 *
 */
public interface BinaryExpression extends ConstantExpression {
	
	ConstantExpression left();
	
	ConstantExpression right();
	
}