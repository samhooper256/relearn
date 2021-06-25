/**
 * 
 */
package fxutils;

import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * @author Sam Hooper
 *
 */
public class HoverExpandButton extends Button {
	
	private static final double DEFAULT_SCALE_FACTOR = 1.1;
	
	private final double scaleFactor;
	
	public HoverExpandButton() {
		this(DEFAULT_SCALE_FACTOR);
	}
	
	public HoverExpandButton(double scaleFactor) {
		super();
		this.scaleFactor = scaleFactor;
		initHover();
	}
	
	public HoverExpandButton(String text) {
		this(text, DEFAULT_SCALE_FACTOR);
	}
	
	public HoverExpandButton(String text, double scaleFactor) {
		super(text);
		this.scaleFactor = scaleFactor;
		initHover();
	}
	
	public HoverExpandButton(String text, Node graphic) {
		this(text, graphic, DEFAULT_SCALE_FACTOR);
	}
	
	public HoverExpandButton(String text, Node graphic, double scaleFactor) {
		super(text, graphic);
		this.scaleFactor = scaleFactor;
		initHover();
	}
	
	private void initHover() {
		this.setOnMouseEntered(e -> {
			setScaleX(scaleFactor);
			setScaleY(scaleFactor);
		});
		this.setOnMouseExited(e -> {
			setScaleX(1);
			setScaleY(1);
		});
	}
}
