/**
 * 
 */
package fxutils;

import java.util.Collection;

import javafx.scene.*;
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
	
	public static void printChildren(Parent p) {
		System.out.printf("%s%n", p);
		printChildren(p, 1);
	}
	
	/** Assumes the {@code parent} has already been printed.*/
	private static void printChildren(Parent parent, int childTabCount) {
		String tabs = "\t".repeat(childTabCount);
		for(Node child : parent.getChildrenUnmodifiable()) {
			System.out.printf("%s%s%n", tabs, child);
			if(child instanceof Parent p)
				printChildren(p, childTabCount + 1);
		}
	}
}
