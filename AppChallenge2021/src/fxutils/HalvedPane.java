package fxutils;

import javafx.scene.Node;
import javafx.scene.layout.*;
import utils.Colls;

/**
 * 
 * @author Sam Hooper
 *
 */
public class HalvedPane extends GridPane {
	
	private final StackPane leftPane, rightPane;
	
	public HalvedPane() {
		leftPane = new StackPane();
		rightPane = new StackPane();
		add(leftPane, 0, 0);
		add(rightPane, 1, 0);
		
		ColumnConstraints c1 = new ColumnConstraints(), c2 = new ColumnConstraints();
		c1.setPercentWidth(50);
		c2.setPercentWidth(50);
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(100);
		getColumnConstraints().addAll(c1, c2);
		getRowConstraints().add(r);
	}
	
	public HalvedPane(Node left, Node right) {
		this();
		setLeft(left);
		setRight(right);
	}
	
	public Node left() {
		return Colls.get(leftPane.getChildren(), 0);
	}
	
	public Node right() {
		return Colls.get(rightPane.getChildren(), 0);
	}
	
	public void setLeft(Node node) {
		Colls.set(leftPane.getChildren(), 0, node);
	}
	
	public void setRight(Node node) {
		Colls.set(rightPane.getChildren(), 0, node);
	}
	
	public void setHalves(Node left, Node right) {
		setLeft(left);
		setRight(right);
	}
	
}
