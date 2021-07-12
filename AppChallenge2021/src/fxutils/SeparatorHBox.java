package fxutils;

import java.util.function.Supplier;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * <p>An {@link HBox} where child nodes are separated by a separator node supplied by the
 * {@link #separatorFactory()}. Nodes should only be added and removed by calls to {@link #addChild(Node)},
 * {@link #removeChild(Node)}, and {@link #clearChildren()}, <em>not</em> by calls to {@link #getChildren()}.</p>
 * 
 * @author Sam Hooper
 *
 */
public class SeparatorHBox extends HBox {
	
	private final Supplier<Node> separatorFactory;
	
	public SeparatorHBox(Supplier<Node> separatorFactory) {
		this.separatorFactory = separatorFactory;
	}
	
	public void addChild(Node node) {
		ObservableList<Node> children = getChildren();
		if(!children.isEmpty())
			children.add(separatorFactory.get());
		children.add(node);
	}
	
	public void removeChild(Node node) {
		ObservableList<Node> children = getChildren();
		int index = children.indexOf(node);
		if(index == 0) {
			children.remove(0);
			if(!children.isEmpty())
				children.remove(0); //remove separator
		}
		else if(index > 0) {
			children.remove(index); //remove node
			children.remove(index - 1); //remove separator
		}
	}
	
	public void clearChildren() {
		getChildren().clear();
	}
	
	public Supplier<Node> separatorFactory() {
		return separatorFactory;
	}
}
