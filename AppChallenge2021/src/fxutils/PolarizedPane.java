package fxutils;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;

/**
 * <p>A {@link Pane} with two direct child nodes: one on the left, and one on the right. The two children
 * are centered on opposite ends of the {@code PolarizedPane}, as far apart from each other as possible. If the
 * children are large enough such that they overlap, the right child will be on top of the left.</p>
 * */
public class PolarizedPane extends StackPane {
	
	private final HBox leftLayer, rightLayer;
	
	public PolarizedPane(Node left, Node right) {
		leftLayer = new HBox(left);
		rightLayer = new HBox(right);
		init();
	}

	public PolarizedPane() {
		leftLayer = new HBox();
		rightLayer = new HBox();
		init();
	}
	
	private void init() {
		initLayers();
		getChildren().addAll(leftLayer, rightLayer);
	}
	
	private void initLayers() {
		initLeftLayer();
		initRightLayer();
	}
	
	private void initRightLayer() {
		rightLayer.setAlignment(Pos.CENTER_RIGHT);
		rightLayer.setPickOnBounds(false);
	}

	private void initLeftLayer() {
		leftLayer.setAlignment(Pos.CENTER_LEFT);
	}

	public Node left() {
		ObservableList<Node> leftChildren = leftLayer.getChildren();
		return leftChildren.isEmpty() ? null : leftChildren.get(0);
	}
	
	public Node right() {
		ObservableList<Node> rightChildren = rightLayer.getChildren();
		return rightChildren.isEmpty() ? null : rightChildren.get(0);
	}
	
	public void setLeft(Node left) {
		ObservableList<Node> leftChildren = leftLayer.getChildren();
		if(leftChildren.isEmpty())
			leftChildren.add(left);
		else
			leftChildren.set(0, left);
	}
	
	public void setRight(Node right) {
		ObservableList<Node> rightChildren = rightLayer.getChildren();
		if(rightChildren.isEmpty())
			rightChildren.add(right);
		else
			rightChildren.set(0, right);
	}
	
	public void setChildren(Node left, Node right) {
		setLeft(left);
		setRight(right);
	}
	
}
