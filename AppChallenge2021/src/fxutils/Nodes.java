/**
 * 
 */
package fxutils;

import java.util.Collection;

import javafx.scene.Node;

/**
 * @author Sam Hooper
 *
 */
public final class Nodes {
	
	private Nodes() {
		
	}
	
	public static void setOpacities(Collection<? extends Node> nodes, double opacity) {
		for(Node n : nodes)
			n.setOpacity(opacity);
	}
}
