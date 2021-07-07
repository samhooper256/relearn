/**
 * 
 */
package fxutils;

import java.util.Collection;

import javafx.scene.Node;
import javafx.scene.shape.Shape;

/**
 * @author Sam Hooper
 *
 */
public final class Nodes {
	
	private Nodes() {
		
	}
	
	public static void setLayout(Shape s, double x, double y) {
		s.setLayoutX(x);
		s.setLayoutY(y);
	}
	
	public static void setTranslate(Shape s, double x, double y) {
		s.setLayoutX(x);
		s.setLayoutY(y);
	}
	
	public static void setOpacities(Collection<? extends Node> nodes, double opacity) {
		for(Node n : nodes)
			n.setOpacity(opacity);
	}
}
