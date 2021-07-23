/**
 * 
 */
package fxutils;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

/**
 * @author Sam Hooper
 *
 */
public class HoverExpandButton extends Button {
	
	private static final double DEFAULT_SCALE_FACTOR = 1.1;
	private static final Duration DEFAULT_DURATION = Duration.millis(200);
	
	private final double scaleFactor;
	
	private final ScaleTransition scale;
	
	public HoverExpandButton(String text) {
		this(text, DEFAULT_SCALE_FACTOR);
	}
	
	public HoverExpandButton(String text, double scaleFactor) {
		this(text, scaleFactor, DEFAULT_DURATION);
	}
	public HoverExpandButton(String text, double scaleFactor, Duration scaleDuration) {
		super(text);
		this.scaleFactor = scaleFactor;
		scale = new ScaleTransition(scaleDuration, this);
		scale.setFromX(1);
		scale.setFromY(1);
		scale.setToX(scaleFactor);
		scale.setToY(scaleFactor);
		initHover();
	}
	
	private void initHover() {
		this.setOnMouseEntered(e -> {
			Animations.activate(scale);
		});
		this.setOnMouseExited(e -> {
			Animations.deactivate(scale);
		});
	}
	
	public double scaleFactor() {
		return scaleFactor;
	}
	
}
