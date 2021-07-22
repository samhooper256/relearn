/**
 * 
 */
package base.graphics;

import fxutils.*;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 * <p>{@link #visibleProperty() Visibility} is set to {@code false} by default.</p>
 * @author Sam Hooper
 *
 */
public final class TitleBox extends HBox {
	
	private static final double STARTING_TRANSLATE_Y = -20;
	private static final Duration CHAR_DURATION = Duration.millis(500);
	private static final Duration MOVE_DELAY = Duration.millis(200);
	private static final Duration CHAR_DELAY = CHAR_DURATION.divide(4);
	private static final String
			TITLE_BOX_CSS = "title-box",
			TITLE_CHARACTER_CSS = "title-character";
	
	private final String text;
	private final Timeline timeline;
	
	public TitleBox(String text) {
		this.setVisible(false);
		this.text = text;
		for(int i = 0; i < text.length(); i++) {
			Label l = new Label(String.valueOf(text.charAt(i)));
			l.getStyleClass().add(TITLE_CHARACTER_CSS);
			getChildren().addAll(l);
		}
		timeline = Animations.animationWithDelay(getChildren(), Node::opacityProperty, CHAR_DURATION, CHAR_DELAY, 0, 1);
		Animations.addAnimationWithDelay(timeline, getChildren(),
				Node::translateYProperty, MOVE_DELAY, CHAR_DURATION, CHAR_DELAY, STARTING_TRANSLATE_Y, 0, Interpolator.EASE_OUT);
		getStyleClass().add(TITLE_BOX_CSS);
	}
	
	public void animateIn() {
		Nodes.setOpacity(getChildren(), 0);
		Nodes.setTranslate(getChildren(), 0, STARTING_TRANSLATE_Y);
		this.setVisible(true);
		timeline.playFromStart();
	}
	
	public String text() {
		return text;
	}
	
}
